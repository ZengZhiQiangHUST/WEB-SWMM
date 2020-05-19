package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;

public class HDSectionXY {
	String sectionID;//断面ID
    double startDistance;//起点距
    double elevation;//高程
    int hdpointsStype;//标记左堤岸、右堤岸（左堤岸1河道2右堤岸）

    public HDSectionXY(String sectionID, double startDistance, double elevation, int hdpointsStype) {
        this.sectionID = sectionID;
        this.startDistance = startDistance;
        this.elevation = elevation;
        this.hdpointsStype = hdpointsStype;
    }
    public String getSectionID() {
        return sectionID;
    }
    public double getStartDistance() {
        return startDistance;
    }

    public double getElevation() {
        return elevation;
    }

    public int getHdpointsStype() {
        return hdpointsStype;
    }
}
