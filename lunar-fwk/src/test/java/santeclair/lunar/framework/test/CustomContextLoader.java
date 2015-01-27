package santeclair.lunar.framework.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.support.AbstractContextLoader;
import org.springframework.util.StringUtils;

import santeclair.lunar.framework.formatter.IFormatter;
import santeclair.lunar.framework.formatter.impl.ElToSystemPropertyFormatter;

/**
 * Chargement spécifique du contexte afin de prendre en compte les EL
 */
public class CustomContextLoader extends AbstractContextLoader {
    /** Logger. */
	protected static final Logger LOGGER = LoggerFactory.getLogger(CustomContextLoader.class);
	
	/**
	 * {@inheritDoc}
	 */
	public final ConfigurableApplicationContext loadContext(String... locations) throws Exception {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Loading ApplicationContext for locations [" + StringUtils.arrayToCommaDelimitedString(locations) + "].");
		}
		
		return innerLoadContext(locations);
	}
	
	/**
	 * {@inheritDoc}
	 */
    public ApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Loading ApplicationContext for mergedConfig [" + mergedConfig.getActiveProfiles().toString() + "].");
        }
        return innerLoadContext(mergedConfig.getLocations());
    }
    
    /**
     * {@inheritDoc}
     */
	protected String[] modifyLocations(Class<?> clazz, String... locations) {
		IFormatter<String, String> formatter = new ElToSystemPropertyFormatter();
		String[] modifiedLocations = new String[locations.length];
		
		for (int i = 0; i < locations.length; i++) {
			modifiedLocations[i] = formatter.format(locations[i]);
		}
		return super.modifyLocations(clazz, modifiedLocations);
	}

	
	protected void prepareContext(GenericApplicationContext context) {
	}

	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
	}

	protected void customizeContext(GenericApplicationContext context) {
	}
	
	protected BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context) {
		return new XmlBeanDefinitionReader(context);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getResourceSuffix() {
		return "-context.xml";
	}
	
	/**
	 * Factorisation de la procédure de chargement du contexte.
	 * @param locations
	 * @return
	 * @throws Exception
	 */
    private GenericApplicationContext innerLoadContext(String... locations) throws Exception {
        GenericApplicationContext context = new GenericApplicationContext();
        prepareContext(context);
        customizeBeanFactory(context.getDefaultListableBeanFactory());
        
        createBeanDefinitionReader(context).loadBeanDefinitions(locations);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
        customizeContext(context);
        context.refresh();
        context.registerShutdownHook();
        return context;
    }
}
