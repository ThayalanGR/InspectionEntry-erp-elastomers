package com.siva.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class Inspection {

    @SerializedName("response")
    @Expose
    public Response response;
    @SerializedName("rejectionList")
    @Expose
    public List<RejectionList> rejectionList = null;
    @SerializedName("userList")
    @Expose
    public List<String> userList = null;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public List<RejectionList> getRejectionList() {
        return rejectionList;
    }

    public void setRejectionList(List<RejectionList> rejectionList) {
        this.rejectionList = rejectionList;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public class RejectionList {

        @SerializedName("sno")
        @Expose
        public String sno;
        @SerializedName("rej_type")
        @Expose
        public String rejType;
        @SerializedName("rej_short_name")
        @Expose
        public String rejShortName;

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getRejType() {
            return rejType;
        }

        public void setRejType(String rejType) {
            this.rejType = rejType;
        }

        public String getRejShortName() {
            return rejShortName;
        }

        public void setRejShortName(String rejShortName) {
            this.rejShortName = rejShortName;
        }

    }
    public  class Response {

        @SerializedName("issref")
        @Expose
        private String issref;
        @SerializedName("cmpdname")
        @Expose
        private String cmpdname;
        @SerializedName("cmpdrefno")
        @Expose
        private String cmpdrefno;
        @SerializedName("isexternal")
        @Expose
        private String isexternal;
        @SerializedName("currrec")
        @Expose
        private String currrec;
        @SerializedName("cmpdid")
        @Expose
        private String cmpdid;
        @SerializedName("sno")
        @Expose
        private String sno;
        @SerializedName("defrecdate")
        @Expose
        private String defrecdate;
        @SerializedName("defrecdatef")
        @Expose
        private String defrecdatef;

        public String getIssref() {
            return issref;
        }

        public void setIssref(String issref) {
            this.issref = issref;
        }

        public String getCmpdname() {
            return cmpdname;
        }

        public void setCmpdname(String cmpdname) {
            this.cmpdname = cmpdname;
        }

        public String getCmpdrefno() {
            return cmpdrefno;
        }

        public void setCmpdrefno(String cmpdrefno) {
            this.cmpdrefno = cmpdrefno;
        }

        public String getIsexternal() {
            return isexternal;
        }

        public void setIsexternal(String isexternal) {
            this.isexternal = isexternal;
        }

        public String getCurrrec() {
            return currrec;
        }

        public void setCurrrec(String currrec) {
            this.currrec = currrec;
        }

        public String getCmpdid() {
            return cmpdid;
        }

        public void setCmpdid(String cmpdid) {
            this.cmpdid = cmpdid;
        }

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }

        public String getDefrecdate() {
            return defrecdate;
        }

        public void setDefrecdate(String defrecdate) {
            this.defrecdate = defrecdate;
        }

        public String getDefrecdatef() {
            return defrecdatef;
        }

        public void setDefrecdatef(String defrecdatef) {
            this.defrecdatef = defrecdatef;
        }

    }
}