package edu.hust.webswmm.framework.mathmodel.tools;

import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDARWB;
import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDData;
import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDSection;
import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDpoint;
import edu.hust.webswmm.framework.service.mdb.MdbDataService;
import edu.hust.webswmm.model.mdb.entity.*;
import edu.hust.webswmm.model.mdb.nonentity.HDInitial;

import java.text.ParseException;
import java.util.*;

public class HDTool {
    /**
     * 获取和组装水动力的河网拓扑
     *
     * @param river
     * @param riverNet
     * @return
     */
    public Hashtable<String, ArrayList<HDData.HdSection>> createHDSec(MdbDataService mdbDataService, String[] river, String riverNet) {
        Hashtable<String, ArrayList<HDData.HdSection>> hdsection = new Hashtable<>();
        Hashtable<String, List<edu.hust.webswmm.model.mdb.entity.HDSection>> hdsecTableGHH = new Hashtable<>();
        Hashtable<String, List<HDPoints>> hdPtsTableGHH = new Hashtable<>();
        //获取
        for (int i = 0; i < river.length; i++) {//河段的循环
            List<edu.hust.webswmm.model.mdb.entity.HDSection> hdSectionList = mdbDataService.getHDSecs(river[i]);
            hdsecTableGHH.put(river[i], hdSectionList);
        }
        for (int i = 0; i < river.length; i++) {//河段循环
            for (int j = 0; j < hdsecTableGHH.get(river[i]).size(); j++) {//断面循环
                String secID = hdsecTableGHH.get(river[i]).get(j).getSectionID();
                List<HDPoints> hdPts = mdbDataService.getHDPtss(riverNet, river[i], secID);
                hdPtsTableGHH.put(secID, hdPts);
            }
        }
        //组装
        for (int i = 0; i < river.length; i++) {//河段循环
            ArrayList<HDData.HdSection> hdSections = new ArrayList<>();
            for (int j = 0; j < hdsecTableGHH.get(river[i]).size(); j++) {//断面循环
                String RiverID = hdsecTableGHH.get(river[i]).get(j).getRiverID();
                String SectionID = hdsecTableGHH.get(river[i]).get(j).getSectionID();
                String SectionName = null;
                ArrayList<HDData.HdPoint> hdPoints = new ArrayList<>();
                for (int k = 0; k < hdPtsTableGHH.get(SectionID).size(); k++) {
                    double startDistance = hdPtsTableGHH.get(SectionID).get(k).getX();
                    double elevation = hdPtsTableGHH.get(SectionID).get(k).getY();
                    int mark = 0;
                    HDData.HdPoint hdPoint = new HDData.HdPoint(startDistance, elevation, mark);
                    hdPoints.add(hdPoint);
                }
                String lat = null;
                String lon = null;
                double nclLeft = hdsecTableGHH.get(river[i]).get(j).getnLeft();
                double nclRiver = hdsecTableGHH.get(river[i]).get(j).getDx();
                double nclRight = hdsecTableGHH.get(river[i]).get(j).getnRight();
                double dx = hdsecTableGHH.get(river[i]).get(j).getDx();
                HDData.HdSection hdSection = new HDData.HdSection(RiverID, SectionID, SectionName, hdPoints, lat, lon, nclLeft, nclRiver, nclRight, dx);
                hdSections.add(hdSection);
            }
            hdsection.put(river[i], hdSections);
        }
        return hdsection;
    }

