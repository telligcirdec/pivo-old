/**
 *
 */
package santeclair.lunar.framework.web.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author yhenry
 * 
 */
public interface PaginatedListAction {

    public ActionForward paginatedList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response);
}
