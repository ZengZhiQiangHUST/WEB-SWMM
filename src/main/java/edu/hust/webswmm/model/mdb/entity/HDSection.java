package edu.hust.webswmm.model.mdb.entity;

/**
 * 一维水动力模型对应的断面表
 */
public class HDSection {
    public String riverID;//河段ID
    public String sectionID;//断面ID
    public double nLeft;//断面左堤岸曼宁系数
    public double nMiddle;//断面河道曼宁系数
    public double nRight;//断面右堤岸曼宁系数
    public double dx;//断面间距

    public HDSection() {
    }

    public HDSection(String riverID, String sectionID, double nLeft, double nMiddle, double nRight, double dx) {
        this.riverID = riverID;
        this.sectionID = sectionID;
        this.nLeft = nLeft;
        this.nMiddle = nMiddle;
        this.nRight = nRight;
        this.dx = dx;
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

    public double getnLeft() {
        return nLeft;
    }

    public void setnLeft(double nLeft) {
        this.nLeft = nLeft;
    }

    public double getnMiddle() {
        return nMiddle;
    }

    public void setnMiddle(double nMiddle) {
        this.nMiddle = nMiddle;
    }

    public double getnRight() {
        return nRight;
    }

    public void setnRight(double nRight) {
        this.nRight = nRight;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }
}
