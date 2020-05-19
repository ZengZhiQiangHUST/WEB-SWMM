package edu.hust.webswmm.model.mdb.nonentity.resStatistic;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 *子汇水区的统计
 */
public class SubStatistic {
    private String id;//要素的ID
    private String type;//要素的类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//结束时间
    private int stepTime;//时间步长
	private double totalRain;//总雨量
    private double totalInfil;//总下渗
    private double totalRunoff;//总径流
    private double peakRunoff;//径流峰值

    public SubStatistic(String id,String type, Date startTime, Date endTime, int stepTime, double totalRain, double totalInfil, double totalRunoff, double peakRunoff) {
        this.id=id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stepTime = stepTime;
        this.totalRain = totalRain;
        this.totalInfil = totalInfil;
        this.totalRunoff = totalRunoff;
        this.peakRunoff = peakRunoff;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStepTime() {
        return stepTime;
    }

    public void setStepTime(int stepTime) {
        this.stepTime = stepTime;
    }

    public double getTotalRain() {
        return totalRain;
    }

    public void setTotalRain(double totalRain) {
        this.totalRain = totalRain;
    }

    public double getTotalInfil() {
        return totalInfil;
    }

    public void setTotalInfil(double totalInfil) {
        this.totalInfil = totalInfil;
    }

    public double getTotalRunoff() {
        return totalRunoff;
    }

    public void setTotalRunoff(double totalRunoff) {
        this.totalRunoff = totalRunoff;
    }

    public double getPeakRunoff() {
        return peakRunoff;
    }

    public void setPeakRunoff(double peakRunoff) {
        this.peakRunoff = peakRunoff;
    }
}
