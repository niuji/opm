package org.fl.opm.spec;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-3
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
public class SortDefinition {
    private String name;
    private String order;

    public SortDefinition(String name, String order){
        this.name = name;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public String getOrder() {
        return order;
    }

}
