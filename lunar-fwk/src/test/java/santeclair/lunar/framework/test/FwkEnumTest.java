package santeclair.lunar.framework.test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import santeclair.lunar.framework.dao.jpa.IFwkCodeJpaDAO;

/**
 * Classe abstraite regroupant toutes les m�thodes permettant de tester un enum.
 * 
 * @author fmokhtari
 * 
 * @param <DOMAINE> : Le type d'objet manipul� par le DAO.
 * @param <T extends Enum<T>> : l'�num�ration associ�e au domaine.
 * 
 */
public abstract class FwkEnumTest<DOMAINE, T extends Enum<T>, DAO extends IFwkCodeJpaDAO<DOMAINE, String>> {

    @Test
    public void testEnumerationComplete() {

        /**
         * P�lie au fait que la m�thode values() ne soit pas accessible sur la classe <code>T extends Enum<T></code>
         */
        List<T> values = new ArrayList<T>();

        /**
         * R�cup�ration des codes de l'objet.
         */
        List<String> codes = getDao().findAllCode();

        /**
         * V�rification si tous les �l�ments de la table r�f�rentiel existent dans l'�numaration
         */
        for (T enumValue : getEnumClass().getEnumConstants()) {
            values.add(enumValue);
        }

        /**
         * V�rification si le meme nombre d'�l�ment est present dans la table referentiel et dans l'�numeration
         * associ�e.
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
