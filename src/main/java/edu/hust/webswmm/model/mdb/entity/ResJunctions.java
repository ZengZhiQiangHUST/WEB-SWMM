package edu.hust.webswmm.model.mdb.entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/**
 * 节点的实体类
 */
public class ResJunctions {
    private String junId;// 节点的id
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date junTime;//时间，以秒为单位
    private float junDepth;// 水深，单位m
    private float junHgl;// 水头高，单位m
    private float junLatFlow;// 侧向补给量，单位m³/s
    private float junFlow;// 流量，单位m³/s
    private float junFlood;// 淹没体积，单位106L
    public ResJunctions() {
    }

    public ResJunctions(String junId, Date junTime, float junDepth, float junHgl, float junLatFlow, float junFlow, float junFlood) {
        this.junId = junId;
        this.junTime = junTime;
        this.junDepth = junDepth;
        this.junHgl = junHgl;
        this.junLatFlow = junLatFlow;
        this.junFlow = junFlow;
        this.junFlood = junFlood;
    }

    public String getJunId() {
        return junId;
    }

    public void setJunId(String junId) {
        this.junId = junId;
    }

    public Date getJunTime() {
        return junTime;
    }

    public void setJunTime(Date junTime) {
        this.junTime = junTime;
    }

    public float getJunDepth() {
        return junDepth;
    }

    public void setJunDepth(float junDepth) {
        this.junDepth = junDepth;
    }

    public float getJunHgl() {
        return junHgl;
    }

    public void setJunHgl(float junHgl) {
        this.junHgl = junHgl;
    }

    public float getJunLatFlow() {
        return junLatFlow;
    }

    public void setJunLatFlow(float junLatFlow) {
        this.junLatFlow = junLatFlow;
    }

    public float getJunFlow() {
        return junFlow;
    }

    public void setJunFlow(float junFlow) {
        this.junFlow = junFlow;
    }

    public float getJunFlood() {
        return junFlood;
    }

    public void setJunFlood(float junFlood) {
        this.junFlood = junFlood;
    }
}

