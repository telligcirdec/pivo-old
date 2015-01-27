package santeclair.lunar.framework.dao.jpa;

import java.util.List;

/**
 * Interface listant les méthodes utilisées pour manipuler les objets de type
 * DOMAINE avec des codes uniques de type CODE dans le système de persistance.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE>
 *            : Le type de données manipulés par ce DAO.
 * @param <CODE>
 *            : Le type de classe servant de code.
 */
public interface IFwkCodeJpaDAO<DOMAINE, CODE> extends
		IFwkGenericJpaDAO<DOMAINE> {

	/**
	 * Permet de récupérer la valeur de l'identifiant d'un objet à partir de son
	 * code unique.
	 * 
	 * @param code
	 *            : La valeur du code de l'objet.
	 * 
	 * @return La valeur entière de l'identifiant si le code existe.
	 * @throws IllegalArgumentException
	 *             Si l'objet du DOMAINE ne possède pas de code ou si le code
	 *             donnée est invalide.
	 */
	Integer findIdByCode(CODE code);

	/**
	 * Permet de récupérer un objet à partir de son code unique.
	 * 
	 * @param code
	 *            : La valeur du code de l'objet.
	 * 
	 * @return L'objet dont la valeur du code est celle donnée.
	 * @throws IllegalArgumentException
	 *             Si l'objet du domaine ne possède pas de code.
	 */
	DOMAINE findEntityByCode(CODE code);

	/**
	 * Permet de récupérer la liste complète des codes utilisés par le type
	 * d'objet DOMAINE.
	 * 
	 * @return La liste de tous les codes connus.
	 */
	List<CODE> findAllCode();

	/**
	 * Permet la récupération du nombre d'enregistrement pour le code donné.
	 * Théoriquement, une occurence ou zéro doivent être remontées. Si plusieurs
	 * occurences sont remontées alors un log warning est déclenché.
	 * 
	 * @param code
	 *            Le code permettant de compter le nombre d'occurence.
	 * @return Le nombre d'occurence trouvée en base.
	 */
	Long getSizeByCode(CODE code);

}
