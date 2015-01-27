package santeclair.portal.vaadin.deprecated.module.api.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import santeclair.portal.vaadin.deprecated.event.ApplicationEventPublisher;
import santeclair.portal.vaadin.deprecated.module.ModuleMapKey;
import santeclair.portal.vaadin.deprecated.module.ModuleUi;
import santeclair.portal.vaadin.deprecated.module.api.ModuleFactory;
import santeclair.portal.vaadin.deprecated.module.api.ModuleFactoryService;
import santeclair.portal.vaadin.deprecated.module.uri.ViewUriFragment;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;

@Deprecated
public abstract class AbstractModuleFactory<T extends ModuleUi> implements ModuleFactory<T> {

	@Autowired
	protected ApplicationContext applicationContext;

	private static final long serialVersionUID = -7257194296205915526L;

	private Class<T> type;

	private ModuleMapKey moduleMapKey;

	@SuppressWarnings("unchecked")
	public AbstractModuleFactory() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class<T>) pt.getActualTypeArguments()[0];
		moduleMapKey = new ModuleMapKey(this.getCode(), this.displayOrder());
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public T createModule(ViewUriFragment viewUriFragment, Navigator navigator, ApplicationEventPublisher applicationEventPublisher, View container,
			Map<String, String> parameters) {
		T moduleUi = applicationContext.getBean(type);
		moduleUi.init(viewUriFragment, navigator, applicationEventPublisher, container, parameters);
		return moduleUi;
	}

	@Override
	public void setModuleFactoryService(ModuleFactoryService service, Map<?, ?> properties) {
		service.registerModuleFactory(this);
	}

	@Override
	public void unsetModuleFactoryService(ModuleFactoryService service, Map<?, ?> properties) {
		service.unregisterModuleFactory(this);
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public int displayOrder() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isCloseable() {
		return true;
	}

	@Override
	public boolean openOnInitialisation() {
		return false;
	}

	@Override
	public final ModuleMapKey getModuleMapKey() {
		return moduleMapKey;
	}

}
