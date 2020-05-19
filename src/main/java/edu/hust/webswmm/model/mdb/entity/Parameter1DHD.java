package edu.hust.webswmm.model.mdb.entity;

public class Parameter1DHD {
    private String parameterName;
    private double parameterValue;

    public Parameter1DHD(String parameterName, double parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public Parameter1DHD() {
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public double getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(double parameterValue) {
        this.parameterValue = parameterValue;
    }
}
