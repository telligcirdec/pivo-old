package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.Annotation;
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
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
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
public class AspectPreProcess {

    @Autowired
    private ApplicationContext applicationContext;

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectPreProcess.class);

    /**
     * Cache des preProcessor par méthode ayant l'annotation {@link PreProcesing}
     */
    private static final Map<String, List<PreProcessorOrder>> preProcessorByMethodCache = new HashMap<String, List<PreProcessorOrder>>();

    private static final Map<String, List<PreProcessorOrder>> preProcessorByParamCache = new HashMap<String, List<PreProcessorOrder>>();

    @Pointcut("execution(* *(..))")
    public void anyMethod() {
    }

    @Before("anyMethod() && @annotation(preProcessing)")
    public void preProcess(JoinPoint joinPoint, PreProcessing preProcessing) throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
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
                LOGGER.debug("Méthode ayant l'annotation @PreProcessing trouvée : {}", methodFromTarget.getName());
                LOGGER.info("Récupération des arguments de la méthode porteuse de l'annotation {} " +
                                "et de leurs annotations potentielles respectives.", PreProcessing.class.getName());
                Annotation[][] annotationsByArg = methodFromTarget.getParameterAnnotations();
                findPreProcessParamAndExecute(joinPoint, preProcessing, methodFromTarget, annotationsByArg);
            }
        }
    }

    private void findPreProcessParamAndExecute(JoinPoint joinPoint, PreProcessing preProcessing, Method methodFromTarget, Annotation[][] annotationsByArg)
                    throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        for (int currentIndex = 0; currentIndex < annotationsByArg.length; currentIndex++) {
            LOGGER.debug("Parcours des annotations du paramètre numéro {}", currentIndex);
            Annotation[] annotations = annotationsByArg[currentIndex];
            findPreProcessParamAndExecute(joinPoint, preProcessing, methodFromTarget, annotations, currentIndex);
        }
    }

    private void findPreProcessParamAndExecute(JoinPoint joinPoint, PreProcessing preProcessing, Method methodFromTarget, Annotation[] annotations, Integer currentIndex)
                    throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        Class<?> targetClass = target.getClass();
        String targetClassName = targetClass.getName();
        String preProcessingName = PreProcessing.class.getName();
        String methodFromTargetName = methodFromTarget.getName();
        for (Annotation annotation : annotations) {
            LOGGER.debug("Annotation du paramètre numéro {} : {}", currentIndex, annotation.getClass().getCanonicalName());
            if (PreProcess.class.isAssignableFrom(annotation.getClass())) {
                PreProcess preProcess = (PreProcess) annotation;
                LOGGER.info("Annotation {} trouvée pour le paramètre numéro {}", preProcessingName, currentIndex);
                Object objetToPreProcess = args[currentIndex];
                LOGGER.debug("Objet à pre-traiter : {}", objetToPreProcess);
                String objetToPreProcessName = objetToPreProcess.getClass().getName();
                String preProcessorByParamCacheKey = targetClassName + "." + methodFromTargetName + "." + objetToPreProcessName + "[" + currentIndex + "]";
                LOGGER.info("Récupération des PreProcessor en cache pour la clé {}", preProcessorByParamCacheKey);
                List<PreProcessorOrder> preProcessorOrders = preProcessorByParamCache.get(preProcessorByParamCacheKey);
                if (preProcessorOrders == null) {
                    LOGGER.info("Aucune cache de PreProcessor trouvée pour la clé {}", preProcessorByParamCacheKey);
                    String preProcessorByMethodKey = targetClassName + "." + methodFromTargetName;
                    preProcessorOrders = getPreProcessorOrderForParam(preProcessorByMethodKey, preProcessing,
                                    objetToPreProcess, preProcessorByParamCacheKey, preProcess);
                }
                executePreProcessor(objetToPreProcess, preProcessorOrders);
            }
        }
    }

    private void executePreProcessor(Object objetToPreProcess, List<PreProcessorOrder> preProcessorOrders) throws IllegalArgumentException, InstantiationException,
                    IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        for (PreProcessorOrder preProcessorOrder : preProcessorOrders) {
            Class<? extends PreProcessorInterface<?>> preProcessorClazz = preProcessorOrder.getPreProcessorInterface();
            PreProcessorInterface<?> preProcessor = null;
            try {
                preProcessor = applicationContext.getBean(preProcessorClazz);
            } catch (Exception e) {
                Constructor<? extends PreProcessorInterface<?>> constructorPreProcessor = preProcessorClazz.getConstructor();
                preProcessor = constructorPreProcessor.newInstance();
            }
            Class<?> genericType = getPreProcessorGenericTypeClass(preProcessorClazz);
            Method preProcessMethod = preProcessorClazz.getMethod("preProcess", genericType);
            preProcessMethod.invoke(preProcessor, objetToPreProcess);
        }
    }

    private List<PreProcessorOrder> getPreProcessorOrderForParam(String preProcessorByMethodKey, PreProcessing preProcessing,
                    Object objetToPreProcess, String preProcessorByParamCacheKey, PreProcess preProcess) {
        List<PreProcessorOrder> preProcessorOrders = new ArrayList<PreProcessorOrder>();
        LOGGER.info("Récupération des classes ayant l'annotation @PreProcessor dans une cache en fonction de la clé {}. " +
                        "Si null, alors parcours des informations contenu dans l'annotation PreProcessing.", preProcessorByMethodKey);
        List<PreProcessorOrder> preProcessorInterfaces = preProcessorByMethodCache.get(preProcessorByMethodKey);
        if (preProcessorInterfaces == null) {
            preProcessorInterfaces = new ArrayList<PreProcessorOrder>();
            LOGGER.debug("Aucune cache trouvée pour la clé {}.", preProcessorByMethodKey);
            scanPreProcessorForMethod(preProcess, preProcessorInterfaces);
            LOGGER.debug("Stockage des PreProcessor de la méthode en cache sous la clé {}", preProcessorByMethodKey);
            preProcessorByMethodCache.put(preProcessorByMethodKey, preProcessorInterfaces);
        }

        LOGGER.info("Parcours des PreProcessor issus de la reflection réalisée sur la " +
                        "base de la configuration de l'annotation associée à la méthode");
        preProcessorOrders = linkAndCachePreProcessorWithMethodParam(objetToPreProcess,
                        preProcessorInterfaces, preProcessorByParamCacheKey);
        Collections.sort(preProcessorOrders);
        return preProcessorOrders;
    }

    private List<PreProcessorOrder> linkAndCachePreProcessorWithMethodParam(Object objetToPreProcess,
                    List<PreProcessorOrder> preProcessorInterfacesFromMethod,
                    String preProcessorByParamCacheKey) {
        List<PreProcessorOrder> preProcessorForMethodLinkedList = new ArrayList<PreProcessorOrder>();
        for (PreProcessorOrder preProcessorOrder : preProcessorInterfacesFromMethod) {
            LOGGER.debug("Parcours des preProcessOrder");
            Class<? extends PreProcessorInterface<?>> preProcessorInterface = preProcessorOrder.getPreProcessorInterface();
            Class<?> genericType = getPreProcessorGenericTypeClass(preProcessorInterface);
            if (genericType != null && genericType.isAssignableFrom(objetToPreProcess.getClass())) {
                preProcessorForMethodLinkedList.add(preProcessorOrder);
            }

        }
        preProcessorByParamCache.put(preProcessorByParamCacheKey, preProcessorForMethodLinkedList);
        return preProcessorForMethodLinkedList;
    }

    private Class<?> getPreProcessorGenericTypeClass(Class<? extends PreProcessorInterface<?>> preProcessorInterface) {
        Type[] types = preProcessorInterface.getGenericInterfaces();
        for (Type type : types) {
            if (ParameterizedType.class.isAssignableFrom(type.getClass())
                            && PreProcessorInterface.class.isAssignableFrom(preProcessorInterface)) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type[] parameterizedTypes = parameterizedType.getActualTypeArguments();
                if (parameterizedTypes != null && parameterizedTypes.length == 1
                                && parameterizedType.getRawType() instanceof Class<?>
                                && PreProcessorInterface.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                    return (Class<?>) parameterizedType.getActualTypeArguments()[0];
                }
            }
        }
        return null;
    }

    private void scanPreProcessorForMethod(PreProcess preProcessing, List<PreProcessorOrder> preProcessorInterfaces) {
        String packageToScan = preProcessing.packageToScan();
        Class<? extends PreProcessorInterface<?>>[] classToScan = preProcessing.clazz();
        boolean springContext = preProcessing.springContext();
        if (!springContext) {
            if (StringUtils.isNotBlank(packageToScan)) {
                LOGGER.debug("preProcessing packageToScan : {}", packageToScan);
                scanPreProcessorByPackage(packageToScan, preProcessorInterfaces);
            }

            scanPreProcessorByClazz(classToScan, preProcessorInterfaces);
        } else {
            LOGGER.debug("Récupération des postProcess dans l'application context.");
            if (StringUtils.isNotBlank(packageToScan)) {
                LOGGER.debug("postProcessing packageToScan : {}", packageToScan);
                scanPreProcessorByApplicationContext(packageToScan, preProcessorInterfaces);
            }
            scanPreProcessorByApplicationContext(classToScan, preProcessorInterfaces);
            if (StringUtils.isBlank(packageToScan) && classToScan.length == 0) {
                scanPreProcessorByApplicationContext(preProcessorInterfaces);
            }
        }
    }

    private void scanPreProcessorByApplicationContext(String packageToScan, List<PreProcessorOrder> preProcessorInterfaces) {
        @SuppressWarnings("rawtypes")
        Map<String, PreProcessorInterface> preProcessorByTypeMap = applicationContext.getBeansOfType(PreProcessorInterface.class);
        Set<String> preProcessorByTypeMapKeys = preProcessorByTypeMap.keySet();
        for (String preProcessorByTypeMapKey : preProcessorByTypeMapKeys) {
            PreProcessorInterface<?> preProcessorInterface = preProcessorByTypeMap.get(preProcessorByTypeMapKey);
            Class<?> clazz = preProcessorInterface.getClass();
            PreProcessor preProcessor = clazz.getAnnotation(PreProcessor.class);
            if (preProcessor != null) {
                if (clazz.getPackage().getName().contains(packageToScan)) {
                    Integer order = preProcessor.order();
                    @SuppressWarnings("unchecked")
                    PreProcessorOrder preProcessorOrder = new PreProcessorOrder(order, (Class<? extends PreProcessorInterface<?>>) clazz);
                    preProcessorInterfaces.add(preProcessorOrder);
                }
            }

        }

    }

    private void scanPreProcessorByApplicationContext(Class<? extends PreProcessorInterface<?>>[] classToScan, List<PreProcessorOrder> preProcessorInterfaces) {
        @SuppressWarnings("rawtypes")
        Map<String, PreProcessorInterface> preProcessorByTypeMap = applicationContext.getBeansOfType(PreProcessorInterface.class);
        Set<String> preProcessorByTypeMapKeys = preProcessorByTypeMap.keySet();
        for (String preProcessorByTypeMapKey : preProcessorByTypeMapKeys) {
            PreProcessorInterface<?> preProcessorInterface = preProcessorByTypeMap.get(preProcessorByTypeMapKey);
            Class<?> clazz = preProcessorInterface.getClass();
            PreProcessor preProcessor = clazz.getAnnotation(PreProcessor.class);
            if (preProcessor != null) {
                for (Class<? extends PreProcessorInterface<?>> scanedClass : classToScan) {
                    if (clazz.isAssignableFrom(scanedClass)) {
                        Integer order = preProcessor.order();
                        @SuppressWarnings("unchecked")
                        PreProcessorOrder preProcessorOrder = new PreProcessorOrder(order, (Class<? extends PreProcessorInterface<?>>) clazz);
                        preProcessorInterfaces.add(preProcessorOrder);
                        break;
                    }
                }
            }
        }
    }

    private void scanPreProcessorByApplicationContext(List<PreProcessorOrder> preProcessorInterfaces) {
        @SuppressWarnings("rawtypes")
        Map<String, PreProcessorInterface> preProcessorByTypeMap = applicationContext.getBeansOfType(PreProcessorInterface.class);
        Set<String> preProcessorByTypeMapKeys = preProcessorByTypeMap.keySet();
        for (String preProcessorByTypeMapKey : preProcessorByTypeMapKeys) {
            PreProcessorInterface<?> preProcessorInterface = preProcessorByTypeMap.get(preProcessorByTypeMapKey);
            Class<?> clazz = preProcessorInterface.getClass();
            PreProcessor preProcessor = clazz.getAnnotation(PreProcessor.class);
            if (preProcessor != null) {
                Integer order = preProcessor.order();
                @SuppressWarnings("unchecked")
                PreProcessorOrder preProcessorOrder = new PreProcessorOrder(order, (Class<? extends PreProcessorInterface<?>>) clazz);
                preProcessorInterfaces.add(preProcessorOrder);
            }

        }

    }

    private void scanPreProcessorByPackage(String packageToScan, List<PreProcessorOrder> preProcessorInterfaces) {

        LOGGER.info("Parcours du packageToScan {}", packageToScan);

        Set<URL> urlsClasspath = ClasspathHelper.forClassLoader(this.getClass().getClassLoader());
        urlsClasspath.addAll(ClasspathHelper.forJavaClassPath());

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(packageToScan)));
        configurationBuilder.setUrls(urlsClasspath);

        Reflections reflections = new Reflections(configurationBuilder);

        Set<Class<?>> classesAvecAnnotations = reflections.getTypesAnnotatedWith(PreProcessor.class);

        for (Class<?> classPreProcessorFromPackageToScan : classesAvecAnnotations) {
            LOGGER.info("Classe de preProcessing trouvée dans le package {} avec l'annotation {} : {}", packageToScan, PreProcessor.class.getName(),
                            classPreProcessorFromPackageToScan.getName());
            if (PreProcessorInterface.class.isAssignableFrom(classPreProcessorFromPackageToScan)) {
                LOGGER.info("Implémentation de l'inteface {} trouvée.", PreProcessorInterface.class.getName());

                LOGGER.info("Récupération de l'ordre du preProcessor dans l'annotation {}" + PreProcessor.class.getName());
                PreProcessor currentPreProcessor = classPreProcessorFromPackageToScan.getAnnotation(PreProcessor.class);
                int order = currentPreProcessor.order();
                LOGGER.debug("Ordre : {}", order);

                LOGGER.info("Instanciation de la classe interne contenant le preProcessor et son ordre");
                @SuppressWarnings("unchecked")
                PreProcessorOrder preProcessorOrder = new PreProcessorOrder(order, (Class<? extends PreProcessorInterface<?>>) classPreProcessorFromPackageToScan);

                LOGGER.info("Ajout de la classe trouvée à la liste de PreProcessor");
                preProcessorInterfaces.add(preProcessorOrder);
            } else {
                LOGGER.warn("Attention votre classe {} comporte bien l'annotation {} mais n'implemente pas l'interface {}",
                                classPreProcessorFromPackageToScan.getName(), PreProcessor.class.getName(), PreProcessorInterface.class.getName());
            }
        }

    }

    private void scanPreProcessorByClazz(Class<? extends PreProcessorInterface<?>>[] classToScan, List<PreProcessorOrder> preProcessorInterfaces) {

        for (Class<? extends PreProcessorInterface<?>> clazz : classToScan) {
            if (LOGGER.isInfoEnabled()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(clazz.getName());
                LOGGER.info("preProcessing clazz to scan : {}", stringBuilder.toString());
            }
            PreProcessor preProcessorClazzToScan = clazz.getAnnotation(PreProcessor.class);
            if (preProcessorClazzToScan != null) {
                LOGGER.info("Classe de pre-traitement {} trouvée.", clazz.getName());

                int order = preProcessorClazzToScan.order();
                LOGGER.debug("Ordre : {}", order);

                LOGGER.info("Instanciation de la classe interne contenant le preProcessor et son ordre");
                PreProcessorOrder preProcessorOrder = new PreProcessorOrder(order, clazz);

                LOGGER.info("Ajout de la classe trouvée à la liste de PreProcessor");
                preProcessorInterfaces.add(preProcessorOrder);
            } else {
                LOGGER.warn("La classe {} implémente bien l'interface {} mais ne possède pas l'annotation {}, " +
                                "elle est donc ignorée dans le pre-traitement.",
                                clazz.getName(), PreProcessorInterface.class.getName(), PreProcessor.class.getName());
            }
        }
    }
}

final class PreProcessorOrder implements Comparable<PreProcessorOrder> {

    private final Integer order;
    private final Class<? extends PreProcessorInterface<?>> preProcessorInterface;

    public PreProcessorOrder(Integer order, Class<? extends PreProcessorInterface<?>> preProcessorInterface) {
        this.order = order;
        this.preProcessorInterface = preProcessorInterface;
    }

    public int getOrder() {
        return order;
    }

    public Class<? extends PreProcessorInterface<?>> getPreProcessorInterface() {
        return preProcessorInterface;
    }

    public int compareTo(PreProcessorOrder preProcessorOrder) {
        return this.order.compareTo(preProcessorOrder.order);
    }

}
