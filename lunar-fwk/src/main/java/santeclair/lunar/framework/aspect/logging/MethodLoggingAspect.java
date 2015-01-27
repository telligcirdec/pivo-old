/**
 * 
 */
package santeclair.lunar.framework.aspect.logging;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import santeclair.lunar.framework.annotation.MethodLogger;
import santeclair.lunar.framework.annotation.MethodLogger.Detail;
import santeclair.lunar.framework.annotation.MethodLogger.Level;
import santeclair.lunar.framework.annotation.MethodLogger.OutputType;

/**
 * Aspect permettant de logger le nom de toutes les méthodes par lesquelles on
 * passe dans le package web.view.
 * 
 * @author jfourmond
 * 
 */
@Aspect
@Component
public class MethodLoggingAspect {

    private Logger loggerMethodFromPackage = null;

    private static volatile Integer callingMethodId = Integer.valueOf(0);

    private static final Logger LOGGER_ASPECT_LOGGER = LoggerFactory
                    .getLogger("AspectLogger");

    private Logger logger = null;

    /**
     * Pointcut des méthodes ou des classes annotées avec l'annotation {@link santeclair.lunar.framework.annotation.MethodLogger}
     */
    @Pointcut("@annotation(santeclair.lunar.framework.annotation.MethodLogger)")
    public void methodWithMethodLoggerAnnotation() {
    }

    @Pointcut("execution(* santeclair..*.*(..))")
    public void methodFromPackage() {
    }

    /**
     * Log
     * 
     * @param proceedJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("methodWithMethodLoggerAnnotation()")
    public Object logMethodWithMethodLoggerAnnotation(
                    ProceedingJoinPoint proceedJoinPoint) throws Throwable {
        callingMethodId++;
        return createLog(proceedJoinPoint);
    }

    /**
     * Logge en niveau TRACE toutes les paramètres en entrée et résultats en
     * sortie des méthodes des beans Springs du projet devis dentaire.
     */
    @Around("methodFromPackage()")
    public Object logMethodFromPackage(final ProceedingJoinPoint joinPoint)
                    throws Throwable {

        loggerMethodFromPackage = getLogger(joinPoint);

        Object result = null;
        if (loggerMethodFromPackage.isTraceEnabled()) {
            Signature signature = joinPoint.getSignature();
            Object target = joinPoint.getTarget();
            String nomClasse = target.getClass().getSimpleName();
            String nomMethode = signature.getName();
            Object[] args = joinPoint.getArgs();
            loggerMethodFromPackage.trace("{}.{} DEBUT - args : {}",
                            new Object[]{nomClasse, nomMethode, args});
            result = joinPoint.proceed();
            loggerMethodFromPackage.trace("{}.{} FIN - result : {}",
                            new Object[]{nomClasse, nomMethode, result});
        } else {
            result = joinPoint.proceed();
        }
        return result;
    }

