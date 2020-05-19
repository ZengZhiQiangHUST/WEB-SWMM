package edu.hust.webswmm.model.mdb.entity;

public class StationLocation {
    private String stationID;
    private String locationType;
    private String locationID;

    public StationLocation() {
    }

    public StationLocation(String stationID, String locationType, String locationID) {
        this.stationID = stationID;
        this.locationType = locationType;
        this.locationID = locationID;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }
}
