package santeclair.lunar.framework.util;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Classe utilitaire de vérification du DNS de l'email
 * 
 * @author ldelemotte
 * 
 */
public class VerificationDnsDomainUtils {
    /** Nombre de réessai par défaut. */
    private static final String DEFAULT_DNS_RETRIES = "1";
    /** Délai avant fin de la demande. */
    private static final String DEFAULT_DNS_TIMEOUT = "1000";

    /**
     * Vérifiation du DNS de l'email avec les paramètre par défaut.
     * 
     * @param dnsMailDomain
     * @return
     */
    public static boolean checkDnsMailDomain(String dnsMailDomain) {
        return checkDnsMailDomain(dnsMailDomain, DEFAULT_DNS_RETRIES, DEFAULT_DNS_TIMEOUT);
    }

    /**
     * Vérification du DNS de l'email avec uniquement le timeout de paramétrable.
     * 
     * @param dnsMailDomain
     * @param timeout
     * @return
     */
    public static boolean checkDnsMailDomain(String dnsMailDomain, String timeout) {
        return checkDnsMailDomain(dnsMailDomain, DEFAULT_DNS_RETRIES, timeout);
    }

    /**
     * Vérification du DNS de l'email avec tous les paramètres.
     * 
     * @param dnsMailDomain
     * @param retries
     * @param timeout
     * @return
     */
    public static boolean checkDnsMailDomain(String dnsMailDomain, String retries, String timeout) {
        try {
            Hashtable<String, String> environnement = new Hashtable<>();
            environnement.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            environnement.put("com.sun.jndi.dns.timeout.initial", timeout);
            environnement.put("com.sun.jndi.dns.timeout.retries", retries);
            DirContext dirContext = new InitialDirContext(environnement);
            Attributes attributes = dirContext.getAttributes(dnsMailDomain, new String[]{"MX"});

            return attributes.get("MX") != null;
        } catch (NamingException e) {
            return false;
        }
    }
}
