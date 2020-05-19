package edu.hust.webswmm.model.mdb.entity;

public class OutfallMap {
    private String gId;
    private String serialNum;

    public OutfallMap(String gId, String serialNum) {
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
