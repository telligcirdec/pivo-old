/**
 *
 */
package santeclair.lunar.framework.web.struts.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.struts.DelegatingActionProxy;

/**
 * @author yhenry
 * 
 */
public class DelegatingMappingDispatchActionProxy extends DelegatingActionProxy {

    private static final Logger LOG = LoggerFactory.getLogger(DelegatingMappingDispatchActionProxy.class);

    protected Class<?>[] types = {ActionMapping.class, ActionForm.class, HttpServletRequest.class, HttpServletResponse.class };

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        MappingDispatchAction delegateMappingDispatchAction = getDelegateMappingDispatchAction(mapping);
        String methodName = getParameter(mapping, form, request, response);

        Method method = delegateMappingDispatchAction.getClass().getMethod(methodName, types);
        Object[] args = {mapping, form, request, response };
        return (ActionForward) method.invoke(delegateMappingDispatchAction, args);
    }

    /**
     * <p>
     * Returns the parameter value.
     * </p>
     * 
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The <code>ActionMapping</code> parameter's value
     * @throws Exception if the parameter is missing.
     * 
     * @see org.apache.struts.actions.DispatchAction
     */
    protected String getParameter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
                    throws ServletException {

        // Identify the request parameter containing the method name
        String parameter = mapping.getParameter();

        if (parameter == null) {
            String message = "mapping.getParameter() is null";
            LOG.error(message);
            throw new ServletException(message);
        }
        return parameter;
    }

    /**
     * @see org.apache.struts.actions.DispatchAction
     */
    protected MappingDispatchAction getDelegateMappingDispatchAction(ActionMapping mapping) {
        WebApplicationContext wac = getWebApplicationContext(getServlet(), mapping.getModuleConfig());
        return wac.getBean(determineActionBeanName(mapping), MappingDispatchAction.class);
    }

}
