package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.SimpleCriteria;
import org.fl.opm.spec.enums.Symbol;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 13:27
 */
public class SimpleCriteriaTranslater implements SqlCriteriaTranslater<SimpleCriteria> {
    @Override
    public String translate(SimpleCriteria criteria, JdbcParamterHolder jph) throws Exception {
        jph.addParam(criteria.getValue(), (Integer)criteria.getType());
        return criteria.getName() + symbolString(criteria.getSymbol());
    }

    private String symbolString(Symbol symbol) throws Exception {
        switch (symbol){
            case EQUAL:
                return " = ? ";
            case ISNULL:
                return " is null ";
            default:
                throw new Exception("Symbol " + symbol.name() + " is not supported.");
        }
    }


}
