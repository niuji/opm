package org.fl.opm.spec.jdbc.criteria.translater;

import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.jdbc.JdbcParamterHolder;
import org.fl.opm.spec.jdbc.SqlCriteriaTranslaters;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 18:02
 */
public class CriteriaTranslater implements SqlCriteriaTranslater<Criteria> {
    @Override
    public String translate(Criteria criteria, JdbcParamterHolder jph) throws Exception {
        if(criteria == null || criteria.isEmpty()){
            return "";
        }else{
            return SqlCriteriaTranslaters.toWhereSql(criteria.getRoot(), jph);
        }
    }
}
