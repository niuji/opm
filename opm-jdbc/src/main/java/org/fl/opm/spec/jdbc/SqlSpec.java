package org.fl.opm.spec.jdbc;

import org.fl.opm.jdbc.MetaInfoHolder;
import org.fl.opm.jdbc.ModelJdbcMetaInfo;
import org.fl.opm.jdbc.util.DbNameUtils;
import org.fl.opm.jdbc.util.JdbcUtils;
import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.Spec;
import org.fl.opm.spec.enums.Symbol;

import java.lang.reflect.Field;

/**
 *
 * User: jiangyixin.stephen
 * Date: 2013-05-17 14:11
 */
public class SqlSpec<T> extends Spec<T> {

    private SqlSpec(Class<T> modelClass) {
        super(modelClass);
    }

    public static <T> SqlSpec<T> from(Class<T> modelClass){
        return new SqlSpec<T>(modelClass);
    }

    public SqlSpec<T> where() throws Exception {
        if(criteria != null){
            throw new Exception("criteria already initialized.");
        }
        criteria = Criteria.emptyCriteria();
        return this;
    }

    public SqlSpec<T> eq(String fieldName, Object val) throws Exception {
        ModelJdbcMetaInfo metaInfo = MetaInfoHolder.getMetaInfo(modelClass);
        Field field = (Field)metaInfo.getFieldMapping().get(fieldName);
        criteria.and(DbNameUtils.getColName(field), Symbol.EQUAL, val, JdbcUtils.javaTypeToJdbcType(field.getClass()));
        return this;
    }
}
