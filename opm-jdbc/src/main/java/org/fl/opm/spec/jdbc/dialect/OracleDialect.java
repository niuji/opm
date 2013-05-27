package org.fl.opm.spec.jdbc.dialect;

import org.fl.opm.spec.jdbc.SqlDialect;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-27 10:34
 */
public class OracleDialect implements SqlDialect {
    private static final OracleDialect sharedInstance = new OracleDialect();

    public static OracleDialect getSharedInstance() {
        return sharedInstance;
    }

    @Override
    public String rangedSql(String original, int start, int limit) {
        return "select * from (select rownum as rn, t.* from (" + original + ") t where rownum < " + (start + limit) + ") o where o.rn>=" + start;
    }
}
