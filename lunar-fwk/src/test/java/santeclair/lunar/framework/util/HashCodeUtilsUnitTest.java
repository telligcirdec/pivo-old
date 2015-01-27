package santeclair.lunar.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import santeclair.lunar.framework.annotation.HashCode;
import santeclair.lunar.framework.annotation.HashCodeEquals;

public class HashCodeUtilsUnitTest {

    /**
     * Permet de tester le fonctionnement de la mécanique d'égalité selon des
     * annotations. Resultat attendu : equal = true
     */
    @Test
    public void testEquals() {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(new Integer(3));
        integerList.add(new Integer(4));

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("coucou", new Object());

        char[] chars = new char[]{'c', 'r'};

        Object11 obj1 = new Object11((byte) 1, (short) 2, 3, 'c', "Test", true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object11 obj2 = new Object11((byte) 1, (short) 2, 3, 'c', "Test", true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Assert.assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    /**
     * Permet de tester le fonctionnement de la mécanique d'égalité selon des
     * annotations avec certaine valeur à null. Resultat attendu : equal = true
     */
    @Test
    public void testEqualsWithNullValue() {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(new Integer(3));
        integerList.add(new Integer(4));

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("coucou", new Object());

        char[] chars = new char[]{'c', 'r'};

        Object11 obj1 = new Object11((byte) 1, (short) 2, 3, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object11 obj2 = new Object11((byte) 1, (short) 2, 3, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Assert.assertEquals(obj1.hashCode(), obj2.hashCode());
    }

    /**
     * Permet de tester le fonctionnement de la mécanique d'égalité selon des
     * annotations avec des valeurs différentes. Resultat attendu : equal =
     * false
     */
    @Test
    public void testEqualsSameObjectDifferentValues() {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(new Integer(3));
        integerList.add(new Integer(4));

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("coucou", new Object());

        char[] chars = new char[]{'c', 'r'};

        Object11 obj1 = new Object11((byte) 2, (short) 2, 4, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object11 obj2 = new Object11((byte) 1, (short) 2, 3, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Assert.assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }

    /**
     * Permet de tester le fonctionnement de la mécanique d'égalité selon des
     * annotations avec des objets différents. Resultat attendu : equal = false
     */
    @Test
    public void testDifferentObject() {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(new Integer(3));
        integerList.add(new Integer(4));

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("coucou", new Object());

        char[] chars = new char[]{'c', 'r'};

        Object11 obj1 = new Object11((byte) 1, (short) 2, 3, 'c', "Test", true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object22 obj2 = new Object22(new Object());

        Assert.assertNotEquals(obj1.hashCode(), obj2.hashCode());
    }

}

class Object11 {

    @HashCode
    private byte byteValue;
    @HashCode
    private short shortValue;
    @HashCode
    private int intValue;
    @HashCode
    protected char charValue;
    @HashCode
    String stringValue;
    @HashCodeEquals
    public boolean booleanValue;
    @HashCode
    private long longValue;
    @HashCode
    private double doubleValue;
    @HashCode
    private char[] charTabValue;
    @HashCode
    private float floatValue;
    @HashCode
    private Integer integerValue;
    @HashCodeEquals
    private List<Integer> integerListValue;
    @HashCode
    private Map<String, Object> mapValue;

    public Object11(byte byteValue, short shortValue, int intValue, char charValue, String stringValue, boolean booleanValue,
                    long longValue, double doubleValue, char[] charTabValue, float floatValue, Integer integerValue,
                    List<Integer> integerListValue, Map<String, Object> mapValue) {
        this.byteValue = byteValue;
        this.shortValue = shortValue;
        this.intValue = intValue;
        this.charValue = charValue;
        this.stringValue = stringValue;
        this.booleanValue = booleanValue;
        this.longValue = longValue;
        this.doubleValue = doubleValue;
        this.charTabValue = charTabValue;
        this.floatValue = floatValue;
        this.integerValue = integerValue;
        this.integerListValue = integerListValue;
        this.mapValue = mapValue;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCode(this);
    }

}

class Object22 {

    @HashCode
    private Object badObject;

    public Object22(Object badObject) {
        this.badObject = badObject;
    }

    @Override
    public int hashCode() {
        return HashCodeUtils.hashCode(this);
    }
}
