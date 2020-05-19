package edu.hust.webswmm.model.mdb.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 水文站点的实体类
 */
public class HydroStation {
    private String stationId;//水文站点编码
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hydroTime;//时间
    private double flowValue;//流量值
    private double watValue;//水位值

    public HydroStation(String stationId, Date hydroTime, double flowValue, double watValue) {
        this.stationId = stationId;
        this.hydroTime = hydroTime;
        this.flowValue = flowValue;
        this.watValue = watValue;
    }

    public HydroStation() {
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public Date getHydroTime() {
        return hydroTime;
    }

    public void setHydroTime(Date hydroTime) {
        this.hydroTime = hydroTime;
    }

    public double getFlowValue() {
        return flowValue;
    }

    public void setFlowValue(double flowValue) {
        this.flowValue = flowValue;
    }

    public double getWatValue() {
        return watValue;
    }

    public void setWatValue(double watValue) {
        this.watValue = watValue;
    }
}
