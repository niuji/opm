package org.fl.opm.spec.criteria;

import org.fl.opm.spec.enums.Relation;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-20 11:15
 */
public class RelationCriteria extends Criteria {
    private Relation relation;
    private List<Criteria> criterias = new ArrayList<Criteria>();

    public RelationCriteria(Relation relation) {
        this.relation = relation;
    }

    public void add(Criteria criteria){
        criterias.add(criteria);
    }

    public Relation getRelation(){
        return relation;
    }

    public List<Criteria> getCriterias(){
        return criterias;
    }

    @Override
    public boolean isEmpty(){
        return false;
    }

}
