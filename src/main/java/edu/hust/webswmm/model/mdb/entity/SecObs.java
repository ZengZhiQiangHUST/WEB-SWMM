package edu.hust.webswmm.model.mdb.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 断面的实测水位和流量过程
 */
public class SecObs {
    private String secID;// 断面的id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date secTime;//时间，以秒为单位
    private double secWatObs;// 实测水位过程，单位m
    private double secVelObs;// 实测断面流速，单位m/s

    public SecObs() {
    }

    public SecObs(String secID, Date secTime, double secWatObs, double secVelObs) {
        this.secID = secID;
        this.secTime = secTime;
        this.secWatObs = secWatObs;
        this.secVelObs = secVelObs;
    }

    public String getSecID() {
        return secID;
    }

    public void setSecID(String secID) {
        this.secID = secID;
    }

    public Date getSecTime() {
        return secTime;
    }

    public void setSecTime(Date secTime) {
        this.secTime = secTime;
    }

    public double getSecWatObs() {
        return secWatObs;
    }

    public void setSecWatObs(double secWatObs) {
        this.secWatObs = secWatObs;
    }

    public double getSecVelObs() {
        return secVelObs;
    }

    public void setSecVelObs(double secVelObs) {
        this.secVelObs = secVelObs;
    }
}
