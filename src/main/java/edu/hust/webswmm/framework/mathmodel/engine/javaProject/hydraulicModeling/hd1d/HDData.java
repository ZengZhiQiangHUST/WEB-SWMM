package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
import java.util.ArrayList;
import java.util.Hashtable;
/**
 * 一维水动力模型所需要的输入数据和输出数据类
 * @author zengz
 *
 */
public class HDData
{
	private String modelFlag = "hd";//模型的标识符
	public ArrayList<BYData> byDatas;//模型的所有边界条件
	public Hashtable<String,ArrayList<InitialData>> initiDatas;//模型的初始条件
	public Hashtable<String,ArrayList<outputData>> outputDatas;//模型的输出结果
	public ArrayList<RiverTopo> riverTopos;//模型的拓扑结构
	public Hashtable<String,ArrayList<HdSection>> hdSections;//模型的断面信息数据
	public boolean isPreHeat;//是否进行预热计算的判定
	/**
	 * 构造方法，模型输入数据的初始化
	 * @param byDatas
	 * @param initiDatas
	 * @param riverTopos
	 * @param hdSections
	 */
	public HDData( ArrayList<BYData> byDatas, Hashtable<String, ArrayList<InitialData>> initiDatas, ArrayList<RiverTopo> riverTopos, Hashtable<String, ArrayList<HdSection>> hdSections/*, double dt, String startDateTime, String endDateTime*/) {
		this.byDatas = byDatas;
		this.initiDatas = initiDatas;
		this.riverTopos = riverTopos;
		this.hdSections = hdSections;
	}

	public HDData(ArrayList<BYData> byDatas, Hashtable<String, ArrayList<InitialData>> initiDatas, ArrayList<RiverTopo> riverTopos, Hashtable<String, ArrayList<HdSection>> hdSections, boolean isPreHeat) {
		this.byDatas = byDatas;
		this.initiDatas = initiDatas;
		this.riverTopos = riverTopos;
		this.hdSections = hdSections;
		this.isPreHeat = isPreHeat;
	}

	/**
	 * 内部类，河网的拓扑结构类
	 */
	public static class RiverTopo{
		public String riverID;//河段的ID
		public String downRiverID;//河段对应的下游河段
    /**
     * 构造方法，河网拓扑结构初始化
     * @param riverID
     * @param downRiverID
     */
		public RiverTopo(String riverID, String downRiverID) {
			this.riverID = riverID;
			this.downRiverID = downRiverID;
		}
		public String getRiverID() {
			return riverID;
		}
		public String getDownRiverID() {
			return downRiverID;
		}
		public void setRiverID(String riverID) {
			this.riverID = riverID;
		}
		public void setDownRiverID(String downRiverID) {
			this.downRiverID = downRiverID;
		}

	}
	/**
	 * 内部类，计算初始条件类
	 */
	public static class InitialData{
		public String secID;//断面ID
		public double initialZ;//初始的水位
		public double initialQ;//初始的流量
		/**
		 * 构造方法，计算初始条件的初始化
		 * @param secID
		 * @param initialZ
		 * @param initialQ
		 */
		public InitialData(String secID, double initialZ, double initialQ) {
			this.secID = secID;
			this.initialZ = initialZ;
			this.initialQ = initialQ;
		}
		public String getSecID() {
			return secID;
		}
		public void setSecID(String secID) {
			this.secID = secID;
		}
		public double getInitialZ() {
			return initialZ;
		}
		public void setInitialZ(double initialZ) {
			this.initialZ = initialZ;
		}
		public double getInitialQ() {
			return initialQ;
		}
		public void setInitialQ(double initialQ) {
			this.initialQ = initialQ;
		}
	}
	/**
	 * 内部类，边界条件输入类
	 */
	public static class BYData {
		private String riverID;//河段的ID
		private int BY1; //上边界条件类型，上边界可为水位、流量和内节点（0:水位;1:流量;-1:内结点）
		private int BY2; //下边界条件类型 ，上边界可为水位、流量、水位流量关系和内节点（0:水位;1:流量;2:水位流量关系曲线;-1:内结点）
	    private String upSectionID; //上边界对应的实际断面
	    private String downSectionID;//下边界对应的实际断面
	    private ArrayList<Double> upBoundary; //上边界值
	    private ArrayList<Double> downBoundary; //下边界值
	    private double xy[][];//水位流量关系值
      /**
       * 构造方法，边界条件的
       * @param riverID
       * @param BY1
       * @param BY2
       * @param upSectionID
       * @param downSectionID
       * @param upBoundary
       * @param downBoundary
       * @param xy
       */
		public BYData(String riverID, int BY1, int BY2, String upSectionID, String downSectionID, ArrayList<Double> upBoundary, ArrayList<Double> downBoundary,double[][] xy) {
			this.riverID = riverID;
			this.BY1 = BY1;
			this.BY2 = BY2;
			this.upSectionID = upSectionID;
			this.downSectionID = downSectionID;
			this.upBoundary = upBoundary;
			this.downBoundary = downBoundary;
			this.xy=xy;
		}

