package edu.hust.webswmm.model.bdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * obs_waterlevel
 */
public class StWaterlevel {
    private String stationID;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date waterlevelTime;
    private double waterlevelValue;

    public StWaterlevel() {
    }

    public StWaterlevel(String stationId, Date waterLevelTime, double waterLevelValue) {
        this.stationID = stationId;
        this.waterlevelTime = waterLevelTime;
        this.waterlevelValue = waterLevelValue;
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

    public double getWaterlevelValue() {
        return waterlevelValue;
    }

}
