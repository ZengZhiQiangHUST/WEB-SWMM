package edu.hust.webswmm.model.mdb.entity;

public class RiverDistance {
    private String riverId;//河流的id
    private String secId;//断面的id
    private double distance;//断面所在的里程数

    public String getRiverId() {
        return riverId;
    }

    public void setRiverId(String riverId) {
        this.riverId = riverId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
