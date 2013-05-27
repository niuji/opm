package org.fl.opm.jdbc.util;

import org.fl.opm.jdbc.ColumnHandler;
import org.fl.opm.jdbc.FieldWrapper;
import org.fl.opm.jdbc.handler.*;
import org.fl.opm.spec.jdbc.SqlDialect;
import org.fl.opm.spec.jdbc.dialect.OracleDialect;
import org.fl.opm.spec.jdbc.dialect.SqlServerDialect;
import org.fl.opm.util.CastUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-24 10:56
 */
public class JdbcUtils {
    private static final Map<Class<?>, ColumnHandler<?>> handlers = new HashMap<Class<?>, ColumnHandler<?>>();

    public static <T> T readValue(ResultSet rs, String key, Class<T> type) throws Exception {
        T rt = getValueByType(rs, key, type);
        if (rt != null) {
            return rt;
        } else {
            Object val = rs.getObject(key);
            return CastUtils.cast(val, type);
        }
    }

    public static Object readValue(ResultSet rs, int idx) throws SQLException {
        int type = rs.getMetaData().getColumnType(idx);
        switch (type) {
            case Types.BIGINT:
                return rs.getLong(idx);
            case Types.INTEGER:
                return rs.getInt(idx);
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                return rs.getTimestamp(idx);
            case Types.CLOB:
                return rs.getString(idx);
            case Types.BLOB:
                return rs.getBytes(idx);
            case Types.NUMERIC:
            case Types.DECIMAL:
            case Types.DOUBLE:
            case Types.FLOAT:
                return rs.getBigDecimal(idx);
            default:
                return rs.getObject(idx);
        }
    }

    private static <T> T getValueByType(ResultSet rs, String key, Class<T> type) throws SQLException {
        ColumnHandler<?> handler = handlers.get(type);
        if (handler != null) {
            return (T) handler.getValue(rs, key);
        } else {
            return null;
        }
    }

    public static int javaTypeToJdbcType(Class<?> clz) {
        ColumnHandler<?> ch = handlers.get(clz);
        if (ch != null) {
            return ch.getJdbcType();
        } else {
            return Types.OTHER;
        }
    }

    public static void setParam(PreparedStatement ps, int i, FieldWrapper param) throws Exception {
        ColumnHandler<?> ch = handlers.get(param.getFieldType());
        if (ch != null) {
            ch.setParam(ps, i, param.getFieldValue());
        } else {
            ps.setObject(i, param.getFieldValue());
        }
    }

    static {
        handlers.put(Integer.class, new IntegerHandler());
        handlers.put(Date.class, new DateHandler());
        handlers.put(Long.class, new LongHandler());
        handlers.put(BigDecimal.class, new BigDecimalHandler());
        handlers.put(String.class, new StringHandler());
    }

    public static SqlDialect getDialect(DataSource dataSource) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            String dbInfo = conn.getMetaData().getDatabaseProductName().toUpperCase();
            if(dbInfo.indexOf("ORACLE") >= 0){
                return OracleDialect.getSharedInstance();
            } else if(dbInfo.indexOf("SQL SERVER")>= 0){
                return SqlServerDialect.getSharedInstance();
            }
        } catch (SQLException e) {
        } finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }
}
