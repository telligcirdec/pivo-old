/**
 * 
 */
package santeclair.lunar.framework.util;

import java.util.Locale;

import net.redhogs.cronparser.CronExpressionDescriptor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe utilitaire permettant de manipuler les expressions CRON.
 * 
 * @author jfourmond
 * 
 */
public class CronUtils {

    private final static Logger logger = LoggerFactory.getLogger(CronUtils.class);

    /**
     * Renvoie une String indiquant en fran�ais la fr�quence d'ex�cution de l'expression CRON du groupe de jobs.
     * Ne g�re pas les expressions CRON avec l'ann�e (renvoie une String d'erreur si l'expression CRON comporte une ann�e).
     * 
     * Exemples :
     * "0 0/5 * * * ?" => "Toutes les 5 minutes"
     * "0 45 8 ? * 1" => "� 8:45 AM, seulement le lundi"
     * "0 45 8 ? * 1 *" => "Erreur dans l'expression CRON "0 45 8 ? * 1 *""
     * 
     * N�cessite un fichier de propri�t�s avec les libell�s dans le classpath du projet parent.
     * Un exemple est disponible : CronParserI18N_fr.properties dans src/test/resources.
     * Pour param�trer la traduction en fran�ais, �diter ce fichier.
     */
    public static String getCronEnFrancais(String cronExpression) {
        if (StringUtils.isNotEmpty(cronExpression)) {
            try {
                return CronExpressionDescriptor.getDescription(cronExpression, Locale.FRENCH);
            } catch (Exception e) {
                logger.info("Impossible de parser l'expression CRON. L'affichage en fran�ais ne g�re pas les CRON avec ann�e.", e);
                return "";
            }
        } else {
            return "";
        }
    }

}
