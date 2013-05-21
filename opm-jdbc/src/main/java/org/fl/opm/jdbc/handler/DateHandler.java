package org.fl.opm.jdbc.handler;

import org.fl.opm.jdbc.ColumnHandler;

import java.sql.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-7
 * Time: 下午5:18
 * To change this template use File | Settings | File Templates.
 */
public class DateHandler implements ColumnHandler<Date> {
    @Override
    public Date getValue(ResultSet rs, String key) throws SQLException {
        return rs.getTimestamp(key);
    }

    @Override
    public int getJdbcType() {
        return Types.TIMESTAMP;
    }

    @Override
    public void setParam(PreparedStatement ps, int i, Object param) throws SQLException {
        ps.setTimestamp(i, new Timestamp(((Date)param).getTime()));
    }
}
