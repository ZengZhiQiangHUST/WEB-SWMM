package edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d;
import java.util.ArrayList;
public class HDSection {
    String RiverID;//河段ID
    String SectionID;//断面ID
    String SectionName;//断面名称
    ArrayList<HDpoint> HDPoints;//断面上的测点
    String lat;//经度
    String lon;//纬度
    double nclLeft;//断面左堤岸曼宁系数
    double nclRiver;//断面河道曼宁系数
    double nclRight;//断面右堤岸曼宁系数
    double dx;//断面间距
    double minz;//断面上最低高程点
    double maxz;//断面上最大高程点
    public ArrayList<Double> Q = null;//断面的计算结果值-流量
    public ArrayList<Double> Z = null;//断面的计算结果值-水位
    public ArrayList<Double> inflowValue=null;//断面间的侧向径流

    public HDSection(ArrayList<HDpoint> HDPoints) {
        this.HDPoints = HDPoints;
    }

    /**
 * 构造方法，
 * @param riverID
 * @param sectionID
 * @param sectionName
 * @param HDPoints
 * @param lat
 * @param lon
 * @param nclLeft
 * @param nclRiver
 * @param nclRight
 * @param dx
 * @param minz
 * @param maxz
 */
    public HDSection(String riverID, String sectionID, String sectionName, ArrayList<HDpoint> HDPoints, String lat, String lon, double nclLeft, double nclRiver, double nclRight, double dx, double minz, double maxz) {
        RiverID = riverID;
        SectionID = sectionID;
        SectionName = sectionName;
        this.HDPoints = HDPoints;
        this.lat = lat;
        this.lon = lon;
        this.nclLeft = nclLeft;
        this.nclRiver = nclRiver;
        this.nclRight = nclRight;
        this.dx = dx;
        this.minz = minz;
        this.maxz = maxz;
        Q = new ArrayList<>();
        Z = new ArrayList<>();
        inflowValue=new ArrayList<>();
    }
    /**
     *计算断面的水力要素
     * @param H 水位值
     * @return
     */
    public HDARWB getHDARWB(double H,double minLevel){
        if(H<=minLevel)
            H=minLevel+0.15;
        ArrayList<HDpoint> list = new ArrayList<>();
        for(int i=0;i<this.HDPoints.size();i++){
            double zz;
            /**
             * 处理高程为负值的情况
             */
            if(this.HDPoints.get(i).getElevation()<0){
                zz = H+Math.abs(this.HDPoints.get(i).getElevation());
            }
            else {
                zz = H - this.HDPoints.get(i).getElevation();
            }
            zz = zz>=0?zz:0;
            list.add(new HDpoint(this.HDPoints.get(i).getstartDisatance(),zz,this.HDPoints.get(i).getMark()));
        }
        HDARWB hdarw =  HDFunction.getHDArwBeanLikeU(list);
        return hdarw;
    }

    /**
     * 处理三个曼宁系数的情况，利用断面面积平均方法
     * @param H
     * @return
     */
    public double getNcl(double minz,double H,double nclLeft,double nclRiver,double nclRight){
        double ncl=0;
        if(H!=0&&H>minz) {
            double startDistanceOfLeft = -Double.MIN_VALUE;
            double startDistanceOfRight = Double.MAX_VALUE;
            for (int i = 0; i < this.HDPoints.size(); i++) {
                /**
                 *左堤岸对应的临界点
                 */
                if ((this.HDPoints.get(i).getMark())==1) {
                    startDistanceOfLeft = this.HDPoints.get(i).startDistance;
                }
                /**
                 *右堤岸对应的临界点
                 */
                if (this.HDPoints.get(i).getMark()==2) {
                    startDistanceOfRight = this.HDPoints.get(i).startDistance;
                }
            }
            ArrayList<HDpoint> listLeft = new ArrayList<>();
            ArrayList<HDpoint> listRiver = new ArrayList<>();
            ArrayList<HDpoint> listRight = new ArrayList<>();
            for (int i = 0; i < this.HDPoints.size(); i++) {
                if (this.HDPoints.get(i).startDistance <= startDistanceOfLeft) {
                    listLeft.add(this.HDPoints.get(i));
                }
                if (this.HDPoints.get(i).startDistance >= startDistanceOfLeft && this.HDPoints.get(i).startDistance <= startDistanceOfRight) {
                    listRiver.add(this.HDPoints.get(i));
                }
                if (this.HDPoints.get(i).startDistance >= startDistanceOfRight) {
                    listRight.add(this.HDPoints.get(i));
                }
            }
            ArrayList<HDpoint> listLeft1 = new ArrayList<>();
            ArrayList<HDpoint> listRiver1 = new ArrayList<>();
            ArrayList<HDpoint> listRight1 = new ArrayList<>();
            for (int i = 0; i < listLeft.size(); i++) {
                double zz;
                if (listLeft.get(i).getElevation() < 0) {
                    zz = H + Math.abs(listLeft.get(i).getElevation());
                } else {
                    zz = H - listLeft.get(i).getElevation();
                }
                zz = zz >= 0 ? zz : 0;
                listLeft1.add(new HDpoint(listLeft.get(i).getstartDisatance(), zz, 0));
            }
            double A1 = HDFunction.getHDArwBeanArea(listLeft1);
            for (int i = 0; i < listRiver.size(); i++) {
                double zz;
                if (listRiver.get(i).getElevation() < 0) {
                    zz = H + Math.abs(listRiver.get(i).getElevation());
                } else {
                    zz = H - listRiver.get(i).getElevation();
                }
                zz = zz >= 0 ? zz : 0;
                listRiver1.add(new HDpoint(listRiver.get(i).getstartDisatance(), zz,0));
            }
            double A2 = HDFunction.getHDArwBeanArea(listRiver1);
            for (int i = 0; i < listRight.size(); i++) {
                double zz;
                if (listRight.get(i).getElevation() < 0) {
                    zz = H + Math.abs(listRight.get(i).getElevation());
                } else {
                    zz = H - listRight.get(i).getElevation();
                }
                zz = zz >= 0 ? zz : 0;
                listRight1.add(new HDpoint(listRight.get(i).getstartDisatance(), zz, 0));
            }
            double A3 = HDFunction.getHDArwBeanArea(listRight1);
            if((A1+A2+A3)>0)
            ncl = (nclLeft * A1 + nclRiver * A2 + nclRight * A3) / (A1 + A2 + A3);
            else {
				ncl=nclRiver;
			}
        }
        else
            ncl=nclRiver;
        return ncl;
    }

    public String getSectionID() {
        return SectionID;
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
    public double getMinz() {
        return minz;
    }
    public double getMaxz() {
        return maxz;
    }
    public ArrayList<Double> getQ() {
        return Q;
    }
    public ArrayList<Double> getZ() {
        return Z;
    }
}