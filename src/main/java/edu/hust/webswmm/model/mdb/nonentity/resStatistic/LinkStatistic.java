package edu.hust.webswmm.model.mdb.nonentity.resStatistic;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
public class LinkStatistic {
    private String id;//要素的ID
    private String type;//要素的类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//结束时间
    private double	maxFlow;//最大流量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date	maxTimeFlow;//最大流量发生时间
    private double	maxVelocity;//最大流速


    public LinkStatistic(String id,String type, Date startTime, Date endTime, double maxFlow, Date maxTimeFlow, double maxVelocity) {
        this.id=id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxFlow = maxFlow;
        this.maxTimeFlow = maxTimeFlow;
        this.maxVelocity = maxVelocity;
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

    public double getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(double maxFlow) {
        this.maxFlow = maxFlow;
    }

    public Date getMaxTimeFlow() {
        return maxTimeFlow;
    }

    public void setMaxTimeFlow(Date maxTimeFlow) {
        this.maxTimeFlow = maxTimeFlow;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }
}
