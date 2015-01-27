package santeclair.lunar.framework.validator.multiemail;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = MultiEmailValidator.class)
@Documented
public @interface MultiEmail {

    String message() default "{santeclair.lunar.framework.validation.multiemail.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Caractere de séparation des email.
     * Virgule par défaut.
     * 
     * @return
     */
    String token() default ",";

    /**
     * A positionner à true pour vérifier si un email a été saisi en doublon.
     * Ainsi, la validation sera erronnée si un email en doublon est détecté.
     * Dans le cas ou les doublons sont acceptés, mettre a false.
     * True par défaut.
     * 
     * @return
     */
    boolean checkDouble() default true;

}