    /**
     * 组装水动力边界
     *
     * @param hydroStationsUp
     * @param hydroStationsDown
     * @param hdRivernetList
     * @return
     * @throws ParseException
     */
    public HDData.BYData createHDBy(List<HydroStation> hydroStationsUp, List<HydroStation> hydroStationsDown, HDRivernet hdRivernetList) {
        HDData.BYData byDatasGHH;
        // private String riverNetID;//河网的ID
//        private String riverID;//河段的ID
//        private int byUp; //上边界条件类型，上边界可为水位、流量和内节点（0:水位;1:流量;-1:内结点）
//        private int byDown; //下边界条件类型 ，上边界可为水位、流量、水位流量关系和内节点（0:水位;1:流量;2:水位流量关系曲线;-1:内结点）
//        private String upSectionID; //上边界对应的实际断面
//        private String downSectionID;//下边界对应的实际断面
        String riverID = hdRivernetList.getRiverID();
        int BY1 = hdRivernetList.getByUp();
        int BY2 = hdRivernetList.getByDown();
        String upSectionID = hdRivernetList.getUpSectionID();
        String downSectionID = hdRivernetList.getDownSectionID();

        //Get data from MDB
        ArrayList<Double> upBoundary = new ArrayList<>();
        ArrayList<Double> downBoundary = new ArrayList<>();
        //
        List<HydroStation> hydroStationList = new ArrayList<>();


        int len = 0;
        if (hydroStationsUp != null)
            len = hydroStationsUp.size();
        if (hydroStationsDown != null)
            len = hydroStationsDown.size();
        if (hydroStationsUp != null || hydroStationsDown != null) {
            for (int j = 0; j < len; j++) {
                if (BY1 == 0) {
                    upBoundary.add(hydroStationsUp.get(j).getWatValue());//上游水位边界
                }
                if (BY1 == 1) {
                    upBoundary.add(hydroStationsUp.get(j).getFlowValue());//上游流量边界
                }
                if (BY2 == 0) {
                    downBoundary.add(hydroStationsDown.get(j).getWatValue());//下游水位边界
                }
                if (BY2 == 1) {
                    downBoundary.add(hydroStationsDown.get(j).getFlowValue());//下游流量边界
                }
            }
        }
        double xy[][] = null;//水位流量关系值
        byDatasGHH = new HDData.BYData(riverID, BY1, BY2, upSectionID, downSectionID, upBoundary, downBoundary, xy);
        return byDatasGHH;
    }

    /**
     * 产生一维水动力模型的边界条件
     *
     * @param mdbDataService
     * @param hdRivernetList
     * @param startTime
     * @param endTime
     * @return
     */
    public ArrayList<HDData.BYData> createHDBoundary(MdbDataService mdbDataService, List<HDRivernet> hdRivernetList, Date startTime, Date endTime) {
        ArrayList<HDData.BYData> byDataList = new ArrayList<>();
        //组装
        for (int i = 0; i < hdRivernetList.size(); i++) {
            String riverID = hdRivernetList.get(i).getRiverID();
            int BY1 = hdRivernetList.get(i).getByUp();
            int BY2 = hdRivernetList.get(i).getByDown();
            String upSectionID = hdRivernetList.get(i).getUpSectionID();
            String downSectionID = hdRivernetList.get(i).getDownSectionID();
            List<HydroStation> hydroStationListUp = mdbDataService.getHydroResults(upSectionID, startTime, endTime);
            List<HydroStation> hydroStationListDown = mdbDataService.getHydroResults(downSectionID, startTime, endTime);
            ArrayList<Double> upBoundary = new ArrayList<>();
            ArrayList<Double> downBoundary = new ArrayList<>();
            double xy[][] = null;//水位流量关系值
            int lenUp = 0;
            int lenDown = 0;
            if (hydroStationListUp.size() > 0)
                lenUp = hydroStationListUp.size();
            if (hydroStationListDown.size() > 0)
                lenDown = hydroStationListDown.size();
            for (int j = 0; j < lenUp; j++) {
                if (BY1 == 0) {
                    upBoundary.add(hydroStationListUp.get(j).getWatValue());//上游水位边界
                }
                if (BY1 == 1) {
                    upBoundary.add(hydroStationListUp.get(j).getFlowValue());//上游流量边界
                }
            }
            for (int j = 0; j < lenDown; j++) {
                if (BY2 == 0) {
                    downBoundary.add(hydroStationListDown.get(j).getWatValue());//下游水位边界
                }
                if (BY2 == 1) {
                    downBoundary.add(hydroStationListDown.get(j).getFlowValue());//下游流量边界
                }
            }
            HDData.BYData byData = new HDData.BYData(riverID, BY1, BY2, upSectionID, downSectionID, upBoundary, downBoundary, xy);
            byDataList.add(byData);
        }
        return byDataList;
    }

