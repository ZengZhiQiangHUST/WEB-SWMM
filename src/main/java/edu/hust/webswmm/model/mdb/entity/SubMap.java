package edu.hust.webswmm.model.mdb.entity;

public class SubMap {
    private String gId;
    private String serialNum;

    public SubMap() {
    }

    public SubMap(String gId, String serialNum) {
        this.gId = gId;
        this.serialNum = serialNum;
    }

    public String getgId() {
        return gId;
    }

    public void setgId(String gId) {
        this.gId = gId;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
}
