package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.criteria.RelationCriteria;
import org.fl.opm.spec.criteria.SimpleCriteria;

import java.util.HashMap;
import java.util.Map;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 13:09
 */
public class SqlCriteriaTranslaters {
    private static Map<Class<? extends Criteria>, SqlCriteriaTranslater> translaterMap = new HashMap<Class<? extends Criteria>, SqlCriteriaTranslater>();
    public static String toWhereSql(Criteria criteria) throws Exception {
        if(criteria == null || criteria.isEmpty()){
            return "";
        }else{
            return getSqlCriteriaTranslater(criteria).translate(criteria);
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
        translaterMap.put(Criteria.class, new CriteriaTranslater());
    }
}
