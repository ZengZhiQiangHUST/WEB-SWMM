package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * obs_waterlevel
 */
public class ObsWaterlevel {
    private String stationID;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date waterlevelTime;
    private double waterlevelValue;

    public ObsWaterlevel() {
    }

    public ObsWaterlevel(String stationId, Date waterLevelTime, double waterLevelValue) {
        this.stationID = stationId;
        this.waterlevelTime = waterLevelTime;
        this.waterlevelValue = waterLevelValue;
    }

    public String getStationId() {
        return stationID;
    }

    public void setStationId(String stationId) {
        this.stationID = stationId;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public Date getWaterlevelTime() {
        return waterlevelTime;
    }

    public void setWaterlevelTime(Date waterlevelTime) {
        this.waterlevelTime = waterlevelTime;
    }

    public double getWaterlevelValue() {
        return waterlevelValue;
    }

    public void setWaterlevelValue(double waterlevelValue) {
        this.waterlevelValue = waterlevelValue;
    }
}
