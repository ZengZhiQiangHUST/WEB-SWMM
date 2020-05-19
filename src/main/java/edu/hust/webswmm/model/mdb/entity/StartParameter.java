package edu.hust.webswmm.model.mdb.entity;

/**
 *start_parameter
 */
public class StartParameter {
    private String modelName;
    private String parameterName;
    private int parameterValue;

    public StartParameter() {
    }

    public StartParameter(String modelName, String parameterName, int parameterValue) {
        this.modelName = modelName;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public int getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(int parameterValue) {
        this.parameterValue = parameterValue;
    }
}
