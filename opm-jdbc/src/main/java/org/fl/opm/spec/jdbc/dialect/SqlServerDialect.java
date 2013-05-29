package org.fl.opm.spec.jdbc.dialect;

import org.fl.opm.spec.jdbc.SqlDialect;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-27 11:18
 */
public class SqlServerDialect implements SqlDialect {
    private static final String SELECT = "SELECT ";
    private static final String TOP = "TOP ";
    private static final String FROM = "FROM ";
    private static final SqlServerDialect sharedInstance = new SqlServerDialect();

    public static SqlServerDialect getSharedInstance() {
        return sharedInstance;
    }

    @Override
    public String rangedSql(String original, String selCol, int start, int limit) {
        StringBuilder sb = new StringBuilder(original.trim().toUpperCase());

        int orderByIndex = sb.indexOf("ORDER BY");
        CharSequence orderby = orderByIndex > 0 ? sb.subSequence(orderByIndex, sb.length())
                : "ORDER BY CURRENT_TIMESTAMP";

        if (orderByIndex > 0) {
            sb.delete(orderByIndex, orderByIndex + orderby.length());
        }
        insertRowNumberFunction(sb, orderby);

        sb.insert(0, SELECT + " " + TOP + limit + " " + selCol + " " + FROM + "(");
        sb.append(")").append(" QUERY_TEMP_").append(" WHERE RowNumber >= ").append(start);
        return sb.toString();
    }

    private void insertRowNumberFunction(StringBuilder sql, CharSequence orderby) {
        int selectEndIndex = sql.indexOf(SELECT) + SELECT.length();
        sql.insert(selectEndIndex, " ROW_NUMBER() OVER (" + orderby + ") as RowNumber,");
    }

}
