package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
import java.util.ArrayList;
/**
 * 一维水动力模型初始条件类
 * @author zengz
 *
 */
public class HDInitCondition {
	    private String riverID;//河段ID
	    private ArrayList<Double>  QAL;//河段断面的初始流量
	    private ArrayList<Double>  ZAL;//河段断面的初始水位
	    /**
	     * 构造方法，初始条件的初始化
	     * @param riverID
	     * @param QAL
	     * @param ZAL
	     */
	    public HDInitCondition(String riverID, ArrayList<Double> QAL, ArrayList<Double> ZAL) {
	        this.riverID = riverID;
	        this.QAL = QAL;
	        this.ZAL = ZAL;
	    }
	    public ArrayList<Double> getQAL() {
	        return QAL;
	    }
	    public ArrayList<Double> getZAL() {
	        return ZAL;
	    }
}
