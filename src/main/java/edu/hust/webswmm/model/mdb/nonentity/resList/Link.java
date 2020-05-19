package edu.hust.webswmm.model.mdb.nonentity.resList;
import edu.hust.webswmm.model.mdb.nonentity.AllFeatures;
import edu.hust.webswmm.model.mdb.entity.ResConduits;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 管段要素类
 */
public class Link extends AllFeatures {
        private String type;//类型
        private String linkId;// 管段的id
        private int timeStep;// 结果时间步长，以秒为基本单位
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
        private List<Float> linkFlow;// 流量，单位m³/s
        private List<Float> linkVelocity;// 流动速率，单位m/s
        private List<Float> linkDepth;// 水深，单位m
        private List<Float> linkVol;//体积，单位106L
        private List<Float> linkCapacity;

    public Link() {
    }

    public Link(String type,String linkId, int timeStep, Date startTime, Date endTime, List<Float> linkFlow, List<Float> linkVelocity, List<Float> linkDepth, List<Float> linkVol,List<Float> linkCapacity) {
        this.type=type;
        this.linkId = linkId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        this.linkFlow = linkFlow;
        this.linkVelocity = linkVelocity;
        this.linkDepth = linkDepth;
        this.linkVol = linkVol;
        this.linkCapacity=linkCapacity;
    }

    /**
     *
     * @param linkId
     * @param timeStep
     * @param startTime
     * @param endTime
     * @param linkResults
     */
    public Link(String type,String linkId, int timeStep, Date startTime, Date endTime,List<ResConduits> linkResults) {
        this.type=type;
        this.linkId = linkId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        List<Float> linkFlow = new ArrayList<>();
        List<Float> linkVelocity = new ArrayList<>();
        List<Float> linkDepth = new ArrayList<>();
        List<Float> linkVol = new ArrayList<>();
        List<Float> linkCapacity = new ArrayList<>();
        for (int i = 0; i <linkResults.size(); i++) {
            linkFlow.add(linkResults.get(i).getLinkFlow());
            linkVelocity.add(linkResults.get(i).getLinkVelocity());
            linkDepth.add(linkResults.get(i).getLinkDepth());
            linkVol.add(linkResults.get(i).getLinkVol());
            linkCapacity.add(linkResults.get(i).getLinkCapacity());
        }
        this.linkFlow=linkFlow;
        this.linkVelocity=linkVelocity;
        this.linkDepth=linkDepth;
        this.linkVol=linkVol;
        this.linkCapacity=linkCapacity;
    }

    public Link(String type, String linkId, int timeStep, Date startTime, Date endTime) {
        this.type = type;
        this.linkId = linkId;
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

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
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

    public List<Float> getLinkFlow() {
        return linkFlow;
    }

    public void setLinkFlow(List<Float> linkFlow) {
        this.linkFlow = linkFlow;
    }

    public List<Float> getLinkVelocity() {
        return linkVelocity;
    }

    public void setLinkVelocity(List<Float> linkVelocity) {
        this.linkVelocity = linkVelocity;
    }

    public List<Float> getLinkDepth() {
        return linkDepth;
    }

    public void setLinkDepth(List<Float> linkDepth) {
        this.linkDepth = linkDepth;
    }

    public List<Float> getLinkVol() {
        return linkVol;
    }

    public void setLinkVol(List<Float> linkVol) {
        this.linkVol = linkVol;
    }

    public List<Float> getLinkCapacity() {
        return linkCapacity;
    }

    public void setLinkCapacity(List<Float> linkCapacity) {
        this.linkCapacity = linkCapacity;
    }
}
