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
     * Permet de vérifier si plusieurs modules peuvent être instancié. Si vrai,
     * alors plusieurs onglets contenant des instances différentes du module
     * s'ouvriront. Si faux, alors seulement un onglet pourra s'ouvrir et
     * soliciter de nouveau ce module devra avoir pour effet de remettre le
     * focus sur l'onglet contenant l'instance en cours.
     * 
     * @return Vrai si plusieurs instance du module sont possibles, faux si une
     *         seule instance est possible.
     */
    Boolean isSeveralModuleAllowed(List<String> roles);

    /**
     * Permet de déterminer un code qui identifie ce type de module de manière
     * unique. Attention, ce code doit être différent entre les modules. Ce code
     * sera utilisé dans le cadre de la mécanique de navigation.
     * 
     * @return Un code unique.
     */
    String getCode();

    /**
     * Permet de déterminer le nom du module. Ce nom sera utilisé par défaut
     * dans le menu comme nom principal. Une valeur null permet de ne pas
     * afficher le module dans le menu.
     * 
     * @return Nom du module.
     */
    String getName();

    /**
     * Permet de déterminer l'icon qui sera affiché à côté du nom du module dans
     * le menu à gauche et également dans l'onglet contenat une instance de ce
     * module.
     * 
     * @return Icon à utiliser pour le module dans le menu et dans l'onglet.
     */
    FontIcon getIcon();

    /**
     * Permet de déterminer l'ordre d'affichage du module dans le menu à gauche.
     * Les modules seront affichés dans l'ordre du plus petit au plus grand nombre.
     * Les factory ayant le même nombre seront classées par ordre alphabétique de
     * nom ({@code getName()}). Si la méthode renvoie null ou une valeur négative
     * ou 0, la valeur maximum d'integer sera alors utilisée.
     * 
     * @return L'ordre d'affichage. Null, -1 ou 0 seront considérés comme valuer max integer.
     */
    Integer displayOrder();

    /**
     * Permet de déterminer si l'onglet contenant l'instance du module peut être
     * fermé ou non.
     * 
     * @param roles
     *            Liste des roles permettant éventuellement de jouer sur la
     *            capacité à fermer un onglet en fonction du role de
     *            l'utilisateur;
     * @return Vrai si l'onglet peut être fermé, faux si l'onglet ne peut pas
     *         être fermé.
     */
    Boolean isCloseable(List<String> roles);

    /**
     * Permet de déterminer si une instance du module doit être lancé au
     * démarrage du portail. Ce qui signifi l'ouverture d'un onglet. Cette
     * capacité du module peut être indexé sur les roles de l'utilisateur.
     * 
     * @param roles
     *            Roles de l'utilisateur actuel.
     * @return Vrai si une instance doit être lancé au démarrage, Faux si non.
     */
    Boolean openOnInitialization(List<String> roles);

    /**
     * Permet de vérifier si les roles passés en paramètre donnent le droit à
     * accéder au Module. Attention, cette vérification se fait sur le module
     * dans son intégralité et ne permet pas de vérifier si l'utilisateur à des
     * droits d'accès plus fin par fonctionnalité dans le module. Pour cela, le
     * module peut utiliser la liste des roles qui est passée lors de la
     * construction de ce dernier.
     * 
     * @param roles
     * @return
     */
    Boolean securityCheck(List<String> roles);

    T buid(List<String> roles);
}
