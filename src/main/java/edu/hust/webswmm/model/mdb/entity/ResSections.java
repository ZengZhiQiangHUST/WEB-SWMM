package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 断面的实体类
 */
public class ResSections {
    private String secId;// 断面的id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date secTime;//时间，以秒为单位
    private double secFlow;// 流量过程，单位m³/s
    private double secWatlevel;// 水位过程，单位m
    private double secVelocity;// 断面流速，单位m/s

    public ResSections() {

    }
    public ResSections(String secId, Date secTime, double secFlow, double secWatlevel, double secVelocity) {
        this.secId = secId;
        this.secTime = secTime;
        this.secFlow = secFlow;
        this.secWatlevel = secWatlevel;
        this.secVelocity = secVelocity;
    }

    public ResSections(String secId, double secFlow, double secWatlevel, double secVelocity) {
        this.secId = secId;
        this.secFlow = secFlow;
        this.secWatlevel = secWatlevel;
        this.secVelocity = secVelocity;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getSecTime() {
        return secTime;
    }

    public void setSecTime(Date secTime) {
        this.secTime = secTime;
    }

    public double getSecFlow() {
        return secFlow;
    }

    public void setSecFlow(double secFlow) {
        this.secFlow = secFlow;
    }

    public double getSecWatlevel() {
        return secWatlevel;
    }

    public void setSecWatlevel(double secWatlevel) {
        this.secWatlevel = secWatlevel;
    }

    public double getSecVelocity() {
        return secVelocity;
    }

    public void setSecVelocity(double secVelocity) {
        this.secVelocity = secVelocity;
    }
}
