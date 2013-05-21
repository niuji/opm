package org.fl.opm.jdbc.spring;

import org.fl.opm.jdbc.MetaInfoHolder;
import org.fl.opm.jdbc.ModelJdbcMetaInfo;
import org.fl.opm.jdbc.util.JdbcUtils;
import org.fl.opm.util.ReflectionUtils;
import org.springframework.jdbc.core.RowMapper;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-21 10:35
 */
public class ModelRowMapper<T> implements RowMapper<T> {

    ModelJdbcMetaInfo metaInfo;
    Class<T> modelClass;

    public ModelRowMapper(Class<T> modelClass) throws Exception {
        this.modelClass = modelClass;
        metaInfo = MetaInfoHolder.getMetaInfo(this.modelClass);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T obj;
        try {
            obj = modelClass.newInstance();
        } catch (Exception e) {
            throw new SQLException("Model class[" + modelClass + "] can not be initialized by method newInstance.", e);
        }
        Map<String, Field> colFieldMapping = metaInfo.getColFieldMapping();
        for (Map.Entry<String, Field> entry : colFieldMapping.entrySet()) {
            try {
                ReflectionUtils.setField(entry.getValue(), obj, JdbcUtils.readValue(rs, entry.getKey(), entry.getValue().getType()));
            } catch (Exception e) {
                throw new SQLException(String.format("Set value to field failed[Column: %s, Field: %s, Model Class: %s].",
                        entry.getKey(), entry.getValue().getName(), modelClass.getName()), e);
            }
        }
        return obj;
    }
}
