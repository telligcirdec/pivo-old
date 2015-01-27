package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cette annotation permet de marquer un param�tre de m�thode comme devant
 * passer dans les pre-processeurs issu de l'annotation {@link PreProcessing} de
 * la m�thode.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PreProcess {

    /**
     * Le package � scanner contenant les classes de pre-traitement.
     * Ces classes doivent impl�ment�es l'interface {@link PreProcessorInterface} et �tre annot�es par
     * {@link PreProcessor}.
     * 
     * @return Le package � scanner.
     */
    String packageToScan() default "";

    /**
     * Le tableau des classes de pre-traitement.
     * Ces classes doivent impl�ment�es l'interface {@link PreProcessorInterface} et �tre annot�es par
     * {@link PreProcessor}.
     * 
     * @return Le tableau des classe de pr�-traitement.
     */
    Class<? extends PreProcessorInterface<?>>[] clazz() default {};

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
