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
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
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
        ps.setInt(i, (Integer)param);
    }
}
