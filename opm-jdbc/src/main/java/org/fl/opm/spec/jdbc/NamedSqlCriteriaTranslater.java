package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.jdbc.criteria.NamedSqlCriteria;
import org.fl.opm.spec.jdbc.criteria.ParamPair;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-29 15:28
 */
public class NamedSqlCriteriaTranslater implements SqlCriteriaTranslater<NamedSqlCriteria> {
    @Override
    public String translate(NamedSqlCriteria criteria, JdbcParamterHolder jph) throws Exception {
        for (ParamPair pair : criteria.getParamPairs()) {
            if(pair == null){
                throw new Exception("Parameter for named sql can not be null.");
            }
            jph.addParam(pair.getVal(), pair.getJdbcType());
        }
        return criteria.getSql();
    }
}
