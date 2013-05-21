package org.fl.opm.jdbc.spring;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午1:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_message_base")
public class MessageBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "OID")
    private Integer id;
    @Column
    private String srcKey;
    @Column
    private Date rcvTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrcKey() {
        return srcKey;
    }

    public void setSrcKey(String srcKey) {
        this.srcKey = srcKey;
    }

    public Date getRcvTime() {
        return rcvTime;
    }

    public void setRcvTime(Date rcvTime) {
        this.rcvTime = rcvTime;
    }
}
