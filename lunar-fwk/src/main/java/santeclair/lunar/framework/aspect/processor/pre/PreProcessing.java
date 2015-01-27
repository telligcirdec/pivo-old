package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cette annotation permet de déterminer si une méthode est concernée par un
 * traitement de pre-processing qui consiste à travailler une donnée avant
 * l'exécution de la méthode. Les paramètres devant faire l'objet d'un
 * pre-traitement doivent être annotés par {@link PreProcess}.
 * 
 * Si aucun packageToScan et clazz ne sont renseignés, le mécanisme de
 * pre-pocessus cherchera les pre-processor dans le contexte spring.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PreProcessing {

}
