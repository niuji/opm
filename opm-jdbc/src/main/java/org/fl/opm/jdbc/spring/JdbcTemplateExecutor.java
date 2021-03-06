package org.fl.opm.jdbc.spring;

import org.fl.opm.jdbc.FieldWrapper;
import org.fl.opm.jdbc.SqlExecutor;
import org.fl.opm.jdbc.util.JdbcUtils;
import org.fl.opm.spec.jdbc.SqlDialect;
import org.fl.opm.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
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
    private static Logger logger = LoggerFactory.getLogger(JdbcTemplateExecutor.class);
    private JdbcTemplate jdbcTemplate;
    private SqlDialect dialect;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dialect = JdbcUtils.getDialect(dataSource);
    }

    @Override
    public Map<String, Object> insert(final String sql, final List<FieldWrapper> params, final List<String> primaryKeys) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL: [{}], Params:[{}]", sql, params);
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(sql, CollectionUtils.toArray(String.class, primaryKeys));
                        int i = 1;
                        try {
                            for (FieldWrapper param : params) {
                                JdbcUtils.setParam(ps, i++, param);
                            }
                        } catch (Exception e) {
                            throw new SQLException(e);
                        }
                        return ps;
                    }
                },
                keyHolder);
        return keyHolder.getKeys();
    }

    @Override
    public <T> T findById(Class<T> modelClass, String sql, Object id) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL: [{}], Params:[{}]", sql, id);
        }
        try {
            return (T) jdbcTemplate.queryForObject(sql, toArgs(id), new ModelRowMapper<T>(modelClass));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deleteById(String sql, Object id) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL: [{}], Params:[{}]", sql, id);
        }
        return jdbcTemplate.update(sql, toArgs(id));
    }

    @Override
    public <T> List<T> findBySql(String sql, Object[] params, int[] types, Class<T> modelClass) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL: [{}], Params:[{}]", sql, params);
        }
        return jdbcTemplate.<T>query(sql, params, types, new ModelRowMapper<T>(modelClass));
    }

    @Override
    public List<Object[]> findBySql(String sql, Object[] params, int[] types) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL: [{}], Params:[{}]", sql, params);
        }
        return jdbcTemplate.query(sql, params, types, ObjectArrayRowMapper.getShareInstance());
    }

    @Override
    public int updateById(String sql, final List<FieldWrapper> updateColVals, final FieldWrapper[] ids) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("SQL: [{}], Params:[{}], ID:[{}]", sql, updateColVals, ids);
        }
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int i = 1;
                try {
                    for (FieldWrapper val : updateColVals) {
                        JdbcUtils.setParam(ps, i++, val);
                    }
                    for (FieldWrapper id : ids) {
                        JdbcUtils.setParam(ps, i++, id);
                    }
                } catch (Exception e) {
                    throw new SQLException(e);
                }
            }
        });
    }

    @Override
    public SqlDialect getDialect() {
        return dialect;
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
