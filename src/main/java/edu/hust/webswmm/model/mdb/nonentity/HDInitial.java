package edu.hust.webswmm.model.mdb.nonentity;

public class HDInitial {
    private String riverID;//断面ID
    private String sectionID;//断面ID
    private double initialZ;//初始的水位
    private double initialQ;//初始的流量

    public HDInitial() {
    }

    public HDInitial(String riverID, String sectionID, double initialZ, double initialQ) {
        this.riverID = riverID;
        this.sectionID = sectionID;
        this.initialZ = initialZ;
        this.initialQ = initialQ;
    }

    public String getRiverID() {
        return riverID;
    }

    public void setRiverID(String riverID) {
        this.riverID = riverID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public double getInitialZ() {
        return initialZ;
    }

    public void setInitialZ(double initialZ) {
        this.initialZ = initialZ;
    }

    public double getInitialQ() {
        return initialQ;
    }

    public void setInitialQ(double initialQ) {
        this.initialQ = initialQ;
    }
}
