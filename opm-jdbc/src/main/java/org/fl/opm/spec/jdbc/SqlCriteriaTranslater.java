package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.Criteria;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 13:15
 */
public interface SqlCriteriaTranslater<T extends Criteria> {
    public String translate(T criteria) throws Exception;
}