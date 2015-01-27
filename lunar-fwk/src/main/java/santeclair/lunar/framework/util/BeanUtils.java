package santeclair.lunar.framework.util;

import org.dozer.DozerBeanMapperSingletonWrapper;
import org.dozer.Mapper;
import org.dozer.MappingException;

/**
 * Classe utilitaire de gestion des beans
 * 
 * @author fmokhtari
 * 
 */
public class BeanUtils {

    private static Mapper mapper = DozerBeanMapperSingletonWrapper.getInstance();

    /**
     * Copie les valeurs des propriétées de la source vers la destinatation. La différence avec BeanUtils de spring est
     * la tolérance au nul
     * 
     * @return
     */
    public static <D> D copyProperties(Object source, Class<D> clazzDestination) {

        if (source == null) {
            return null;
        }
        try {
            return mapper.map(source, clazzDestination);
        } catch (MappingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    /**
     * 
     * 
     * @return
     */
    public static <D> D copyPropertiesToDestination(Object source, D destination, String... ignoreProperties) {

        if (source == null) {
            return null;
        }
        org.springframework.beans.BeanUtils.copyProperties(source, destination, ignoreProperties);
        return destination;
    }

}
