package santeclair.lunar.framework.dao.jpa;

import java.util.List;

/**
 * Interface listant les m�thodes utilis�es pour manipuler les objets de type
 * DOMAINE avec des codes uniques de type CODE dans le syst�me de persistance.
 * 
 * @author tducloyer
 * 
 * @param <DOMAINE>
 *            : Le type de donn�es manipul�s par ce DAO.
 * @param <CODE>
 *            : Le type de classe servant de code.
 */
public interface IFwkCodeJpaDAO<DOMAINE, CODE> extends
		IFwkGenericJpaDAO<DOMAINE> {

	/**
	 * Permet de r�cup�rer la valeur de l'identifiant d'un objet � partir de son
	 * code unique.
	 * 
	 * @param code
	 *            : La valeur du code de l'objet.
	 * 
	 * @return La valeur enti�re de l'identifiant si le code existe.
	 * @throws IllegalArgumentException
	 *             Si l'objet du DOMAINE ne poss�de pas de code ou si le code
	 *             donn�e est invalide.
	 */
	Integer findIdByCode(CODE code);

	/**
	 * Permet de r�cup�rer un objet � partir de son code unique.
	 * 
	 * @param code
	 *            : La valeur du code de l'objet.
	 * 
	 * @return L'objet dont la valeur du code est celle donn�e.
	 * @throws IllegalArgumentException
	 *             Si l'objet du domaine ne poss�de pas de code.
	 */
	DOMAINE findEntityByCode(CODE code);

	/**
	 * Permet de r�cup�rer la liste compl�te des codes utilis�s par le type
	 * d'objet DOMAINE.
	 * 
	 * @return La liste de tous les codes connus.
	 */
	List<CODE> findAllCode();

	/**
	 * Permet la r�cup�ration du nombre d'enregistrement pour le code donn�.
	 * Th�oriquement, une occurence ou z�ro doivent �tre remont�es. Si plusieurs
	 * occurences sont remont�es alors un log warning est d�clench�.
	 * 
	 * @param code
	 *            Le code permettant de compter le nombre d'occurence.
	 * @return Le nombre d'occurence trouv�e en base.
	 */
	Long getSizeByCode(CODE code);

}
