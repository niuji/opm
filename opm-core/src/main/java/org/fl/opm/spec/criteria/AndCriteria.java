package org.fl.opm.spec.criteria;

import org.fl.opm.spec.enums.Relation;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-20 11:10
 */
public class AndCriteria extends RelationCriteria {
    private AndCriteria(){
        super(Relation.AND);
    }

    public AndCriteria(Criteria c1, Criteria c2) {
        this();
        add(c1);
        add(c2);
    }


}
