package santeclair.lunar.framework.aspect.exception;

public class TestInfoException extends Exception implements InfoException {

    /**
     * 
     */
    private static final long serialVersionUID = 257158663830567451L;

    @Override
    public String getInfo() {
        return "Ceci est un test";
    }

}
