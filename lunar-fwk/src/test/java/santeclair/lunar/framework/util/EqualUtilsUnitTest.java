package santeclair.lunar.framework.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import santeclair.lunar.framework.annotation.Equals;
import santeclair.lunar.framework.annotation.HashCodeEquals;

public class EqualUtilsUnitTest {

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

        Object1 obj1 = new Object1((byte) 1, (short) 2, 3, 'c', "Test", true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object obj2 = new Object1((byte) 1, (short) 2, 3, 'c', "Test", true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Assert.assertTrue(obj1.equals(obj2));
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

        Object1 obj1 = new Object1((byte) 1, (short) 2, 3, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object obj2 = new Object1((byte) 1, (short) 2, 3, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Assert.assertTrue(obj1.equals(obj2));
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

        Object1 obj1 = new Object1((byte) 2, (short) 2, 4, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object obj2 = new Object1((byte) 1, (short) 2, 3, 'c', null, true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Assert.assertFalse(obj1.equals(obj2));
    }

    /**
     * Permet de tester le fonctionnement de la mécanique d'égalité selon des
     * annotations avec des objets différents. Resultat attendu : equal =
     * false
     */
    @Test
    public void testDifferentObject() {

        List<Integer> integerList = new ArrayList<>();
        integerList.add(new Integer(3));
        integerList.add(new Integer(4));

        Map<String, Object> mapValue = new HashMap<>();
        mapValue.put("coucou", new Object());

        char[] chars = new char[]{'c', 'r'};

        Object1 obj1 = new Object1((byte) 1, (short) 2, 3, 'c', "Test", true, 1l, 2d, chars, 3f, new Integer(3), integerList, mapValue);

        Object2 obj2 = new Object2(new Object());

        Assert.assertFalse(obj1.equals(obj2));
    }

}

class Object1 {

    @Equals
    private byte byteValue;
    @Equals
    private short shortValue;
    @Equals
    private int intValue;
    @Equals
    protected char charValue;
    @Equals
    String stringValue;
    @Equals
    public boolean booleanValue;
    @Equals
    private long longValue;
    @Equals
    private double doubleValue;
    @Equals
    private char[] charTabValue;
    @HashCodeEquals
    private float floatValue;
    @Equals
    private Integer integerValue;
    @Equals
    private List<Integer> integerListValue;
    @HashCodeEquals
    private Map<String, Object> mapValue;

    public Object1(byte byteValue, short shortValue, int intValue, char charValue, String stringValue, boolean booleanValue,
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
    public boolean equals(Object obj) {
        return EqualsUtils.isEqual(this, obj);
    }

}

class Object2 {

    @Equals
    private Object badObject;

    public Object2(Object badObject) {
        this.badObject = badObject;
    }

}
