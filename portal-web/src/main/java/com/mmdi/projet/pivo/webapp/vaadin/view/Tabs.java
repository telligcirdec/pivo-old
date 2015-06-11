package com.mmdi.projet.pivo.webapp.vaadin.view;

import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_CONTEXT_TABS;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_CONTEXT_VIEW_UI;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_ASKING_CLOSED;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_CLOSED;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_NEW;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.EVENT_NAME_STARTED;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EVENT_CONTEXT;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_EVENT_NAME;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_MODULE_UI_CODE;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_PARAMS;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_PORTAL_SESSION_ID;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_TAB_HASH;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.PROPERTY_KEY_VIEW_UI_CODE;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_COMPONENT_UI;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_MODULE_UI;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_NAVIGATOR;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_TABS;
import static com.mmdi.projet.pivo.utils.event.PivoEventDictionary.TOPIC_VIEW_UI;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mmdi.projet.pivo.utils.event.handler.AbstractEventHandler;
import com.mmdi.projet.pivo.utils.event.handler.EventProperty;
import com.mmdi.projet.pivo.utils.event.handler.PivoEventHandler;
import com.mmdi.projet.pivo.utils.event.handler.Subscriber;
import com.mmdi.projet.pivo.utils.event.publisher.callback.TabsCallback;
import com.mmdi.projet.pivo.utils.listener.service.impl.EventAdminServiceListener;
import com.mmdi.projet.pivo.utils.listener.service.impl.EventAdminServiceListener.DataPublisher;
import com.mmdi.projet.pivo.utils.listener.service.impl.EventAdminServiceListener.Publisher;
import com.mmdi.projet.pivo.webapp.vaadin.navigator.NavigatorEventHandler;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents.ComponentDetachListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.CloseHandler;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.themes.ValoTheme;

