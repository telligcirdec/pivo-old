package santeclair.portal.vaadin.deprecated.event;

import java.io.Serializable;
import java.util.Map;

import santeclair.portal.vaadin.deprecated.presenter.Presenter;

/**
 * Classe d'événement inter présentateur.
 * 
 * @author ldelemotte
 * 
 * @param <U>
 */
@Deprecated
public class UIEvent<U extends Enum<?>, S extends Presenter<?, ?>> implements Serializable {

	/** Serial version UID */
	private static final long serialVersionUID = 1L;

	private final long timestamp;

	private U eventType;

	private S source;

	private String target;

	private String moduleId;

	/** Donnée pour l'échange entre presentateur. */
	private Object data;
	/** Map de données pour l'échange entre presentateur. */
	private Map<String, Object> dataMap;

	private UIEvent(S source, U eventType) {
		this.source = source;
		this.eventType = eventType;
		this.timestamp = System.currentTimeMillis();
	}

	private UIEvent(S source, String target, U eventType) {
		this.source = source;
		this.target = target;
		this.eventType = eventType;
		this.timestamp = System.currentTimeMillis();
	}

	private <X> UIEvent(X data, S source, U eventType) {
		this(source, eventType);
		this.data = data;
	}

	private <X> UIEvent(X data, S source, String target, U eventType) {
		this(source, target, eventType);
		this.data = data;
	}

	private UIEvent(Map<String, Object> dataMap, S source, U eventType) {
		this(source, eventType);
		this.dataMap = dataMap;
	}

	private UIEvent(Map<String, Object> dataMap, S source, String target, U eventType) {
		this(source, target, eventType);
		this.dataMap = dataMap;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link UIEvent#eventType}
	 * 
	 * @return eventType
	 */
	public U getEventType() {
		return eventType;
	}

	/**
	 * Return the system time in milliseconds when the event happened.
	 */
	public final long getTimestamp() {
		return this.timestamp;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link UIEvent#target}
	 * 
	 * @return target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link UIEvent#moduleId}
	 * 
	 * @return moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * Méthode d'écriture de l'attribut : {@link UIEvent#moduleId}
	 * 
	 * @param moduleId
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public S getSource() {
		return source;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link UIDataEvent#data}
	 * 
	 * @return data
	 */
	@SuppressWarnings("unchecked")
	public <X> X getData() {
		return (X) data;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link UIDataEvent#dataMap}
	 * 
	 * @return dataMap
	 */
	@SuppressWarnings("unchecked")
	public <X> X getData(String key) {
		if (dataMap == null) {
			return null;
		}
		return (X) dataMap.get(key);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>> UIEvent<U, S> createUIEvent(S source, U eventType) {
		return new UIEvent<U, S>(source, eventType);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>> UIEvent<U, S> createUIEvent(S source, String target, U eventType) {
		return new UIEvent<U, S>(source, target, eventType);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>, X> UIEvent<U, S> createUIEvent(X data, S source, U eventType) {
		return new UIEvent<U, S>(data, source, eventType);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>, X> UIEvent<U, S> createUIEvent(X data, S source, String target, U eventType) {
		return new UIEvent<U, S>(data, source, target, eventType);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>> UIEvent<U, S> createUIEvent(Map<String, Object> dataMap, S source, U eventType) {
		return new UIEvent<U, S>(dataMap, source, eventType);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>> UIEvent<U, S> createUIEvent(Map<String, Object> dataMap, S source, String target, U eventType) {
		return new UIEvent<U, S>(dataMap, source, target, eventType);
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>, X> UIEvent<U, S> createUIEventResponse(X data, UIEvent<U, S> request) {
		return new UIEvent<U, S>(data, request.getSource(), request.getEventType());
	}

	public static <U extends Enum<?>, S extends Presenter<?, ?>> UIEvent<U, S> createUIEventResponse(Map<String, Object> dataMap, UIEvent<U, S> request) {
		return new UIEvent<U, S>(dataMap, request.getSource(), request.getEventType());
	}
}
