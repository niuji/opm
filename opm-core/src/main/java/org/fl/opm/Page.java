package org.fl.opm;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-3
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public class Page implements Serializable {
    public static final int DEFAULT_PAGE_SIZE = 10;

    private List results = null;

    private int pageSize = DEFAULT_PAGE_SIZE;

    private int page = 0;

    private int total = 0;

    public List getResults() {
        return results;
    }

    public void setResults(List results) {
        this.results = results;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
