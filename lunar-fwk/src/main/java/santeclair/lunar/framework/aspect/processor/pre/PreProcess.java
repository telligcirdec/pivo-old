package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cette annotation permet de marquer un paramètre de méthode comme devant
 * passer dans les pre-processeurs issu de l'annotation {@link PreProcessing} de
 * la méthode.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PreProcess {

    /**
     * Le package à scanner contenant les classes de pre-traitement.
     * Ces classes doivent implémentées l'interface {@link PreProcessorInterface} et être annotées par
     * {@link PreProcessor}.
     * 
     * @return Le package à scanner.
     */
    String packageToScan() default "";

    /**
     * Le tableau des classes de pre-traitement.
     * Ces classes doivent implémentées l'interface {@link PreProcessorInterface} et être annotées par
     * {@link PreProcessor}.
     * 
     * @return Le tableau des classe de pré-traitement.
     */
    Class<? extends PreProcessorInterface<?>>[] clazz() default {};

    /**
     * Permet d'indiquer si la recherche par packageToScan et clazz doit se faire
     * dans un contexte Spring ou non.<br>
     * <br>
     * Si aucun packageToScan et class n'est indiqué, alors la recherche se fera
     * sans aucune limite.<br>
     * <br>
     * Valeur par défaut true.
     */
    boolean springContext() default true;

}
