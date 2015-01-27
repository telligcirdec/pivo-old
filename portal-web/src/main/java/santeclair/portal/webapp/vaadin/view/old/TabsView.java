package santeclair.portal.webapp.vaadin.view.old;


public class TabsView {// implements SelectedTabChangeListener, CloseHandler,
						// ComponentDetachListener, View, ViewDisplay {
/*
 * private static final Logger LOGGER = LoggerFactory
 * .getLogger(TabsView.class); private static final String PARAMS_URI_FRAGMENT =
 * "params"; private static final long serialVersionUID = 2517129660651168945L;
 * 
 * private final TabSheet tabSheet = new TabSheet(); private final Map<String,
 * Integer> containerTabPositionMap; private final Set<String>
 * moduleOnlyOneInstance = new HashSet<>(); private final Map<ModuleUriFragment,
 * ModuleUi> moduleUiMap = new HashMap<>(); private Integer lastContainerId = 0;
 * 
 * private ModuleUi moduleUi; private SideMenu sideMenu;
 * 
 * /** La fenêtre pop in qui s'affiche quand un avertissement de fermeture est à
 * afficher.
 * 
 * private Window popInInformation;
 * 
 * public TabsView(SideMenu sideMenu) { this.sideMenu = sideMenu;
 * containerTabPositionMap = new HashMap<>(); tabSheet.setSizeFull();
 * tabSheet.addSelectedTabChangeListener(this); tabSheet.setCloseHandler(this);
 * tabSheet.addStyleName(ValoTheme.TABSHEET_FRAMED);
 * tabSheet.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR); }
 * 
 * @Override public void showView(View view) {
 * 
 * }
 * 
 * @Override public void selectedTabChange(SelectedTabChangeEvent event) {
 * TabSheet tabSheet = (TabSheet) event.getSource(); ModuleUi selectedModuleUi =
 * (ModuleUi) tabSheet.getSelectedTab(); ViewUriFragment selectedViewUriFragment
 * = selectedModuleUi .getViewUriFragment(); String uriFragment =
 * Page.getCurrent().getUriFragment(); if (uriFragment == null) {
 * LOGGER.debug("Before : Entering portal"); LOGGER.debug("After : " +
 * selectedViewUriFragment.toString());
 * 
 * Page.getCurrent().setUriFragment( "!" + selectedViewUriFragment.toString(),
 * false); } else { ViewUriFragment viewUriFragment = ViewUriFragment
 * .newViewUriFragment(uriFragment); if (viewUriFragment != null &&
 * selectedViewUriFragment != null &&
 * !viewUriFragment.equals(selectedViewUriFragment)) { LOGGER.debug("Before : "
 * + viewUriFragment.toString()); LOGGER.debug("After : " +
 * selectedViewUriFragment.toString());
 * 
 * Page.getCurrent().setUriFragment( "!" + selectedViewUriFragment.toString(),
 * false); } } }
 * 
 * public void showContainer(ViewUriFragment viewUriFragment) { Integer
 * containerPosition = containerTabPositionMap.get(viewUriFragment
 * .getContainerId()); if (containerPosition != null) { Tab tab =
 * tabSheet.getTab(containerPosition); if (tab != null) {
 * tabSheet.setSelectedTab(tab); } else { LOGGER.warn(
 * "Vous tentez d'afficher un onglet qui n'existe pas. (Erreur technique)"); } }
 * else { LOGGER.warn("Vous tentez d'afficher un onglet qui n'existe pas"); } }
 * 
 * public void addContent(ModuleUi moduleUi, boolean isCloseable) { Tab tab =
 * tabSheet.addTab(moduleUi, moduleUi.getCaption());
 * tab.setIcon(moduleUi.getIconName()); tab.setClosable(isCloseable);
 * containerTabPositionMap.put(moduleUi.getViewUriFragment() .getContainerId(),
 * tabSheet.getTabPosition(tab)); }
 * 
 * /** Event déclenché lorsque l'on ferme un onglet de la tabsheet de module.
 * Cela doit permettre de décharger le module et de répercuter les modifications
 * de position des tabs sur les autres modules.
 * 
 * @Override public void onTabClose(TabSheet tabsheet, Component tabContent) {
 * boolean closeTab = true; if
 * (ModuleUi.class.isAssignableFrom(tabContent.getClass())) { ModuleUi module =
 * (ModuleUi) tabContent; CloseViewResult closeViewResult =
 * module.closeModule(); closeTab =
 * CloseMessageType.OK.equals(closeViewResult.getType());
 * 
 * // Action de déchargement du module if (closeTab) { unloadModuleUi(tabsheet,
 * module); } else { popInInformation = creerPopInInformation(tabsheet, module,
 * closeViewResult); UI.getCurrent().addWindow(popInInformation);
 * UI.getCurrent().setFocusedComponent(popInInformation); } }
 * 
 * // Suppression du tab if (closeTab) { tabsheet.removeComponent(tabContent); }
 * }
 * 
 * @Override public void enter(ViewChangeEvent event) {
 * 
 * String uriFragement = Page.getCurrent().getUriFragment();
 * 
 * if (uriFragement != null) { ViewUriFragment viewUriFragment = ViewUriFragment
 * .newViewUriFragment(uriFragement); LOGGER.debug("navigate : " +
 * viewUriFragment); String containerId = viewUriFragment.getContainerId(); if
 * (containerId != null) { String codeModule = viewUriFragment.getModuleCode();
 * ModuleFactory<? extends ModuleUi> moduleFactory = sideMenu
 * .getModuleFactories().get( new ModuleMapKey(codeModule, null)); if
 * (moduleFactory != null) { moduleUi = moduleUiMap.get(viewUriFragment
 * .getModuleUriFragment()); if (moduleUi == null &&
 * ContainerUriFragment.NEW.equals(containerId)) { if
 * (!moduleFactory.isSeveralInstanceAllowed() &&
 * moduleOnlyOneInstance.contains(codeModule)) { moduleUi = this
 * .findUniqueModuleByModuleCode(codeModule); } else { moduleUi =
 * createNewContainer(moduleFactory, viewUriFragment.getViewCode(),
 * sideMenu.getNavigator(), sideMenu.getApplicationEventPublisher(),
 * extractParams(uriFragement)); } viewUriFragment =
 * moduleUi.getViewUriFragment(); } } else {
 * LOGGER.warn("Vous tentez d'afficher un module qui n'existe pas (factory null)"
 * ); } if (moduleUi != null) {
 * moduleUi.setParameters(extractParams(uriFragement)); if
 * (moduleUi.currentModuleState().equals( ModuleState.PRINTED)) {
 * moduleUi.printView(viewUriFragment.getViewCode());
 * showContainer(viewUriFragment); } else if
 * (moduleUi.currentModuleState().equals( ModuleState.STARTED)) {
 * moduleUi.printView(viewUriFragment.getViewCode()); addContent(moduleUi,
 * moduleFactory.isCloseable()); showContainer(viewUriFragment); } } else {
 * LOGGER
 * .warn("Vous tentez d'afficher un module qui n'existe pas (moduleUi null)"); }
 * } else { LOGGER.warn("Veuillez indiquer un identifiant de container"); } }
 * else { // Ouverture de la page d'accueil ModuleFactory<? extends ModuleUi>
 * moduleFactoryToOpen = null; for (Entry<ModuleMapKey, ModuleFactory<? extends
 * ModuleUi>> entry : sideMenu .getModuleFactories().entrySet()) {
 * ModuleFactory<? extends ModuleUi> moduleFactory = entry .getValue(); if
 * (moduleFactory.openOnInitialisation()) { moduleFactoryToOpen = moduleFactory;
 * if (!moduleFactory.isSeveralInstanceAllowed() &&
 * moduleOnlyOneInstance.contains(moduleFactory .getCode())) { moduleUi = this
 * .findUniqueModuleByModuleCode(moduleFactory .getCode()); } else { moduleUi =
 * createNewContainer(moduleFactory, null, sideMenu.getNavigator(),
 * sideMenu.getApplicationEventPublisher(), extractParams(uriFragement)); } } }
 * 
 * if (moduleFactoryToOpen != null && moduleUi != null) { if
 * (moduleUi.currentModuleState().equals(ModuleState.PRINTED)) {
 * moduleUi.printView(""); showContainer(moduleUi.getViewUriFragment()); } else
 * if (moduleUi.currentModuleState().equals( ModuleState.STARTED)) {
 * moduleUi.printView(""); addContent(moduleUi,
 * moduleFactoryToOpen.isCloseable());
 * showContainer(moduleUi.getViewUriFragment()); } } }
 * 
 * if (uriFragement != null) { ViewUriFragment viewUriFragment = ViewUriFragment
 * .newViewUriFragment(uriFragement); String containerId =
 * viewUriFragment.getContainerId(); if (containerId != null) { String
 * codeModule = viewUriFragment.getModuleCode(); ModuleFactory<? extends
 * ModuleUi> moduleFactory = sideMenu .getModuleFactories().get( new
 * ModuleMapKey(codeModule, null)); if (moduleFactory != null) { moduleUi =
 * moduleUiMap.get(viewUriFragment .getModuleUriFragment()); if (moduleUi ==
 * null && ContainerUriFragment.NEW.equals(containerId)) { if
 * (!moduleFactory.isSeveralInstanceAllowed() &&
 * moduleOnlyOneInstance.contains(codeModule)) { moduleUi = this
 * .findUniqueModuleByModuleCode(codeModule); } else { moduleUi =
 * createNewContainer(moduleFactory, viewUriFragment.getViewCode(),
 * sideMenu.getNavigator(), sideMenu.getApplicationEventPublisher(),
 * extractParams(uriFragement)); } viewUriFragment =
 * moduleUi.getViewUriFragment(); } } if (moduleUi != null) { if
 * (moduleUi.currentModuleState().equals( ModuleState.PRINTED)) {
 * tabSheet.getTab(moduleUi).setCaption( moduleUi.getCaption()); } } } } }
 * 
 * public Component getModuleContainer() { return tabSheet; }
 * 
 * @Override public void componentDetachedFromContainer(ComponentDetachEvent
 * event) { event.getSource(); if (event.getDetachedComponent() instanceof
 * ModuleUi) { moduleUi = (ModuleUi) event.getDetachedComponent();
 * moduleOnlyOneInstance.remove(moduleUi.getViewUriFragment() .getModuleCode());
 * moduleUiMap.remove(moduleUi.getViewUriFragment() .getModuleUriFragment()); }
 * 
 * }
 * 
 * public void changeModuleState(String codeModule, ModuleState moduleState) {
 * 
 * }
 * 
 * public ModuleUi findUniqueModuleByModuleCode(String moduleCode) { if
 * (moduleOnlyOneInstance.contains(moduleCode)) { for (ModuleUriFragment
 * moduleUriFramgment : moduleUiMap.keySet()) { if
 * (moduleUriFramgment.getModuleCode().equals(moduleCode)) { return
 * moduleUiMap.get(moduleUriFramgment); } } } return null; }
 * 
 * public Set<String> getModuleOnlyOneInstance() { return moduleOnlyOneInstance;
 * }
 * 
 * /**
 * 
 * @param tabsheet
 * 
 * @param tabContent
 * 
 * @param module
 * 
 * private void unloadModuleUi(TabSheet tabsheet, ModuleUi module) { //
 * Déchargement du module ModuleUriFragment moduleUriFragment =
 * module.getViewUriFragment() .getModuleUriFragment();
 * moduleUiMap.remove(moduleUriFragment);
 * moduleOnlyOneInstance.remove(moduleUriFragment.getModuleCode());
 * 
 * // Suppression de l'entré dans la map de position des tabs. int
 * closedTabPosition = tabsheet .getTabPosition(tabsheet.getTab(module)); for
 * (Entry<String, Integer> entryContainerTabPosition : containerTabPositionMap
 * .entrySet()) { int tabPosition =
 * entryContainerTabPosition.getValue().intValue(); if (tabPosition >
 * closedTabPosition) { entryContainerTabPosition.setValue(tabPosition - 1); } }
 * containerTabPositionMap.remove(module.getViewUriFragment()
 * .getContainerId()); }
 * 
 * /** Crée la fenêtre pop in d'information déclencher par le module en cours de
 * fermeture.
 * 
 * private Window creerPopInInformation(final TabSheet tabsheet, final ModuleUi
 * module, CloseViewResult closeViewResult) { final Window popInInformation =
 * new Window("Abandon création devis");
 * 
 * Label label = new Label(closeViewResult.getMessage(), ContentMode.HTML);
 * 
 * HorizontalLayout horizontalLayout = null; if
 * (CloseMessageType.CONFIRMATION_NEEDED.equals(closeViewResult .getType())) {
 * Button boutonOui = new Button("Oui", new ClickListener() { /** Serial Version
 * UID * private static final long serialVersionUID = 1L;
 * 
 * @Override public void buttonClick(ClickEvent event) {
 * popInInformation.close(); unloadModuleUi(tabsheet, module);
 * tabsheet.removeComponent(module); } });
 * boutonOui.setClickShortcut(KeyCode.ENTER);
 * boutonOui.addStyleName(ValoTheme.BUTTON_PRIMARY);
 * 
 * Button boutonNon = new Button("Non", new ClickListener() { /** Serial Version
 * UID * private static final long serialVersionUID = 1L;
 * 
 * @Override public void buttonClick(ClickEvent event) {
 * popInInformation.close(); } }); boutonNon.setClickShortcut(KeyCode.ESCAPE);
 * 
 * horizontalLayout = new HorizontalLayout(boutonOui, boutonNon); } else {
 * Button boutonOk = new Button("Ok", new ClickListener() { /** Serial Version
 * UID * private static final long serialVersionUID = 1L;
 * 
 * @Override public void buttonClick(ClickEvent event) {
 * popInInformation.close(); } }); boutonOk.setClickShortcut(KeyCode.ENTER);
 * 
 * horizontalLayout = new HorizontalLayout(boutonOk); }
 * 
 * horizontalLayout.setSpacing(true);
 * 
 * VerticalLayout verticalLayout = new VerticalLayout(label, horizontalLayout);
 * verticalLayout.setSpacing(true); verticalLayout.setMargin(true);
 * verticalLayout.setStyleName("popup");
 * verticalLayout.setComponentAlignment(horizontalLayout,
 * Alignment.MIDDLE_CENTER);
 * 
 * popInInformation.setContent(verticalLayout); popInInformation.setModal(true);
 * popInInformation.center(); popInInformation.setResizable(false);
 * 
 * return popInInformation; }
 * 
 * private Map<String, String> extractParams(String fragment) { Map<String,
 * String> parameters = new HashMap<>(); if (fragment != null &&
 * fragment.contains(PARAMS_URI_FRAGMENT)) { String paramsUriFragement =
 * fragment.split(PARAMS_URI_FRAGMENT + "/")[1]; if
 * (StringUtils.isNotBlank(paramsUriFragement)) { parameters =
 * parseParameters(paramsUriFragement); } } return parameters; }
 * 
 * /**
 * 
 * @param parameters
 * 
 * @param paramsAsArray
 * 
 * private Map<String, String> parseParameters(String paramsFromUri) { String[]
 * paramsAsArray = paramsFromUri.split("/"); HashMap<String, String> parameters
 * = new HashMap<>(); String currentKey = null; for (int i = 0; i <
 * paramsAsArray.length; i++) { if (i % 2 == 0) { currentKey = paramsAsArray[i];
 * } else { parameters.put(currentKey, paramsAsArray[i]); } } return parameters;
 * }
 * 
 * private ModuleUi createNewContainer( ModuleFactory<? extends ModuleUi>
 * moduleFactory, String viewCode, Navigator navigator,
 * ApplicationEventPublisher applicationEventPublisher, Map<String, String>
 * parameters) { int containerId = lastContainerId++; String moduleCode =
 * moduleFactory.getCode(); ViewUriFragment viewUriFragment = new
 * ViewUriFragment( String.valueOf(containerId), moduleCode,
 * StringUtils.defaultString(viewCode)); ModuleUi moduleUi =
 * moduleFactory.createModule(viewUriFragment, navigator,
 * applicationEventPublisher, this, parameters);
 * moduleUiMap.put(viewUriFragment.getModuleUriFragment(), moduleUi); if
 * (!moduleFactory.isSeveralInstanceAllowed()) {
 * moduleOnlyOneInstance.add(moduleCode); } return moduleUi; }
 */
}
