package edu.hust.webswmm.model.mdb.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ResSystem {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sysTime;//时间
    private double sysRain;
    private double sysInfil;
    private double sysFlow;//总径流
    private double sysTolInflow;//
    private double sysFlood;
    private double sysOutflow;
    private double sysStorage;

    public ResSystem() {
    }

    public ResSystem(Date sysTime, double sysRain, double sysInfil, double sysFlow, double sysTolInflow, double sysFlood, double sysOutflow, double sysStorage) {
        this.sysTime = sysTime;
        this.sysRain = sysRain;
        this.sysInfil = sysInfil;
        this.sysFlow = sysFlow;
        this.sysTolInflow = sysTolInflow;
        this.sysFlood = sysFlood;
        this.sysOutflow = sysOutflow;
        this.sysStorage = sysStorage;
    }

    public Date getSysTime() {
        return sysTime;
    }

    public void setSysTime(Date sysTime) {
        this.sysTime = sysTime;
    }

    public double getSysRain() {
        return sysRain;
    }

    public void setSysRain(double sysRain) {
        this.sysRain = sysRain;
    }

    public double getSysInfil() {
        return sysInfil;
    }

    public void setSysInfil(double sysInfil) {
        this.sysInfil = sysInfil;
    }

    public double getSysFlow() {
        return sysFlow;
    }

    public void setSysFlow(double sysFlow) {
        this.sysFlow = sysFlow;
    }

    public double getSysTolInflow() {
        return sysTolInflow;
    }

    public void setSysTolInflow(double sysTolInflow) {
        this.sysTolInflow = sysTolInflow;
    }

    public double getSysFlood() {
        return sysFlood;
    }

    public void setSysFlood(double sysFlood) {
        this.sysFlood = sysFlood;
    }

    public double getSysOutflow() {
        return sysOutflow;
    }

    public void setSysOutflow(double sysOutflow) {
        this.sysOutflow = sysOutflow;
    }

    public double getSysStorage() {
        return sysStorage;
    }

    public void setSysStorage(double sysStorage) {
        this.sysStorage = sysStorage;
    }
}
