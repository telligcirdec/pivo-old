package santeclair.lunar.framework.aspect.processor.post;


/**
 * Cette interface permet de d�finir une classe l'impl�mentant comme un
 * post-traitement. Un post-traitement ex�cute la m�thode postProcess avant
 * l'execution d'une m�thode afin de travailler un param�tre avant passage
 * dans la m�thode.
 * 
 * @author cgillet
 * 
 * @param <T> Le type de l'objet � post-traiter.
 */
public interface PostProcessorInterface<T> {

    /**
     * Cette m�thode est ex�cut�e avant le traitement
     * de la m�thode annot�e par {@link PostProcessing}.
     * 
     * @param retour Le param�tre d'appel de la m�thode qui doit faire
     *            l'objet d'un post-traitement.
     */
    public void postProcess(T retour);

}
