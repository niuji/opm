package org.fl.opm.jdbc.handler;

import org.fl.opm.jdbc.ColumnHandler;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-21 10:35
 */
public class StringHandler implements ColumnHandler<String> {
    private static final LobHandler lobHandler = new DefaultLobHandler();

    @Override
    public String getValue(ResultSet rs, String key) throws SQLException {
        int jdbcType = rs.getMetaData().getColumnType(rs.findColumn(key));
        if (jdbcType == Types.CLOB) {
            return lobHandler.getClobAsString(rs, key);
        } else {
            return rs.getString(key);
        }
    }

    @Override
    public int getJdbcType() {
        return Types.VARCHAR;
    }

    @Override
    public void setParam(PreparedStatement ps, int i, Object param) throws SQLException {
        ps.setString(i, (String)param);
    }
}