    /**
     * 组装水动力模型的初始条件
     *
     * @param river
     * @param initialTable
     * @return
     */
    public Hashtable<String, ArrayList<HDData.InitialData>> createHDInitial(String[] river, Hashtable<String, List<HDInitial>> initialTable) {
        Hashtable<String, ArrayList<HDData.InitialData>> initiDatasGHH = new Hashtable<>();
        for (int i = 0; i < river.length; i++) {//河段的循环
            ArrayList<HDData.InitialData> initialData1 = new ArrayList<>();
            for (int j = 0; j < initialTable.get(river[i]).size(); j++) {//断面书的循环
                String secID = initialTable.get(river[i]).get(j).getSectionID();
                double initialZ = initialTable.get(river[i]).get(j).getInitialZ();
                double initialQ = initialTable.get(river[i]).get(j).getInitialQ();
                HDData.InitialData initialData = new HDData.InitialData(secID, initialZ, initialQ);
                initialData1.add(initialData);
            }
            initiDatasGHH.put(river[i], initialData1);
        }
        return initiDatasGHH;
    }

    /**
     * 组装河网的拓扑结构
     *
     * @param riverNetID
     * @return
     */
    public ArrayList<HDData.RiverTopo> createRivertopo(MdbDataService mdbDataService, String riverNetID) {
        ArrayList<HDData.RiverTopo> riverTopos = new ArrayList<>();
        //获取
        List<HDRivertopo> hdRivertopos = mdbDataService.getHDRivertopos(riverNetID);
        //组装
        for (int i = 0; i < hdRivertopos.size(); i++) {
            String riverID = hdRivertopos.get(i).getRiverID();
            String downRiverID = hdRivertopos.get(i).getDownRiverID();
            HDData.RiverTopo riverTopo = new HDData.RiverTopo(riverID, downRiverID);
            riverTopos.add(riverTopo);
        }
        return riverTopos;
    }

    /**
     * 设定
     *
     * @param outputData
     * @param riverID
     * @param len
     * @param hdSections
     * @param dates
     * @return
     */
    public List<ResSections> setHDResults(Hashtable<String, ArrayList<HDData.outputData>> outputData, String[] riverID, int len, Hashtable<String, ArrayList<HDData.HdSection>> hdSections, List<Date> dates) {
        List<ResSections> hdwqData = new ArrayList<>();
        for (int i = 0; i < outputData.size(); i++) {//河段的循环
            for (int j = 0; j < outputData.get(riverID[i]).size(); j++) {//断面的循环
                for (int k = 0; k < len; k++) {//时间循环
                    String secId = outputData.get(riverID[i]).get(j).getSecID();// 断面的id
                    Date secTime = dates.get(k);//时间，以秒为单位
                    double secFlow = outputData.get(riverID[i]).get(j).getQ()[k];// 流量过程，单位m³/s
                    double secWatlevel = outputData.get(riverID[i]).get(j).getZ()[k];// 水位过程，单位m
                    //流量计算
                    double secVelocity = 0;// 断面流速，单位m/s
                    ArrayList<HDpoint> hDpoints = new ArrayList<>();
                    for (int l = 0; l < hdSections.get(riverID[i]).get(j).getHdPoints().size(); l++) {//断面点数的循环
                        double startDistance = hdSections.get(riverID[i]).get(j).getHdPoints().get(l).getStartDistance();//起点距
                        double elevation = hdSections.get(riverID[i]).get(j).getHdPoints().get(l).getElevation();//高程
                        int mark = hdSections.get(riverID[i]).get(j).getHdPoints().get(l).getMark();//标记左堤岸、右堤岸（左堤岸1河道2右堤岸）
                        HDpoint hDpoint = new HDpoint(startDistance, elevation, mark);
                        hDpoints.add(hDpoint);
                    }
                    double minLevel = 10000000;
                    for (int l = 0; l < hDpoints.size(); l++) {
                        if (hDpoints.get(l).getElevation() <= minLevel)
                            minLevel = hDpoints.get(l).getElevation();
                    }
                    HDSection hdSection = new HDSection(hDpoints);
                    HDARWB hdarwb = hdSection.getHDARWB(secWatlevel, minLevel);
                    double area = hdarwb.getA();
                    secVelocity = secFlow / area;
                    ResSections resSections = new ResSections(secId, secTime, secFlow, secWatlevel, secVelocity);
                    hdwqData.add(resSections);
                }
            }
        }
        return hdwqData;
    }