		public BYData() {
		}



		public void setRiverID(String riverID) {
			this.riverID = riverID;
		}

		public void setBY1(int bY1) {
			BY1 = bY1;
		}

		public void setBY2(int bY2) {
			BY2 = bY2;
		}

		public void setUpSectionID(String upSectionID) {
			this.upSectionID = upSectionID;
		}

		public void setDownSectionID(String downSectionID) {
			this.downSectionID = downSectionID;
		}

		public void setUpBoundary(ArrayList<Double> upBoundary) {
			this.upBoundary = upBoundary;
		}

		public void setDownBoundary(ArrayList<Double> downBoundary) {
			this.downBoundary = downBoundary;
		}

		public void setXy(double[][] xy) {
			this.xy = xy;
		}

		public double[][] getXy() {
			return xy;
		}
		public String getRiverID() {
			return riverID;
		}
		public int getBY1() {
			return BY1;
		}
		public int getBY2() {
			return BY2;
		}
		public String getUpSectionID() {
			return upSectionID;
		}
		public String getDownSectionID() {
			return downSectionID;
		}
		public ArrayList<Double> getUpBoundary() {
			return upBoundary;
		}
		public ArrayList<Double> getDownBoundary() {
			return downBoundary;
		}
	}
	/**
     * 内部类，水动力模型的输出类
     */
	public static class outputData{
		public String secID;//断面编码
		public double[] Z;//水位
		public double[] Q;//流量
		/**
		 * 构造方法，模型输出的初始化
		 * @param secID
		 * @param z
		 * @param q
		 */
		public outputData(String secID, double[] z, double[] q) {
			this.secID = secID;
			Z = z;
			Q = q;
		}
		public String getSecID() {
			return secID;
		}
		public double[] getZ() {
			return Z;
		}
		public double[] getQ() {
			return Q;
		}
	}
	/**
	 *内部类， 断面结构类
	 */
	public static class HdSection {
		public String RiverID;//河段ID
		public String SectionID;//断面ID
		public String SectionName;//断面名称
		public ArrayList<HdPoint> hdPoints;//断面上的测点
		public String lat;//经度
		public String lon;//纬度
		public double nclLeft;//断面左堤岸曼宁系数
		public double nclRiver;//断面河道曼宁系数
		public double nclRight;//断面右堤岸曼宁系数
		public double dx;//断面间距
        /**
         * 构造方法，断面结构的初始化
         * @param riverID
         * @param sectionID
         * @param sectionName
         * @param hdPoints
         * @param lat
         * @param lon
         * @param nclLeft
         * @param nclRiver
         * @param nclRight
         * @param dx
         */
		public HdSection(String riverID, String sectionID, String sectionName, ArrayList<HdPoint> hdPoints, String lat, String lon, double nclLeft, double nclRiver, double nclRight, double dx) {
			RiverID = riverID;
			SectionID = sectionID;
			SectionName = sectionName;
			this.hdPoints = hdPoints;
			this.lat = lat;
			this.lon = lon;
			this.nclLeft = nclLeft;
			this.nclRiver = nclRiver;
			this.nclRight = nclRight;
			this.dx = dx;
		}
		public String getRiverID() {
			return RiverID;
		}

		public void setRiverID(String riverID) {
			RiverID = riverID;
		}

