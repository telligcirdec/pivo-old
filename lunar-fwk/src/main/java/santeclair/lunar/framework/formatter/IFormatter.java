package santeclair.lunar.framework.formatter;

/**
 * Cette interface permet de définir des classes qui ont vocation à formatter un object.
 * 
 * @author cgillet
 * 
 * @param <CLASSTOFORMAT>
 *            Classe de l'objet à formatter.
 * @param <CLASSFORMATTED>
 *            Classe de l'objet une fois formatter.
 */
public interface IFormatter<CLASSTOFORMAT, CLASSFORMATTED> {

	/**
	 * Cette méthode formatte l'objet passé en paramètre.
	 * 
	 * @param toFormat
	 *            Objet à formatter.
	 * @return Objet formatté.
	 */
	public CLASSFORMATTED format(CLASSTOFORMAT toFormat);

}
