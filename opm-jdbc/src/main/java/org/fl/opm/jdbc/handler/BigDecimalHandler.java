package org.fl.opm.jdbc.handler;

import org.fl.opm.jdbc.ColumnHandler;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-7
 * Time: 下午5:37
 * To change this template use File | Settings | File Templates.
 */
public class BigDecimalHandler implements ColumnHandler<BigDecimal> {
    @Override
    public BigDecimal getValue(ResultSet rs, String key) throws SQLException {
        return rs.getBigDecimal(key);
    }

    @Override
    public int getJdbcType() {
        return Types.NUMERIC;
    }

    @Override
    public void setParam(PreparedStatement ps, int i, Object param) throws SQLException {
        ps.setBigDecimal(i, (BigDecimal)param);
    }
}
