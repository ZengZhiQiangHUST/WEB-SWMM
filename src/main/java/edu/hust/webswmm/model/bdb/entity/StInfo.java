package edu.hust.webswmm.model.bdb.entity;
/**
 * st_info
 */
public class StInfo {
    private String stationPosition;
    private String stationID;
    private String stationType;

    public StInfo() {
    }

    public StInfo(String stationPosition, String stationID, String stationType) {
        this.stationPosition = stationPosition;
        this.stationID = stationID;
        this.stationType = stationType;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getStationType() {
        return stationType;
    }


}
