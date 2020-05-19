package edu.hust.webswmm.model.mdb.entity;

public class RiverSec {
    private String rivernetID;
    private String riverID;
    private String secID;

    public RiverSec() {
    }

    public RiverSec(String rivernetID, String riverID, String secID) {
        this.rivernetID = rivernetID;
        this.riverID = riverID;
        this.secID = secID;
    }

    public String getRivernetID() {
        return rivernetID;
    }

    public void setRivernetID(String rivernetID) {
        this.rivernetID = rivernetID;
    }

    public String getRiverID() {
        return riverID;
    }

    public void setRiverID(String riverID) {
        this.riverID = riverID;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }
}
