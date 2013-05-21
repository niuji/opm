package org.fl.opm.jdbc.spring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangyixin.stephen
 * Date: 13-5-6
 * Time: 下午5:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class PayMethod {
    @Id
    @Column(name = "BizCode")
    private String bizCode;

    @Column(name = "BDesc")
    private String bdesc;

    @Column(name = "InsertDate")
    private Date insertDate;

    @Column(name = "ShowIndex")
    private Integer showIndex;

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBdesc() {
        return bdesc;
    }

    public void setBdesc(String bdesc) {
        this.bdesc = bdesc;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Integer getShowIndex() {
        return showIndex;
    }

    public void setShowIndex(Integer showIndex) {
        this.showIndex = showIndex;
    }
}
