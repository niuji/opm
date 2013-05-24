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

    public SqlSpec<T> select(String select){
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
            ModelJdbcMetaInfo metaInfo = MetaInfoHolder.getMetaInfo(modelClass);
            Field field = (Field) metaInfo.getFieldMapping().get(fieldName);
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
            ModelJdbcMetaInfo metaInfo = MetaInfoHolder.getMetaInfo(modelClass);
            Field field = (Field) metaInfo.getFieldMapping().get(fieldName);
            criteria.and(DbNameUtils.getColName(field), Symbol.ISNULL, null, null);
            return this;
        } else {
            return eq(fieldName, val);
        }
    }

    /**
     * 将criteria转换成执行jdbc必须的参数：sql, parameters, jdbc types
     * @param dialet
     * @return
     * @throws Exception
     */
    public JdbcParamterHolder getJdbcParamter(SqlDialect dialet) throws Exception {
        JdbcParamterHolder jph = new JdbcParamterHolder();
        StringBuilder sb = new StringBuilder("select ");
        if (StringUtils.isNotBlank(selectCol)) {
            sb.append(selectCol);
        } else {
            sb.append("*");
        }
        sb.append(" from ").append(MetaInfoHolder.getMetaInfo(modelClass).getTableName());
        if (!criteria.isEmpty()) {
            sb.append(" where ").append(SqlCriteriaTranslaters.toWhereSql(criteria.getRoot(), jph));
        }
        if (limit != null) {
            sb = new StringBuilder(dialet.rangedSql(sb.toString(), limit.getStart(), limit.getEnd() - limit.getStart()));
        }
        if (sort != null && CollectionUtils.isNotEmpty(sort.getSortDefinitions())) {
            sb.append(" order by ").append(generateOrderBySql(sort));
        }
        jph.setSql(sb.toString());
        return jph;
    }

    public boolean isSelectByModel(){
        return selectCol == null;
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

}
