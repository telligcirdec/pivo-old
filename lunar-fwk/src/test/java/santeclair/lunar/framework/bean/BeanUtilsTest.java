package santeclair.lunar.framework.bean;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import santeclair.lunar.framework.util.BeanUtils;

/**
 * @author fmokhtari
 * 
 */
public class BeanUtilsTest {

    @Test
    public void copyProperties_SourceNull() {
        Assert.assertNull(BeanUtils.copyProperties(null, null));

    }

    @Test(expected = IllegalArgumentException.class)
    public void copyProperties_DestinationClassNull() {
        Object toto = BeanUtils.copyProperties(new MyClassSource(2, new Date(), "Hello World"), null);
        Assert.assertNull(toto);
    }

    @Test
    public void copyProperties_OK() {
        Date date = new Date();
        MyClassDestination instanceDestination = BeanUtils.copyProperties(new MyClassSource(2, date, "Hello World"), MyClassDestination.class);
        Assert.assertNotNull(instanceDestination);
        Assert.assertEquals(2, instanceDestination.getEntier());
        Assert.assertEquals(date, instanceDestination.getDate());
    }

    @Test
    public void copyPropertiesToDestination_SourceNull() {
        Assert.assertNull(BeanUtils.copyPropertiesToDestination(null, new MyClassDestination()));
    }

    @Test
    public void copyPropertiesToDestination_OK() {
        Date date = new Date();
        MyClassDestination instanceDestination = new MyClassDestination();
        instanceDestination.setSomeDouble(2.59);
        MyClassDestination instanceDestinationAvecCopie = BeanUtils.copyPropertiesToDestination(new MyClassSource(2, date, "Hello World"), instanceDestination);
        Assert.assertNotNull(instanceDestinationAvecCopie);
        Assert.assertEquals(2, instanceDestinationAvecCopie.getEntier());
        Assert.assertEquals(date, instanceDestinationAvecCopie.getDate());
        Assert.assertTrue(2.59 == instanceDestinationAvecCopie.getSomeDouble().doubleValue());

    }
}
