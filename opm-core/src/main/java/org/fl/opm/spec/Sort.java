package org.fl.opm.spec;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-3
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public class Sort {
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private List<SortDefinition> sortDefinitions = new ArrayList<SortDefinition>();

    public Sort add(String name, String order){
        sortDefinitions.add(new SortDefinition(name, order));
        return this;
    }

    public List<SortDefinition> getSortDefinitions(){
        return sortDefinitions;
    }
}
