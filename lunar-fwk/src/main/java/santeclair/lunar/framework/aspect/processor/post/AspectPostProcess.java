package santeclair.lunar.framework.aspect.processor.post;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectPostProcess {

    @Autowired
    private ApplicationContext applicationContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectPostProcess.class);

    /**
     * Cache des postProcessor par méthode ayant l'annotation {@link PostProcesing}
     */
    private static final Map<String, List<PostProcessorOrder>> postProcessorByMethodCache = new HashMap<String, List<PostProcessorOrder>>();

    private static final Map<String, List<PostProcessorOrder>> postProcessorByReturnCache = new HashMap<String, List<PostProcessorOrder>>();

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
    }

    @AfterReturning(pointcut = "anyMethod() && @annotation(postProcessing)",
                    returning = "retVal")
    @AfterThrowing()
    public void postProcess(JoinPoint joinPoint, PostProcessing postProcessing, Object retVal) throws SecurityException, NoSuchMethodException, IllegalArgumentException,
                    InstantiationException,
                    IllegalAccessException, InvocationTargetException {

        MethodSignature methodSignature = null;
        Method method = null;
        Method methodFromTarget = null;

        Signature signature = joinPoint.getSignature();
        Object target = joinPoint.getTarget();
        Class<?> targetClass = target.getClass();

        LOGGER.info("Récupération de la signature de la méthode");
        if (signature != null && MethodSignature.class.isAssignableFrom(signature.getClass())) {
            methodSignature = (MethodSignature) signature;
            method = methodSignature.getMethod();
            try {
                methodFromTarget = targetClass.getDeclaredMethod(method.getName(),
                                method.getParameterTypes());
            } catch (NoSuchMethodException noSuchMethodException) {
                methodFromTarget = targetClass.getMethod(method.getName(),
                                method.getParameterTypes());
            }
            if (methodFromTarget != null) {
                LOGGER.debug("Méthode ayant l'annotation @PostProcessing trouvée : {}", methodFromTarget.getName());
                LOGGER.info("Récupération des arguments de la méthode porteuse de l'annotation {} " +
                                "et de leurs annotations potentielles respectives.", PostProcessing.class.getName());
                findPostProcessReturnAndExecute(joinPoint, postProcessing, retVal, methodFromTarget);
            }
        }
    }

    private void findPostProcessReturnAndExecute(JoinPoint joinPoint, PostProcessing postProcessing, Object retVal, Method methodFromTarget)
                    throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        Object target = joinPoint.getTarget();
        Class<?> targetClass = target.getClass();
        String targetClassName = targetClass.getName();
        String methodFromTargetName = methodFromTarget.getName();

        LOGGER.debug("Objet à post-traiter : {}", retVal);
        String objetToPostProcessName = retVal.getClass().getName();
        String postProcessorByParamCacheKey = targetClassName + "." + methodFromTargetName + "." + objetToPostProcessName;
        LOGGER.info("Récupération des PostProcessor en cache pour la clé {}", postProcessorByParamCacheKey);
        List<PostProcessorOrder> postProcessorOrders = postProcessorByReturnCache.get(postProcessorByParamCacheKey);
        if (postProcessorOrders == null) {
            LOGGER.info("Aucune cache de PostProcessor trouvée pour la clé {}", postProcessorByParamCacheKey);
            String postProcessorByMethodKey = targetClassName + "." + methodFromTargetName;
            postProcessorOrders = getPostProcessorOrderForReturn(postProcessorByMethodKey, postProcessing,
                            retVal, postProcessorByParamCacheKey);
        }
        executePostProcessor(retVal, postProcessorOrders);

    }

    private void executePostProcessor(Object retVal, List<PostProcessorOrder> postProcessorOrders) throws IllegalArgumentException, InstantiationException,
                    IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        for (PostProcessorOrder postProcessorOrder : postProcessorOrders) {
            Class<? extends PostProcessorInterface<?>> postProcessorClazz = postProcessorOrder.getPostProcessorInterface();
            PostProcessorInterface<?> postProcessor = null;
            try {
                postProcessor = applicationContext.getBean(postProcessorClazz);
            } catch (Exception e) {
                Constructor<? extends PostProcessorInterface<?>> constructorPostProcessor = postProcessorClazz.getConstructor();
                postProcessor = constructorPostProcessor.newInstance();
            }
            Class<?> genericType = getPostProcessorGenericTypeClass(postProcessorClazz);
            Method postProcessMethod = postProcessorClazz.getMethod("postProcess", genericType);
            postProcessMethod.invoke(postProcessor, retVal);
        }
    }

    private List<PostProcessorOrder> getPostProcessorOrderForReturn(String postProcessorByMethodKey, PostProcessing postProcessing,
                    Object retVal, String postProcessorByParamCacheKey) {
        List<PostProcessorOrder> postProcessorOrders = new ArrayList<PostProcessorOrder>();
        LOGGER.info("Récupération des classes ayant l'annotation @PostProcessor dans une cache en fonction de la clé {}. " +
                        "Si null, alors parcours des informations contenu dans l'annotation PostProcessing.", postProcessorByMethodKey);
        List<PostProcessorOrder> postProcessorInterfaces = postProcessorByMethodCache.get(postProcessorByMethodKey);
        if (postProcessorInterfaces == null) {
            postProcessorInterfaces = new ArrayList<PostProcessorOrder>();
            LOGGER.debug("Aucune cache trouvée pour la clé {}.", postProcessorByMethodKey);
            scanPostProcessorForMethod(postProcessing, postProcessorInterfaces);
            LOGGER.debug("Stockage des PostProcessor de la méthode en cache sous la clé {}", postProcessorByMethodKey);
            postProcessorByMethodCache.put(postProcessorByMethodKey, postProcessorInterfaces);
        }

        LOGGER.info("Parcours des PostProcessor issus de la reflection réalisée sur la " +
                        "base de la configuration de l'annotation associée à la méthode");
        postProcessorOrders = linkAndCachePostProcessorWithReturn(retVal,
                        postProcessorInterfaces, postProcessorByParamCacheKey);
        Collections.sort(postProcessorOrders);
        return postProcessorOrders;
    }

    private List<PostProcessorOrder> linkAndCachePostProcessorWithReturn(Object retVal,
                    List<PostProcessorOrder> postProcessorInterfacesFromMethod,
                    String postProcessorByParamCacheKey) {
        List<PostProcessorOrder> postProcessorForMethodLinkedList = new ArrayList<PostProcessorOrder>();
        for (PostProcessorOrder postProcessorOrder : postProcessorInterfacesFromMethod) {
            LOGGER.debug("Parcours des postProcessOrder");
            Class<? extends PostProcessorInterface<?>> postProcessorInterface = postProcessorOrder.getPostProcessorInterface();
            Class<?> genericType = getPostProcessorGenericTypeClass(postProcessorInterface);

            if (genericType != null && genericType.isAssignableFrom(retVal.getClass())) {
                postProcessorForMethodLinkedList.add(postProcessorOrder);
            }

        }
        postProcessorByReturnCache.put(postProcessorByParamCacheKey, postProcessorForMethodLinkedList);
        return postProcessorForMethodLinkedList;
    }

    private Class<?> getPostProcessorGenericTypeClass(Class<? extends PostProcessorInterface<?>> postProcessorInterface) {
        Type[] types = postProcessorInterface.getGenericInterfaces();

        for (Type type : types) {
            if (ParameterizedType.class.isAssignableFrom(type.getClass())
                            && PostProcessorInterface.class.isAssignableFrom(postProcessorInterface)) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] parameterizedTypes = parameterizedType.getActualTypeArguments();
                if (parameterizedTypes != null && parameterizedTypes.length == 1
                                && parameterizedType.getRawType() instanceof Class<?>
                                && PostProcessorInterface.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        }

        return null;
    }

    private void scanPostProcessorForMethod(PostProcessing postProcessing, List<PostProcessorOrder> postProcessorInterfaces) {
        String packageToScan = postProcessing.packageToScan();
        Class<? extends PostProcessorInterface<?>>[] classToScan = postProcessing.clazz();
        boolean springContext = postProcessing.springContext();
        if (!springContext) {
            if (StringUtils.isNotBlank(packageToScan)) {
                LOGGER.debug("postProcessing packageToScan : {}", packageToScan);
                scanPostProcessorByPackage(packageToScan, postProcessorInterfaces);
            }

            scanPostProcessorByClazz(classToScan, postProcessorInterfaces);
        } else {
            LOGGER.debug("Récupération des postProcess dans l'application context.");
            if (StringUtils.isNotBlank(packageToScan)) {
                LOGGER.debug("postProcessing packageToScan : {}", packageToScan);
                scanPostProcessorByApplicationContext(packageToScan, postProcessorInterfaces);
            }
            scanPostProcessorByApplicationContext(classToScan, postProcessorInterfaces);
            if (StringUtils.isBlank(packageToScan) && classToScan.length == 0) {
                scanPostProcessorByApplicationContext(postProcessorInterfaces);
            }
        }
    }

    private void scanPostProcessorByApplicationContext(String packageToScan, List<PostProcessorOrder> postProcessorInterfaces) {
        @SuppressWarnings("rawtypes")
        Map<String, PostProcessorInterface> postProcessorByTypeMap = applicationContext.getBeansOfType(PostProcessorInterface.class);
        Set<String> postProcessorByTypeMapKeys = postProcessorByTypeMap.keySet();
        for (String postProcessorByTypeMapKey : postProcessorByTypeMapKeys) {
            PostProcessorInterface<?> postProcessorInterface = postProcessorByTypeMap.get(postProcessorByTypeMapKey);
            Class<?> clazz = postProcessorInterface.getClass();
            PostProcessor postProcessor = clazz.getAnnotation(PostProcessor.class);
            if (postProcessor != null) {
                if (clazz.getPackage().getName().contains(packageToScan)) {
                    Integer order = postProcessor.order();
                    @SuppressWarnings("unchecked")
                    PostProcessorOrder postProcessorOrder = new PostProcessorOrder(order, (Class<? extends PostProcessorInterface<?>>) clazz);
                    postProcessorInterfaces.add(postProcessorOrder);
                }
            }
        }
    }

    private void scanPostProcessorByApplicationContext(Class<? extends PostProcessorInterface<?>>[] classToScan, List<PostProcessorOrder> postProcessorInterfaces) {
        @SuppressWarnings("rawtypes")
        Map<String, PostProcessorInterface> postProcessorByTypeMap = applicationContext.getBeansOfType(PostProcessorInterface.class);
        Set<String> postProcessorByTypeMapKeys = postProcessorByTypeMap.keySet();
        for (String postProcessorByTypeMapKey : postProcessorByTypeMapKeys) {
            PostProcessorInterface<?> postProcessorInterface = postProcessorByTypeMap.get(postProcessorByTypeMapKey);
            Class<?> clazz = postProcessorInterface.getClass();
            PostProcessor postProcessor = clazz.getAnnotation(PostProcessor.class);
            if (postProcessor != null) {
                for (Class<? extends PostProcessorInterface<?>> scanedClass : classToScan) {
                    if (clazz.isAssignableFrom(scanedClass)) {
                        Integer order = postProcessor.order();
                        @SuppressWarnings("unchecked")
                        PostProcessorOrder postProcessorOrder = new PostProcessorOrder(order, (Class<? extends PostProcessorInterface<?>>) clazz);
                        postProcessorInterfaces.add(postProcessorOrder);
                        break;
                    }
                }
            }
        }

    }

    private void scanPostProcessorByApplicationContext(List<PostProcessorOrder> postProcessorInterfaces) {
        @SuppressWarnings("rawtypes")
        Map<String, PostProcessorInterface> postProcessorByTypeMap = applicationContext.getBeansOfType(PostProcessorInterface.class);
        Set<String> postProcessorByTypeMapKeys = postProcessorByTypeMap.keySet();
        for (String postProcessorByTypeMapKey : postProcessorByTypeMapKeys) {
            PostProcessorInterface<?> postProcessorInterface = postProcessorByTypeMap.get(postProcessorByTypeMapKey);
            Class<?> clazz = postProcessorInterface.getClass();
            PostProcessor postProcessor = clazz.getAnnotation(PostProcessor.class);
            if (postProcessor != null) {
                Integer order = postProcessor.order();
                @SuppressWarnings("unchecked")
                PostProcessorOrder postProcessorOrder = new PostProcessorOrder(order, (Class<? extends PostProcessorInterface<?>>) clazz);
                postProcessorInterfaces.add(postProcessorOrder);
            }

        }

    }

    private void scanPostProcessorByPackage(String packageToScan, List<PostProcessorOrder> postProcessorInterfaces) {

        LOGGER.info("Parcours du packageToScan {}", packageToScan);
        Set<URL> urlsClasspath = ClasspathHelper.forClassLoader(this.getClass().getClassLoader());
        urlsClasspath.addAll(ClasspathHelper.forJavaClassPath());

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(packageToScan)));
        configurationBuilder.setUrls(urlsClasspath);

        Reflections reflections = new Reflections(configurationBuilder);

        Set<Class<?>> classesAvecAnnotations = reflections.getTypesAnnotatedWith(PostProcessor.class);

        for (Class<?> classPostProcessorFromPackageToScan : classesAvecAnnotations) {
            LOGGER.info("Classe de postProcessing trouvée dans le package {} avec l'annotation {} : {}", packageToScan, PostProcessor.class.getName(),
                            classPostProcessorFromPackageToScan.getName());
            if (PostProcessorInterface.class.isAssignableFrom(classPostProcessorFromPackageToScan)) {
                LOGGER.info("Implémentation de l'inteface {} trouvée.", PostProcessorInterface.class.getName());

                LOGGER.info("Récupération de l'ordre du postProcessor dans l'annotation {}" + PostProcessor.class.getName());
                PostProcessor currentPostProcessor = classPostProcessorFromPackageToScan.getAnnotation(PostProcessor.class);
                int order = currentPostProcessor.order();
                LOGGER.debug("Ordre : {}", order);

                LOGGER.info("Instanciation de la classe interne contenant le postProcessor et son ordre");
                @SuppressWarnings("unchecked")
                PostProcessorOrder postProcessorOrder = new PostProcessorOrder(order, (Class<? extends PostProcessorInterface<?>>) classPostProcessorFromPackageToScan);

                LOGGER.info("Ajout de la classe trouvée à la liste de PostProcessor");
                postProcessorInterfaces.add(postProcessorOrder);
            } else {
                LOGGER.warn("Attention votre classe {} comporte bien l'annotation {} mais n'implemente pas l'interface {}",
                                classPostProcessorFromPackageToScan.getName(), PostProcessor.class.getName(), PostProcessorInterface.class.getName());
            }
        }

    }

    private void scanPostProcessorByClazz(Class<? extends PostProcessorInterface<?>>[] classToScan, List<PostProcessorOrder> postProcessorInterfaces) {

        for (Class<? extends PostProcessorInterface<?>> clazz : classToScan) {
            if (LOGGER.isInfoEnabled()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(clazz.getName());
                LOGGER.info("postProcessing clazz to scan : {}", stringBuilder.toString());
            }
            PostProcessor postProcessorClazzToScan = clazz.getAnnotation(PostProcessor.class);
            if (postProcessorClazzToScan != null) {
                LOGGER.info("Classe de post-traitement {} trouvée.", clazz.getName());

                int order = postProcessorClazzToScan.order();
                LOGGER.debug("Ordre : {}", order);

                LOGGER.info("Instanciation de la classe interne contenant le postProcessor et son ordre");
                PostProcessorOrder postProcessorOrder = new PostProcessorOrder(order, clazz);

                LOGGER.info("Ajout de la classe trouvée à la liste de PostProcessor");
                postProcessorInterfaces.add(postProcessorOrder);
            } else {
                LOGGER.warn("La classe {} implémente bien l'interface {} mais ne possède pas l'annotation {}, " +
                                "elle est donc ignorée dans le post-traitement.",
                                clazz.getName(), PostProcessorInterface.class.getName(), PostProcessor.class.getName());
            }
        }
    }
}

final class PostProcessorOrder implements Comparable<PostProcessorOrder> {

    private final Integer order;
    private final Class<? extends PostProcessorInterface<?>> postProcessorInterface;

    public PostProcessorOrder(Integer order, Class<? extends PostProcessorInterface<?>> postProcessorInterface) {
        this.order = order;
        this.postProcessorInterface = postProcessorInterface;
    }

    public int getOrder() {
        return order;
    }

    public Class<? extends PostProcessorInterface<?>> getPostProcessorInterface() {
        return postProcessorInterface;
    }

    public int compareTo(PostProcessorOrder postProcessorOrder) {
        return this.order.compareTo(postProcessorOrder.order);
    }

}
