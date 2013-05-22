package org.fl.opm.jdbc;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-3
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public interface SqlExecutor {
    Map<String, Object> insert(String sql, List<FieldWrapper> params, List<String> primaryKeys) throws Exception;

    <T> T findById(Class<T> modelClass, String queryByIdSql, Object id) throws Exception;

    int deleteById(String deleteByIdSql, Object id) throws Exception;

    int updateById(String updateByIdSql, List<FieldWrapper> updateColVals, FieldWrapper[] ids) throws Exception;

    <T> List<T> findBySql(String sql, Object[] params, int[] types, Class<T> modelClass) throws Exception;

    List<Object[]> findBySql(String sql, Object[] params, int[] types) throws Exception;
}
