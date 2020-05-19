package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
import java.util.ArrayList;
import java.util.Hashtable;
/**
 * 一维水动力模型河道类
 * @author zengz
 *
 */
public class HDRiver {
	private String riverID;//河段ID
	private Hashtable<String,ArrayList<HDSection>> sectionAL;//河段的断面
	Hashtable<String, HDTopo> hatopo;//河网拓扑结构
	private static double g = 9.81;//重力加速度
	private double theta;
	private double alpha;
	private double dt;//计算时间步长
	private int BY1;//河段的上边界条件
	private int BY2;//河段的下边界条件
	private  ArrayList<Double> upBoundary;//河段的上边界条件值
	private  ArrayList<Double> downBoundary;//河段下边界条件值
	private double[][] xy;//河段水位流量关系值
	private ArrayList<Double> initConditionQ;//河段断面的初始计算条件-流量
	private ArrayList<Double> initConditionZ;//河段断面的初始计算条件-水位
	//-----------------------------------------
	//追赶系数PP、VV、SS、TT
	//-----------------------------------------
	private ArrayList<Double> PP;
	private ArrayList<Double> VV;
	private ArrayList<Double> SS;
	private ArrayList<Double> TT;
/**
 * 构造方法
 * @param riverID
 * @param sectionAL
 * @param theta
 * @param alpha
 * @param dt
 * @param BY1
 * @param BY2
 * @param upBoundary
 * @param downBoundary
 * @param initConditionQ
 * @param initConditionZ
 * @param hatopo
 * @param xy
 */
	public HDRiver(String riverID, Hashtable<String,ArrayList<HDSection>> sectionAL, double theta, double alpha, double dt, int BY1, int BY2, ArrayList<Double> upBoundary, ArrayList<Double> downBoundary, ArrayList<Double> initConditionQ, ArrayList<Double> initConditionZ, Hashtable<String, HDTopo> hatopo,double xy[][])
	{
		this.riverID = riverID;
		this.sectionAL = sectionAL;
		this.hatopo=hatopo;
		this.theta = theta;
		this.alpha = alpha;
		this.dt = dt;
		this.BY1 = BY1;
		this.BY2 = BY2;
		this.upBoundary = upBoundary;
		this.downBoundary = downBoundary;
		this.initConditionQ = initConditionQ;
		this.initConditionZ = initConditionZ;
		this.xy=xy;
		for (int i = 0; i < this.initConditionQ.size(); i++)
		{
			this.sectionAL.get(riverID).get(i).getQ().add(this.initConditionQ.get(i));
			this.sectionAL.get(riverID).get(i).getZ().add(this.initConditionZ.get(i));
		}
	}
	/**
	 * 向前计算追赶系数
	 * @param riversID
	 */
	public void computeForward(String riversID) {
		//---------------------------------------------------------------------------------------
		//C、D、E、F、G、H、Y1、Y2、Y3、Y4为计算追赶系数的中间参数
		//---------------------------------------------------------------------------------------
		SS = new ArrayList<Double>();
		TT = new ArrayList<Double>();
		SS.add(0.0);
		TT.add(0.0);
		ArrayList<Double> D= new ArrayList<Double>();
		ArrayList<Double> C= new ArrayList<Double>();
		ArrayList<Double> E= new ArrayList<Double>();
		ArrayList<Double> G= new ArrayList<Double>();
		ArrayList<Double> F= new ArrayList<Double>();
		ArrayList<Double> H= new ArrayList<Double>();

		ArrayList<Double> Y1= new ArrayList<Double>();
		ArrayList<Double> Y2= new ArrayList<Double>();
		ArrayList<Double> Y3= new ArrayList<Double>();
		ArrayList<Double> Y4= new ArrayList<Double>();

			for (int i = 0; i < this.sectionAL.get(riversID).size() - 1; i++) {//河段断面的循环
               //断面1的水力要素
				double H1=this.sectionAL.get(riversID).get(i).getZ().get(this.sectionAL.get(riversID).get(i).getZ().size() - 1);
				double minLevel1=this.sectionAL.get(riversID).get(i).getMinz();
				HDARWB hdarw1 = this.sectionAL.get(riversID).get(i).getHDARWB(H1,minLevel1);
		       //断面2的水力要素
				double H2=this.sectionAL.get(riversID).get(i + 1).getZ().get(this.sectionAL.get(riversID).get(i + 1).getZ().size() - 1);
				double minLevel2=this.sectionAL.get(riversID).get(i + 1).getMinz();
				HDARWB hdarw2 = this.sectionAL.get(riversID).get(i + 1).getHDARWB(H2,minLevel2);
				double a1 = hdarw1.getA();//断面1的过水断面面积
				double a2 = hdarw2.getA();//断面2的过水断面面积
				double b1 = hdarw1.getB();//断面1的过水断面宽度
				double b2 = hdarw2.getB();//断面2的过水断面宽度
				double r1 = hdarw1.getR();//断面1的水力半径
				double r2 = hdarw2.getR();//断面2的水力半径
				
				double nclLeft=this.sectionAL.get(riversID).get(i).getNclLeft();//获取断面1的左岸曼宁系数
				double nclRiver=this.sectionAL.get(riversID).get(i).getNclRiver();//获取断面1的河道曼宁系数
				double nclRight=this.sectionAL.get(riversID).get(i).getNclRight();//获取断面1的右岸曼宁系数
				
			
				//通过getNcl（）方法计算断面1的曼宁系数
				double n1 = this.sectionAL.get(riversID).get(i).getNcl(this.sectionAL.get(riversID).get(i).getMinz(),this.sectionAL.get(riversID).get(i).getZ().get(this.sectionAL.get(riversID).get(i).getZ().size() - 1),nclLeft, nclRiver, nclRight);
				
				double nclLeft1=this.sectionAL.get(riversID).get(i+1).getNclLeft();//获取断面2的左岸曼宁系数
				double nclRiver1=this.sectionAL.get(riversID).get(i+1).getNclRiver();//获取断面2的河道曼宁系数
				double nclRight1=this.sectionAL.get(riversID).get(i+1).getNclRight();//获取断面2的右岸曼宁系数
				//通过getNcl（）方法计算断面2的曼宁系数
				double n2 = this.sectionAL.get(riversID).get(i + 1).getNcl(this.sectionAL.get(riversID).get(i).getMinz(),this.sectionAL.get(riversID).get(i + 1).getZ().get(this.sectionAL.get(riversID).get(i + 1).getZ().size() - 1),nclLeft1, nclRiver1, nclRight1);
              
				//断面1 计算时段前一时段的流量
				double q1 = this.sectionAL.get(riversID).get(i).getQ().get(this.sectionAL.get(riversID).get(i).getQ().size() - 1);
				//断面2 计算时段前一时段的流量
				double q2 = this.sectionAL.get(riversID).get(i + 1).getQ().get(this.sectionAL.get(riversID).get(i + 1).getQ().size() - 1);
				//断面1 计算时段前一时段的水位
				double z1 = this.sectionAL.get(riversID).get(i).getZ().get(this.sectionAL.get(riversID).get(i).getZ().size() - 1);
				//断面2计算时段前一时段的水位
				double z2 = this.sectionAL.get(riversID).get(i + 1).getZ().get(this.sectionAL.get(riversID).get(i + 1).getZ().size() - 1);
                //断面1的谢才系数
				double c1 = Math.pow(r1, 1.0 / 6.0) / n1;
				//断面2的谢才系数
				double c2 = Math.pow(r2, 1.0 / 6.0) / n2;
				double u1= q1 / a1;
				double u2= q2 / a2;
				double dx = this.sectionAL.get(riversID).get(i).getDx();//断面1和断面2之间的间距
				//--------------------------------------------------------------------------------------
				//--------------------------------------------------------------------------------------
				C.add((b1 + b2) * (dx) / (4.0 * dt * theta));
				D.add(-(1 - theta) * (q2 - q1) / theta + C.get(i) * (z2 + z1));
				E.add(dx / (2.0 * theta * dt) - alpha * u1 + (g * Math.abs(u1) * dx) / (2.0 * theta * r1 * Math.pow(c1, 2)));
				G.add(dx / (2.0 * theta * dt) + alpha * u2 + (g * Math.abs(u2) * dx) / (2.0 * theta * r2 * Math.pow(c2, 2)));
				F.add((g / 2.0) * (a1 + a2));
				H.add((dx / (2.0 * theta * dt)) * (q2 + q1) - ((1 - theta) / theta) * (alpha * (u2 * q2 - u1 * q1) - F.get(i) * (z2 - z1)));
				//--------------------------------------------------------------------------------------
				//--------------------------------------------------------------------------------------
				/**
				 * 上边界为流量或内节点条件
				 */
				if (BY1 == 1 || BY1 == -1)
				{
				Y1.add(VV.get(i) + C.get(i));
				Y2.add(F.get(i) + E.get(i) * VV.get(i));
				Y3.add(D.get(i) + PP.get(i));
				Y4.add(H.get(i) - E.get(i) * PP.get(i));
				this.SS.add((G.get(i) * Y3.get(i) - Y4.get(i)) / (Y1.get(i) * G.get(i) + Y2.get(i)));
				this.TT.add((C.get(i) * G.get(i) - F.get(i)) / (Y1.get(i) * G.get(i) + Y2.get(i)));
				this.PP.add(Y3.get(i) - Y1.get(i)*SS.get(i+1));
				this.VV.add(C.get(i) - Y1.get(i)*TT.get(i+1));
			   }
				//--------------------------------------------------------------------------------------
				//--------------------------------------------------------------------------------------
		        else if (BY1 == 0)  //上边界为水位条件
		        {
				Y1.add(D.get(i) - C.get(i)*PP.get(i));
				Y2.add(H.get(i) + F.get(i) * PP.get(i));
				Y3.add(1 + C.get(i)*VV.get(i));
				Y4.add(E.get(i) + F.get(i) * VV.get(i));
				this.SS.add((C.get(i) * Y2.get(i) - F.get(i)*Y1.get(i)) / (F.get(i) *Y3.get(i) +C.get(i)*Y4.get(i)));
				this.TT.add((C.get(i) * G.get(i) - F.get(i)) / (F.get(i) *Y3.get(i) +C.get(i)*Y4.get(i)));
				this.PP.add((Y1.get(i) + Y3.get(i) * SS.get(i + 1)) / C.get(i));
				this.VV.add((Y3.get(i) * TT.get(i + 1) + 1) / C.get(i));
				}
			  }
	        }
			//--------------------------------------------------------------------------------------
			//--------------------------------------------------------------------------------------

