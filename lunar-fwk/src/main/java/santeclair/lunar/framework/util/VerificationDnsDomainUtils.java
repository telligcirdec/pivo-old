package santeclair.lunar.framework.util;

import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Classe utilitaire de v�rification du DNS de l'email
 * 
 * @author ldelemotte
 * 
 */
public class VerificationDnsDomainUtils {
    /** Nombre de r�essai par d�faut. */
    private static final String DEFAULT_DNS_RETRIES = "1";
    /** D�lai avant fin de la demande. */
    private static final String DEFAULT_DNS_TIMEOUT = "1000";

    /**
     * V�rifiation du DNS de l'email avec les param�tre par d�faut.
     * 
     * @param dnsMailDomain
     * @return
     */
    public static boolean checkDnsMailDomain(String dnsMailDomain) {
        return checkDnsMailDomain(dnsMailDomain, DEFAULT_DNS_RETRIES, DEFAULT_DNS_TIMEOUT);
    }

    /**
     * V�rification du DNS de l'email avec uniquement le timeout de param�trable.
     * 
     * @param dnsMailDomain
     * @param timeout
     * @return
     */
    public static boolean checkDnsMailDomain(String dnsMailDomain, String timeout) {
        return checkDnsMailDomain(dnsMailDomain, DEFAULT_DNS_RETRIES, timeout);
    }

    /**
     * V�rification du DNS de l'email avec tous les param�tres.
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
