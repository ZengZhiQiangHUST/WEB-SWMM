package edu.hust.webswmm.model.mdb.entity;

/**
 * 一维水动力模型的测点表
 */
public class HDPoints {
    private String riverNetID;//河网的ID
    private String riverID;//河段的ID
    private String sectionID;//断面的ID
    private double x;//起点距
    private double y;//高程

    public HDPoints() {
    }

    public HDPoints(String riverNetID, String riverID, String sectionID, double x, double y) {
        this.riverNetID = riverNetID;
        this.riverID = riverID;
        this.sectionID = sectionID;
        this.x = x;
        this.y = y;
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

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
