package org.fl.opm.jdbc.util;

import org.fl.opm.jdbc.ColumnHandler;
import org.fl.opm.jdbc.FieldWrapper;
import org.fl.opm.jdbc.handler.*;
import org.fl.opm.util.CastUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午2:18
 * To change this template use File | Settings | File Templates.
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

}
