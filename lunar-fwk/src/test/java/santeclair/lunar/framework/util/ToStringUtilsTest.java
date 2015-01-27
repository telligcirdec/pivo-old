package santeclair.lunar.framework.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import santeclair.lunar.framework.annotation.ToString;

@Ignore
public class ToStringUtilsTest {

    @Test
    public void testNominal() {

        Map<String, Object> map2 = new HashMap<>();
        map2.put("test11", "mouahahaha125456");
        map2.put("test12", "object2");

        Object1 object2 = new Object1(new Byte("2"), new Short("5"), new Integer("526"), new Character('h'), "test2", Boolean.FALSE, new Long("87542"), new Double("1.9"),
                        new Character[]{new Character('z'), new Character('w'), new Character('x')}, new Float("1.8"), new Integer("158"), Arrays.asList(new Integer("9"),
                                        new Integer("8"), new Integer("7")), map2);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("test1", "mouahahaha");
        map1.put("test2", object2);

        Object1 object1 = new Object1(new Byte("1"), new Short("1"), new Integer("2"), new Character('c'), "test", Boolean.TRUE, new Long("123456"), new Double("1.2"),
                        new Character[]{new Character('a'), new Character('b'), new Character('c')}, new Float("1.5"), new Integer("15"), Arrays.asList(new Integer("1"),
                                        new Integer("2"), new Integer("3")), map1);

        String string = object1.toString();

        Assert.assertEquals(
                        "Object1 : { byteValue : '1', shortValue : '1', intValue : '2', charValue : 'c', stringValue : 'test', booleanValue : 'true', longValue : '123456', doubleValue : '1.2', charTabValue :  ['a', 'b', 'c'] , floatValue : '1.5', integerValue : '15', integerListValue :  ['1', '2', '3'] , mapValue :  [test1 : 'mouahahaha', test2 : 'Object1 : { byteValue : '2', shortValue : '5', intValue : '526', charValue : 'h', stringValue : 'test2', booleanValue : 'false', longValue : '87542', doubleValue : '1.9', charTabValue :  ['z', 'w', 'x'] , floatValue : '1.8', integerValue : '158', integerListValue :  ['9', '8', '7'] , mapValue :  [test12 : 'object2', test11 : 'mouahahaha125456']  }']  }",
                        string);

    }

    @Test
    public void testNominalInherited() {

        Map<String, Object> map2 = new HashMap<>();
        map2.put("test11", "mouahahaha125456");
        map2.put("test12", "object2");

        Object1 object2 = new Object1(new Byte("2"), new Short("5"), new Integer("526"), new Character('h'), "test2", Boolean.FALSE, new Long("87542"), new Double("1.9"),
                        new Character[]{new Character('z'), new Character('w'), new Character('x')}, new Float("1.8"), new Integer("158"), Arrays.asList(new Integer("9"),
                                        new Integer("8"), new Integer("7")), map2);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("test1", "mouahahaha");
        map1.put("test2", object2);

        Object11 object11 = new Object11(new Byte("1"), new Short("1"), new Integer("2"), new Character('c'), "test", Boolean.TRUE, new Long("123456"), new Double("1.2"),
                        new Character[]{new Character('a'), new Character('b'), new Character('c')}, new Float("1.5"), new Integer("15"), Arrays.asList(new Integer("1"),
                                        new Integer("2"), new Integer("3")), map1);

        String string = object11.toString();

        Assert.assertEquals(
                        "Object11 : { byteValue : '1', shortValue : '1', intValue : '2', charValue : 'c', stringValue : 'test', booleanValue : 'true', longValue : '123456'\n\t extends \n\t\tObject111 : { doubleValue : '1.2', charTabValue :  ['a', 'b', 'c'] , floatValue : '1.5', integerValue : '15', integerListValue :  ['1', '2', '3'] , mapValue :  [test1 : 'mouahahaha', test2 : 'Object1 : { byteValue : '2', shortValue : '5', intValue : '526', charValue : 'h', stringValue : 'test2', booleanValue : 'false', longValue : '87542', doubleValue : '1.9', charTabValue :  ['z', 'w', 'x'] , floatValue : '1.8', integerValue : '158', integerListValue :  ['9', '8', '7'] , mapValue :  [test12 : 'object2', test11 : 'mouahahaha125456']  }']  } }",
                        string);

    }

    class Object1 {

        @ToString
        private Byte byteValue;
        @ToString
        private Short shortValue;
        @ToString
        private Integer intValue;
        @ToString
        protected Character charValue;
        @ToString
        String stringValue;
        @ToString
        public Boolean booleanValue;
        @ToString
        private Long longValue;
        @ToString
        private Double doubleValue;
        @ToString
        private Character[] charTabValue;
        @ToString
        private Float floatValue;
        @ToString
        private Integer integerValue;
        @ToString
        private List<Integer> integerListValue;
        @ToString
        private Map<String, Object> mapValue;

        public Object1(byte byteValue, short shortValue, int intValue, char charValue, String stringValue, boolean booleanValue, long longValue, double doubleValue,
                       Character[] charTabValue, float floatValue, Integer integerValue, List<Integer> integerListValue, Map<String, Object> mapValue) {
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
        public String toString() {
            return ToStringUtils.toString(this);
        }

    }

    class Object11 extends Object111 {

        @ToString
        private Byte byteValue;
        @ToString
        private Short shortValue;
        @ToString
        private Integer intValue;
        @ToString
        protected Character charValue;
        @ToString
        String stringValue;
        @ToString
        public Boolean booleanValue;
        @ToString
        private Long longValue;

        public Object11(byte byteValue, short shortValue, int intValue, char charValue, String stringValue, boolean booleanValue, long longValue, double doubleValue,
                        Character[] charTabValue, float floatValue, Integer integerValue, List<Integer> integerListValue, Map<String, Object> mapValue) {
            super(doubleValue, charTabValue, floatValue, integerValue, integerListValue, mapValue);
            this.byteValue = byteValue;
            this.shortValue = shortValue;
            this.intValue = intValue;
            this.charValue = charValue;
            this.stringValue = stringValue;
            this.booleanValue = booleanValue;
            this.longValue = longValue;
        }

        @Override
        public String toString() {
            return ToStringUtils.toString(this, true);
        }

    }

    class Object111 {

        @ToString
        public Double doubleValue;
        @ToString
        public Character[] charTabValue;
        @ToString
        protected Float floatValue;
        @ToString
        protected Integer integerValue;
        @ToString
        protected List<Integer> integerListValue;
        @ToString
        protected Map<String, Object> mapValue;

        public Object111(Double doubleValue, Character[] charTabValue, Float floatValue, Integer integerValue, List<Integer> integerListValue, Map<String, Object> mapValue) {
            super();
            this.doubleValue = doubleValue;
            this.charTabValue = charTabValue;
            this.floatValue = floatValue;
            this.integerValue = integerValue;
            this.integerListValue = integerListValue;
            this.mapValue = mapValue;
        }

        @Override
        public String toString() {
            return ToStringUtils.toString(this);
        }

    }

}
