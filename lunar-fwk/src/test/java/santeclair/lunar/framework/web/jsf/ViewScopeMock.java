package santeclair.lunar.framework.web.jsf;

public class ViewScopeMock extends ViewScope {

    @Override
    protected FacesContextHelper getFacesContextHelper() {
        return FacesContextHelperMock.getInstance();
    }

}