    private Object createLog(final ProceedingJoinPoint proceedJoinPoint)
                    throws Throwable {

        Class<?> returnType = null;

        if (proceedJoinPoint != null) {
            logger = getLogger(proceedJoinPoint);

            // Récupération des valeurs de l'annotation AspectLogger
            OutputType outputTypeFromAnnotation = null;
            Level levelFromAnnotation = null;
            Level levelErrorFromAnnotation = null;
            Detail detailFromAnnotation = null;
            StringBuffer longLogMessageBefore = new StringBuffer();
            StringBuffer shortLogMessageBefore = new StringBuffer();
            StringBuffer errorMessage = new StringBuffer();
            MethodSignature methodSignature = null;
            Method method = null;
            Method methodFromTarget = null;
            MethodLogger aspectLogger = null;
            StringBuffer methodName = new StringBuffer();
            Class<?>[] parameterTypes = null;
            Class<?>[] exceptionTypes = null;
            Integer methodCallId = Integer.valueOf(callingMethodId);
            Object[] args = null;

            // Récupération de la signature du point de jonction
            if (proceedJoinPoint.getSignature() instanceof MethodSignature) {
                methodSignature = (MethodSignature) proceedJoinPoint
                                .getSignature();
            }

            // Construction de la chaine contenant le nom de la méthode, ses
            // paramètres formels et ses exceptions si elles existent.
            if (methodSignature != null) {
                method = methodSignature.getMethod();
                try {
                    methodFromTarget = proceedJoinPoint.getTarget().getClass()
                                    .getDeclaredMethod(method.getName(),
                                                    method.getParameterTypes());
                } catch (NoSuchMethodException noSuchMethodException) {
                    methodFromTarget = proceedJoinPoint.getTarget().getClass()
                                    .getMethod(method.getName(),
                                                    method.getParameterTypes());
                }
                args = proceedJoinPoint.getArgs();
                parameterTypes = methodSignature.getParameterTypes();
                returnType = methodSignature.getReturnType();

                if (methodFromTarget != null) {
                    aspectLogger = methodFromTarget
                                    .getAnnotation(MethodLogger.class);
                    exceptionTypes = methodFromTarget.getExceptionTypes();

                    if (Detail.LONG.equals(aspectLogger.detail())) {
                        generateLongMethodName(methodName, method,
                                        parameterTypes, exceptionTypes);
                    }
                }
            }

            // Construction du log AVANT l'appel de la méthode.
            if (aspectLogger != null) {
                levelFromAnnotation = aspectLogger.messageLevel();
                levelErrorFromAnnotation = aspectLogger.errorLevel();
                outputTypeFromAnnotation = aspectLogger.outputType();
                detailFromAnnotation = aspectLogger.detail();

                if (isLogEnable(levelFromAnnotation)
                                && (OutputType.ARGS.equals(outputTypeFromAnnotation) || OutputType.ARGS_AND_RETURN
                                                .equals(outputTypeFromAnnotation))) {

                    if (Detail.LONG.equals(detailFromAnnotation)) {
                        longLogMessageBefore.append(
                                        "\nLog before calling method with id ").append(
                                        methodCallId).append(" : ").append(methodName);
                        if (StringUtils.isNotBlank(aspectLogger.message())) {
                            longLogMessageBefore.append("\nMessage : ").append(
                                            aspectLogger.message());
                        } else {
                            longLogMessageBefore.append("\nNo Message").append(
                                            aspectLogger.message());
                        }

                    } else if (Detail.SHORT.equals(detailFromAnnotation)) {
                        shortLogMessageBefore.append("Call method (id = ")
                                        .append(methodCallId).append(") : ").append(
                                                        method.getReturnType().getSimpleName())
                                        .append(" ").append(method.getName()).append(
                                                        "(");
                    }

                    errorMessage
                                    .append(
                                                    "Exception during logging by aspect for method (id = ")
                                    .append(methodCallId).append(") : ").append(
                                                    method.getReturnType().getSimpleName())
                                    .append(" ").append(method.getName()).append("(");

                    if (parameterTypes != null && args != null
                                    && parameterTypes.length == args.length
                                    && parameterTypes.length > 0) {

                        longLogMessageBefore
                                        .append("\nMethod Call Parameter : \n");
                        longLogMessageBefore.append("\t - args ").append(0)
                                        .append(" : type => ")
                                        .append(parameterTypes[0]).append(
                                                        " , value => ");
                        for (int i = 0; i < parameterTypes.length; i++) {
                            logObject(args[i], shortLogMessageBefore);
                            logObject(args[i], longLogMessageBefore);
                            logObject(args[i], errorMessage);
                            if (i < parameterTypes.length - 1) {
                                shortLogMessageBefore.append(", ");
                                errorMessage.append(", ");
                                longLogMessageBefore.append("\t - args ")
                                                .append(i + 1).append(" : type => ")
                                                .append(parameterTypes[i + 1]).append(
                                                                " , value => ");
                            }
                        }
                    }
                    shortLogMessageBefore.append(")");
                    errorMessage.append(")");
                    if (exceptionTypes != null && exceptionTypes.length > 0) {
                        shortLogMessageBefore.append(" throws ");
                        errorMessage.append(" throws ");
                        for (int i = 0; i < exceptionTypes.length; i++) {
                            shortLogMessageBefore.append(exceptionTypes[i]
                                            .getSimpleName());
                            errorMessage.append(exceptionTypes[i]
                                            .getSimpleName());
                            if (i < exceptionTypes.length - 1) {
                                shortLogMessageBefore.append(", ");
                                errorMessage.append(", ");
                            }
                        }
                    }
                }
            }
            // FIN Construction du log AVANT l'appel de la méthode.

            // Affiche le log précédent l'appel
            if (Detail.LONG.equals(detailFromAnnotation)) {
                log(longLogMessageBefore, levelFromAnnotation, null);
            } else if (Detail.SHORT.equals(detailFromAnnotation)) {
                log(shortLogMessageBefore, levelFromAnnotation, null);
            }

            Object retValue = null;

            try {
                // Appel de la méthode
                retValue = proceedJoinPoint.proceed();
            } catch (Throwable throwable) {
                // Construction du log APRES exception de la méthode.
                generateLogException(levelErrorFromAnnotation, errorMessage,
                                aspectLogger, throwable);
                throw throwable;
                // FIN Construction du log APRES exception de la méthode.
            }

            // Construction du log APRES resultat de la méthode.
            if (isLogEnable(levelFromAnnotation)
                            && (OutputType.ARGS_AND_RETURN
                                            .equals(outputTypeFromAnnotation) || OutputType.RETURN
                                            .equals(outputTypeFromAnnotation))) {

                if (Detail.LONG.equals(detailFromAnnotation)) {
                    StringBuffer longLogMessageAfter = new StringBuffer(
                                    "\nLog After calling method with id ").append(
                                    methodCallId).append(" : ").append(methodName)
                                    .append(" (").append(methodCallId).append(
                                                    ")\nReturn : \n\t - Type : ").append(
                                                    returnType).append("\n\t - Value : ");
                    logObject(retValue, longLogMessageAfter);
                    log(longLogMessageAfter, levelFromAnnotation, null);

                } else if (Detail.SHORT.equals(detailFromAnnotation)) {
                    StringBuffer shortLogMessageAfter = new StringBuffer(
                                    "Return (id = ").append(methodCallId)
                                    .append(") : ");
                    logObject(retValue, shortLogMessageAfter);
                    log(shortLogMessageAfter, levelFromAnnotation, null);
                }
            }
            // FIN - Construction du log APRES resultat de la méthode.
            return retValue;

        }
        return null;
    }

