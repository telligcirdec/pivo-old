package santeclair.lunar.framework.aspect.processor.post;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import santeclair.lunar.framework.aspect.processor.pre.PreProcessor;
import santeclair.lunar.framework.aspect.processor.pre.PreProcessorInterface;

/**
 * Cette annotation permet de déterminer si une méthode est concernée par un
 * traitement de post-processing qui consiste à travailler une donnée retour après
 * l'exécution de la méthode.
 * 
 * Si aucun packageToScan et clazz ne sont renseignés, le mécanisme de
 * post-pocessus cherchera les post-processor dans le contexte spring.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostProcessing {

    /**
     * Le package à scanner contenant les classes de pre-traitement.
     * Ces classes doivent implémentées l'interface {@link PreProcessorInterface} et être annotées par
     * {@link PreProcessor}.
     * 
     * @return Le package à scanner.
     */
    String packageToScan() default "";

    /**
     * Les class implémentant l'interface {@link PreProcessorInterface} et être annotées par {@link PreProcessor} qui
     * seront executé lors du post-traitement.
     * 
     * @return
     */
    Class<? extends PostProcessorInterface<?>>[] clazz() default {};

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
