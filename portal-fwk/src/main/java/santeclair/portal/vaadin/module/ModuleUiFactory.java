package santeclair.portal.vaadin.module;

import java.io.Serializable;
import java.util.List;

import com.vaadin.server.FontIcon;

/**
 * Factory permettant de construire une instance ou plus d'un module.
 * 
 * @author Puppet master
 *
 * @param <T>
 */
public interface ModuleUiFactory<T extends ModuleUi> extends Serializable {

    /**
     * Permet de v�rifier si plusieurs modules peuvent �tre instanci�. Si vrai,
     * alors plusieurs onglets contenant des instances diff�rentes du module
     * s'ouvriront. Si faux, alors seulement un onglet pourra s'ouvrir et
     * soliciter de nouveau ce module devra avoir pour effet de remettre le
     * focus sur l'onglet contenant l'instance en cours.
     * 
     * @return Vrai si plusieurs instance du module sont possibles, faux si une
     *         seule instance est possible.
     */
    Boolean isSeveralModuleAllowed(List<String> roles);

    /**
     * Permet de d�terminer un code qui identifie ce type de module de mani�re
     * unique. Attention, ce code doit �tre diff�rent entre les modules. Ce code
     * sera utilis� dans le cadre de la m�canique de navigation.
     * 
     * @return Un code unique.
     */
    String getCode();

    /**
     * Permet de d�terminer le nom du module. Ce nom sera utilis� par d�faut
     * dans le menu comme nom principal. Une valeur null permet de ne pas
     * afficher le module dans le menu.
     * 
     * @return Nom du module.
     */
    String getName();

    /**
     * Permet de d�terminer l'icon qui sera affich� � c�t� du nom du module dans
     * le menu � gauche et �galement dans l'onglet contenat une instance de ce
     * module.
     * 
     * @return Icon � utiliser pour le module dans le menu et dans l'onglet.
     */
    FontIcon getIcon();

    /**
     * Permet de d�terminer l'ordre d'affichage du module dans le menu � gauche.
     * Les modules seront affich�s dans l'ordre du plus petit au plus grand nombre.
     * Les factory ayant le m�me nombre seront class�es par ordre alphab�tique de
     * nom ({@code getName()}). Si la m�thode renvoie null ou une valeur n�gative
     * ou 0, la valeur maximum d'integer sera alors utilis�e.
     * 
     * @return L'ordre d'affichage. Null, -1 ou 0 seront consid�r�s comme valuer max integer.
     */
    Integer displayOrder();

    /**
     * Permet de d�terminer si l'onglet contenant l'instance du module peut �tre
     * ferm� ou non.
     * 
     * @param roles
     *            Liste des roles permettant �ventuellement de jouer sur la
     *            capacit� � fermer un onglet en fonction du role de
     *            l'utilisateur;
     * @return Vrai si l'onglet peut �tre ferm�, faux si l'onglet ne peut pas
     *         �tre ferm�.
     */
    Boolean isCloseable(List<String> roles);

    /**
     * Permet de d�terminer si une instance du module doit �tre lanc� au
     * d�marrage du portail. Ce qui signifi l'ouverture d'un onglet. Cette
     * capacit� du module peut �tre index� sur les roles de l'utilisateur.
     * 
     * @param roles
     *            Roles de l'utilisateur actuel.
     * @return Vrai si une instance doit �tre lanc� au d�marrage, Faux si non.
     */
    Boolean openOnInitialization(List<String> roles);

    /**
     * Permet de v�rifier si les roles pass�s en param�tre donnent le droit �
     * acc�der au Module. Attention, cette v�rification se fait sur le module
     * dans son int�gralit� et ne permet pas de v�rifier si l'utilisateur � des
     * droits d'acc�s plus fin par fonctionnalit� dans le module. Pour cela, le
     * module peut utiliser la liste des roles qui est pass�e lors de la
     * construction de ce dernier.
     * 
     * @param roles
     * @return
     */
    Boolean securityCheck(List<String> roles);

    T buid(List<String> roles);
}
