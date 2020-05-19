package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
/**
 * 一维水动力模型断面点类
 * @author zengz
 *
 */
public class HDpoint {
    double startDistance;//起点距
    double elevation;//高程
    int mark;//标记左堤岸、右堤岸（左堤岸1河道2右堤岸）
/**
 * 构造方法，断面的点初始化
 * @param startDistance
 * @param elevation
 * @param mark
 */
    public HDpoint(double startDistance, double elevation, int mark) {
        this.startDistance = startDistance;
        this.elevation = elevation;
        this.mark = mark;
    }

    public double getstartDisatance() {
        return startDistance;
    }
    public double getElevation() {
        return elevation;
    }
    public int getMark() {
        return mark;
    }
}
