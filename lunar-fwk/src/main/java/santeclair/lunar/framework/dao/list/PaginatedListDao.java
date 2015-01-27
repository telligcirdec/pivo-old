/**
 *
 */
package santeclair.lunar.framework.dao.list;

import java.util.Collection;

import santeclair.lunar.framework.bean.list.PaginatedListBean;


/**
 * @author yhenry
 *
 */
public interface PaginatedListDao {

	public Integer countDataInPaginatedList(Object... args);

	public Collection<?> findDataInPaginatedList(PaginatedListBean paginatedListBean, Object... args);

}
