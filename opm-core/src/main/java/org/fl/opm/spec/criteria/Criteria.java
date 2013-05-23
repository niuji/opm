package org.fl.opm.spec.criteria;

import org.fl.opm.spec.enums.Symbol;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-17 13:55
 */
public class Criteria {

    private Criteria root;

    Criteria(){
    }

    public static Criteria emptyCriteria() {
        return new Criteria();
    }

    public Criteria and(String name, Symbol symbol, Object val, Object type) throws Exception {
        Criteria sc = new SimpleCriteria(name, symbol, val, type);
        if(root != null){
            root = new AndCriteria(root, sc);
        } else{
            root = sc;
        }
        return this;
    }

    public boolean isEmpty(){
        return root == null || root.isEmpty();
    }

    public Criteria getRoot() {
        return root;
    }
}
