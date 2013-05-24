package org.fl.opm.spec.jdbc;

import org.fl.opm.spec.criteria.Criteria;
import org.fl.opm.spec.criteria.RelationCriteria;
import org.fl.opm.spec.enums.Relation;

import java.util.List;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-23 13:27
 */
public class RelationCriteriaTranslater implements SqlCriteriaTranslater<RelationCriteria> {
    @Override
    public String translate(RelationCriteria criteria, JdbcParamterHolder jph) throws Exception {
        List<Criteria> criterias = criteria.getCriterias();
        Relation relation = criteria.getRelation();
        String relationStr = "";
        switch (relation) {
            case AND:
                relationStr = "and";
                break;
            case OR:
                relationStr = "or";
                break;
            default:
                throw new Exception("Relation " + relation.name() + " is not supported.");
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Criteria c : criterias){
            if(first){
                first = false;
            }                 else{
                sb.append(' ').append(relationStr).append(' ');
            }
            sb.append(SqlCriteriaTranslaters.toWhereSql(c, jph));
        }
        return sb.toString();
    }

}
