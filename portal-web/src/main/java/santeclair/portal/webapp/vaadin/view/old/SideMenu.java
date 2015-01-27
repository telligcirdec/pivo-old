package santeclair.portal.webapp.vaadin.view.old;


/**
 * Template avec le menu du portail sur le coté.
 * 
 * @author tsensebe
 * 
 */
public class SideMenu {// implements Button.ClickListener, Serializable
/*
 * private static final long serialVersionUID = -283950529916636684L;
 * 
 * private static final Logger LOGGER = LoggerFactory.getLogger(SideMenu.class);
 * 
 * private final CustomLayout sidebarLayout; private final HorizontalLayout
 * mainLayout; private final VerticalLayout buttonContainer; private final
 * Map<String, Component> moduleFactoryButtons = new HashMap<>();
 * 
 * private Navigator navigator; private TabsView tabsView; private
 * ApplicationEventPublisher applicationEventPublisher;
 * 
 * public SideMenu() { tabsView = new TabsView(this); sidebarLayout = new
 * CustomLayout("sidebarLayout"); buttonContainer = new VerticalLayout();
 * mainLayout = new HorizontalLayout(); }
 * 
 * public void init() { UI ui = UI.getCurrent(); applicationEventPublisher = new
 * ApplicationEventPublisherImpl(); navigator = new Navigator(ui, tabsView);
 * navigator.addView("", tabsView); navigator.addView("container", tabsView);
 * 
 * setErrorHandler();
 * 
 * mainLayout.setSizeFull(); sidebarLayout.setSizeFull();
 * sidebarLayout.addComponent(buttonContainer, "buttonLayout");
 * buttonContainer.setSizeFull(); mainLayout.addComponent(sidebarLayout);
 * mainLayout.addComponent(tabsView.getModuleContainer());
 * mainLayout.setExpandRatio(sidebarLayout, 0.14f);
 * mainLayout.setExpandRatio(tabsView.getModuleContainer(), 0.86f); }
 * 
 * public void addModuleButton(ModuleFactory<?> moduleFactory) { String
 * moduleCode = moduleFactory.getCode(); final Button menuComponent; final
 * VerticalLayout secoundaryButonVerticalLayout = new VerticalLayout();
 * secoundaryButonVerticalLayout.setStyleName("menu-button-layout"); final
 * List<PresenterName> menuView = moduleFactory.getMenuView(roles);
 * 
 * if (menuView != null && menuView.size() > 1) { for (PresenterName
 * presenterName : menuView) { Button secoundaryButton = new
 * Button(presenterName.getLibelle()); secoundaryButton.addClickListener(this);
 * secoundaryButton.setId(moduleCode + "|" + presenterName);
 * secoundaryButton.setSizeFull();
 * secoundaryButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
 * secoundaryButonVerticalLayout.addComponent(secoundaryButton);
 * secoundaryButonVerticalLayout.setComponentAlignment(secoundaryButton,
 * Alignment.MIDDLE_RIGHT); } final AnimatorProxy proxy = new AnimatorProxy();
 * buttonContainer.addComponent(proxy);
 * 
 * menuComponent = new Button();
 * 
 * menuComponent.addClickListener(new Button.ClickListener() { private static
 * final long serialVersionUID = -283950529916638684L;
 * 
 * private Boolean open = false;
 * 
 * @Override public void buttonClick(ClickEvent event) { if (!open) { int index
 * = buttonContainer.getComponentIndex(menuComponent); int layoutIndex =
 * buttonContainer.getComponentIndex(secoundaryButonVerticalLayout); if
 * (layoutIndex == -1) {
 * buttonContainer.addComponent(secoundaryButonVerticalLayout, ++index);
 * 
 * } menuComponent.removeStyleName("btn-menu-close");
 * menuComponent.addStyleName("btn-menu-open");
 * proxy.animate(secoundaryButonVerticalLayout, AnimType.ROLL_DOWN_OPEN_POP);
 * open = true; } else { menuComponent.removeStyleName("btn-menu-open");
 * menuComponent.addStyleName("btn-menu-close");
 * proxy.animate(secoundaryButonVerticalLayout, AnimType.ROLL_UP_CLOSE_REMOVE);
 * open = false; }
 * 
 * } }); buttonContainer.addComponent(menuComponent); } else { menuComponent =
 * new Button(); menuComponent.addClickListener(this);
 * menuComponent.setId(moduleCode); // Utilie uniquement pour que tous les
 * boutons soient séparé par un // même espace final AnimatorProxy proxy = new
 * AnimatorProxy(); buttonContainer.addComponent(proxy);
 * buttonContainer.addComponent(menuComponent); }
 * 
 * menuComponent.setStyleName(ValoTheme.BUTTON_BORDERLESS);
 * menuComponent.addStyleName("button-heading");
 * menuComponent.setCaption(moduleFactory.getName());
 * menuComponent.setIcon(moduleFactory.getIconName());
 * menuComponent.setWidth(100, Unit.PERCENTAGE);
 * buttonContainer.setComponentAlignment(menuComponent,
 * Alignment.MIDDLE_CENTER); moduleFactoryButtons.put(moduleCode,
 * menuComponent); }
 * 
 * @Override public void buttonClick(ClickEvent event) { String[] moduleId =
 * event.getButton().getId().split("\\|"); String moduleCode = moduleId[0];
 * String moduleView = (moduleId.length > 1 ? moduleId[1] : "");
 * viewButtonClick(moduleCode, moduleView); }
 * 
 * private void viewButtonClick(String moduleCode, String moduleView) {
 * ViewUriFragment viewUriFragment = null; if
 * (tabsView.getModuleOnlyOneInstance().contains(moduleCode)) { viewUriFragment
 * = tabsView.findUniqueModuleByModuleCode(moduleCode).getViewUriFragment();
 * viewUriFragment.setViewCode(moduleView); } else { viewUriFragment = new
 * ViewUriFragment(ContainerUriFragment.NEW, moduleCode, moduleView); }
 * 
 * navigator.navigateTo(viewUriFragment.toString()); }
 * 
 * public ComponentContainer getButtonMenuContainer() { return buttonContainer;
 * }
 * 
 * public Layout getMainLayout() { return mainLayout; }
 * 
 * // Abstract
 * 
 * public synchronized void removeModuleFactory(ModuleFactory<?> moduleFactory)
 * { moduleFactories.remove(moduleFactory.getModuleMapKey());
 * tabsView.changeModuleState(moduleFactory.getCode(), ModuleState.STOPPED);
 * Component buttonToRemoved =
 * moduleFactoryButtons.get(moduleFactory.getCode());
 * getButtonMenuContainer().removeComponent(buttonToRemoved);
 * moduleFactoryButtons.remove(moduleFactory.getCode()); }
 * 
 * public synchronized void addModuleFactory(ModuleFactory<?> moduleFactory) {
 * moduleFactories.put(moduleFactory.getModuleMapKey(), moduleFactory);
 * getButtonMenuContainer().removeAllComponents(); for (ModuleMapKey key :
 * moduleFactories.keySet()) { addModuleButton(moduleFactories.get(key)); } }
 * 
 * // public Map<ModuleMapKey, ModuleFactory<? extends ModuleUi>> //
 * getModuleFactories() { // return moduleFactories; // }
 * 
 * public Navigator getNavigator() { return navigator; }
 * 
 * public ApplicationEventPublisher getApplicationEventPublisher() { return
 * applicationEventPublisher; }
 */
}