		public void setSectionID(String sectionID) {
			SectionID = sectionID;
		}

		public void setSectionName(String sectionName) {
			SectionName = sectionName;
		}

		public void setHdPoints(ArrayList<HdPoint> hdPoints) {
			this.hdPoints = hdPoints;
		}

		public void setLat(String lat) {
			this.lat = lat;
		}

		public void setLon(String lon) {
			this.lon = lon;
		}

		public void setNclLeft(double nclLeft) {
			this.nclLeft = nclLeft;
		}

		public void setNclRiver(double nclRiver) {
			this.nclRiver = nclRiver;
		}

		public void setNclRight(double nclRight) {
			this.nclRight = nclRight;
		}

		public void setDx(double dx) {
			this.dx = dx;
		}

		public ArrayList<HdPoint> getHdPoints() {
			return hdPoints;
		}
		public String getSectionID() {
			return SectionID;
		}
		public String getSectionName() {
			return SectionName;
		}
		public String getLat() {
			return lat;
		}
		public String getLon() {
			return lon;
		}
		public double getNclLeft() {
			return nclLeft;
		}
		public double getNclRiver() {
			return nclRiver;
		}
		public double getNclRight() {
			return nclRight;
		}
		public double getDx() {
			return dx;
		}
	}
	/**
	 * 内部类，断面所在点类
	 */
	public static class HdPoint{
		double startDistance;//起点距
		double elevation;//高程
		int mark;//标记左堤岸、右堤岸（左堤岸1河道2右堤岸）
		/**
		 * 构造方法，断面点类的初始化
		 * @param startDistance
		 * @param elevation
		 * @param mark
		 */
		public HdPoint(double startDistance, double elevation, int mark) {
			this.startDistance = startDistance;
			this.elevation = elevation;
			this.mark=mark;
		}
		public double getStartDistance() {
			return startDistance;
		}
		public double getElevation() {
			return elevation;
		}
		public int getMark() {
			return mark;
		}
	}

	/**
	 * 内部类，断面间的区间入流
	 * @return
	 */
	public static class HDSectionInflow{
		public String sectionID;//断面ID
		public ArrayList<Double> inflowValue;//入流值
		/**
		 * 构造方法，区间入流的初始化
		 * @param sectionID
		 * @param inflowValue
		 */
		public HDSectionInflow(String sectionID, ArrayList<Double> inflowValue) {
			this.sectionID = sectionID;
			this.inflowValue = inflowValue;
		}
		public String getSectionID() {
			return sectionID;
		}
		public void setSectionID(String sectionID) {
			this.sectionID = sectionID;
		}
		public ArrayList<Double> getInflowValue() {
			return inflowValue;
		}
		public void setInflowValue(ArrayList<Double> inflowValue) {
			this.inflowValue = inflowValue;
		}
	}
	public ArrayList<BYData> getByDatas() {
		return byDatas;
	}

	public void setByDatas(ArrayList<BYData> byDatas) {
		this.byDatas = byDatas;
	}

	public Hashtable<String, ArrayList<InitialData>> getInitiDatas() {
		return initiDatas;
	}

	public void setInitiDatas(Hashtable<String, ArrayList<InitialData>> initiDatas) {
		this.initiDatas = initiDatas;
	}

	public Hashtable<String,ArrayList<outputData>> getOutputDatas() {
		return outputDatas;
	}

	public void setOutputDatas(Hashtable<String,ArrayList<outputData>> outputDatas) {
		this.outputDatas = outputDatas;
	}
	
	public Hashtable<String, ArrayList<HdSection>> getHdSections() {
		return hdSections;
	}

	public void setHdSections(Hashtable<String, ArrayList<HdSection>> hdSections) {
		this.hdSections = hdSections;
	}

	public void setRiverTopos(ArrayList<RiverTopo> riverTopos) {
		this.riverTopos = riverTopos;
	}

	public ArrayList<RiverTopo> getRiverTopos() {
		return riverTopos;
	}
	public boolean isPreHeat() {
		return isPreHeat;
	}
	public void setPreHeat(boolean isPreHeat) {
		this.isPreHeat = isPreHeat;
	}
	public String getModelFlag() {
	return modelFlag;
	}
}
