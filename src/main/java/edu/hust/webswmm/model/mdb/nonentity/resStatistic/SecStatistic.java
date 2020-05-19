package edu.hust.webswmm.model.mdb.nonentity.resStatistic;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 断面的水动力统计
 */
public class SecStatistic {
    private String id;//要素的ID
    private String type;//要素的类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//结束时间
    private double maxWatlevel;//最大水位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  maxWatlevelTime;//最大水位对应的时间
    private double maxFlow;//最大流量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  maxFlowTime;//最大流量对应的时间
    private double maxVelocity;//最大流速
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date  maxVelTime;//最大流量对应的时间
    public SecStatistic() {
    }

    public SecStatistic(String id, String type, Date startTime, Date endTime, double maxWatlevel, double maxFlow, double maxVelocity,Date maxWatlevelTime,Date  maxFlowTime,Date  maxVelTime) {
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxWatlevel = maxWatlevel;
        this.maxFlow = maxFlow;
        this.maxVelocity = maxVelocity;
        this.maxWatlevelTime=maxWatlevelTime;
        this.maxFlowTime=maxFlowTime;
        this.maxVelTime=maxVelTime;

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

    public double getMaxWatlevel() {
        return maxWatlevel;
    }

    public void setMaxWatlevel(double maxWatlevel) {
        this.maxWatlevel = maxWatlevel;
    }

    public Date getMaxWatlevelTime() {
        return maxWatlevelTime;
    }

    public void setMaxWatlevelTime(Date maxWatlevelTime) {
        this.maxWatlevelTime = maxWatlevelTime;
    }

    public double getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(double maxFlow) {
        this.maxFlow = maxFlow;
    }

    public Date getMaxFlowTime() {
        return maxFlowTime;
    }

    public void setMaxFlowTime(Date maxFlowTime) {
        this.maxFlowTime = maxFlowTime;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public Date getMaxVelTime() {
        return maxVelTime;
    }

    public void setMaxVelTime(Date maxVelTime) {
        this.maxVelTime = maxVelTime;
    }
}
