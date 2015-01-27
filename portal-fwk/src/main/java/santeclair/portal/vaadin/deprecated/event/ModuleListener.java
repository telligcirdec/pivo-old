package santeclair.portal.vaadin.deprecated.event;

import java.util.List;

import santeclair.portal.vaadin.deprecated.module.uri.ModuleUriFragment;

@Deprecated
public class ModuleListener {
	private ModuleUriFragment moduleUriFragment;
	private List<UIListener<UIEvent<?, ?>>> listeners;

	public ModuleListener(ModuleUriFragment moduleUriFragment, List<UIListener<UIEvent<?, ?>>> listeners) {
		this.listeners = listeners;
		this.moduleUriFragment = moduleUriFragment;
	}

	/**
	 * Méthode de lecture de l'attribut : {@link ModuleListener#listeners}
	 * 
	 * @return listeners
	 */
	public List<UIListener<UIEvent<?, ?>>> getListeners() {
		return listeners;
	}

	public void publishEvent(UIEvent<?, ?> event) {
		if (moduleUriFragment.getModuleCode().equals(event.getTarget()) || moduleUriFragment.getContainerId().equals(event.getModuleId())) {
			for (UIListener<UIEvent<?, ?>> listener : listeners) {
				listener.onApplicationEvent(event);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleUriFragment.getContainerId() == null) ? 0 : moduleUriFragment.getContainerId().hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModuleListener other = (ModuleListener) obj;
		if (moduleUriFragment.getContainerId() == null) {
			if (other.moduleUriFragment.getContainerId() != null)
				return false;
		} else if (!moduleUriFragment.getContainerId().equals(other.moduleUriFragment.getContainerId()))
			return false;
		return true;
	}
}
