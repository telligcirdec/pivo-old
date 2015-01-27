package santeclair.portal.vaadin.deprecated.module.api;

import java.io.Serializable;

import santeclair.portal.vaadin.deprecated.module.ModuleUi;

@Deprecated
public interface ModuleFactoryServiceListener extends Serializable {

	/**
	 * Permet d'enregistrer une factory d'un module
	 * 
	 * @param moduleFactory
	 */
	public void moduleFactoryRegistered(ModuleFactory<? extends ModuleUi> moduleFactory);

	/**
	 * Permet de supprimer une factory d'un module
	 * 
	 * @param moduleFactory
	 */
	public void moduleFactoryUnregistered(ModuleFactory<? extends ModuleUi> moduleFactory);
}