    /**
     * 首次设定水动力模型的初始值
     *
     * @param ZInitial
     * @param Qinitial
     * @param river
     * @param hdSections
     * @return
     */
    public Hashtable<String, List<HDInitial>> setHDInitial(double ZInitial, double Qinitial, String[] river, Hashtable<String, ArrayList<HDData.HdSection>> hdSections,Hashtable<String, HDData.HdSection> hdSection) {
        Hashtable<String, List<HDInitial>> hdInitials = new Hashtable<>();
        for (int i = 0; i < river.length; i++) {// 河流的循环
            List<HDInitial> hdInitials1 = new ArrayList<>();
            for (int j = 0; j < hdSections.get(river[i]).size(); j++) {// 断面的循环
                String riverID = hdSections.get(river[i]).get(j).getRiverID();//断面ID
                String sectionID = hdSections.get(river[i]).get(j).getSectionID();//断面ID
                List<Double> hdpoints=new ArrayList<>();
                for (int k = 0; k <hdSection.get(sectionID).getHdPoints().size(); k++) {
                    hdpoints.add(hdSection.get(sectionID).getHdPoints().get(k).getElevation());
                }
                double minEleva= Collections.min(hdpoints);
                double initialZ = minEleva+ZInitial;//断面底高程+给定的水深
                double initialQ = Qinitial;//初始的流量
                HDInitial hdInitial = new HDInitial(riverID, sectionID, initialZ, initialQ);
                hdInitials1.add(hdInitial);
            }
            hdInitials.put(river[i], hdInitials1);
        }
        return hdInitials;
    }

    /**
     * 用新的水动力计算结果设置水动力下次计算的初始条件
     *
     * @param river
     * @param outputData
     * @return
     */
    public Hashtable<String, List<HDInitial>> setHDInitialByres(String[] river, Hashtable<String, ArrayList<HDData.outputData>> outputData) {
        Hashtable<String, List<HDInitial>> hdInitials = new Hashtable<>();
        for (int i = 0; i < river.length; i++) {//河段的循环
            List<HDInitial> hdInitialss = new ArrayList<>();//初始条件
            for (int j = 0; j < outputData.get(river[i]).size(); j++) {//断面的循环
                String riverID = river[i];//断面ID
                String sectionID = outputData.get(river[i]).get(j).getSecID();//断面ID
                int sizez = outputData.get(river[i]).get(j).getZ().length;
                double initialZ = outputData.get(river[i]).get(j).getZ()[sizez - 1];//初始的水位
                int sizeq = outputData.get(river[i]).get(j).getQ().length;
                double initialQ = outputData.get(river[i]).get(j).getQ()[sizeq - 1];//初始的流量
                HDInitial hdInitial = new HDInitial(riverID, sectionID, initialZ, initialQ);
                hdInitialss.add(hdInitial);
            }
            hdInitials.put(river[i], hdInitialss);
        }
        return hdInitials;
    }

    /**
     * 断面流速的推求
     *
     * @param hdSections
     * @param riverID
     * @param secNo
     * @param secFlow
     * @param secWatlevel
     * @return
     */
    public double clacVel(Hashtable<String, ArrayList<HDData.HdSection>> hdSections, String riverID, int secNo, double secFlow, double secWatlevel) {
        double secVelocity = 0;// 断面流速，单位m/s
        ArrayList<HDpoint> hDpoints = new ArrayList<>();
        for (int l = 0; l < hdSections.get(riverID).get(secNo).getHdPoints().size(); l++) {//断面点数的循环
            double startDistance = hdSections.get(riverID).get(secNo).getHdPoints().get(l).getStartDistance();//起点距
            double elevation = hdSections.get(riverID).get(secNo).getHdPoints().get(l).getElevation();//高程
            int mark = hdSections.get(riverID).get(secNo).getHdPoints().get(l).getMark();//标记左堤岸、右堤岸（左堤岸1河道2右堤岸）
            HDpoint hDpoint = new HDpoint(startDistance, elevation, mark);
            hDpoints.add(hDpoint);
        }
        double minLevel = 10000000;
        for (int l = 0; l < hDpoints.size(); l++) {
            if (hDpoints.get(l).getElevation() <= minLevel)
                minLevel = hDpoints.get(l).getElevation();
        }
        HDSection hdSection = new HDSection(hDpoints);
        HDARWB hdarwb = hdSection.getHDARWB(secWatlevel, minLevel);
        double area = hdarwb.getA();
        secVelocity = secFlow / area;
        return secVelocity;
    }
}
