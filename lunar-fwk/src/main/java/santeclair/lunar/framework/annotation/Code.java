package santeclair.lunar.framework.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permet d'indiquer la propri�t� associ�e � un champ ou une m�thode qui se traduit par un code. Un code d�finit de mani�re unique un
 * enregistrement en base de donn�es. Par opposition � la cl� primaire qui ne doit pas �tre porteuse d'information, le code qui est cl� candidate
 * peut porter des informations.
 * 
 * @author cgillet
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Code {

}
