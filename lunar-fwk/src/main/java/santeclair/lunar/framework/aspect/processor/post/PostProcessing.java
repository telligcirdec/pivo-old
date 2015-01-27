package santeclair.lunar.framework.aspect.processor.post;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import santeclair.lunar.framework.aspect.processor.pre.PreProcessor;
import santeclair.lunar.framework.aspect.processor.pre.PreProcessorInterface;

/**
 * Cette annotation permet de d�terminer si une m�thode est concern�e par un
 * traitement de post-processing qui consiste � travailler une donn�e retour apr�s
 * l'ex�cution de la m�thode.
 * 
 * Si aucun packageToScan et clazz ne sont renseign�s, le m�canisme de
 * post-pocessus cherchera les post-processor dans le contexte spring.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostProcessing {

    /**
     * Le package � scanner contenant les classes de pre-traitement.
     * Ces classes doivent impl�ment�es l'interface {@link PreProcessorInterface} et �tre annot�es par
     * {@link PreProcessor}.
     * 
     * @return Le package � scanner.
     */
    String packageToScan() default "";

    /**
     * Les class impl�mentant l'interface {@link PreProcessorInterface} et �tre annot�es par {@link PreProcessor} qui
     * seront execut� lors du post-traitement.
     * 
     * @return
     */
    Class<? extends PostProcessorInterface<?>>[] clazz() default {};

    /**
     * Permet d'indiquer si la recherche par packageToScan et clazz doit se faire
     * dans un contexte Spring ou non.<br>
     * <br>
     * Si aucun packageToScan et class n'est indiqu�, alors la recherche se fera
     * sans aucune limite.<br>
     * <br>
     * Valeur par d�faut true.
     */
    boolean springContext() default true;
}
