package org.fl.opm.jdbc.spring;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-7
 * Time: 下午5:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class MerchantContractfee {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rowid;

    @Column
    private Long serviceid;

    @Column
    private BigDecimal maxvalue;

    @Column
    private BigDecimal minvalue;

    @Column
    private Integer feetype;

    @Column
    private Date createtime;

    public Long getRowid() {
        return rowid;
    }

    public void setRowid(Long rowid) {
        this.rowid = rowid;
    }

    public Long getServiceid() {
        return serviceid;
    }

    public void setServiceid(Long serviceid) {
        this.serviceid = serviceid;
    }

    public BigDecimal getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(BigDecimal maxvalue) {
        this.maxvalue = maxvalue;
    }

    public BigDecimal getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(BigDecimal minvalue) {
        this.minvalue = minvalue;
    }

    public Integer getFeetype() {
        return feetype;
    }

    public void setFeetype(Integer feetype) {
        this.feetype = feetype;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
