package santeclair.lunar.framework.util;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;

import santeclair.lunar.framework.bean.LdapAttribute;
import santeclair.lunar.framework.bean.LdapAttributeBean;

import com.google.common.collect.Lists;

/**
 * Classe de m�thodes facilitant l'utilisation du LDAP.
 * 
 * @author jfourmond
 * 
 */
public class LdapUtils {

    private final static Logger logger = LoggerFactory.getLogger(LdapUtils.class);

    /**
     * M�thode de transformation d'un mot de passe en texte clair en mot de passe au format LDAP (hash MD5).
     * 
     * @return Le mot de passe hash� en MD5
     */
    public static String encodeMotDePasseLdap(String motDePasseEnClair) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(motDePasseEnClair.getBytes());
            byte[] hashMd5 = md.digest();

            byte[] bytesMotDePasseEncode = Base64.encode(hashMd5);
            String motDePasseEncode = "{MD5}" + new String(bytesMotDePasseEncode);
            return motDePasseEncode;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Scanne l'objet en param�tre � la recherche de m�thodes annot�es par @LdapAttribute
     * Chaque m�thode trouv�e est ex�cut�e et le r�sultat est stock� dans un objet LdapAttributeBean.
     * 
     * @param ldapObject un objet repr�sent� dans le LDAP.
     * @return la liste des attributs LDAP de l'objet.
     */
    public static List<LdapAttributeBean> getAttributsLdap(Object ldapObject) {

        List<LdapAttributeBean> listeAttributsMetier = Lists.newArrayList();
        Class<?> classeLdapPs = ldapObject.getClass();

        Method[] methodesClasseLdap = classeLdapPs.getMethods();
        for (Method methode : methodesClasseLdap) {
            LdapAttribute ldapAttribute = methode.getAnnotation(LdapAttribute.class);
            if (ldapAttribute != null) {
                String nomAttribut = ldapAttribute.value();
                try {
                    Object valeurAttribut = methode.invoke(ldapObject, (Object[]) null);
                    LdapAttributeBean attributMetier = new LdapAttributeBean(nomAttribut, valeurAttribut);
                    listeAttributsMetier.add(attributMetier);
                } catch (Exception e) {
                    String nomMethode = methode.getName();
                    logger.error("Impossible d'ex�cuter la m�thode " + nomMethode +
                                    ", v�rifiez qu'elle est bien de type getter (pas d'arguments et retour != void).", e);
                }
            }
        }

        return listeAttributsMetier;
    }
}
