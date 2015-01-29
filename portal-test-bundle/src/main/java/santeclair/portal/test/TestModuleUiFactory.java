package santeclair.portal.test;

import java.util.List;

import org.apache.felix.ipojo.annotations.Component;

import santeclair.portal.vaadin.module.ModuleUiFactory;

import com.vaadin.server.FontIcon;

@Component
public class TestModuleUiFactory implements ModuleUiFactory<TestModuleUi> {

    @Override
    public Boolean isSeveralModuleAllowed() {
        return false;
    }

    @Override
    public String getCode() {
        return "TEST";
    }

    @Override
    public String getName() {
        return "Module de Test";
    }

    @Override
    public FontIcon getIcon() {
        return null;
    }

    @Override
    public Integer displayOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isCloseable(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean openOnInitialization(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean securityCheck(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TestModuleUi buid(List<String> roles) {
        // TODO Auto-generated method stub
        return null;
    }

}
