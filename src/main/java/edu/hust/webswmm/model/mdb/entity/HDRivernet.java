package edu.hust.webswmm.model.mdb.entity;
/**
 * 一维水动力模型的边界类
 */
public class HDRivernet {
    private String riverNetID;//河网的ID
    private String riverID;//河段的ID
    private int byUp; //上边界条件类型，上边界可为水位、流量和内节点（0:水位;1:流量;-1:内结点）
    private int byDown; //下边界条件类型 ，上边界可为水位、流量、水位流量关系和内节点（0:水位;1:流量;2:水位流量关系曲线;-1:内结点）
    private String upSectionID; //上边界对应的实际断面
    private String downSectionID;//下边界对应的实际断面

    public HDRivernet() {
    }

    public HDRivernet(String riverNetID, String riverID, int byUp, int byDown, String upSectionID, String downSectionID) {
        this.riverNetID = riverNetID;
        this.riverID = riverID;
        this.byUp = byUp;
        this.byDown = byDown;
        this.upSectionID = upSectionID;
        this.downSectionID = downSectionID;
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

    public int getByUp() {
        return byUp;
    }

    public void setByUp(int byUp) {
        this.byUp = byUp;
    }

    public int getByDown() {
        return byDown;
    }

    public void setByDown(int byDown) {
        this.byDown = byDown;
    }

    public String getUpSectionID() {
        return upSectionID;
    }

    public void setUpSectionID(String upSectionID) {
        this.upSectionID = upSectionID;
    }

    public String getDownSectionID() {
        return downSectionID;
    }

    public void setDownSectionID(String downSectionID) {
        this.downSectionID = downSectionID;
    }
}
