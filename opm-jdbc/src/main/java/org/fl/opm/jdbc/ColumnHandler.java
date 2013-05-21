package org.fl.opm.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-7
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public interface ColumnHandler<T> {
    T getValue(ResultSet rs, String key) throws SQLException;

    int getJdbcType();

    void setParam(PreparedStatement ps, int i, Object param) throws SQLException;
}
