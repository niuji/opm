package org.fl.opm.spec.jdbc.criteria;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-29 15:54
 */
public class ParamPair {
    private Object val;
    private int jdbcType;

    ParamPair(Object value, int jdbcType) {
        this.setVal(value);
        this.setJdbcType(jdbcType);
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public int getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(int jdbcType) {
        this.jdbcType = jdbcType;
    }
}
