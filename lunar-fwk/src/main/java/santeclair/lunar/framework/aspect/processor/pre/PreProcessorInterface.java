package santeclair.lunar.framework.aspect.processor.pre;

/**
 * Cette interface permet de d�finir une classe l'impl�mentant comme un
 * pre-traitement. Un pre-traitement ex�cute la m�thode preProcess avant
 * l'execution d'une m�thode afin de travailler un param�tre avant passage
 * dans la m�thode.
 * 
 * @author cgillet
 * 
 * @param <T> Le type de l'objet � pre-traiter.
 */
public interface PreProcessorInterface<T> {

    /**
     * Cette m�thode est ex�cut�e avant le traitement
     * de la m�thode annot�e par {@link PreProcessing}.
     * 
     * @param param Le param�tre d'appel de la m�thode qui doit
     *            faire l'objet d'un pre-traitement.
     */
    public void preProcess(T param);

}
