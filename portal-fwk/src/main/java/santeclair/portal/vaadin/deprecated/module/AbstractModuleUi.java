package santeclair.portal.vaadin.deprecated.module;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;

import santeclair.portal.vaadin.deprecated.event.ApplicationEventPublisher;
import santeclair.portal.vaadin.deprecated.event.ModuleListener;
import santeclair.portal.vaadin.deprecated.event.UIEvent;
import santeclair.portal.vaadin.deprecated.event.UIListener;
import santeclair.portal.vaadin.deprecated.module.uri.ViewUriFragment;
import santeclair.portal.vaadin.deprecated.module.view.CloseViewResult;
import santeclair.portal.vaadin.deprecated.module.view.PresenterName;
import santeclair.portal.vaadin.deprecated.presenter.AbstractModulePresenter;
import santeclair.portal.vaadin.deprecated.presenter.AbstractPresenter;
import santeclair.portal.vaadin.deprecated.presenter.ModulePresenter;
import santeclair.portal.vaadin.deprecated.presenter.Presenter;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.VerticalLayout;

@Deprecated
public abstract class AbstractModuleUi extends VerticalLayout implements ModuleUi {

    private static final long serialVersionUID = -3174843584744206697L;
    // FIXME : à replacer en private une fois la migration MVP de devischir
    // terminé.
    protected Map<String, ModulePresenter<?, ?>> presenterMap = new HashMap<>();
    private ModulePresenter<?, ?> currentPresenter;
    protected ViewUriFragment viewUriFragment;
    protected Navigator navigator;
    protected View container;
    protected ModuleState moduleState;
    protected Map<String, String> parameters;
    protected ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationContext applicationContext;

    public AbstractModuleUi() {
    }

    @Override
    public void init(ViewUriFragment viewUriFragment, Navigator navigator, ApplicationEventPublisher applicationEventPublisher, View container,
                    Map<String, String> parameters) {
        this.viewUriFragment = viewUriFragment;
        this.navigator = navigator;
        this.applicationEventPublisher = applicationEventPublisher;
        initAllPresenter();
        this.container = container;
        this.parameters = parameters;
        this.moduleState = ModuleState.STARTED;
        for (String viewName : presenterMap.keySet()) {
            ViewUriFragment viewUriFragmentForNavigator = new ViewUriFragment(viewUriFragment, viewName);
            this.navigator.addView(viewUriFragmentForNavigator.toString(), container);
        }
        ViewUriFragment viewUriFragmentForNavigator = new ViewUriFragment(viewUriFragment, "");
        this.navigator.addView(viewUriFragmentForNavigator.toString(), container);
        initBeforeView();
        this.setStyleName("application " + getModuleTitle());
        initAfterView();
    }

    @Override
    public ViewUriFragment getViewUriFragment() {
        return viewUriFragment;
    }

    @Override
    public void printView(String viewName) {
        viewName = !StringUtils.isEmpty(viewName) ? viewName : getMainViewName();
        ModulePresenter<?, ?> view = presenterMap.get(viewName);
        if (moduleState.equals(ModuleState.STARTED) || moduleState.equals(ModuleState.PRINTED)) {
            if (view == null) {
                Notification.show("Vous n'avez les droits suffisant pour accéder à la ressource demandée.", Type.WARNING_MESSAGE);
            } else {
                if (Presenter.class.isAssignableFrom(view.getClass())) {
                    this.removeAllComponents();
                    this.addComponent(((Presenter<?, ?>) view).getDisplay().getViewRoot());
                    view.enter(null);
                    currentPresenter = view;
                    viewUriFragment.setViewCode(viewName);
                    moduleState = ModuleState.PRINTED;
                }
            }
        } else if (moduleState.equals(ModuleState.STOPPED)) {
            Notification.show("L'application a été arrêtée. " + "Veuillez noter les informations sur un papier ou en effectuant "
                            + "une capture d'écran afin de vous assurer de ne rien perdre.", Type.ERROR_MESSAGE);
        } else if (moduleState.equals(ModuleState.STOPPING)) {
            Notification.show("L'application est sur le point d'être arrêtée." + " Merci de finir votre saisie et de fermer l'onglet en cours.",
                            Type.WARNING_MESSAGE);
        }
    }

    public void initBeforeView() {

    }

    public void initAfterView() {

    }

    @Override
    public String getCaption() {
        if (currentPresenter != null) {
            return getModuleTitle() + " - " + currentPresenter.getPresenterTitle();
        }
        return "";
    }

