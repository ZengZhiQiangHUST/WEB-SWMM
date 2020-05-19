package edu.hust.webswmm.model.bdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * obs_discharge
 */
public class StDischarge {
    private String stationID;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dischargeTime;
    private double dischargeValue;

    public StDischarge() {

    }

    public StDischarge(String stationId, Date dischargeTime, double dischargeValue) {
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

    public Date getDischargeTime() {
        return dischargeTime;
    }

    public double getDischargeValue() {
        return dischargeValue;
    }

}
