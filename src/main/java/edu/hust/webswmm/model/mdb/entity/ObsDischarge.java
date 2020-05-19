package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * obs_discharge
 */
public class ObsDischarge {
    private String stationID;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dischargeTime;
    private double dischargeValue;

    public ObsDischarge() {
    }

    public ObsDischarge(String stationId, Date dischargeTime, double dischargeValue) {
        this.stationID = stationId;
        this.dischargeTime = dischargeTime;
        this.dischargeValue = dischargeValue;
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

    public Date getDischargeTime() {
        return dischargeTime;
    }

    public void setDischargeTime(Date dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public double getDischargeValue() {
        return dischargeValue;
    }

    public void setDischargeValue(double dischargeValue) {
        this.dischargeValue = dischargeValue;
    }
}
