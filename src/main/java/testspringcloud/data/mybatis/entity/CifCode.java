package testspringcloud.data.mybatis.entity;

import java.io.Serializable;
import java.util.Date;

public class CifCode implements Cloneable,Serializable {
    private Integer SERIALNO;

    private String CODETYPE;

    private String TYPENAME;

    private String CODECODE;

    private String REMARK;

    private String FLAG;

    private String CREATOR;

    private Date CREATEDATE;

    private String OPERATOR;

    private Date UPDATEDATE;

    private String CODECNAME;

    public Integer getSERIALNO() {
        return SERIALNO;
    }

    public void setSERIALNO(Integer SERIALNO) {
        this.SERIALNO = SERIALNO;
    }

    public String getCODETYPE() {
        return CODETYPE;
    }

    public void setCODETYPE(String CODETYPE) {
        this.CODETYPE = CODETYPE;
    }

    public String getTYPENAME() {
        return TYPENAME;
    }

    public void setTYPENAME(String TYPENAME) {
        this.TYPENAME = TYPENAME;
    }

    public String getCODECODE() {
        return CODECODE;
    }

    public void setCODECODE(String CODECODE) {
        this.CODECODE = CODECODE;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getCREATOR() {
        return CREATOR;
    }

    public void setCREATOR(String CREATOR) {
        this.CREATOR = CREATOR;
    }

    public Date getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(Date CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getOPERATOR() {
        return OPERATOR;
    }

    public void setOPERATOR(String OPERATOR) {
        this.OPERATOR = OPERATOR;
    }

    public Date getUPDATEDATE() {
        return UPDATEDATE;
    }

    public void setUPDATEDATE(Date UPDATEDATE) {
        this.UPDATEDATE = UPDATEDATE;
    }

    public String getCODECNAME() {
        return CODECNAME;
    }

    public void setCODECNAME(String CODECNAME) {
        this.CODECNAME = CODECNAME;
    }
}