package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 出水口的实体类
 */

public class ResOutfalls {
    private String outfallId;// 出口的id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outfallTime;//时间，以秒为单位
    private float outfallDepth;
    private float outfallHgl;
    private float outfallLatflow;
    private float outfallFlow;// 流量，单位m³/s
    public ResOutfalls() {
    }

    public ResOutfalls(String outfallId, Date outfallTime, float outfallDepth, float outfallHgl, float outfallLatflow, float outfallFlow) {
        this.outfallId = outfallId;
        this.outfallTime = outfallTime;
        this.outfallDepth = outfallDepth;
        this.outfallHgl = outfallHgl;
        this.outfallLatflow = outfallLatflow;
        this.outfallFlow = outfallFlow;
    }

    public String getOutfallId() {
        return outfallId;
    }

    public void setOutfallId(String outfallId) {
        this.outfallId = outfallId;
    }

    public Date getOutfallTime() {
        return outfallTime;
    }

    public void setOutfallTime(Date outfallTime) {
        this.outfallTime = outfallTime;
    }

    public float getOutfallDepth() {
        return outfallDepth;
    }

    public void setOutfallDepth(float outfallDepth) {
        this.outfallDepth = outfallDepth;
    }

    public float getOutfallHgl() {
        return outfallHgl;
    }

    public void setOutfallHgl(float outfallHgl) {
        this.outfallHgl = outfallHgl;
    }

    public float getOutfallLatflow() {
        return outfallLatflow;
    }

    public void setOutfallLatflow(float outfallLatflow) {
        this.outfallLatflow = outfallLatflow;
    }

    public float getOutfallFlow() {
        return outfallFlow;
    }

    public void setOutfallFlow(float outfallFlow) {
        this.outfallFlow = outfallFlow;
    }
}