	/**
	 * 向后计算水位和流量
	 * @param riversID1
	 * @param k
	 */
	public void computeBackward(String riversID1,int k) {
		/**
		 * 1、下边界为水位
		 */
		if (BY2 == 0) {
			/**
			 * 获取该河段最后一个断面的水位
			 */
			double z2 = this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getZ().get(this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getZ().size() - 1);
			/**
			 * 1.1、本河段上的上边界为流量和内节点,这两种情况的追赶方程相同
			 */
			if (BY1 == 1 || BY1 == -1) {
				/**
				 * 计算末断面的流量
				 */
				double q = this.PP.get(this.sectionAL.get(riversID1).size() - 1) - this.VV.get(this.sectionAL.get(riversID1).size() - 1) * z2;
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().add(q);
				/**
				 * 针对断面的循环，求取水位和流量
				 */
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--) {
					double z1 =SS.get(i + 1) - this.TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getZ().get(this.sectionAL.get(riversID1).get(i + 1).getZ().size() - 1);
					/**
					 * 限制水位范围
					 */
					z1=limitWaterLevel(z1,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getZ().add(z1);

					double q1 = PP.get(i) - VV.get(i) * z1;
					this.sectionAL.get(riversID1).get(i).getQ().add(q1);
				}
			}
			/**
			 * 1.2、本河段上的上边界为水位
			 */
			if (BY1 == 0) {
            /**
             * 计算末断面的流量
             */
				double q = (PP.get(this.sectionAL.get(riversID1).size() - 1) - z2) / VV.get(this.sectionAL.get(riversID1).size() - 1);
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().add(q);
				/**
				 * 针对断面的循环，求取水位和流量
				 */
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--) {
					double q1 = this.SS.get(i + 1) - TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getQ().get(this.sectionAL.get(riversID1).get(i + 1).getQ().size() - 1);
					this.sectionAL.get(riversID1).get(i).getQ().add(q1);
					double z3 = this.PP.get(i) - this.VV.get(i) * q1;
					/**
					 * 限制水位范围?????????????????????????????????
					 */
					z3=limitWaterLevel(z3,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getZ().add(z3);
				}
			}
		}
		//-----------------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------
		
		//-----------------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------
		/**
		 * 2、下边界为流量的情况
		 */
		else if (BY2 == 1)
		{
			//获取该河段最后一个断面的流量
			double q2 = this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().get(this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().size() - 1);
			/*************************************************
			 * 2.1、本河段上的上边界为流量和内节点,这2种情况的追赶方程相同
			 * ***********************************************
			 */
			if (BY1 == 1 || BY1 == -1) {
				//计算末断面的水位-----Z(j+1)=(P(j+1)-Q(j+1))/V(j+1)
				double z = (this.PP.get(this.sectionAL.get(riversID1).size() - 1) - q2) / this.VV.get(this.sectionAL.get(riversID1).size() - 1);
			    //限制水位低于河底高程
				z=limitWaterLevel(z,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
				//添加水位结果到集合中
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getZ().add(z);
                //针对断面的循环，求取每个断面的水位和流量
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--)
				{
					//Z(j)=S(j+1)-T(j+1)Z(j+1)
					double z0 = this.SS.get(i + 1) - this.TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getZ().get(this.sectionAL.get(riversID1).get(i + 1).getZ().size() - 1);
					//限制水位低于河底高程
					z0=limitWaterLevel(z0,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getZ().add(z0);
					//Q(j+1)=P(j+1)-V(j+1)Z(j+1)
					double q3 = PP.get(i) - VV.get(i) * z0;
					//将计算得到的流量添加到集合中
					this.sectionAL.get(riversID1).get(i).getQ().add(q3);
				}
			}
			/***********************************************************
			 * 2.2、本河段上的上边界为水位
			 * *********************************************************
			 */
			if (BY1 == 0)
			{
				//计算末断面的水位----Z(j+1)=P(j+1)-V(j+1)Q(j+1)
				double z = this.PP.get(this.sectionAL.get(riversID1).size() - 1) - this.VV.get(this.sectionAL.get(riversID1).size() - 1) * q2;
				//限制水位低于河底高程
				z=limitWaterLevel(z,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
				//添加水位结果到集合中
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getZ().add(z);
				//针对断面的循环，求取每个断面的水位和流量
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--)
				{
					//Q(j)=S(j+1)-T(j+1)Q(j+1)
					double q = this.SS.get(i + 1) - this.TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getQ().get(this.sectionAL.get(riversID1).get(i + 1).getQ().size() - 1);
					//将计算得到的流量添加到集合中
					this.sectionAL.get(riversID1).get(i).getQ().add(q);
					//Z(j+1)=P(j+1)-V(j+1)Q(j+1)
					double z4 = this.PP.get(i) - this.VV.get(i) * q;
					//限制水位低于河底高程
					z4=limitWaterLevel(z4,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					//添加水位结果到集合中
					this.sectionAL.get(riversID1).get(i).getZ().add(z4);
				}
			}
		}
		//-----------------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------
		
		
		//-----------------------------------------------------------------------------------------------------------------
		//-----------------------------------------------------------------------------------------------------------------
		/**
		 * 3、下边界为水位流量关系
		 */
		if (BY2 == 2) {
			/**
			 * 最小二乘法拟合：Q=a1+a2*H+a3*pow(H,2)
			 */
			    /**
				 * 2次拟合
				 */
			HDFunction.LeastSquareMethod eastSquareMethod = new HDFunction.LeastSquareMethod(this.xy[0],this.xy[1],3);
			double[] coefficients = eastSquareMethod.getCoefficient();
			double a1=coefficients[0];
			double a2=coefficients[1];
			double a3=coefficients[2];

			/**
			 * 3.1、本河段上的上边界为流量和内节点,这2种情况的追赶方程相同
			 */
			if (BY1 == 1 || BY1 == -1) {
				double z=0;
				double v=this.VV.get(this.sectionAL.get(riversID1).size() - 1);
				double p=this.PP.get(this.sectionAL.get(riversID1).size() - 1);
				if(Math.pow(a2+v,2)-4*a3*(a1-p)>0){
					double z1=(-1*(a2+v)+Math.sqrt(Math.pow(a2+v,2)-4*a3*(a1-p)))/(2.0*a3);
					double z2=(-1*(a2+v)-Math.sqrt(Math.pow(a2+v,2)-4*a3*(a1-p)))/(2.0*a3);
					if(z1>0)
						z=z1;
					if(z2>0)
						z=z2;
				}
				if(Math.pow(a2+v,2)-4*a3*(a1-p)==0){
					z=-1*(a2+v)/(2.0*a3);
				}
				/**
				 * 线性拟合
				 */
				if(Math.pow(a2+v,2)-4*a3*(a1-p)<0||z==0){
					HDFunction.LeastSquareMethod eastSquareMethod2 = new HDFunction.LeastSquareMethod(this.xy[0],this.xy[1],2);
					double[] coefficients2 = eastSquareMethod2.getCoefficient();
					double a11=coefficients2[0];
					double a22=coefficients2[1];
					z=(p-a11)/(a22+v);
				}
				/**
				 * 限制水位范围
				 */
				z=limitWaterLevel(z,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getZ().add(z);
				double q=p-v*z;
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().add(q);
				/**
				 * 赶求水位和流量
				 */
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--) {
					double z2=this.SS.get(i+1) -TT.get(i+1) *this.sectionAL.get(riversID1).get(i + 1).getZ().get(this.sectionAL.get(riversID1).get(i + 1).getZ().size() - 1);
					/**
					 * 限制水位范围
					 */
					z2=limitWaterLevel(z2,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getZ().add(z2);
					double q2 = this.PP.get(i) - this.VV.get(i) * this.sectionAL.get(riversID1).get(i).getZ().get(this.sectionAL.get(riversID1).get(i).getZ().size() - 1);
					this.sectionAL.get(riversID1).get(i).getQ().add(q2);
				}
			}
			/**
			 * 3.2、本河段上的上边界为水位
			 */
			if (BY1 == 0) {
				double z=0;
				double v1=this.VV.get(this.sectionAL.get(riversID1).size() - 1);
				double p1=this.PP.get(this.sectionAL.get(riversID1).size() - 1);
				if(Math.pow(a2+1.0/v1,2)-4*a3*(a1-p1/v1)>0){
					double z1=(-1*(a2+1.0/v1)+Math.sqrt(Math.pow(a2+1.0/v1,2)-4*a3*(a1-p1/v1)))/(2.0*a3);
					double z2=(-1*(a2+1.0/v1)-Math.sqrt(Math.pow(a2+1.0/v1,2)-4*a3*(a1-p1/v1)))/(2.0*a3);
					if(z1>0)
						z=z1;
					if(z2>0)
						z=z2;
				}
				if(Math.pow(a2+1.0/v1,2)-4*a3*(a1-p1/v1)==0){
					z=-1*(a2+1.0/v1)/(2.0*a3);
				}
				/**
				 * 线性拟合
				 */
				if(Math.pow(a2+1.0/v1,2)-4*a3*(a1-p1/v1)<0||z==0){
					HDFunction.LeastSquareMethod eastSquareMethod2 = new HDFunction.LeastSquareMethod(this.xy[0],this.xy[1],2);
					double[] coefficients2 = eastSquareMethod2.getCoefficient();
					double a11=coefficients2[0];
					double a22=coefficients2[1];
					z=(p1-v1*a11)/(a22*v1+1);
				}
				/**
				 * 限制水位范围
				 */
				z=limitWaterLevel(z,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getZ().add(z);
				double q5=(p1-z)/v1;
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().add(q5);
				/**
				 * 赶求水位和流量
				 */
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--) {
					double q = SS.get(i + 1) - TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getQ().get(this.sectionAL.get(riversID1).get(i + 1).getQ().size() - 1);
					this.sectionAL.get(riversID1).get(i).getZ().add(q);
					double z2 = PP.get(i) - VV.get(i) * q;
					/**
					 * 限制水位范围
					 */
					z2=limitWaterLevel(z2,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getQ().add(z2);
				}
			}
		}
		/**
		 * 4、下边界为内节点的情况
		 */
		if (BY2 == -1) {
			/**
			 * 先找到下游河段
			 */
			String downRiverID = this.hatopo.get(riverID).getDownriverID();
			double z = this.sectionAL.get(downRiverID).get(0).getZ().get(k + 1);
			/**
			 * 限制水位范围
			 */
			z=limitWaterLevel(z,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
			this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size()-1).getZ().add(z);
			/**
			 * 4.1、本河段上的上边界为流量和内节点,这两种情况的追赶方程相同
			 */
			if (BY1 == 1 || BY1 == -1) {
				/**
				 * 计算末断面的流量
				 */
				double q = this.PP.get(this.sectionAL.get(riversID1).size() - 1) - this.VV.get(this.sectionAL.get(riversID1).size() - 1) * z;
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().add(q);
				/**
				 * 赶求水位和流量
				 */
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--) {
					double z1 = SS.get(i + 1) - TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getZ().get(this.sectionAL.get(riversID1).get(i + 1).getZ().size() - 1);
					/**
					 * 限制水位范围
					 */
					z1=limitWaterLevel(z1,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getZ().add(z1);
					double q1 = PP.get(i) - VV.get(i) * z1;
					this.sectionAL.get(riversID1).get(i).getQ().add(q1);
				}
			}
			/**
			 * 4.2、河段上的上边界为水位
			 */
			if (BY1 == 0) {
				/**
				 * 计算末断面的流量
				 */
				double q = (PP.get(this.sectionAL.get(riversID1).size() - 1) - z) / VV.get(this.sectionAL.get(riversID1).size() - 1);
				this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getQ().add(q);
				/**
				 * 赶求水位和流量
				 */
				for (int i = this.sectionAL.get(riversID1).size() - 2; i >= 0; i--) {
					double q1 = SS.get(i + 1) - TT.get(i + 1) * this.sectionAL.get(riversID1).get(i + 1).getQ().get(this.sectionAL.get(riversID1).get(i + 1).getQ().size() - 1);
					this.sectionAL.get(riversID1).get(i).getQ().add(q1);
					double z1 = PP.get(i) - VV.get(i) * q;
					/**
					 * 限制水位范围
					 */
					z1=limitWaterLevel(z1,this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMaxz(),this.sectionAL.get(riversID1).get(this.sectionAL.get(riversID1).size() - 1).getMinz());
					this.sectionAL.get(riversID1).get(i).getZ().add(z1);
				}
			}
		}
	}
	/**初始化PP和VV
	 * @param pp1
	 * @param vv1
	 */
	public void initPPVV(Double pp1, Double vv1) {
		this.PP = new ArrayList<Double>();
		this.VV = new ArrayList<Double>();
		this.PP.add(pp1);
		this.VV.add(vv1);
	}
	/**
	 * 限制水位在河槽内
	 * @param z
	 * @param max
	 * @param min
	 * @return
	 */
	public double limitWaterLevel(double z,double max,double min) {
		double vaule=z;
		/**
		 * 限制超出最高水位
		 */
		if (vaule > max) {
			vaule = max - 0.2;
		}
		/**
		 * 限制低于最低水位
		 */
		if (vaule < min) {
			vaule= min + 0.2;
		}
		return vaule;
	}
	public void setBoundaryZ(Double Z,String riverID) {
		this.sectionAL.get(riverID).get(this.sectionAL.get(riverID).size() - 1).getZ().add(Z);
	}
	public void setBoundaryQ(Double Q,String riverID) {
		this.sectionAL.get(riverID).get(this.sectionAL.get(riverID).size() - 1).getQ().add(Q);
	}
	public Hashtable<String, ArrayList<HDSection>> getSectionAL() {
		return sectionAL;
	}
	public ArrayList<Double> getPP() {
		return PP;
	}
	public ArrayList<Double> getVV() {
		return VV;
	}

}
