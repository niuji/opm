package org.fl.opm.spec.criteria;

import org.fl.opm.spec.enums.Symbol;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-20 10:51
 */
public class SimpleCriteria extends Criteria {

    private final String name;
    private final Symbol symbol;
    private final Object value;
    private final Object type;

    public SimpleCriteria(String name, Symbol symbol, Object value, Object type) {
        super();
        this.name = name;
        this.symbol = symbol;
        this.value = value;
        this.type = type;
    }
}
