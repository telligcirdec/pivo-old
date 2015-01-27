package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cette annotation permet de marquer une classe comme un pre-traitement avant
 * l'éxecution de la méthode. La classe ainsi annotée doit également implémenter
 * l'nterface {@link PreProcessorInterface}.<br>
 * <br>
 * L'annotation {@link PreProcessing} positionnée sur la méthode qui doit exécuter
 * les pré-traitements définie soit un packageToScan dans lequel on doit trouver
 * les classes annotées avec {@link PreProcessor} soit directement les classes dans
 * un tableau avec cette annotation. Cette annotation permet notamment de déterminer
 * l'ordre dans lequel le pre-traitement doit être executé.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PreProcessor {

    /**
     * Ordre d'éxécution. Par défaut -1 => l'ordre n'a pas d'importance
     * et ce process sera exécuter après ceux ayant un ordre supérieur à zéro.
     * En cas de chevauchement des ordres, l'exécution sera aléatoire.
     * 
     * @return L'ordre d'éxécution.
     */
    int order() default 10000;

}
