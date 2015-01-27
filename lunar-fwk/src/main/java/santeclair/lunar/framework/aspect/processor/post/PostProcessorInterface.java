package santeclair.lunar.framework.aspect.processor.post;


/**
 * Cette interface permet de définir une classe l'implémentant comme un
 * post-traitement. Un post-traitement exécute la méthode postProcess avant
 * l'execution d'une méthode afin de travailler un paramètre avant passage
 * dans la méthode.
 * 
 * @author cgillet
 * 
 * @param <T> Le type de l'objet à post-traiter.
 */
public interface PostProcessorInterface<T> {

    /**
     * Cette méthode est exécutée avant le traitement
     * de la méthode annotée par {@link PostProcessing}.
     * 
     * @param retour Le paramètre d'appel de la méthode qui doit faire
     *            l'objet d'un post-traitement.
     */
    public void postProcess(T retour);

}
