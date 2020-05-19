package edu.hust.webswmm.model.mdb.nonentity.resList;

import edu.hust.webswmm.model.mdb.entity.ResSystem;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CitySystems {
    private int timeStep;// 结果时间步长，以秒为基本单位
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;// 开始时间,时间格式："2014-10-29 15:19:23"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;// 结束时间，时间格式："2014-10-29 15:19:23"
    private List<Double> sysRain;
    private List<Double> sysInfil;
    private List<Double> sysFlow;
    private List<Double> sysTolInflow;
    private List<Double> sysFlood;
    private List<Double> sysOutflow;
    private List<Double> sysStorage;

    public CitySystems(int timeStep, Date startTime, Date endTime,List<ResSystem> citysystem) {
        this.timeStep = timeStep;
        this.startTime = startTime;
        this.endTime = endTime;
         List<Double> sysRain= new ArrayList<>();
         List<Double> sysInfil= new ArrayList<>() ;
         List<Double> sysFlow= new ArrayList<>();
         List<Double> sysTolInflow= new ArrayList<>();
         List<Double> sysFlood= new ArrayList<>();
         List<Double> sysOutflow= new ArrayList<>();
         List<Double> sysStorage= new ArrayList<>();
        for (int i = 0; i <citysystem.size(); i++) {
            sysRain.add(citysystem.get(i).getSysRain());
            sysInfil.add(citysystem.get(i).getSysInfil());
            sysFlow.add(citysystem.get(i).getSysFlow());
            sysTolInflow.add(citysystem.get(i).getSysTolInflow());
            sysFlood.add(citysystem.get(i).getSysFlood());
            sysOutflow.add(citysystem.get(i).getSysOutflow());
            sysStorage.add(citysystem.get(i).getSysStorage());
        }
        this.sysRain=sysRain;
        this.sysInfil=sysInfil;
        this.sysFlow=sysFlow;
        this.sysTolInflow=sysTolInflow;
        this.sysFlood=sysFlood;
        this.sysOutflow=sysOutflow;
        this.sysStorage=sysStorage;
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

    public List<Double> getSysRain() {
        return sysRain;
    }

    public void setSysRain(List<Double> sysRain) {
        this.sysRain = sysRain;
    }

    public List<Double> getSysInfil() {
        return sysInfil;
    }

    public void setSysInfil(List<Double> sysInfil) {
        this.sysInfil = sysInfil;
    }

    public List<Double> getSysFlow() {
        return sysFlow;
    }

    public void setSysFlow(List<Double> sysFlow) {
        this.sysFlow = sysFlow;
    }

    public List<Double> getSysTolInflow() {
        return sysTolInflow;
    }

    public void setSysTolInflow(List<Double> sysTolInflow) {
        this.sysTolInflow = sysTolInflow;
    }

    public List<Double> getSysFlood() {
        return sysFlood;
    }

    public void setSysFlood(List<Double> sysFlood) {
        this.sysFlood = sysFlood;
    }

    public List<Double> getSysOutflow() {
        return sysOutflow;
    }

    public void setSysOutflow(List<Double> sysOutflow) {
        this.sysOutflow = sysOutflow;
    }

    public List<Double> getSysStorage() {
        return sysStorage;
    }

    public void setSysStorage(List<Double> sysStorage) {
        this.sysStorage = sysStorage;
    }
}
