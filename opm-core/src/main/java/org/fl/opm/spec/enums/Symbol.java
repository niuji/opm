package org.fl.opm.spec.enums;

/**
 * User: jiangyixin.stephen
 * Date: 2013-05-20 10:37
 */
public enum Symbol {
    ISNULL("为空"), GT("大于"), LT("小于"), GE("大于等于"), LE("小于等于"), EQUAL("等于");

    private String desc;

    private Symbol(String desc){
        this.desc = desc;
    }
}
