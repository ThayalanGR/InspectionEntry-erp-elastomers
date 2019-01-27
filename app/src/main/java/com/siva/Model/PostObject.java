package com.siva.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostObject {

    @SerializedName("cmpdid")
    @Expose
    private String cmpdid;
    @SerializedName("mdlrref")
    @Expose
    private String mdlrref;
    @SerializedName("isexternal")
    @Expose
    private String isexternal;
    @SerializedName("inspdate")
    @Expose
    private String inspdate;
    @SerializedName("recqty")
    @Expose
    private Integer recqty;
    @SerializedName("appqty")
    @Expose
    private Integer appqty;
    @SerializedName("inspector")
    @Expose
    private String inspector;
    @SerializedName("planid")
    @Expose
    private String planid;
    @SerializedName("rejection")
    @Expose
    private List<Rejection> rejection = null;

    public String getCmpdid() {
        return cmpdid;
    }

    public void setCmpdid(String cmpdid) {
        this.cmpdid = cmpdid;
    }

    public String getMdlrref() {
        return mdlrref;
    }

    public void setMdlrref(String mdlrref) {
        this.mdlrref = mdlrref;
    }

    public String getIsexternal() {
        return isexternal;
    }

    public void setIsexternal(String isexternal) {
        this.isexternal = isexternal;
    }

    public String getInspdate() {
        return inspdate;
    }

    public void setInspdate(String inspdate) {
        this.inspdate = inspdate;
    }

    public Integer getRecqty() {
        return recqty;
    }

    public void setRecqty(Integer recqty) {
        this.recqty = recqty;
    }

    public Integer getAppqty() {
        return appqty;
    }

    public void setAppqty(Integer appqty) {
        this.appqty = appqty;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }

    public List<Rejection> getRejection() {
        return rejection;
    }

    public void setRejection(List<Rejection> rejection) {
        this.rejection = rejection;
    }
    public class Rejection {

        @SerializedName("rej_short_name")
        @Expose
        private String rejShortName;
        @SerializedName("value")
        @Expose
        private Integer value;

        public String getRejShortName() {
            return rejShortName;
        }

        public void setRejShortName(String rejShortName) {
            this.rejShortName = rejShortName;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }
}