    @Override
    public final void changeCurrentModuleState(ModuleState newModuleState) {
        this.moduleState = newModuleState;
    }

    @Override
    public ModuleState currentModuleState() {
        return moduleState;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    @Override
    public abstract String getMainViewName();

    public abstract String getModuleTitle();

    public abstract String getPresenterPackage();

    @Override
    public String getModuleCode() {
        return viewUriFragment.getModuleCode();
    }

    @Override
    public CloseViewResult closeModule() {
        unregisterAllListener();

        return new CloseViewResult();
    }

    /**
     * Nettoyage des listeners de l'ordonanceur.
     */
    protected void unregisterAllListener() {
        ModuleListener moduleListener = new ModuleListener(viewUriFragment.getModuleUriFragment(), null);
        applicationEventPublisher.unregisterModule(moduleListener);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void initAllPresenter() {
        FilterBuilder filterBuilder = new FilterBuilder().add(FilterBuilder.parse(createIncludePattern(getPresenterPackage())));
        Set<URL> urls = new HashSet<URL>();
        for (String presenterPackage : getPresenterPackage().split(",")) {
            urls.addAll(ClasspathHelper.forPackage(presenterPackage, this.getClass().getClassLoader()));
        }
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.filterInputsBy(filterBuilder);
        builder.setUrls(urls);
        builder.setScanners(new SubTypesScanner());
        builder.addClassLoader(this.getClass().getClassLoader());
        Reflections reflections = new Reflections(builder);
        Set<Class<? extends AbstractPresenter>> presentersClassList = reflections.getSubTypesOf(AbstractPresenter.class);
        presentersClassList.addAll(reflections.getSubTypesOf(AbstractModulePresenter.class));
        List<UIListener<UIEvent<?, ?>>> listeners = new ArrayList<UIListener<UIEvent<?, ?>>>();
        for (Class<? extends Presenter> presenterClass : presentersClassList) {
            try {
                Presenter presenter = applicationContext.getBean(presenterClass);
                presenter.setModuleUi(this);
                if (AbstractModulePresenter.class.isAssignableFrom(presenterClass)) {
                    ModulePresenter<?, ?> moduleView = (AbstractModulePresenter<?, ?>) presenter;
                    presenterMap.put(moduleView.getPresenterName().name(), moduleView);
                    moduleView.setModuleUriFragment(viewUriFragment);
                    moduleView.setNavigator(navigator);
                }
                listeners.add(presenter);
            } catch (AccessDeniedException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(listeners, new Comparator<UIListener<UIEvent<?, ?>>>() {
            @Override
            public int compare(UIListener o1, UIListener o2) {
                return o1.order().compareTo(o2.order());
            }
        });

        ModuleListener moduleListener = new ModuleListener(viewUriFragment.getModuleUriFragment(), listeners);
        applicationEventPublisher.registerModule(moduleListener);
    }

    private String createIncludePattern(String sPackage) {
        StringBuilder includePattern = new StringBuilder();
        boolean first = true;
        for (String presenterPackage : sPackage.split(",")) {
            if (!first) {
                includePattern.append(",");
            } else {
                first = false;
            }
            includePattern.append("+");
            includePattern.append(presenterPackage.replaceAll("\\.", "\\\\."));
            includePattern.append(".*");
        }
        return includePattern.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void publishEvent(UIEvent<?, ?> event) {
        if (event.getTarget() == null) {
            event.setModuleId(viewUriFragment.getContainerId());
        }
        applicationEventPublisher.publishEvent(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateTo(PresenterName cible, Map<String, String> parameters) {
        ViewUriFragment newViewUriFragment = new ViewUriFragment(viewUriFragment.getModuleUriFragment(), cible.name());

        navigator.navigateTo(newViewUriFragment.toString() + createParametersString(parameters));
    }

    /**
     * Convertir la Map de paramètre en chaine de caractère
     * 
     * @param parameters
     * @return
     */
    private String createParametersString(Map<String, String> parameters) {
        if (parameters == null) {
            return "";
        }

        StringBuilder sbParameters = new StringBuilder();
        for (Entry<String, String> parameter : parameters.entrySet()) {
            sbParameters.append("/").append(parameter.getKey()).append("/").append(parameter.getValue());
        }
        return sbParameters.toString();
    }

    @Override
    public FontIcon getIconName() {
        return null;
    }
}
