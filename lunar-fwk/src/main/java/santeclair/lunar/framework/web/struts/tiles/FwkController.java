package santeclair.lunar.framework.web.struts.tiles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.tiles.ComponentControllerSupport;

public abstract class FwkController extends ComponentControllerSupport {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

}
