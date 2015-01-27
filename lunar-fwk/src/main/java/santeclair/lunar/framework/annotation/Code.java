package santeclair.lunar.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permet d'indiquer la propriété associée à un champ ou une méthode qui se traduit par un code. Un code définit de manière unique un
 * enregistrement en base de données. Par opposition à la clé primaire qui ne doit pas être porteuse d'information, le code qui est clé candidate
 * peut porter des informations.
 * 
 * @author cgillet
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Code {

}
