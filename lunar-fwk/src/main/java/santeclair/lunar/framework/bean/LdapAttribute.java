/**
 * 
 */
package santeclair.lunar.framework.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation marquant un getter correspondant à un attribut dans le LDAP Santéclair.
 * Exemple d'utilisation : voir classe "Osteopathe" dans projet annuairepsV2-applicatif.
 * 
 * @author jfourmond
 * 
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LdapAttribute {

    /**
     * Renvoie le nom de l'attribut correspondant dans le LDAP.
     */
    String value();
}
