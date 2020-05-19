package edu.hust.webswmm.model.mdb.nonentity.resStatistic;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
public class JunStatistic {
    private String id;//要素的ID
    private String type;//要素的类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;//结束时间
    private double aveDepth;//平均水深=∑_1^36▒depth /36
    private double maxDepth;//最大水深=max (Depth Meters)
    private double maxHGL;//最大水头高=(最大水深+井底高程)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxTimeDepth;//最大水深发生时间
    private double maxLateralFlow;//最大侧向补给量
    private double maxInFlow;//最大入流量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxTimeInflow;//最大入流量发生时间
    private double latFlowVolume;//侧向补给总体积=y=y_n+(y_(n+1)-y_n)/300*150
    private double totalFlowVolume;//总入流体积=y=y_n+(y_(n+1)-y_n)/300*150
    private double floodedTime;//溢出时长
    private double maxFlood;//最大溢出量
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxTimeFlood;//最大溢出流量发生时间
    private double totalFloodVolume;//总计溢出体积=y=y_n+(y_(n+1)-y_n)/300*150

    public JunStatistic() {
    }

    public JunStatistic(String id, String type, Date startTime, Date endTime, double aveDepth, double maxDepth, double maxHGL, Date maxTimeDepth, double maxLateralFlow, double maxInFlow, Date maxTimeInflow, double latFlowVolume, double totalFlowVolume,double floodedTime,double maxFlood, Date maxTimeFlood, double totalFloodVolume) {
        this.id = id;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.aveDepth = aveDepth;
        this.maxDepth = maxDepth;
        this.maxHGL = maxHGL;
        this.maxTimeDepth = maxTimeDepth;
        this.maxLateralFlow = maxLateralFlow;
        this.maxInFlow = maxInFlow;
        this.maxTimeInflow = maxTimeInflow;
        this.latFlowVolume = latFlowVolume;
        this.totalFlowVolume = totalFlowVolume;
        this.floodedTime = floodedTime;
        this.maxFlood=maxFlood;
        this.maxTimeFlood = maxTimeFlood;
        this.totalFloodVolume = totalFloodVolume;
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

    public double getAveDepth() {
        return aveDepth;
    }

    public void setAveDepth(double aveDepth) {
        this.aveDepth = aveDepth;
    }

    public double getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(double maxDepth) {
        this.maxDepth = maxDepth;
    }

    public double getMaxHGL() {
        return maxHGL;
    }

    public void setMaxHGL(double maxHGL) {
        this.maxHGL = maxHGL;
    }

    public Date getMaxTimeDepth() {
        return maxTimeDepth;
    }

    public void setMaxTimeDepth(Date maxTimeDepth) {
        this.maxTimeDepth = maxTimeDepth;
    }

    public double getMaxLateralFlow() {
        return maxLateralFlow;
    }

    public void setMaxLateralFlow(double maxLateralFlow) {
        this.maxLateralFlow = maxLateralFlow;
    }

    public double getMaxInFlow() {
        return maxInFlow;
    }

    public void setMaxInFlow(double maxInFlow) {
        this.maxInFlow = maxInFlow;
    }

    public Date getMaxTimeInflow() {
        return maxTimeInflow;
    }

    public void setMaxTimeInflow(Date maxTimeInflow) {
        this.maxTimeInflow = maxTimeInflow;
    }

    public double getLatFlowVolume() {
        return latFlowVolume;
    }

    public void setLatFlowVolume(double latFlowVolume) {
        this.latFlowVolume = latFlowVolume;
    }

    public double getTotalFlowVolume() {
        return totalFlowVolume;
    }

    public void setTotalFlowVolume(double totalFlowVolume) {
        this.totalFlowVolume = totalFlowVolume;
    }

    public double getFloodedTime() {
        return floodedTime;
    }

    public void setFloodedTime(double floodedTime) {
        this.floodedTime = floodedTime;
    }

    public double getMaxFlood() {
        return maxFlood;
    }

    public void setMaxFlood(double maxFlood) {
        this.maxFlood = maxFlood;
    }

    public Date getMaxTimeFlood() {
        return maxTimeFlood;
    }

    public void setMaxTimeFlood(Date maxTimeFlood) {
        this.maxTimeFlood = maxTimeFlood;
    }

    public double getTotalFloodVolume() {
        return totalFloodVolume;
    }

    public void setTotalFloodVolume(double totalFloodVolume) {
        this.totalFloodVolume = totalFloodVolume;
    }
}
