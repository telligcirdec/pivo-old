package santeclair.lunar.framework.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import santeclair.lunar.framework.dao.jpa.IFwkCodeJpaDAO;

/**
 * Classe abstraite regroupant toutes les méthodes permettant de tester un enum.
 * 
 * @author fmokhtari
 * 
 * @param <DOMAINE> : Le type d'objet manipulé par le DAO.
 * @param <T extends Enum<T>> : l'énumération associée au domaine.
 * 
 */
public abstract class FwkEnumTest<DOMAINE, T extends Enum<T>, DAO extends IFwkCodeJpaDAO<DOMAINE, String>> {

    @Test
    public void testEnumerationComplete() {

        /**
         * Pâlie au fait que la méthode values() ne soit pas accessible sur la classe <code>T extends Enum<T></code>
         */
        List<T> values = new ArrayList<T>();

        /**
         * Récupération des codes de l'objet.
         */
        List<String> codes = getDao().findAllCode();

        /**
         * Vérification si tous les éléments de la table référentiel existent dans l'énumaration
         */
        for (T enumValue : getEnumClass().getEnumConstants()) {
            values.add(enumValue);
        }

        /**
         * Vérification si le meme nombre d'élément est present dans la table referentiel et dans l'énumeration
         * associée.
         */
        assertEquals(values.size(), codes.size());

        for (T value : values) {
            assertThat(codes, hasItem(value.toString()));
        }

    }

    /** {@inheritDoc} */
    public abstract DAO getDao();

    /** {@inheritDoc} */
    public abstract Class<T> getEnumClass();

}
