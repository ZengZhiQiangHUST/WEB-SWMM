package edu.hust.webswmm.model.bdb.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * obs_rainfall
 */
public class StRainfall {
    private String stationID;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rainfallTime;
    private double rainfallValue;

    public StRainfall() {
    }

    public StRainfall(String stationId, Date rainfalltime, double rainfall) {
        this.stationID = stationId;
        this.rainfallTime = rainfalltime;
        this.rainfallValue = rainfall;
    }

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public Date getRainfallTime() {
        return rainfallTime;
    }

    public double getRainfallValue() {
        return rainfallValue;
    }

}
