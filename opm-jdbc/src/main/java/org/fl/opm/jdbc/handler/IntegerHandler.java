package org.fl.opm.jdbc.handler;

import org.fl.opm.jdbc.ColumnHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-22 14:02
 */
public class IntegerHandler implements ColumnHandler<Integer> {
    @Override
    public Integer getValue(ResultSet rs, String key) throws SQLException {
        return rs.getInt(key);
    }

    @Override
    public int getJdbcType() {
        return Types.INTEGER;
    }

    @Override
    public void setParam(PreparedStatement ps, int i, Object param) throws SQLException {
        if (param == null) {
            ps.setNull(i, getJdbcType());
        } else {
            ps.setInt(i, (Integer) param);
        }
    }
}
