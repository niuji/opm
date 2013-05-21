package org.fl.opm.jdbc.spring;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-7
 * Time: 下午12:53
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="t_message_content")
public class MessageContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "OID")
    private Integer id;
    @Column
    private String msgContent;
    @Column
    private String cntType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getCntType() {
        return cntType;
    }

    public void setCntType(String cntType) {
        this.cntType = cntType;
    }
}
