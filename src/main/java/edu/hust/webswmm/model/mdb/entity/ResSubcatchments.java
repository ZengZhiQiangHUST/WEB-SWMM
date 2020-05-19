package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 子汇水区的实体类
 */

public class ResSubcatchments {
    private String subId;// 子汇水区的id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date subTime;//时间，以秒为单位
    private  float subRain;// 雨量，单位mm
    private  float subInfil;// 渗透，单位mm
    private  float subFlow;// 径流，单位mm
    public ResSubcatchments() {
    }

    public ResSubcatchments(String subId, Date subTime, float subRain, float subInfil, float subFlow) {
        this.subId = subId;
        this.subTime = subTime;
        this.subRain = subRain;
        this.subInfil = subInfil;
        this.subFlow = subFlow;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public Date getSubTime() {
        return subTime;
    }

    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }

    public float getSubRain() {
        return subRain;
    }

    public void setSubRain(float subRain) {
        this.subRain = subRain;
    }

    public float getSubInfil() {
        return subInfil;
    }

    public void setSubInfil(float subInfil) {
        this.subInfil = subInfil;
    }

    public float getSubFlow() {
        return subFlow;
    }

    public void setSubFlow(float subFlow) {
        this.subFlow = subFlow;
    }
}
