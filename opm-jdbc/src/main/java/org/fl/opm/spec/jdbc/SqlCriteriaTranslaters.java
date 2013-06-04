package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.criteria.RelationCriteria;
import org.fl.opm.spec.criteria.SimpleCriteria;
import org.fl.opm.spec.jdbc.criteria.NamedSqlCriteria;
import org.fl.opm.spec.jdbc.criteria.translater.*;

import java.util.HashMap;
import java.util.Map;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 13:09
 */
public class SqlCriteriaTranslaters {
    private static Map<Class<? extends Criteria>, SqlCriteriaTranslater> translaterMap = new HashMap<Class<? extends Criteria>, SqlCriteriaTranslater>();
    public static String toWhereSql(Criteria criteria, JdbcParamterHolder jph) throws Exception {
        if(criteria == null){
            return "";
        }else{
            return getSqlCriteriaTranslater(criteria).translate(criteria, jph);
        }
    }

    private static SqlCriteriaTranslater getSqlCriteriaTranslater(Criteria criteria) {
        Class<?> clz = criteria.getClass();
        SqlCriteriaTranslater translater = null;
        while(clz != Object.class && (translater = translaterMap.get(clz)) == null){
            clz = clz.getSuperclass();
        }
        return translater;
    }

    static {
        translaterMap.put(RelationCriteria.class, new RelationCriteriaTranslater());
        translaterMap.put(SimpleCriteria.class, new SimpleCriteriaTranslater());
        translaterMap.put(NamedSqlCriteria.class, new NamedSqlCriteriaTranslater());
        translaterMap.put(Criteria.class, new CriteriaTranslater());
    }
}
