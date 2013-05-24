package org.fl.opm.spec;

import org.fl.opm.Page;
import org.fl.opm.PersistenceManager;
import org.fl.opm.spec.criteria.Criteria;

import java.util.List;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-17 14:01
 */
public abstract class Spec<T> {
    protected Criteria criteria;
    protected Class<T> modelClass;
    protected Sort sort;
    protected Limit limit;

    protected Spec(Class<T> modelClass){
        this.modelClass = modelClass;
    }

    /**
     * 增加顺序排序
     * @param name
     * @return
     */
    public Spec<T> asc(String name){
        ensureSort();
        sort.add(name, Sort.ASC);
        return this;
    }

    /**
     * 增加逆序排序
     * @param name
     * @return
     */
    public Spec<T> desc(String name){
        ensureSort();
        sort.add(name, Sort.DESC);
        return this;
    }

    /**
     * 设置查询分页
     * @param page
     * @return
     */
    public Spec<T> limit(Page page){
        limit = new Limit();
        limit.setStart(page.getPage() * page.getPageSize());
        limit.setEnd(limit.getStart() + page.getPageSize());
        return this;
    }

    public List<Object> list(PersistenceManager pm) throws Exception {
        return pm.findBySpec(this);
    }

    public Class<T> getModelClass(){
        return modelClass;
    }

    private void ensureSort() {
        if(sort == null){
            sort = new Sort();
        }
    }
}
