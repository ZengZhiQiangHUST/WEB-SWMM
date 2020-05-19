package edu.hust.webswmm.model.mdb.entity;
public class HDRivertopo {
    private String riverNetID;//河网ID
    private String riverID;//河段ID
    private String downRiverID;//下游河段的ID

    public HDRivertopo() {
    }

    public HDRivertopo(String riverNetID, String riverID, String downRiverID) {
        this.riverNetID = riverNetID;
        this.riverID = riverID;
        this.downRiverID = downRiverID;
    }

    public String getRiverNetID() {
        return riverNetID;
    }

    public void setRiverNetID(String riverNetID) {
        this.riverNetID = riverNetID;
    }

    public String getRiverID() {
        return riverID;
    }

    public void setRiverID(String riverID) {
        this.riverID = riverID;
    }

    public String getDownRiverID() {
        return downRiverID;
    }

    public void setDownRiverID(String downRiverID) {
        this.downRiverID = downRiverID;
    }
}
