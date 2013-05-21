package org.fl.opm.jdbc.spring;

import org.fl.opm.jdbc.util.JdbcUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-20 14:25
 */
public class ObjectArrayRowMapper implements RowMapper<Object[]> {
    private static ObjectArrayRowMapper shareInstance = new ObjectArrayRowMapper();

    public static ObjectArrayRowMapper getShareInstance(){
        return shareInstance;
    }

    @Override
    public Object[] mapRow(ResultSet rs, int rowNum) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        Object[] result = new Object[columnCount];
        for (int i = 0; i < columnCount; i++){
            result[i] = JdbcUtils.readValue(rs, i + 1);
        }
        return result;
    }
}
