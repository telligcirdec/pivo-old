package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cette annotation permet de d�terminer si une m�thode est concern�e par un
 * traitement de pre-processing qui consiste � travailler une donn�e avant
 * l'ex�cution de la m�thode. Les param�tres devant faire l'objet d'un
 * pre-traitement doivent �tre annot�s par {@link PreProcess}.
 * 
 * Si aucun packageToScan et clazz ne sont renseign�s, le m�canisme de
 * pre-pocessus cherchera les pre-processor dans le contexte spring.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreProcessing {

}
