package org.fl.opm.spec.jdbc;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-22 18:03
 */
public interface SqlDialect {
    /**
     * 将原始SQL转成分页查询SQL
     *
     * @param original
     * @param selCol
     * @return
     */
    public String rangedSql(String original, String selCol, int start, int limit);
}
