package santeclair.lunar.framework.aspect.processor.pre;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Cette annotation permet de marquer une classe comme un pre-traitement avant
 * l'�xecution de la m�thode. La classe ainsi annot�e doit �galement impl�menter
 * l'nterface {@link PreProcessorInterface}.<br>
 * <br>
 * L'annotation {@link PreProcessing} positionn�e sur la m�thode qui doit ex�cuter
 * les pr�-traitements d�finie soit un packageToScan dans lequel on doit trouver
 * les classes annot�es avec {@link PreProcessor} soit directement les classes dans
 * un tableau avec cette annotation. Cette annotation permet notamment de d�terminer
 * l'ordre dans lequel le pre-traitement doit �tre execut�.
 * 
 * @author cgillet
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PreProcessor {

    /**
     * Ordre d'�x�cution. Par d�faut -1 => l'ordre n'a pas d'importance
     * et ce process sera ex�cuter apr�s ceux ayant un ordre sup�rieur � z�ro.
     * En cas de chevauchement des ordres, l'ex�cution sera al�atoire.
     * 
     * @return L'ordre d'�x�cution.
     */
    int order() default 10000;

}
