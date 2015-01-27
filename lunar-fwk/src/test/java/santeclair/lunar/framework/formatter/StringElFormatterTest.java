package santeclair.lunar.framework.formatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import santeclair.lunar.framework.formatter.impl.ElToSystemPropertyFormatter;

@RunWith(BlockJUnit4ClassRunner.class)
public class StringElFormatterTest {

	private IFormatter<String, String> formatter;
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(StringElFormatterTest.class);
	
	public StringElFormatterTest() {
		formatter = new ElToSystemPropertyFormatter();
	}
	
	/**
	 * Test la sortie d'un formattage de la classe ElToSystemPropertyFormatter avec une seule balise el.
	 * 
	 * Valeur en entrée : classpath:/spring/${test-group}/applicationContext.xml
	 * Valeur attendue : classpath:/spring/local/applicationContext.xml
	 * 
	 */
	@Test
	public void testStringElFormatterWhithOneEl(){
		
		String propertySystemKey = "test-group";
		String propertySystemValue = "local";
		
		System.setProperty(propertySystemKey, propertySystemValue);
		
		String toFormat = "classpath:/spring/${test-group}/applicationContext.xml";
		
		String resultExpected = "classpath:/spring/" + propertySystemValue + "/applicationContext.xml";
		
		String formatted = formatter.format(toFormat);
	
		LOGGER.debug("toFormat : " + toFormat);
		LOGGER.debug("formatted : " + formatted);
		
		assertThat(resultExpected, is(formatted));
	}
	
	
	/**
	 * Test la sortie d'un formattage de la classe ElToSystemPropertyFormatter sans balise el.
	 * 
	 * Valeur en entrée : classpath:/spring/${test-group}/applicationContext.xml
	 * Valeur attendue : classpath:/spring/local/applicationContext.xml
	 * 
	 */
	@Test
	public void testStringElFormatterWhithoutEl(){
		
		String toFormat = "classpath:/spring/test/applicationContext.xml";
		
		String resultExpected = "classpath:/spring/test/applicationContext.xml";
		
		String formatted = formatter.format(toFormat);
	
		LOGGER.debug("toFormat : " + toFormat);
		LOGGER.debug("formatted : " + formatted);
		
		assertThat(resultExpected, is(formatted));
	}
	
	
	/**
	 * Test la sortie d'un formattage de la classe ElToSystemPropertyFormatter avec plusieurs balises el.
	 * 
	 * Valeur en entrée : classpath:/spring/${test-group}/applicationContext.xml
	 * Valeur attendue : classpath:/spring/local/applicationContext.xml
	 * 
	 */
	@Test
	public void testStringElFormatterWhithSeveralEl(){
		
		String propertySystemKey1 = "test-group";
		String propertySystemValue1 = "local";
		
		String propertySystemKey2 = "filename";
		String propertySystemValue2 = "spring";
		
		System.setProperty(propertySystemKey1, propertySystemValue1);
		System.setProperty(propertySystemKey2, propertySystemValue2);
		
		String toFormat = "classpath:/${filename}/${test-group}/applicationContext.xml";
		
		String resultExpected = "classpath:/" + propertySystemValue2 + "/" + propertySystemValue1 + "/applicationContext.xml";
		
		String formatted = formatter.format(toFormat);
	
		LOGGER.debug("toFormat : " + toFormat);
		LOGGER.debug("formatted : " + formatted);
		
		assertThat(resultExpected, is(formatted));
	}
	
}
