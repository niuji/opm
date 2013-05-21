package org.fl.opm.jdbc.spring;

import org.fl.opm.jdbc.SqlExecutor;
import org.fl.opm.jdbc.util.JdbcUtils;
import org.fl.opm.util.ArrayUtils;
import org.fl.opm.util.CollectionUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-15 13:50
 */
public class JdbcTemplateExecutor implements SqlExecutor {
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Map<String, Object> insert(final String sql, final List<Object> params, final List<String> primaryKeys) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(sql, CollectionUtils.toArray(String.class, primaryKeys));
                        int i = 1;
                        for (Object param : params) {
                            JdbcUtils.setParam(ps, i++, param);
                        }
                        return ps;
                    }
                },
                keyHolder);
        return keyHolder.getKeys();
    }

    @Override
    public <T> T findById(Class<T> modelClass, String sql, Object id) throws Exception {
        return (T) jdbcTemplate.queryForObject(sql, toArgs(id), new ModelRowMapper<T>(modelClass));
    }

    @Override
    public int deleteById(String sql, Object id) throws Exception {
        return jdbcTemplate.update(sql, toArgs(id));
    }

    @Override
    public <T> List<T> findBySql(String sql, Object[] params, int[] types, Class<T> modelClass) throws Exception {
        return jdbcTemplate.<T>query(sql, params, types, new ModelRowMapper<T>(modelClass));
    }

    @Override
    public List<Object[]> findBySql(String sql, Object[] params, int[] types) throws Exception {
        return jdbcTemplate.query(sql, params, types, ObjectArrayRowMapper.getShareInstance());
    }

    @Override
    public int updateById(String updateByIdSql, List<Object> updateColVals, Object id) throws Exception {
        Object[] values = ArrayUtils.union(updateColVals.toArray(), toArgs(id));
        int[] types = JdbcUtils.translateJdbcTypes(values);
        return jdbcTemplate.update(updateByIdSql, values, types);
    }

    private Object[] toArgs(Object param) {
        if (param.getClass().isArray()) {
            return (Object[]) param;
        } else if (param instanceof Collection) {
            return ((Collection) param).toArray();
        } else {
            return new Object[]{param};
        }
    }
}
