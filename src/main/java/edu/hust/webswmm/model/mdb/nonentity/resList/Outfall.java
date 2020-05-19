package edu.hust.webswmm.model.mdb.nonentity.resList;

import edu.hust.webswmm.model.mdb.nonentity.AllFeatures;
import edu.hust.webswmm.model.mdb.entity.ResOutfalls;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 出水口要素类
 */
public class Outfall extends AllFeatures {
    private String type;
    private String outfallId;// 出口的id
    private int timeStep;// 结果时间步长，以秒为基本单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
    private List<Float> outfallDepth;
    private List<Float> outfallHgl;
    private List<Float> outfallLatflow;
    private List<Float> outfallFlow;// 流量，单位m³/s

    public Outfall() {
    }

    public Outfall(String type, String outfallId, int timeStep, Date startTime, Date endTime, List<Float> outfallDepth, List<Float> outfallHgl, List<Float> outfallLatflow, List<Float> outfallFlow) {
        this.type = type;
        this.outfallId = outfallId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        this.outfallDepth = outfallDepth;
        this.outfallHgl = outfallHgl;
        this.outfallLatflow = outfallLatflow;
        this.outfallFlow = outfallFlow;
    }

    /**
     * @param outfallId
     * @param timeStep
     * @param startTime
     * @param endTime
     * @param outfalls
     */
    public Outfall(String type,String outfallId, int timeStep, Date startTime, Date endTime, List<ResOutfalls> outfalls) {
        this.type=type;
        this.outfallId = outfallId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        List<Float> outfallDepth = new ArrayList<>();
        List<Float> outfallHgl = new ArrayList<>();
        List<Float> outfallLatflow = new ArrayList<>();
        List<Float> outfallFlow = new ArrayList<>();
        for (int i = 0; i < outfalls.size(); i++) {
            outfallDepth.add(outfalls.get(i).getOutfallDepth());
            outfallHgl.add(outfalls.get(i).getOutfallHgl());
            outfallLatflow.add(outfalls.get(i).getOutfallLatflow());
            outfallFlow.add(outfalls.get(i).getOutfallFlow());
        }
        this.outfallDepth = outfallDepth;
        this.outfallHgl = outfallHgl;
        this.outfallLatflow = outfallLatflow;
        this.outfallFlow = outfallFlow;
    }

    public Outfall(String type, String outfallId, int timeStep, Date startTime, Date endTime) {
        this.type = type;
        this.outfallId = outfallId;
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

    public String getOutfallId() {
        return outfallId;
    }

    public void setOutfallId(String outfallId) {
        this.outfallId = outfallId;
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

    public List<Float> getOutfallDepth() {
        return outfallDepth;
    }

    public void setOutfallDepth(List<Float> outfallDepth) {
        this.outfallDepth = outfallDepth;
    }

    public List<Float> getOutfallHgl() {
        return outfallHgl;
    }

    public void setOutfallHgl(List<Float> outfallHgl) {
        this.outfallHgl = outfallHgl;
    }

    public List<Float> getOutfallLatflow() {
        return outfallLatflow;
    }

    public void setOutfallLatflow(List<Float> outfallLatflow) {
        this.outfallLatflow = outfallLatflow;
    }

    public List<Float> getOutfallFlow() {
        return outfallFlow;
    }

    public void setOutfallFlow(List<Float> outfallFlow) {
        this.outfallFlow = outfallFlow;
    }
}