    private void logObject(Object object, StringBuffer logMessage) {
        if (object instanceof String) {
            logMessage.append("|");
        }
        // On limite l'affiche des listes aux 10 premiers éléments.
        if (object instanceof Collection<?>
                        && ((Collection<?>) object).size() > 10) {
            logMessage.append(new ArrayList<Object>((Collection<?>) object)
                            .subList(0, 10));
        } else {
            logMessage.append(object);
        }
        if (object instanceof String) {
            logMessage.append("|");
        }
    }

    private Logger getLogger(ProceedingJoinPoint proceedJoinPoint) {
        if (proceedJoinPoint.getThis() != null) {
            return LoggerFactory.getLogger(proceedJoinPoint.getTarget()
                            .getClass());
        }
        LOGGER_ASPECT_LOGGER
                        .warn("proceedJoinPoint.getThis() is null. Logger is enable for AspectLogger.class.");
        return LoggerFactory.getLogger(MethodLoggingAspect.class
                        .getSimpleName());
    }

    private void generateLogException(Level levelErrorFromAnnotation,
                    StringBuffer errorMessage, MethodLogger aspectLogger,
                    Throwable throwable) {
        if (isLogEnable(levelErrorFromAnnotation)) {
            errorMessage.append("\n\t Caused by : ");
            if (aspectLogger != null
                            && StringUtils.isNotBlank(aspectLogger.errorMessage())) {
                errorMessage.append("\n\t").append(aspectLogger.errorMessage());
            }
            log(errorMessage, levelErrorFromAnnotation, throwable);
        }
    }

    /**
     * Détermine si le nivean de log de l'annotation est en correspondance avec
     * celui de la classe.
     * 
     * @param level
     *            : Le niveau de Log de l'annotation.
     * @return <code>true</code> si le niveau de log de l'annotation est inclu
     *         dans le niveau de log de la classe ciblée, <code>false</code> sinon.
     */
    private boolean isLogEnable(Level level) {
        return (Level.DEBUG.equals(level) && logger.isDebugEnabled())
                        || ((Level.INFO.equals(level) || Level.MESSAGE.equals(level)) && logger
                                        .isInfoEnabled())
                        || (Level.ERROR.equals(level) && logger.isErrorEnabled())
                        || ((Level.WARN.equals(level) || Level.WARNING.equals(level)) && logger
                                        .isWarnEnabled());
    }

    /**
     * Génere le nom de la méthode appelée.
     * 
     * @param methodName
     *            : Le StringBuffer à compléter.
     * @param method
     *            : L'objet méthode.
     * @param parameterTypes
     *            : Le tableau des paramètres.
     * @param exceptionTypes
     *            : Le tableau des exceptions.
     */
    private void generateLongMethodName(StringBuffer methodName, Method method,
                    Class<?>[] parameterTypes, Class<?>[] exceptionTypes) {
        // Ajout du type retour et du nom de la méthode
        methodName.append(method.getReturnType().getSimpleName()).append(" ")
                        .append(method.getName()).append("(");

        // Ajout du type de chaque paramètre.
        for (int i = 0; i < parameterTypes.length; i++) {
            methodName.append(parameterTypes[i].getSimpleName());
            if (i < parameterTypes.length - 1) {
                methodName.append(", ");
            }
        }
        methodName.append(")");

        // Ajout du type de chaque exception
        if (exceptionTypes != null && exceptionTypes.length > 0) {
            methodName.append(" throws ");
            for (int i = 0; i < exceptionTypes.length; i++) {
                methodName.append(exceptionTypes[i].getSimpleName());
                if (i < exceptionTypes.length - 1) {
                    methodName.append(", ");
                }
            }
        }
    }

    private void log(final StringBuffer message, final Level level,
                    final Throwable throwable) {
        if (message.length() > 0 && level != null) {
            switch (level) {
            case DEBUG:
                logger.debug(message.toString(), throwable);
                break;
            case INFO:
            case MESSAGE:
                logger.info(message.toString(), throwable);
                break;
            case WARN:
            case WARNING:
                logger.warn(message.toString(), throwable);
                break;
            case ERROR:
                logger.error(message.toString(), throwable);
                break;
            default:
                LOGGER_ASPECT_LOGGER.error("Level " + level
                                + " is not expected here Possible values : "
                                + Level.values() + " .");
            }
        }
    }

}
