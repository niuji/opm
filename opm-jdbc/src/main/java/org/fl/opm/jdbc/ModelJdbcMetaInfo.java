package org.fl.opm.jdbc;

import org.fl.opm.jdbc.util.DbNameUtils;
import org.fl.opm.util.ReflectionUtils;
import org.fl.opm.util.StringUtils;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-3
 * Time: 下午5:55
 * To change this template use File | Settings | File Templates.
 */
public class ModelJdbcMetaInfo<T> {
    protected final List<Field> cols;
    private final String tableName;
    private final List<Field> primaryKeys;
    private final Map<String, Field> fieldMapping;
    private final Map<String, Field> colFieldMapping;
    private final String insertSql;
    private final String updateByIdSql;
    private final String queryByIdSql;
    private final String deleteByIdSql;
    private final List<String> primaryKeyNames;

    public ModelJdbcMetaInfo(Class<T> modelClass) throws Exception {
        this.tableName = getTableName(modelClass);
        this.cols = ReflectionUtils.findAllFields(modelClass, Id.class, Column.class);
        this.primaryKeys = new ArrayList<Field>();
        this.colFieldMapping = new HashMap<String, Field>();
        this.fieldMapping = new HashMap<String, Field>();
        this.primaryKeyNames = new ArrayList<String>();
        initPrimaryKeysAndColFieldMapping(this.cols);
        this.insertSql = buildInsertSql();
        this.updateByIdSql = buildUpdateByIdSql();
        this.deleteByIdSql = buildDeleteByIdSql();
        this.queryByIdSql = buildQueryByIdSql();
    }

    private String buildQueryByIdSql() {
        StringBuilder selectCols = new StringBuilder("select ");
        boolean first = true;
        for (Field col : cols) {
            if (!first) {
                selectCols.append(',');
            } else {
                first = false;
            }
            selectCols.append(DbNameUtils.getColName(col));
        }
        return selectCols + " from " + tableName + " where " + getPrimaryKeysConditionSql();
    }

    private String buildDeleteByIdSql() {
        return "delete from " + tableName + " where " + getPrimaryKeysConditionSql();
    }

    private String buildUpdateByIdSql() {
        StringBuilder update = new StringBuilder("update ").append(tableName).append(" set ");
        boolean first = true;
        for (Field col : cols) {
            if (!isPrimaryKey(col)) {
                if (!first) {
                    update.append(',');
                } else {
                    first = false;
                }
                update.append(DbNameUtils.getColName(col)).append(" = ? ");
            }
        }
        return update + " where " + getPrimaryKeysConditionSql();
    }

    public List<Object> buildUpdateParams(T model) throws Exception {
        List<Object> result = new ArrayList<Object>();
        for (Field col : cols) {
            if (!isPrimaryKey(col)) {
                result.add(ReflectionUtils.getFieldValue(model, col));
            }
        }
        return result;
    }

    public Object buildPrimaryKeyParams(T model) throws Exception {
        if (primaryKeys.size() == 1) {
            return ReflectionUtils.getFieldValue(model, primaryKeys.get(0));
        } else {
            Object[] result = new Object[primaryKeys.size()];
            int i = 0;
            for (Field pk : primaryKeys) {
                result[i] = ReflectionUtils.getFieldValue(model, pk);
                i++;
            }
            return result;
        }
    }

    public boolean isPrimaryKey(Field col) {
        Id id = col.getAnnotation(Id.class);
        return id != null;
    }

    private String buildInsertSql() {
        StringBuilder colStr = new StringBuilder();
        StringBuilder valStr = new StringBuilder();
        for (Field pk : primaryKeys) {
            GeneratedValue gv = pk.getAnnotation(GeneratedValue.class);
            if (gv != null && GenerationType.IDENTITY.equals(gv.strategy())) {
                continue;
            } else {
                colStr.append(',').append(DbNameUtils.getColName(pk));
                valStr.append(",?");
            }
        }
        for (Field col : cols) {
            if (!primaryKeys.contains(col)) {
                colStr.append(',').append(DbNameUtils.getColName(col));
                valStr.append(",?");
            }
        }
        return "insert into " + tableName + " (" + colStr.substring(1) + ") values (" + valStr.substring(1) + ")";
    }

    public <T> List<Object> buildInsertParams(T model) throws Exception {
        List<Object> result = new ArrayList<Object>();
        for (Field pk : primaryKeys) {
            GeneratedValue gv = pk.getAnnotation(GeneratedValue.class);
            if (gv == null || !GenerationType.IDENTITY.equals(gv.strategy())) {
                result.add(ReflectionUtils.getFieldValue(model, pk));
            }
        }
        for (Field col : cols) {
            if (!isPrimaryKey(col)) {
                result.add(ReflectionUtils.getFieldValue(model, col));
            }
        }
        return result;
    }

    private void initPrimaryKeysAndColFieldMapping(List<Field> cols) {
        for (Field col : cols) {
            if (col.getAnnotation(Id.class) != null) {
                primaryKeys.add(col);
                primaryKeyNames.add(DbNameUtils.getColName(col));
            }
            colFieldMapping.put(DbNameUtils.getColName(col), col);
            fieldMapping.put(col.getName(), col);
        }
    }

    private String getTableName(Class<T> modelClass) throws Exception {
        Class<?> entityClass = ReflectionUtils.findAnnotatedClass(modelClass, Entity.class);
        Entity entity = entityClass.getAnnotation(Entity.class);
        Table table = entityClass.getAnnotation(Table.class);
        if (entity == null && table == null) {
            throw new Exception("Not a entity instance.");
        }
        if (table != null && StringUtils.isNotBlank(table.name())) {
            return table.name();
        } else {
            return DbNameUtils.toDbName(modelClass.getSimpleName());
        }
    }

    private String getPrimaryKeysConditionSql() {
        StringBuilder where = new StringBuilder();
        boolean first = true;
        for (Field pk : primaryKeys) {
            if (!first) {
                where.append(',');
            } else {
                first = false;
            }
            where.append(DbNameUtils.getColName(pk)).append(" = ? ");
        }
        return where.toString();
    }

    public String getInsertSql() {
        return insertSql;
    }

    public String getUpdateByIdSql() {
        return updateByIdSql;
    }

    public String getQueryByIdSql() {
        return queryByIdSql;
    }

    public String getDeleteByIdSql() {
        return deleteByIdSql;
    }

    public List<String> getPrimaryKeyNames() {
        return primaryKeyNames;
    }

    public Map<String, Field> getColFieldMapping() {
        return colFieldMapping;
    }

    public Map<String, Field> getFieldMapping() {
        return fieldMapping;
    }
}
