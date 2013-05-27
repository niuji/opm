package org.fl.opm.jdbc;

import org.fl.opm.spec.jdbc.SqlDialect;

import java.util.List;
import java.util.Map;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-27 10:44
 */
public interface SqlExecutor {
    Map<String, Object> insert(String sql, List<FieldWrapper> params, List<String> primaryKeys) throws Exception;

    <T> T findById(Class<T> modelClass, String queryByIdSql, Object id) throws Exception;

    int deleteById(String deleteByIdSql, Object id) throws Exception;

    int updateById(String updateByIdSql, List<FieldWrapper> updateColVals, FieldWrapper[] ids) throws Exception;

    <T> List<T> findBySql(String sql, Object[] params, int[] types, Class<T> modelClass) throws Exception;

    List<Object[]> findBySql(String sql, Object[] params, int[] types) throws Exception;

    SqlDialect getDialect();
}
