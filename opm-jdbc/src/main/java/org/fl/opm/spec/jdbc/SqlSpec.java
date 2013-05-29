package org.fl.opm.spec.jdbc;

import org.fl.opm.jdbc.MetaInfoHolder;
import org.fl.opm.jdbc.ModelJdbcMetaInfo;
import org.fl.opm.jdbc.util.DbNameUtils;
import org.fl.opm.jdbc.util.JdbcUtils;
import org.fl.opm.spec.Sort;
import org.fl.opm.spec.SortDefinition;
import org.fl.opm.spec.Spec;
import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.enums.Symbol;
import org.fl.opm.spec.jdbc.criteria.NamedSqlCriteria;
import org.fl.opm.util.CollectionUtils;
import org.fl.opm.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-17 14:11
 */
public class SqlSpec<T> extends Spec<T> {
    private String selectCol;

    private SqlSpec(Class<T> modelClass) {
        super(modelClass);
    }

    public static <T> SqlSpec<T> from(Class<T> modelClass) {
        return new SqlSpec<T>(modelClass);
    }

    public SqlSpec<T> where() throws Exception {
        if (criteria != null) {
            throw new Exception("criteria already initialized.");
        }
        criteria = Criteria.emptyCriteria();
        return this;
    }

    public SqlSpec<T> where(String sql) throws Exception {
        if (criteria != null) {
            throw new Exception("criteria already initialized.");
        }
        criteria = new NamedSqlCriteria(sql);
        return this;
    }

    public SqlSpec<T> addParameter(String name, Object value) throws Exception {
        if (criteria instanceof NamedSqlCriteria) {
            NamedSqlCriteria c = (NamedSqlCriteria) criteria;
            c.addParam(name, value);
        } else {
            throw new Exception("Spec is not in named sql model.");
        }
        return this;
    }

    public SqlSpec<T> select(String select) {
        this.selectCol = select;
        //TODO: 增加列名解析功能
        return this;
    }

    /**
     * 增加相等条件，val为空则忽略
     *
     * @param fieldName
     * @param val
     * @return
     * @throws Exception
     */
    public SqlSpec<T> eq(String fieldName, Object val) throws Exception {
        if (val != null) {
            Field field = getField(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.EQUAL, val, JdbcUtils.javaTypeToJdbcType(field.getType()));
        }
        return this;
    }

    /**
     * 增加相等条件，val为空则增加isnull条件
     *
     * @param fieldName
     * @param val
     * @return
     * @throws Exception
     */
    public SqlSpec<T> eqNullable(String fieldName, Object val) throws Exception {
        if (val == null) {
            Field field = getAndEnsureField(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.ISNULL, null, null);
            return this;
        } else {
            return eq(fieldName, val);
        }
    }

    /**
     * 大于
     *
     * @param fieldName
     * @param val
     * @return
     * @throws Exception
     */
    public SqlSpec<T> gt(String fieldName, Object val) throws Exception {
        if (val != null) {
            Field field = getAndEnsureField(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.GT, val, JdbcUtils.javaTypeToJdbcType(field.getType()));
        }
        return this;
    }

    /**
     * 大于等于
     *
     * @param fieldName
     * @param val
     * @return
     * @throws Exception
     */
    public SqlSpec<T> ge(String fieldName, Object val) throws Exception {
        if (val != null) {
            Field field = getAndEnsureField(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.GE, val, JdbcUtils.javaTypeToJdbcType(field.getType()));
        }
        return this;
    }

    /**
     * 小于
     *
     * @param fieldName
     * @param val
     * @return
     * @throws Exception
     */
    public SqlSpec<T> lt(String fieldName, Object val) throws Exception {
        if (val != null) {
            Field field = getAndEnsureField(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.LT, val, JdbcUtils.javaTypeToJdbcType(field.getType()));
        }
        return this;
    }

    /**
     * 小于等于
     *
     * @param fieldName
     * @param val
     * @return
     * @throws Exception
     */
    public SqlSpec<T> le(String fieldName, Object val) throws Exception {
        if (val != null) {
            Field field = getAndEnsureField(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.LE, val, JdbcUtils.javaTypeToJdbcType(field.getType()));
        }
        return this;
    }

    /**
     * 将criteria转换成执行jdbc必须的参数：sql, parameters, jdbc types
     *
     * @param dialet
     * @return
     * @throws Exception
     */
    public JdbcParamterHolder getJdbcParamter(SqlDialect dialet) throws Exception {
        JdbcParamterHolder jph = new JdbcParamterHolder();
        String selCol = StringUtils.isNotBlank(this.selectCol) ? this.selectCol : "*";
        StringBuilder sb = new StringBuilder("select ");
        sb.append(selCol);
        sb.append(" from ").append(MetaInfoHolder.getMetaInfo(modelClass).getTableName());
        if (criteria != null) {
            sb.append(" where ").append(SqlCriteriaTranslaters.toWhereSql(criteria, jph));
        }
        if (sort != null && CollectionUtils.isNotEmpty(sort.getSortDefinitions())) {
            sb.append(" order by ").append(generateOrderBySql(sort));
        }
        if (limit != null) {
            if (dialet == null) {
                throw new Exception("Sql dialect can't be null.");
            }
            sb = new StringBuilder(dialet.rangedSql(sb.toString(), selCol, limit.getStart(), limit.getEnd() - limit.getStart()));
        }
        jph.setSql(sb.toString());
        return jph;
    }

    public boolean isSelectByModel() {
        return selectCol == null;
    }

    @Override
    public Spec<T> asc(String fieldName) throws Exception {
        Field field = getAndEnsureField(fieldName);
        return super.asc(DbNameUtils.getColName(field));
    }

    @Override
    public Spec<T> desc(String fieldName) throws Exception {
        Field field = getAndEnsureField(fieldName);
        return super.asc(DbNameUtils.getColName(field));
    }

    private String generateOrderBySql(Sort sort) {
        List<SortDefinition> sortDefinitions = sort.getSortDefinitions();
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (SortDefinition sd : sortDefinitions) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(sd.getName()).append(' ').append(sd.getOrder());
        }
        return sb.toString();
    }

    private Field getField(String fieldName) throws Exception {
        ModelJdbcMetaInfo metaInfo = MetaInfoHolder.getMetaInfo(modelClass);
        return (Field) metaInfo.getFieldMapping().get(fieldName);
    }

    private Field getAndEnsureField(String fieldName) throws Exception {
        Field field = getField(fieldName);
        if (field == null) {
            throw new Exception("Can not find column field with name '" + fieldName + "'.");
        }
        return field;
    }

}
