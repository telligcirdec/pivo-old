package santeclair.lunar.framework.formatter;

/**
 * Cette interface permet de d�finir des classes qui ont vocation � formatter un object.
 * 
 * @author cgillet
 * 
 * @param <CLASSTOFORMAT>
 *            Classe de l'objet � formatter.
 * @param <CLASSFORMATTED>
 *            Classe de l'objet une fois formatter.
 */
public interface IFormatter<CLASSTOFORMAT, CLASSFORMATTED> {

	/**
	 * Cette m�thode formatte l'objet pass� en param�tre.
	 * 
	 * @param toFormat
	 *            Objet � formatter.
	 * @return Objet formatt�.
	 */
	public CLASSFORMATTED format(CLASSTOFORMAT toFormat);

}
