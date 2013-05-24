package org.fl.opm.spec.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-24 11:01
 */
public class JdbcParamterHolder {
    private String sql;
    private List<Object> params = new ArrayList<Object>();
    private List<Integer> types = new ArrayList<Integer>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addParam(Object val, Integer jdbcType) {
        params.add(val);
        types.add(jdbcType);
    }

    public Object[] getParamArray() {
        return params.toArray(new Object[params.size()]);
    }

    public int[] getTypeArray() {
        int[] rt = new int[types.size()];
        int i = 0;
        for (Integer type : types) {
            rt[i++] = type;
        }
        return rt;
    }
}
