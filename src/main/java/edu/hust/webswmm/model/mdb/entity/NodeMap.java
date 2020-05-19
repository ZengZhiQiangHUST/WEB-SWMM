package edu.hust.webswmm.model.mdb.entity;

public class NodeMap {
    private String gId;
    private String serialNum;
    private String type;

    public NodeMap() {
    }

    public NodeMap(String gId, String serialNum, String type) {
        this.gId = gId;
        this.serialNum = serialNum;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
