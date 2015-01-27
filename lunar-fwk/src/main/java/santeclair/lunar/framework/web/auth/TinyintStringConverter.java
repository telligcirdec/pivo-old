/**
 * 
 */
package santeclair.lunar.framework.web.auth;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.lang.StringUtils;

/**
 * Converter permettant de définir le comportement d'une donnée qui peut être égale à 0 ou 1 ou null;
 * 
 * @author equinton
 */
public class TinyintStringConverter extends XmlAdapter<String, String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String marshal(String v) throws Exception {
        return v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String unmarshal(String v) throws Exception {
        if (StringUtils.isBlank(v)) {
            return null;
        }
        return v;
    }

}
