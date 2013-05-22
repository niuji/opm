package org.fl.opm.jdbc.handler;

import org.fl.opm.jdbc.ColumnHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-7
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
public class LongHandler implements ColumnHandler<Long> {
    @Override
    public Long getValue(ResultSet rs, String key) throws SQLException {
        return rs.getLong(key);
    }

    @Override
    public int getJdbcType() {
        return Types.BIGINT;
    }

    @Override
    public void setParam(PreparedStatement ps, int i, Object param) throws SQLException {
        if (param == null) {
            ps.setNull(i, getJdbcType());
        } else {
            ps.setLong(i, (Long) param);
        }
    }
}
