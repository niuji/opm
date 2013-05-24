package org.fl.opm.jdbc;

import org.fl.opm.PersistenceManager;
import org.fl.opm.spec.Spec;
import org.fl.opm.spec.jdbc.JdbcParamterHolder;
import org.fl.opm.spec.jdbc.SqlSpec;
import org.fl.opm.util.ArrayUtils;
import org.fl.opm.util.CastUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-21 14:34
 */
public class SqlPersistenceManager implements PersistenceManager {
    private SqlExecutor sqlExecutor;

    public void setSqlExecutor(SqlExecutor sqlExecutor) {
        this.sqlExecutor = sqlExecutor;
    }

    @Override
    public <T> Object save(T model) throws Exception {
        notNull(model, "Model can not be null.");
        ModelJdbcMetaInfo metaInfo = MetaInfoHolder.getMetaInfo(model.getClass());
        FieldWrapper[] pkWrappers = metaInfo.buildPrimaryKeyParams(model);
        Object[] pks = getValues(pkWrappers);
        if (ArrayUtils.contains(pks, null) || findById(model.getClass(), pks) == null) {
            return convert2KeyValue(sqlExecutor.insert(metaInfo.getInsertSql(), metaInfo.buildInsertParams(model), metaInfo.getPrimaryKeyNames()), metaInfo);
        } else {
            if (sqlExecutor.updateById(metaInfo.getUpdateByIdSql(), metaInfo.buildUpdateParams(model), pkWrappers) == 1) {
                if (pks.length == 1) {
                    return pks[0];
                } else {
                    return pks;
                }
            } else {
                throw new Exception("Save/Update model failed. ");
            }
        }
    }

    private Object convert2KeyValue(Map<String, Object> primaryKeyValue, ModelJdbcMetaInfo metaInfo) throws Exception {
        if (primaryKeyValue == null) {
            return null;
        } else {
            Set<Map.Entry<String, Object>> entries = primaryKeyValue.entrySet();
            if (entries.size() != 1) {
                throw new Exception("only support one primary key.");
            } else {
                Map.Entry<String, Object> entry = entries.iterator().next();
                String key = entry.getKey();
                Object value = entry.getValue();
                Field pkField = getCorrespondingPkField(metaInfo, key);
                if (pkField == null) {
                    return value;
                } else {
                    return CastUtils.cast(value, pkField.getType());
                }
            }
        }
    }

    private Field getCorrespondingPkField(ModelJdbcMetaInfo metaInfo, String key) {
        Field field = (Field) metaInfo.getColFieldMapping().get(key);
        if (field == null) {
            for (Object pkName : metaInfo.getPrimaryKeyNames()) {
                Field pk = (Field) metaInfo.getColFieldMapping().get(pkName);
                GeneratedValue gv = pk.getAnnotation(GeneratedValue.class);
                if (gv != null && GenerationType.IDENTITY.equals(gv.strategy())) {
                    return pk;
                }
            }
        }
        return field;
    }

    private Object[] getValues(FieldWrapper[] wrappers) throws Exception {
        if(ArrayUtils.isEmpty(wrappers)){
            return null;
        }
        Object[] rt = new Object[wrappers.length];
        for (int i = 0; i < wrappers.length; i++){
            rt[i] = wrappers[i].getFieldValue();
        }
        return rt;
    }

    @Override
    public <T> T findById(Class<T> modelClass, Object id) throws Exception {
        notNull(id, "Id can not be null.");
        return sqlExecutor.findById(modelClass, MetaInfoHolder.getMetaInfo(modelClass).getQueryByIdSql(), id);
    }

    @Override
    public <T> int deleteById(Class<T> modelClass, Object id) throws Exception {
        notNull(id, "Id can not be null.");
        return sqlExecutor.deleteById(MetaInfoHolder.getMetaInfo(modelClass).getDeleteByIdSql(), id);
    }

    @Override
    public List findBySpec(Spec spec) throws Exception {
        notNull(spec, "Spec can not be null.");
        SqlSpec s = (SqlSpec) spec;
        JdbcParamterHolder jph = s.getJdbcParamter(null);
        if (s.isSelectByModel()) {
            return sqlExecutor.findBySql(jph.getSql(), jph.getParamArray(), jph.getTypeArray(), s.getModelClass());
        } else {
            return sqlExecutor.findBySql(jph.getSql(), jph.getParamArray(), jph.getTypeArray());
        }
    }

    @Override
    public <T> int updateBySpec(Class<T> modelClass, Spec spec) throws Exception {
        throw new Exception("not implemented yet.");
    }

    @Override
    public <T> int deleteBySpec(Class<T> modelClass, Spec spec) throws Exception {
        throw new Exception("not implemented yet.");
    }

    private void notNull(Object obj, String msg) throws Exception {
        if (obj == null) {
            throw new Exception(msg);
        }
    }

}
