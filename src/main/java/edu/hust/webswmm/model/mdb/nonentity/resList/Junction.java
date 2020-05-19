package edu.hust.webswmm.model.mdb.nonentity.resList;
import edu.hust.webswmm.model.mdb.nonentity.AllFeatures;
import edu.hust.webswmm.model.mdb.entity.ResJunctions;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 节点要素类
 */
public class Junction extends AllFeatures {
    private String type;//类型
    private String junId;// 节点的id
    private int timeStep;// 结果时间步长，以秒为基本单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
    private List<Float> junDepth;// 水深，单位m
    private List<Float> junHgl;// 水头高，单位m
    private List<Float> junLatFlow;// 侧向补给量，单位m³/s
    private List<Float> junFlow;// 流量，单位m³/s
    private List<Float> junFlood;//溢出流量

    public Junction() {
    }

    public Junction(String type,String junId, int timeStep, Date startTime, Date endTime, List<Float> junDepth, List<Float> junHgl, List<Float> junLatFlow, List<Float> junFlow, List<Float> junFlood) {
        this.type=type;
        this.junId = junId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        this.junDepth = junDepth;
        this.junHgl = junHgl;
        this.junLatFlow = junLatFlow;
        this.junFlow = junFlow;
        this.junFlood = junFlood;
    }

    public Junction(String type,String junId, int timeStep, Date startTime, Date endTime, List<ResJunctions> junResults) {
        this.type=type;
        this.junId = junId;
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
        List<Float> junDepth = new ArrayList<>();
        List<Float> junHgl = new ArrayList<>();
        List<Float> junFlow = new ArrayList<>();
        List<Float> junFlood = new ArrayList<>();
        List<Float> junLatFlow = new ArrayList<>();
        for (int i = 0; i <junResults.size(); i++) {
            junDepth.add(junResults.get(i).getJunDepth());
            junHgl.add(junResults.get(i).getJunHgl());
            junFlow.add(junResults.get(i).getJunFlow());
            junFlood.add(junResults.get(i).getJunFlood());
            junLatFlow.add(junResults.get(i).getJunLatFlow());
        }
        this.junDepth=junDepth;
        this.junHgl=junHgl;
        this.junFlood=junFlood;
        this.junFlow=junFlow;
        this.junLatFlow=junLatFlow;
    }

    public Junction(String type, String junId, int timeStep, Date startTime, Date endTime) {
        this.type = type;
        this.junId = junId;
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

    public String getJunId() {
        return junId;
    }

    public void setJunId(String junId) {
        this.junId = junId;
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

    public List<Float> getJunDepth() {
        return junDepth;
    }

    public void setJunDepth(List<Float> junDepth) {
        this.junDepth = junDepth;
    }

    public List<Float> getJunHgl() {
        return junHgl;
    }

    public void setJunHgl(List<Float> junHgl) {
        this.junHgl = junHgl;
    }

    public List<Float> getJunLatFlow() {
        return junLatFlow;
    }

    public void setJunLatFlow(List<Float> junLatFlow) {
        this.junLatFlow = junLatFlow;
    }

    public List<Float> getJunFlow() {
        return junFlow;
    }

    public void setJunFlow(List<Float> junFlow) {
        this.junFlow = junFlow;
    }

    public List<Float> getJunFlood() {
        return junFlood;
    }

    public void setJunFlood(List<Float> junFlood) {
        this.junFlood = junFlood;
    }
}


