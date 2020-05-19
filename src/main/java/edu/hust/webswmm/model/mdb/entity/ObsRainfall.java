package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * obs_rainfall
 */
public class ObsRainfall {
    private String stationID;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rainfallTime;
    private double rainfallValue;

    public ObsRainfall() {
    }

    public ObsRainfall(String stationId, Date rainfalltime, double rainfall) {
        this.stationID = stationId;
        this.rainfallTime = rainfalltime;
        this.rainfallValue = rainfall;
    }

    public String getStationId() {
        return stationID;
    }

    public void setStationId(String stationId) {
        this.stationID = stationId;
    }

    public double getRainfall() {
        return rainfallValue;
    }

    public void setRainfall(double rainfall) {
        this.rainfallValue = rainfall;
    }

    public Date getRainfallTime() {
        return rainfallTime;
    }

    public void setRainfallTime(Date rainfallTime) {
        this.rainfallTime = rainfallTime;
    }
}
