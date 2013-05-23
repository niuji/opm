package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.Criteria;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 18:02
 */
public class CriteriaTranslater implements SqlCriteriaTranslater<Criteria> {
    @Override
    public String translate(Criteria criteria) throws Exception {
        if(criteria == null || criteria.isEmpty()){
            return "";
        }else{
            return SqlCriteriaTranslaters.toWhereSql(criteria.getRoot());
        }
    }
}
