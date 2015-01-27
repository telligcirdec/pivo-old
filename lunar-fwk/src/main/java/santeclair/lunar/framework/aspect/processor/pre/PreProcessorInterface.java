package santeclair.lunar.framework.aspect.processor.pre;

/**
 * Cette interface permet de définir une classe l'implémentant comme un
 * pre-traitement. Un pre-traitement exécute la méthode preProcess avant
 * l'execution d'une méthode afin de travailler un paramètre avant passage
 * dans la méthode.
 * 
 * @author cgillet
 * 
 * @param <T> Le type de l'objet à pre-traiter.
 */
public interface PreProcessorInterface<T> {

    /**
     * Cette méthode est exécutée avant le traitement
     * de la méthode annotée par {@link PreProcessing}.
     * 
     * @param param Le paramètre d'appel de la méthode qui doit
     *            faire l'objet d'un pre-traitement.
     */
    public void preProcess(T param);

}
