package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
import java.util.ArrayList;
/**
 * 一维水动力模型的边界条件类
 * @author zengz
 *
 */
public class HDBoundary {
    private String riverID;//河段的ID
    private int BY1; //上边界条件类型，上边界可为水位、流量和内节点（0:水位;1:流量;-1:内结点）    
    private int BY2; //下边界条件类型 ，上边界可为水位、流量、水位流量关系和内节点（0:水位;1:流量;2:水位流量关系曲线;-1:内结点）          
    private String upSectionID; //上边界对应的实际断面
    private String downSectionID;//下边界对应的实际断面
    private ArrayList<Double> upBoundary; //上边界值
    private ArrayList<Double> downBoundary; //下边界值
    private double xy[][];//水位流量关系值
    /**
     * 构造方法，边界条件的初始化
     * @param riverID
     * @param BY1
     * @param BY2
     * @param upSectionID
     * @param downSectionID
     * @param upBoundary
     * @param downBoundary
     * @param xy
     */
    public HDBoundary(String riverID, int BY1, int BY2, String upSectionID, String downSectionID, ArrayList<Double> upBoundary, ArrayList<Double> downBoundary,double[][] xy) {
        this.riverID = riverID;
        this.BY1 = BY1;
        this.BY2 = BY2;
        this.upSectionID = upSectionID;
        this.downSectionID = downSectionID;
        this.upBoundary = upBoundary;
        this.downBoundary = downBoundary;
        this.xy=xy;
    }
    public double[][] getXy() {
        return xy;
    }
    public int getBY1() {
        return BY1;
    }
    public int getBY2() {
        return BY2;
    }
    public ArrayList<Double> getUpBoundary() {
        return upBoundary;
    }
    public ArrayList<Double> getDownBoundary() {
        return downBoundary;
    }
}





