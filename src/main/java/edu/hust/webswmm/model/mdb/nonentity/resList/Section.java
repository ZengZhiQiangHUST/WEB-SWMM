package edu.hust.webswmm.model.mdb.nonentity.resList;

import edu.hust.webswmm.model.mdb.nonentity.AllFeatures;
import edu.hust.webswmm.model.mdb.entity.ResSections;
import edu.hust.webswmm.model.mdb.entity.SecObs;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 断面要素类
 */
public class Section extends AllFeatures {
    private String type;
    private String secId;// 断面的id
    private int timeStep;// 结果时间步长，以秒为基本单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
    private List<Double> secFlow;// 流量过程，单位m³/s
    private List<Double> secWatlevel;// 水位过程，单位m
    private List<Double> secVelocity;// 断面流速，单位m/s
    private List<Double> secWatObs;// 实测水位过程，单位m
    private List<Double> secVelObs;// 实测断面流速，单位m/s

    public Section() {

    }
    /**
     *
     * @param secId
     * @param timeStep
     * @param startTime
     * @param endTime
     * @param secResults
     */
    public Section(String type, String secId, int timeStep, Date startTime, Date endTime, List<ResSections> secResults, List<SecObs> secObHDS) {
        this.type=type;
        this.secId = secId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        List<Double> secFlow=new ArrayList<>();
        List<Double> secWatlevel=new ArrayList<>();
        List<Double> secVelocity=new ArrayList<>();
         List<Double> secWatObs=new ArrayList<>();// 实测水位过程，单位m
         List<Double> secVelObs=new ArrayList<>();// 实测断面流速，单位m/s
        for (int i = 0; i < secResults.size(); i++) {
            secFlow.add(secResults.get(i).getSecFlow());
            secWatlevel.add(secResults.get(i).getSecWatlevel());
            secVelocity.add(secResults.get(i).getSecVelocity());
        }
        for (int i = 0; i < secObHDS.size(); i++) {
            secVelObs.add(secObHDS.get(i).getSecVelObs());
            secWatObs.add(secObHDS.get(i).getSecWatObs());
        }

        this.secFlow=secFlow;
        this.secWatlevel=secWatlevel;
        this.secVelocity=secVelocity;
        this.secVelObs=secVelObs;
        this.secWatObs=secWatObs;
    }
    public Section(String type, String secId, int timeStep, Date startTime, Date endTime) {
        this.type = type;
        this.secId = secId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Section(String type, String secId, int timeStep, Date startTime, Date endTime, List<ResSections> secResults) {
        this.type=type;
        this.secId = secId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        List<Double> secFlow=new ArrayList<>();
        List<Double> secWatlevel=new ArrayList<>();
        List<Double> secVelocity=new ArrayList<>();
        for (int i = 0; i < secResults.size(); i++) {
            secFlow.add(secResults.get(i).getSecFlow());
            secWatlevel.add(secResults.get(i).getSecWatlevel());
            secVelocity.add(secResults.get(i).getSecVelocity());
        }
        this.secFlow=secFlow;
        this.secWatlevel=secWatlevel;
        this.secVelocity=secVelocity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
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

    public List<Double> getSecFlow() {
        return secFlow;
    }

    public void setSecFlow(List<Double> secFlow) {
        this.secFlow = secFlow;
    }

    public List<Double> getSecWatlevel() {
        return secWatlevel;
    }

    public void setSecWatlevel(List<Double> secWatlevel) {
        this.secWatlevel = secWatlevel;
    }

    public List<Double> getSecVelocity() {
        return secVelocity;
    }

    public void setSecVelocity(List<Double> secVelocity) {
        this.secVelocity = secVelocity;
    }

    public List<Double> getSecWatObs() {
        return secWatObs;
    }

    public void setSecWatObs(List<Double> secWatObs) {
        this.secWatObs = secWatObs;
    }

    public List<Double> getSecVelObs() {
        return secVelObs;
    }

    public void setSecVelObs(List<Double> secVelObs) {
        this.secVelObs = secVelObs;
    }
}

