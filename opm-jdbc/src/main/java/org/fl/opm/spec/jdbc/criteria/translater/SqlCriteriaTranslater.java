package org.fl.opm.spec.jdbc.criteria.translater;

import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.jdbc.JdbcParamterHolder;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 13:15
 */
public interface SqlCriteriaTranslater<T extends Criteria> {
    public String translate(T criteria, JdbcParamterHolder jph) throws Exception;
}
