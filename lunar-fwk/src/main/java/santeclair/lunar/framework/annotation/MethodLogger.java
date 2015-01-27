package santeclair.lunar.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permet de logger les param�tres d'entr�es et/ou de sortie d'une m�thode par aspect.
 * 
 * Par d�faut les log sont param�tr�s comme suit : <br>
 * <br>
 * <ul>
 * <li>en version courte, par opposition � la version longue</li>
 * <li>en niveau de debug pour les messages informatifs</li>
 * <li>log l'entr�e et la sortie</li>
 * <li>en niveau erreur pour les messages d'erreur</li>
 * <li>une personalisation du message d'erreur vide</li>
 * <li>une personalisation du message informatif vide</li>
 * </ul>
 * 
 * @author cgillet
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MethodLogger {

    public enum Level {
        MESSAGE, WARNING, ERROR, WARN, INFO, DEBUG
    }

    public enum OutputType {
        ARGS, RETURN, ARGS_AND_RETURN, NONE
    }

    public enum Detail {
        LONG, SHORT
    }

    String message() default "";

    String errorMessage() default "";

    Level errorLevel() default Level.ERROR;

    Level messageLevel() default Level.DEBUG;

    OutputType outputType() default OutputType.ARGS_AND_RETURN;

    Detail detail() default Detail.SHORT;
}