public class Tabs extends TabSheet implements View, SelectedTabChangeListener,
		CloseHandler, ComponentDetachListener, TabsCallback, PivoEventHandler {

	private static final long serialVersionUID = 5672663000761618207L;

	private static final Logger LOGGER = LoggerFactory.getLogger(Tabs.class);

	private static final String PARAMS_URI_FRAGMENT = "params";

	private final DataPublisher<Tabs, TabsCallback> dataPublisherToModuleUiTopic;
	private final DataPublisher<Tabs, TabsCallback> dataPublisherToViewUiTopic;
	private final DataPublisher<Tabs, TabsCallback> dataPublisherToComponentUiTopic;
	private final Publisher<Tabs> publisherToNavigatorTopic;

	private final String sessionId;
	private final List<String> currentUserRoles;

	private Boolean keepView = false;

	public Tabs(final EventAdminServiceListener eventAdminServiceListener,
			final String sessionId, final List<String> currentUserRoles) {
		this.sessionId = sessionId;
		this.currentUserRoles = currentUserRoles;
		this.dataPublisherToModuleUiTopic = eventAdminServiceListener
				.getDataPublisher(this, TabsCallback.class, TOPIC_MODULE_UI);
		this.dataPublisherToViewUiTopic = eventAdminServiceListener
				.getDataPublisher(this, TabsCallback.class, TOPIC_VIEW_UI);
		this.dataPublisherToComponentUiTopic = eventAdminServiceListener
				.getDataPublisher(this, TabsCallback.class, TOPIC_COMPONENT_UI);
		this.publisherToNavigatorTopic = eventAdminServiceListener
				.getPublisher(this, TOPIC_NAVIGATOR);
	}

	public void init() {
		this.setSizeFull();
		this.addSelectedTabChangeListener(this);
		this.setCloseHandler(this);
		this.addStyleName(ValoTheme.TABSHEET_FRAMED);
		this.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);

		Dictionary<String, Object> props = new Hashtable<>();
		props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_TABS);
		props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_STARTED);
		props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);

		dataPublisherToViewUiTopic.publishEventDataAndDictionnarySynchronously(
				this, props);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		LOGGER.debug("enter : " + event);
		String parameters = event.getParameters();
		StringTokenizer st = new StringTokenizer(parameters, "/");
		String tabHashSt = null;
		String moduleUiCode = "";
		String viewUiCode = "";

		int count = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (count == 0) {
				tabHashSt = token;
			}
			if (count == 2) {
				moduleUiCode = token;
			}
			if (count == 4) {
				viewUiCode = token;
			}
			count++;
		}
		if ("NEW".equalsIgnoreCase(tabHashSt)
				&& StringUtils.isNotBlank(moduleUiCode)
				&& StringUtils.isNotBlank(viewUiCode)) {
			Dictionary<String, Object> props = new Hashtable<>();

			props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_TABS);
			props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_NEW);
			props.put(PROPERTY_KEY_MODULE_UI_CODE, moduleUiCode);
			props.put(PROPERTY_KEY_VIEW_UI_CODE, viewUiCode);
			props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
			props.put(PROPERTY_KEY_PORTAL_CURRENT_USER_ROLES, currentUserRoles);

			addExtractedParams(parameters, props);

			dataPublisherToModuleUiTopic
					.publishEventDataAndDictionnarySynchronously(this, props);
		} else if (StringUtils.isNumeric(tabHashSt)) {
			Integer tabHash = new Integer(tabHashSt);
			Integer numberOfTab = this.getComponentCount();
			for (int i = 0; i < numberOfTab; i++) {
				Tab tab = this.getTab(i);
				if (tab != null && tab.hashCode() == tabHash) {
					this.setSelectedTab(tab);
					break;
				}
			}
		}
	}

	@Override
	public int addView(FontIcon icon, Boolean closable, Component moduleUiView) {
		Tab tab = this.getTab(moduleUiView);
		int tabHash = -1;
		if (tab != null) {
			tabHash = tab.hashCode();
			tab.setCaption(moduleUiView.getCaption());
			tab.setIcon(icon);
			tab.setClosable(closable);
			publisherToNavigatorTopic
					.publishEventSynchronously(NavigatorEventHandler
							.getNavigateEventProperty("container/" + tabHash,
									sessionId));
		} else {
			tab = this.addTab(moduleUiView, moduleUiView.getCaption());
			tab.setIcon(icon);
			tab.setClosable(closable);
			tabHash = tab.hashCode();
			publisherToNavigatorTopic
					.publishEventSynchronously(NavigatorEventHandler
							.getNavigateEventProperty("container/" + tabHash,
									sessionId));
		}
		return tabHash;
	}

	@Override
	public void componentDetachedFromContainer(ComponentDetachEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabClose(TabSheet tabsheet, Component tabContent) {

		keepView = Boolean.FALSE;
		Tab tab = tabsheet.getTab(tabContent);
		Integer tabHash = tab.hashCode();

		Dictionary<String, Object> props = new Hashtable<>();
		props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_TABS);
		props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_ASKING_CLOSED);
		props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
		props.put(PROPERTY_KEY_TAB_HASH, tabHash);

		dataPublisherToComponentUiTopic
				.publishEventDataAndDictionnarySynchronously(this, props);
		dataPublisherToViewUiTopic.publishEventDataAndDictionnarySynchronously(
				this, props);
		dataPublisherToModuleUiTopic
				.publishEventDataAndDictionnarySynchronously(this, props);

		if (!keepView) {
			removeView(tabHash);
		}
	}

	@Override
	public void keepView() {
		keepView = Boolean.TRUE;
	}

	@Override
	public void selectedTabChange(SelectedTabChangeEvent event) {
		TabSheet tabSheet = event.getTabSheet();
		Tab tab = tabSheet.getTab(tabSheet.getSelectedTab());
		if (tab != null) {
			publisherToNavigatorTopic
					.publishEventSynchronously(NavigatorEventHandler
							.getNavigateEventProperty(
									"container/" + tab.hashCode(), sessionId));
		}
	}

	@Override
	public List<ServiceRegistration<?>> registerEventHandlerItself(
			BundleContext bundleContext) {
		List<ServiceRegistration<?>> srList = new ArrayList<>();
		try {
			srList = AbstractEventHandler.registerEventHandler(bundleContext,
					this);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return srList;
	}

	@Subscriber(topic = TOPIC_TABS, filter = "(&(" + PROPERTY_KEY_EVENT_CONTEXT
			+ "=" + EVENT_CONTEXT_VIEW_UI + ")(" + PROPERTY_KEY_EVENT_NAME
			+ "=" + EVENT_NAME_ASKING_CLOSED + ")(" + PROPERTY_KEY_TAB_HASH
			+ "=*)(" + PROPERTY_KEY_PORTAL_SESSION_ID + "=*))")
	public void removeView(
			@EventProperty(propKey = PROPERTY_KEY_TAB_HASH, required = true) Integer tabHash,
			@EventProperty(propKey = PROPERTY_KEY_PORTAL_SESSION_ID, required = true) String sessionId) {
		if (sessionId.equals(this.sessionId)) {
			removeView(tabHash);
		}
	}

	private void removeView(Integer tabHash) {
		Integer numberOfTab = this.getComponentCount();
		for (int i = 0; i < numberOfTab; i++) {
			Tab tab = this.getTab(i);
			if (tab != null && tab.hashCode() == tabHash) {

				this.removeTab(tab);

				Dictionary<String, Object> props = new Hashtable<>();
				props.put(PROPERTY_KEY_EVENT_CONTEXT, EVENT_CONTEXT_TABS);
				props.put(PROPERTY_KEY_EVENT_NAME, EVENT_NAME_CLOSED);
				props.put(PROPERTY_KEY_PORTAL_SESSION_ID, sessionId);
				props.put(PROPERTY_KEY_TAB_HASH, tabHash);

				dataPublisherToComponentUiTopic
						.publishEventSynchronously(props);
				dataPublisherToViewUiTopic.publishEventSynchronously(props);
				dataPublisherToModuleUiTopic.publishEventSynchronously(props);

				break;
			}
		}
	}

	private void addExtractedParams(String fragment,
			Dictionary<String, Object> props) {
		Map<String, Object> mapParams = new HashMap<>();
		if (fragment != null && fragment.contains(PARAMS_URI_FRAGMENT)) {
			String paramsUriFragement = fragment.split(PARAMS_URI_FRAGMENT
					+ "/")[1];
			if (StringUtils.isNotBlank(paramsUriFragement)) {
				mapParams = addParsedParameters(paramsUriFragement, props);
			}
		}
		props.put(PROPERTY_KEY_PARAMS, mapParams);
	}

	/**
	 * @param parameters
	 * @param paramsAsArray
	 */
	private Map<String, Object> addParsedParameters(String paramsFromUri,
			Dictionary<String, Object> props) {
		String[] paramsAsArray = paramsFromUri.split("/");
		Map<String, Object> mapParams = new HashMap<>();
		String currentKey = null;
		for (int i = 0; i < paramsAsArray.length; i++) {
			if (i % 2 == 0) {
				currentKey = paramsAsArray[i];
			} else {
				mapParams.put(currentKey, paramsAsArray[i]);
			}
		}
		return mapParams;
	}

}
