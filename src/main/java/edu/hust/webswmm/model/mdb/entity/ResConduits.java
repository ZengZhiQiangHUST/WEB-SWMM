package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 管段的实体类
 */
public class ResConduits {
    private String linkId;// 管段的id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date linkTime;//时间，以秒为单位
    private float linkFlow;// 流量，单位m³/s
    private float linkVelocity;// 流动速率，单位m/s
    private float linkDepth;// 水深，单位m
    private float linkVol;//体积，单位106L
    private float linkCapacity;

    public ResConduits() {
    }

    public ResConduits(String linkId, Date linkTime, float linkFlow, float linkVelocity, float linkDepth, float linkVol, float linkCapacity) {
        this.linkId = linkId;
        this.linkTime = linkTime;
        this.linkFlow = linkFlow;
        this.linkVelocity = linkVelocity;
        this.linkDepth = linkDepth;
        this.linkVol = linkVol;
        this.linkCapacity=linkCapacity;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Date getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(Date linkTime) {
        this.linkTime = linkTime;
    }

    public float getLinkFlow() {
        return linkFlow;
    }

    public void setLinkFlow(float linkFlow) {
        this.linkFlow = linkFlow;
    }

    public float getLinkVelocity() {
        return linkVelocity;
    }

    public void setLinkVelocity(float linkVelocity) {
        this.linkVelocity = linkVelocity;
    }

    public float getLinkDepth() {
        return linkDepth;
    }

    public void setLinkDepth(float linkDepth) {
        this.linkDepth = linkDepth;
    }

    public float getLinkVol() {
        return linkVol;
    }

    public void setLinkVol(float linkVol) {
        this.linkVol = linkVol;
    }

    public float getLinkCapacity() {
        return linkCapacity;
    }

    public void setLinkCapacity(float linkCapacity) {
        this.linkCapacity = linkCapacity;
    }
}
