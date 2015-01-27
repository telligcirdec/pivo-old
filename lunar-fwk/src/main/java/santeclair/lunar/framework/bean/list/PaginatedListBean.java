/**
 *
 */
package santeclair.lunar.framework.bean.list;

import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * @author yhenry
 * 
 */
public class PaginatedListBean {

    private Integer page;
    private Integer nbTotalData;
    private Integer pageSize;

    private Collection<?> partialListData;

    public Integer getNbTotalData() {
        return nbTotalData;
    }

    public void setNbTotalData(Integer nbTotalData) {
        this.nbTotalData = nbTotalData;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Collection<?> getPartialListData() {
        return partialListData;
    }

    public void setPartialListData(Collection<?> partialListData) {
        this.partialListData = partialListData;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(nbTotalData).append(page).append(pageSize).append(partialListData).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PaginatedListBean other = (PaginatedListBean) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(nbTotalData, other.nbTotalData).append(page, other.page).append(pageSize, other.pageSize).append(partialListData,
                        other.partialListData);

        return builder.isEquals();
    }
}
