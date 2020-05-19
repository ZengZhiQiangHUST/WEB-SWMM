package edu.hust.webswmm.model.mdb.nonentity.resList;

import edu.hust.webswmm.model.mdb.nonentity.AllFeatures;
import edu.hust.webswmm.model.mdb.entity.ResSubcatchments;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 子汇水区域的要素类
 */
public class Subcatchment extends AllFeatures {
    private String type;
    private String subId;// 子汇水区的id
    private int timeStep;// 结果时间步长，以秒为基本单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
    private List<Float> subRain;// 雨量，单位mm
    private List<Float> subInfil;// 渗透，单位mm
    private List<Float> subFlow;// 径流，单位mm

    public Subcatchment() {
    }


    public Subcatchment(String type, String subId, int timeStep, Date startTime, Date endTime, List<Float> subRain, List<Float> subInfil, List<Float> subFlow) {
        this.type = type;
        this.subId = subId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subRain = subRain;
        this.subInfil = subInfil;
        this.subFlow = subFlow;
    }

    /**
     * @param subId
     * @param timeStep
     * @param startTime
     * @param endTime
     * @param subResults
     */
    public Subcatchment(String type, String subId, int timeStep, Date startTime, Date endTime, List<ResSubcatchments> subResults) {
        this.type = type;
        this.subId = subId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        List<Float> subRain = new ArrayList<>();
        List<Float> subInfil = new ArrayList<>();
        List<Float> subFlow = new ArrayList<>();
        for (int i = 0; i < subResults.size(); i++) {
            subRain.add(subResults.get(i).getSubRain());
            subInfil.add(subResults.get(i).getSubInfil());
            subFlow.add(subResults.get(i).getSubFlow());
        }
        this.subRain = subRain;
        this.subInfil = subInfil;
        this.subFlow = subFlow;
    }

    public Subcatchment(String type, String subId, int timeStep, Date startTime, Date endTime) {
        this.type = type;
        this.subId = subId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public List<Float> getSubRain() {
        return subRain;
    }

    public void setSubRain(List<Float> subRain) {
        this.subRain = subRain;
    }

    public List<Float> getSubInfil() {
        return subInfil;
    }

    public void setSubInfil(List<Float> subInfil) {
        this.subInfil = subInfil;
    }

    public List<Float> getSubFlow() {
        return subFlow;
    }

    public void setSubFlow(List<Float> subFlow) {
        this.subFlow = subFlow;
    }
}
