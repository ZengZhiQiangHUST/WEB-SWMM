package edu.hust.webswmm.model.mdb.nonentity.resList;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 洪水淹没过程展示
 */
public class Flood {
    private String type;
    private String subId;// 子汇水区的id
    private int timeStep;// 结果时间步长，以秒为基本单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
    private List<Float> subFlow;// 径流，单位mm

    public Flood() {
    }

    public Flood(String type, String subId, int timeStep, Date startTime, Date endTime, List<Float> subFlow) {
        this.type = type;
        this.subId = subId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subFlow = subFlow;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public int getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(int timeStep) {
        this.timeStep = timeStep;
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

    public List<Float> getSubFlow() {
        return subFlow;
    }

    public void setSubFlow(List<Float> subFlow) {
        this.subFlow = subFlow;
    }
}
