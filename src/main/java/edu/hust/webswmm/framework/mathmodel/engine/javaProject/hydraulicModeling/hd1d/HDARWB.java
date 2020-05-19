package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;

/**
 * 一维水动力模型的断面水力要素类
 * @author zengz
 *
 */
public class HDARWB {
	double A; //过水断面面积
    double R;//水力半径
    double W; //湿周
    double B; //过水断面宽度
    /**
     * 构造方法，水力要素的初始化
     * @param a
     * @param r
     * @param w
     * @param b
     */
    public HDARWB(double a, double r, double w, double b) {
        A = a;
        R = r;
        W = w;
        B = b;
    }
    public double getA() {
        return A;
    }
    public double getR() {
        return R;
    }
    public double getB() {
        return B;
    }
}
