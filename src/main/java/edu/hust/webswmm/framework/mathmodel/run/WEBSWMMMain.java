package edu.hust.webswmm.framework.mathmodel.run;

import edu.hust.webswmm.framework.mathmodel.engine.dll.hydrologiclModeling.SWMMEngine;
import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDData;
import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDModel;
import edu.hust.webswmm.framework.mathmodel.tools.HDTool;
import edu.hust.webswmm.framework.mathmodel.tools.MergeFile;
import edu.hust.webswmm.framework.mathmodel.tools.Tools;
import edu.hust.webswmm.framework.mathmodel.tools.WriteTxtFile;
import edu.hust.webswmm.framework.service.bdb.BdbDataService;
import edu.hust.webswmm.framework.service.mdb.MdbDataService;
import edu.hust.webswmm.model.bdb.entity.StDischarge;
import edu.hust.webswmm.model.bdb.entity.StInfo;
import edu.hust.webswmm.model.bdb.entity.StRainfall;
import edu.hust.webswmm.model.bdb.entity.StWaterlevel;
import edu.hust.webswmm.model.mdb.entity.*;
import edu.hust.webswmm.model.mdb.nonentity.HDInitial;
import edu.hust.webswmm.model.mdb.nonentity.resList.Junction;
import edu.hust.webswmm.model.mdb.nonentity.resList.Link;
import edu.hust.webswmm.model.mdb.nonentity.resList.Outfall;
import edu.hust.webswmm.model.mdb.nonentity.resList.Subcatchment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class WEBSWMMMain {
    /**
     * 数据库访问引擎
     */
    @Autowired
    MdbDataService mdbDataService;
    @Autowired
    BdbDataService bdbDataService;
    public static int times = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0 0/1 * * * ?")
    public void schedule() throws Exception {
        if (times == 0) {
            System.out.println("***************************************************************************************************");
            System.out.println("*| Project Name|++++++++++++++++++++++++++++++++++++++WEB-SWMM+++++++++++++++++++++++++++++++++++|*");
            System.out.println("*| Application |Web-Based SWMM to Provide Real-Time Computing Services for Urban Water Management|*");
            System.out.println("*| Developer   |Zhiqiang Zeng, Water Science Lab, HUST                                           |*");
            System.out.println("*| Version     |1.0  (12/5/2019)                                                                 |*");
            System.out.println("*| Email       |zengzhiqiang@hust.edu.cn                                                         |*");
            System.out.println("***************************************************************************************************");
        }
        times = times + 1;
        System.out.println("Status: Start | Number of times:" + times);
        System.out.println("============>Task 1: GETTING AND PROCESSING DATA<================");
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("|Station name|     Station type     | Latest time in MDB  | Latest time in EDB  |Number of values|");
        System.out.println("--------------------------------------------------------------------------------------------------");
        long t_task1_start = System.currentTimeMillis();
        String projectName = "suqian"; // Study area for testing WEB-SWMM
        //Get information about the monitoring stations
        List<StInfo> stInfos = bdbDataService.getMonStationService(projectName);
        //Access the BDB to get the latest measurement data
        int k_rainfall = 0;
        int k_discharge = 0;
        int k_waterlevel = 0;
        for (int i = 0; i < stInfos.size(); i++) {
            String stationID = stInfos.get(i).getStationID();
            //Meteorological station
            if (stInfos.get(i).getStationType().equals("Meteorological_station")) {//Meteorological station
                // Step 1: Get the time T1-1 for the latest model input data in the MDB.
                Date maxTime_MDB = mdbDataService.getMaxTimeMDBRainService(stationID);
                // Step 2: Get the time T1-2 for the latest model input data in the BDB.
                Date maxTime_BDB = bdbDataService.getMaxTimeBDBRainService(stationID);
                // Step 3； Determine whether there is a new data update in BDB.If T1-1 < T1-2, perform Ste 4; otherwise, perform Step 5.
                int k = 0;
                List<StRainfall> strainfallList = new ArrayList<>();
                if (maxTime_BDB != null) {// BDB中要有数据，否则将无法获取新的数据
                    if (maxTime_MDB != null) {// MDB中有数据
                        if (maxTime_MDB.getTime() < maxTime_BDB.getTime())
                            //Step 4.1:Get and process the model input data from the BDB (BDB:"st_rainfall" table)
                            strainfallList = bdbDataService.getRainfallFromBDBService(stationID, maxTime_MDB, maxTime_BDB);
                    } else {
                        maxTime_MDB = bdbDataService.getMinTimeBDBRainService(stationID);
                        Date maxTime_MDB_1 = new Date(maxTime_MDB.getTime() - 1);
                        strainfallList = bdbDataService.getRainfallFromBDBService(stationID, maxTime_MDB_1, maxTime_BDB);
                    }
                    if (strainfallList.size() > 0) {
                        //Step 4.2:Store them in the MDB (MDB:"obs_rainfall" table)
                        List<ObsRainfall> obsRainfallList = new ArrayList<>();
                        for (int j = 0; j < strainfallList.size(); j++) {
                            ObsRainfall obsRainfall = new ObsRainfall(stationID, strainfallList.get(j).getRainfallTime(), strainfallList.get(j).getRainfallValue());
                            obsRainfallList.add(obsRainfall);
                        }
                        mdbDataService.deleteRainfallToMDBService(stationID, obsRainfallList.get(0).getRainfallTime(), obsRainfallList.get(obsRainfallList.size() - 1).getRainfallTime());
                        k = mdbDataService.insertRainfallToMDBService(obsRainfallList);
                        k_rainfall = k_rainfall + k;
                    }
                }
                System.out.println("|" + stationID + " station |Meteorological station| " + sdf.format(maxTime_MDB) + " | " + sdf.format(maxTime_BDB) + " |        " + k + "       |");
            }
            //Stream gauge station
            if (stInfos.get(i).getStationType().equals("Stream_gauge_station")) {
                Date maxTime_MDB = mdbDataService.getMaxTimeMDBDisService(stationID);
                Date maxTime_BDB = bdbDataService.getMaxTimeBDBDisService(stationID);
                int k = 0;
                if (maxTime_BDB != null) {
                    List<StDischarge> stDischargeList = new ArrayList<>();
                    if (maxTime_BDB != null) {
                        if (maxTime_MDB != null) {
                            if (maxTime_MDB.getTime() < maxTime_BDB.getTime())
                                stDischargeList = bdbDataService.getDischargeFromBDBService(stationID, maxTime_MDB, maxTime_BDB);
                        } else {
                            maxTime_MDB = bdbDataService.getMinTimeBDBDisService(stationID);
                            Date maxTime_MDB_1 = new Date(maxTime_MDB.getTime() - 1);
                            stDischargeList = bdbDataService.getDischargeFromBDBService(stationID, maxTime_MDB_1, maxTime_BDB);

                        }
                        if (stDischargeList.size() > 0) {
                            List<ObsDischarge> obsDischargeList = new ArrayList<>();
                            for (int j = 0; j < stDischargeList.size(); j++) {
                                ObsDischarge obsDischarge = new ObsDischarge(stationID, stDischargeList.get(j).getDischargeTime(), stDischargeList.get(j).getDischargeValue());
                                obsDischargeList.add(obsDischarge);
                            }
                            mdbDataService.deleteDischargeToMDBService(stationID, obsDischargeList.get(0).getDischargeTime(), obsDischargeList.get(obsDischargeList.size() - 1).getDischargeTime());
                            k = mdbDataService.insertDischargeToMDBService(obsDischargeList);
                            k_discharge = k_discharge + k;
                            //Station-Sec
                            String locationID = mdbDataService.getLocationIDByStationIDService(stationID);
                            String locationType = mdbDataService.getLocationTypeByStationIDService(stationID);
                            if (locationType.equals("sec")) {
                                List<HydroStation> hydroStationList = new ArrayList<>();
                                for (int j = 0; j < stDischargeList.size(); j++) {
                                    String stationId = locationID;
                                    Date hydroTime = stDischargeList.get(j).getDischargeTime();
                                    double watValue = 0;
                                    double flowValue = stDischargeList.get(j).getDischargeValue();
                                    HydroStation hydroStation = new HydroStation(stationId, hydroTime, flowValue, watValue);
                                    hydroStationList.add(hydroStation);
                                }
                                mdbDataService.deleteHydroStationData(locationID, hydroStationList.get(0).getHydroTime(), hydroStationList.get(hydroStationList.size() - 1).getHydroTime());
                                mdbDataService.insetHydro(hydroStationList);
                            }
                        }
                    }
                }
                System.out.println("|" + stationID + " station |stream gauge station  | " + sdf.format(maxTime_MDB) + " | " + sdf.format(maxTime_BDB) + " |        " + k + "       |");
            }
            //Water level station
            if (stInfos.get(i).getStationType().equals("Water_level_station")) {
                Date maxTime_MDB = mdbDataService.getMaxTimeMDBWaterlevelService(stationID);
                Date maxTime_BDB = bdbDataService.getMaxTimeBDBWaterlevelService(stationID);
                int k = 0;
                if (maxTime_BDB != null) {
                    List<StWaterlevel> stWaterlevelList = new ArrayList<>();
                    if (maxTime_BDB != null) {
                        if (maxTime_MDB != null) {
                            if (maxTime_MDB.getTime() < maxTime_BDB.getTime())
                                stWaterlevelList = bdbDataService.getWaterlevelFromBDBService(stationID, maxTime_MDB, maxTime_BDB);
                        } else {
                            maxTime_MDB = bdbDataService.getMinTimeBDBWaterlevelService(stationID);
                            Date maxTime_MDB_1 = new Date(maxTime_MDB.getTime() - 1);
                            stWaterlevelList = bdbDataService.getWaterlevelFromBDBService(stationID, maxTime_MDB_1, maxTime_BDB);
                        }
                        if (stWaterlevelList.size() > 0) {
                            List<ObsWaterlevel> obsWaterlevelList = new ArrayList<>();
                            for (int j = 0; j < stWaterlevelList.size(); j++) {
                                ObsWaterlevel obsWaterlevel = new ObsWaterlevel(stationID, stWaterlevelList.get(j).getWaterlevelTime(), stWaterlevelList.get(j).getWaterlevelValue());
                                obsWaterlevelList.add(obsWaterlevel);
                            }
                            mdbDataService.deleteDischargeToMDBService(stationID, obsWaterlevelList.get(0).getWaterlevelTime(), obsWaterlevelList.get(obsWaterlevelList.size() - 1).getWaterlevelTime());
                            k = mdbDataService.insertWaterlevelToMDBService(obsWaterlevelList);
                            k_waterlevel = k_waterlevel + k;
                            //Station-Sec
                            String locationID = mdbDataService.getLocationIDByStationIDService(stationID);
                            String locationType = mdbDataService.getLocationTypeByStationIDService(stationID);
                            if (locationType.equals("sec")) {
                                List<HydroStation> hydroStationList = new ArrayList<>();
                                for (int j = 0; j < obsWaterlevelList.size(); j++) {
                                    String stationId = locationID;
                                    Date hydroTime = obsWaterlevelList.get(j).getWaterlevelTime();
                                    double flowValue = 0;
                                    double watValue = obsWaterlevelList.get(j).getWaterlevelValue();
                                    HydroStation hydroStation = new HydroStation(stationId, hydroTime, flowValue, watValue);
                                    hydroStationList.add(hydroStation);
                                }
                                mdbDataService.deleteHydroStationData(locationID, hydroStationList.get(0).getHydroTime(), hydroStationList.get(hydroStationList.size() - 1).getHydroTime());
                                mdbDataService.insetHydro(hydroStationList);
                            }
                        }
                    }
                }
                System.out.println("|" + stationID + " station |Water level station   | " + sdf.format(maxTime_MDB) + " | " + sdf.format(maxTime_BDB) + " |        " + k + "       |");
            }
        }
        long t_task1_end = System.currentTimeMillis();
        long t_task1 = t_task1_end - t_task1_start;

        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println("============>Task 3: CALCULATION AND RESULTS STORAGE<============");
        //Satrt time parameter
        long t_task3_start = System.currentTimeMillis();
        int task3_hour = mdbDataService.getStartParaService("swmm", "task3_hour");
        Tools tools = new Tools();
        List<ObsRainfall> obsRainfallLis = new ArrayList<>();
        List<ObsDischarge> obsDischargeList = new ArrayList<>();
        List<ObsWaterlevel> obsWaterlevelList = new ArrayList<>();
        for (int i = 0; i < stInfos.size(); i++) {
            String stationID = stInfos.get(i).getStationID();
            if (stInfos.get(i).getStationType().equals("Meteorological_station")) {//Rainfall data
                Date endTime = mdbDataService.getMaxTimeMDBRainService(stationID);
                Date startTime = tools.getStartDateByEndDate(endTime, task3_hour);
                obsRainfallLis = mdbDataService.getRainfallService(stationID, startTime, endTime);
            }
            if (stInfos.get(i).getStationType().equals("Stream_gauge_station")) {//Discharge data
                Date endTime = mdbDataService.getMaxTimeMDBDisService(stationID);
                Date startTime = tools.getStartDateByEndDate(endTime, task3_hour);
                obsDischargeList = mdbDataService.getDischargeService(stationID, startTime, endTime);
            }
            if (stInfos.get(i).getStationType().equals("Water_level_station")) {//Water level data
                Date endTime = mdbDataService.getMaxTimeMDBWaterlevelService(stationID);
                Date startTime = tools.getStartDateByEndDate(endTime, task3_hour);
                obsWaterlevelList = mdbDataService.getWaterlevelService(stationID, startTime, endTime);
            }
        }
        System.out.println("Step1: " + obsRainfallLis.size() + " rainfall data, " + obsDischargeList.size() + " water level data and " + obsWaterlevelList.size() + " discharge data were obtained from MDB!");

        //File path
        String inpFilePath = "../mathmodel/Example1.inp";
        String MDFilePath = "../mathmodel/Rainfall.txt";
        String ADFilePath = "../mathmodel/Original.txt";
        String reportFilePath = "../mathmodel/Report.rpt";
        String outputFilePath = "../mathmodel/Output.out";

        new WriteTxtFile().writeInputFile(obsRainfallLis, MDFilePath, obsRainfallLis.get(0).getRainfallTime(), obsRainfallLis.get(obsRainfallLis.size() - 1).getRainfallTime());
        System.out.println("Step2: The above data were written to the MD file!");
        new MergeFile().mergeFiles(inpFilePath, MDFilePath, ADFilePath);
        System.out.println("Step3: MD file and AD file were merged!");

//        // Run SWMM
        int modelStep = 300;
        SWMMEngine coi = SWMMEngine.swhhmtool;
        coi.RunSwmmDll(inpFilePath, reportFilePath, outputFilePath);
        System.out.println("Step4: SWMM was run!");
        // Open the binary file that stores the time series results of SWMM
        coi.OpenSwmmOutFile(outputFilePath);

        // Run one-dimension hydrodynamic model. The following will run a self-developed one-dimensional hydrodynamic model
        HDTool hdTool = new HDTool();
        String riverNetID = "SQCT";// Natural channel ID
        List<HDRivernet> hdRivernets = mdbDataService.getHDBys(riverNetID);
        Hashtable<String, List<String>> sectionTable = new Hashtable<>();
        int k = 0;
        for (int i = 0; i < hdRivernets.size(); i++) {
            String riverID = hdRivernets.get(i).getRiverID();
            List<String> sectionIDList = new ArrayList<>();
            if (hdRivernets.get(i).getByUp() == 1 || hdRivernets.get(i).getByUp() == 0) {
                sectionIDList.add(hdRivernets.get(i).getUpSectionID());
                k++;
            }
            if (hdRivernets.get(i).getByDown() == 1 || hdRivernets.get(i).getByDown() == 0) {
                sectionIDList.add(hdRivernets.get(i).getDownSectionID());
                k++;
            }
            sectionTable.put(riverID, sectionIDList);
        }

        List<Long> maxTimeList = new ArrayList<>();
        List<Long> minTimeList = new ArrayList<>();
        for (int i = 0; i < hdRivernets.size(); i++) {
            String riverID = hdRivernets.get(i).getRiverID();
            for (int j = 0; j < sectionTable.get(riverID).size(); j++) {
                String secId = sectionTable.get(riverID).get(j);
                Date dateTimeMax = mdbDataService.getMaxTimeHydroService(secId);
                if (dateTimeMax != null)
                    maxTimeList.add((mdbDataService.getMaxTimeHydroService(secId)).getTime());
                Date dateTimeMin = mdbDataService.getMinTimeHydroService(secId);
                if (dateTimeMin != null)
                    minTimeList.add((mdbDataService.getMinTimeHydroService(secId)).getTime());
            }
        }
        if (maxTimeList.size() == k && minTimeList.size() == k) {
            Long minTime = Collections.min(maxTimeList);
            Long maxTime = Collections.max(minTimeList);
            Date startTimeHD = new Date(maxTime);
            Date endTimeHD = new Date(minTime);
            if (((minTime - maxTime) / (modelStep * 1000) + 1) >= 36) {// 至少需要3个小时的数据才可以启动水动力模型
                int len = (int) ((minTime - maxTime) / (modelStep * 1000) + 1);
                String[] riverID = new String[hdRivernets.size()];
                for (int i = 0; i < hdRivernets.size(); i++) {
                    riverID[i] = hdRivernets.get(i).getRiverID();
                }

                ArrayList<HDData.BYData> byDataList = hdTool.createHDBoundary(mdbDataService, hdRivernets, startTimeHD, endTimeHD);
                ArrayList<HDData.RiverTopo> riverTopoList = hdTool.createRivertopo(mdbDataService, riverNetID);
                Hashtable<String, ArrayList<HDData.HdSection>> hdSectionsTable = hdTool.createHDSec(mdbDataService, riverID, riverNetID);
                Hashtable<String, HDData.HdSection> hdSections = new Hashtable<>();
                for (int i = 0; i < riverID.length; i++) {
                    for (int j = 0; j < hdSectionsTable.get(riverID[i]).size(); j++) {
                        String RiverID = riverID[i];
                        String SectionID = hdSectionsTable.get(riverID[i]).get(j).getSectionID();
                        String SectionName = hdSectionsTable.get(riverID[i]).get(j).SectionName;
                        ArrayList<HDData.HdPoint> hdPoints = hdSectionsTable.get(riverID[i]).get(j).getHdPoints();
                        String lat = hdSectionsTable.get(riverID[i]).get(j).getLat();
                        String lon = hdSectionsTable.get(riverID[i]).get(j).getLon();
                        double nclLeft = hdSectionsTable.get(riverID[i]).get(j).getNclLeft();
                        double nclRiver = hdSectionsTable.get(riverID[i]).get(j).getNclRiver();
                        double nclRight = hdSectionsTable.get(riverID[i]).get(j).getNclRight();
                        double dx = hdSectionsTable.get(riverID[i]).get(j).getDx();
                        HDData.HdSection hdSection = new HDData.HdSection(RiverID, SectionID, SectionName, hdPoints, lat, lon, nclLeft, nclRiver, nclRight, dx);
                        hdSections.put(SectionID, hdSection);
                    }
                }
                //1DHD parameter:initialz, intialq, and dt.
                    double ZInitial = mdbDataService.getParameter1DHDService("initialz");
                    double Qinitial = mdbDataService.getParameter1DHDService("intialq");
                    int dt_1dhd= (int)mdbDataService.getParameter1DHDService("dt");

                    Hashtable<String, List<HDInitial>> hdInitial = hdTool.setHDInitial(ZInitial, Qinitial, riverID, hdSectionsTable, hdSections);
                    Hashtable<String, ArrayList<HDData.InitialData>> initiDataTable = hdTool.createHDInitial(riverID, hdInitial);

                    boolean isPreHeat = true;// Hot start
                    HDData hdData = new HDData(byDataList, initiDataTable, riverTopoList, hdSectionsTable, isPreHeat);
                    HDModel hdModel = new HDModel(hdData);
                    hdModel.calc("people", dt_1dhd, len, "tree");
                    Hashtable<String, ArrayList<HDData.outputData>> outputData = hdData.getOutputDatas();
                    int k33 = 0;
                }
            }

        // Store 1DHD model results
        // 测试GIT的本地代码提交

            List<NodeMap> nodeMaps = mdbDataService.selectJunMap();
            Map<String, SubMap> subMapMap = mdbDataService.selectSubMap();
            Map<String, JunctionMap> junMapMap = new HashMap<>();
            Map<String, NodeMap> nodeMap = mdbDataService.selectnodeMaps();
            Map<String, OutfallMap> OutfallMapMap = new HashMap<>();
            Map<String, LinkMap> linkMapMap = mdbDataService.selectlinkMap();

            for (int i = 0; i < nodeMaps.size(); i++) {
                if (nodeMaps.get(i).getType().equals("jun")) {
                    String getId = nodeMaps.get(i).getgId();
                    String serialNum = nodeMaps.get(i).getSerialNum();
                    JunctionMap junctionMap = new JunctionMap(getId, serialNum);
                    junMapMap.put(serialNum, junctionMap);
                }
                if (nodeMaps.get(i).getType().equals("outfall")) {
                    String getId = nodeMaps.get(i).getgId();
                    String serialNum = nodeMaps.get(i).getSerialNum();
                    OutfallMap outfallMap = new OutfallMap(getId, serialNum);
                    OutfallMapMap.put(serialNum, outfallMap);
                }
            }

            //Number of objects
            //System.out.println("Subcatchments number:" + subMapMap.size());
            //System.out.println("Outfalls number:" + OutfallMapMap.size());
            //System.out.println("Junctions number:" + (junMapMap.size() - OutfallMapMap.size()));
            //System.out.println("Conduits number:" + linkMapMap.size());

            Date startTimeForRain = new Date(obsRainfallLis.get(0).getRainfallTime().getTime() + modelStep * 1000);
            Date endTimeForRain = new Date(obsRainfallLis.get(obsRainfallLis.size() - 1).getRainfallTime().getTime() + modelStep * 1000);

            List<Junction> junctions = new ArrayList<>();
            List<Outfall> outfalls = new ArrayList<>();
            List<Subcatchment> subcatchments = new ArrayList<>();
            List<Link> links = new ArrayList<>();

            int k1 = coi.getSWMM_Nsubcatch() / 5;
            int k2 = coi.getSWMM_Nsubcatch() % 5;
            int k3;
            int k4 = 0;
            int k5;
            int k6;
            int k7 = 0;
            if (k2 != 0)
                k7 = 1;
            System.out.println("Read the result of Subcatchments.........");
            for (int j = 0; j < k1 + k7; j++) {
                if (j < k1) {
                    k3 = 5;
                    k5 = k4 * k3;
                    k6 = (k4 + 1) * k3;
                } else {
                    k5 = k1 * 5;
                    k6 = k5 + k2;
                }
                for (int a = k5; a < k6; a++) {
                    String type = "Subcatchment:";
                    String subId = subMapMap.get(String.valueOf(a)).getgId();
                    List<Float> subRains = new ArrayList<>();
                    List<Float> subInfils = new ArrayList<>();
                    List<Float> subFlows = new ArrayList<>();
                    for (int i = 0; i < coi.getSWMM_Nperiods(); i++) {
                        subRains.add(coi.GetSwmmResult(0, a, 0, i));
                        subFlows.add(coi.GetSwmmResult(0, a, 4, i));
                        subInfils.add(coi.GetSwmmResult(0, a, 3, i));
                    }
                    Subcatchment subcatchment = new Subcatchment(type, subId, modelStep, startTimeForRain, endTimeForRain, subRains, subInfils, subFlows);
                    subcatchments.add(subcatchment);
                }
                k4 = k4 + 1;
            }
            System.out.println("Storage result of Subcatchments.........");
            for (int i = 0; i < subcatchments.size(); i++) {
                if (subcatchments.get(i).getEndTime().getTime() - subcatchments.get(i).getStartTime().getTime() > 0) {
                    List<ResSubcatchments> subResults = new ArrayList<>();
                    Date startTime = subcatchments.get(i).getStartTime();
                    Date endTime = subcatchments.get(i).getEndTime();
                    int step = 0;
                    if (subcatchments.size() > 1)
                        step = (int) (subcatchments.get(i).getEndTime().getTime() - subcatchments.get(i).getStartTime().getTime()) / ((subcatchments.get(i).getSubFlow().size() - 1) * 1000);
                    String subId = subcatchments.get(i).getSubId();
                    float subRain;
                    float subInfil;
                    float subFlow;
                    for (int j = 0; j < subcatchments.get(i).getSubFlow().size(); j++) {
                        Date subTime = new Date((subcatchments.get(i).getStartTime().getTime() + j * step * 1000));
                        subRain = subcatchments.get(i).getSubRain().get(j);
                        subInfil = subcatchments.get(i).getSubInfil().get(j);
                        subFlow = subcatchments.get(i).getSubFlow().get(j);
                        ResSubcatchments resSubcatchments1 = new ResSubcatchments(subId, subTime, subRain, subInfil, subFlow);
                        subResults.add(resSubcatchments1);
                    }
                    mdbDataService.deleteSub(subId, startTime, endTime);
                    mdbDataService.insertSub(subResults);
                }
            }
            //*******************************Junctions and outfalls*******************************
            System.out.println("Read the result of junctions and outfalls.........");
            k1 = coi.getSWMM_Nnodes() / 5;
            k2 = coi.getSWMM_Nnodes() % 5;
            k4 = 0;
            for (int j = 0; j < k1 + k7; j++) {
                if (j < k1) {
                    k3 = 5;
                    k5 = k4 * k3;
                    k6 = (k4 + 1) * k3;
                } else {
                    k5 = k1 * 5;
                    k6 = k5 + k2;
                }
                for (int b = k5; b < k6; b++) {
                    if ("jun".equals(nodeMap.get(b + "").getType())) {
                        String type = "junction";
                        String junId = junMapMap.get(String.valueOf(b)).getgId();
                        List<Float> junDepths = new ArrayList<>();
                        List<Float> junHgls = new ArrayList<>();
                        List<Float> junFlows = new ArrayList<>();
                        List<Float> junFloods = new ArrayList<>();
                        List<Float> junLatFlows = new ArrayList<>();
                        for (int i = 0; i < coi.getSWMM_Nperiods(); i++) {
                            junDepths.add(coi.GetSwmmResult(1, b, 0, i));
                            junHgls.add(coi.GetSwmmResult(1, b, 1, i));
                            junLatFlows.add(coi.GetSwmmResult(1, b, 3, i));
                            junFlows.add(coi.GetSwmmResult(1, b, 4, i));
                            junFloods.add(coi.GetSwmmResult(1, b, 5, i));
                        }
                        Junction junction = new Junction(type, junId, modelStep, startTimeForRain, endTimeForRain, junDepths, junHgls, junLatFlows, junFlows, junFloods);
                        junctions.add(junction);
                    } else {
                        String type = "outfall";
                        String junId = OutfallMapMap.get(String.valueOf(b)).getgId();
                        List<Float> outfallDepth = new ArrayList<>();
                        List<Float> outfallHgl = new ArrayList<>();
                        List<Float> outfallLatflow = new ArrayList<>();
                        List<Float> outfallFlow = new ArrayList<>();
                        for (int i = 0; i < coi.getSWMM_Nperiods(); i++) {
                            outfallDepth.add(coi.GetSwmmResult(1, b, 0, i));
                            outfallHgl.add(coi.GetSwmmResult(1, b, 1, i));
                            outfallLatflow.add(coi.GetSwmmResult(1, b, 3, i));
                            outfallFlow.add(coi.GetSwmmResult(1, b, 4, i));
                        }
                        Outfall outfall = new Outfall(type, junId, modelStep, startTimeForRain, endTimeForRain, outfallDepth, outfallHgl, outfallLatflow, outfallFlow);
                        outfalls.add(outfall);
                    }
                }
                k4 = k4 + 1;
            }
            System.out.println("Storage results of junctions...........");
            for (int i = 0; i < junctions.size(); i++) {
                if (junctions.get(i).getEndTime().getTime() - junctions.get(i).getStartTime().getTime() > 0) {
                    List<ResJunctions> junResults = new ArrayList<>();
                    Date startTime = junctions.get(i).getStartTime();
                    Date endTime = junctions.get(i).getEndTime();
                    String junId = junctions.get(i).getJunId();
                    int step = 0;
                    if (junctions.size() > 1)
                        step = (int) (junctions.get(i).getEndTime().getTime() - junctions.get(i).getStartTime().getTime()) / ((junctions.get(i).getJunDepth().size() - 1) * 1000);
                    float junDepth;
                    float junHgl;
                    float junLatFlow;
                    float junFlow;
                    float junFlood;
                    for (int j = 0; j < junctions.get(i).getJunDepth().size(); j++) {
                        Date junTime = new Date((junctions.get(i).getStartTime().getTime() + j * step * 1000));
                        junDepth = junctions.get(i).getJunDepth().get(j);
                        junHgl = junctions.get(i).getJunHgl().get(j);
                        junLatFlow = junctions.get(i).getJunLatFlow().get(j);
                        junFlow = junctions.get(i).getJunFlow().get(j);
                        junFlood = junctions.get(i).getJunFlood().get(j);
                        ResJunctions resJunctions1 = new ResJunctions(junId, junTime, junDepth, junHgl, junLatFlow, junFlow, junFlood);
                        junResults.add(resJunctions1);
                    }
                    mdbDataService.deleteJun(junId, startTime, endTime);
                    mdbDataService.insertJun(junResults);
                }
            }
            System.out.println("Storage results of outfalls...........");
            for (int i = 0; i < outfalls.size(); i++) {
                if (outfalls.get(i).getEndTime().getTime() - outfalls.get(i).getStartTime().getTime() > 0) {
                    List<ResOutfalls> outfallResults = new ArrayList<>();
                    Date startTime = outfalls.get(i).getStartTime();
                    Date endTime = outfalls.get(i).getEndTime();
                    int step = 0;
                    if (outfalls.size() > 1)
                        step = (int) (outfalls.get(i).getEndTime().getTime() - outfalls.get(i).getStartTime().getTime()) / ((outfalls.get(i).getOutfallDepth().size() - 1) * 1000);
                    String outfallId = outfalls.get(i).getOutfallId();
                    float outfallDepth;
                    float outfallHgl;
                    float outfallLatflow;
                    float outfallFlow;
                    for (int j = 0; j < outfalls.get(i).getOutfallDepth().size(); j++) {
                        Date outfallTime = new Date((outfalls.get(i).getStartTime().getTime() + j * step * 1000));
                        outfallDepth = outfalls.get(i).getOutfallDepth().get(j);
                        outfallHgl = outfalls.get(i).getOutfallHgl().get(j);
                        outfallLatflow = outfalls.get(i).getOutfallLatflow().get(j);
                        outfallFlow = outfalls.get(i).getOutfallFlow().get(j);
                        ResOutfalls resOutfalls1 = new ResOutfalls(outfallId, outfallTime, outfallDepth, outfallHgl, outfallLatflow, outfallFlow);
                        outfallResults.add(resOutfalls1);
                    }
                    mdbDataService.deleteOutfall(outfallId, startTime, endTime);
                    mdbDataService.insertOutfall(outfallResults);
                }
            }
            //*******************************Conduits*******************************
            System.out.println("Read the result of conduits.........");
            k1 = coi.getSWMM_Nlinks() / 5;
            k2 = coi.getSWMM_Nlinks() % 5;
            k4 = 0;
            for (int j = 0; j < k1 + k7; j++) {
                if (j < k1) {
                    k3 = 5;
                    k5 = k4 * k3;
                    k6 = (k4 + 1) * k3;
                } else {
                    k5 = k1 * 5;
                    k6 = k5 + k2;
                }
                for (int c = k5; c < k6; c++) {
                    String type = "link";
                    String linkId = linkMapMap.get(String.valueOf(c)).getgId();
                    List<Float> linkFlows = new ArrayList<>();
                    List<Float> linkVelocitys = new ArrayList<>();
                    List<Float> linkDepths = new ArrayList<>();
                    List<Float> linkVols = new ArrayList<>();
                    List<Float> linkCapacity = new ArrayList<>();
                    for (int i = 0; i < coi.getSWMM_Nperiods(); i++) {
                        linkFlows.add(coi.GetSwmmResult(2, c, 0, i));
                        linkDepths.add(coi.GetSwmmResult(2, c, 1, i));
                        linkVelocitys.add(coi.GetSwmmResult(2, c, 2, i));
                        linkVols.add(coi.GetSwmmResult(2, c, 3, i));
                        linkCapacity.add(coi.GetSwmmResult(2, c, 4, i));
                    }
                    Link link = new Link(type, linkId, modelStep, startTimeForRain, endTimeForRain, linkFlows, linkVelocitys, linkDepths, linkVols, linkCapacity);
                    links.add(link);
                }
                //
                k4 = k4 + 1;
            }
            System.out.println("Storage the results of conduits...........");
            for (int i = 0; i < links.size(); i++) {
                if (links.get(i).getEndTime().getTime() - links.get(i).getStartTime().getTime() > 0) {
                    List<ResConduits> linkResults = new ArrayList<>();
                    Date startTime = links.get(i).getStartTime();
                    Date endTime = links.get(i).getEndTime();
                    int step = 0;
                    if (links.size() > 1)
                        step = (int) (links.get(i).getEndTime().getTime() - links.get(i).getStartTime().getTime()) / ((links.get(i).getLinkDepth().size() - 1) * 1000);
                    String linkId = links.get(i).getLinkId();
                    float linkDepth;
                    float linkFlow;
                    float Velocity;
                    float linkVol;
                    float linkCapacity;
                    for (int j = 0; j < links.get(i).getLinkDepth().size(); j++) {
                        Date linkTime = new Date((links.get(i).getStartTime().getTime() + j * step * 1000));
                        linkDepth = links.get(i).getLinkDepth().get(j);
                        linkFlow = links.get(i).getLinkFlow().get(j);
                        Velocity = links.get(i).getLinkVelocity().get(j);
                        linkVol = links.get(i).getLinkVol().get(j);
                        linkCapacity = links.get(i).getLinkCapacity().get(j);
                        ResConduits resConduits1 = new ResConduits(linkId, linkTime, linkFlow, Velocity, linkDepth, linkVol, linkCapacity);
                        linkResults.add(resConduits1);
                    }
                    mdbDataService.deleteLink(linkId, startTime, endTime);
                    mdbDataService.insertLink(linkResults);
                }
            }
            //*******************************Summary results*******************************
            System.out.println("Read the summary results");
            List<ResSystem> citysystems = new ArrayList<>();
            for (int i = 0; i < coi.getSWMM_Nperiods(); i++) {
                Date sysTime = new Date(startTimeForRain.getTime() + i * modelStep * 1000);
                double sysRain = coi.GetSwmmResult(3, 0, 1, i);
                double sysInfil = coi.GetSwmmResult(3, 0, 3, i);
                double sysFlow = coi.GetSwmmResult(3, 0, 4, i);
                double sysTolInflow = coi.GetSwmmResult(3, 0, 9, i);
                double sysFlood = coi.GetSwmmResult(3, 0, 10, i);
                double sysOutflow = coi.GetSwmmResult(3, 0, 11, i);
                double sysStorage = coi.GetSwmmResult(3, 0, 12, i);
                ResSystem system = new ResSystem(sysTime, sysRain, sysInfil, sysFlow, sysTolInflow, sysFlood, sysOutflow, sysStorage);
                citysystems.add(system);
            }
            System.out.println("Storage the summary results");
            mdbDataService.deleteCitySystem(startTimeForRain, endTimeForRain);
            mdbDataService.insertCitySystem(citysystems);
            System.out.println("Step5: Results were saved to the database!");

            long t_task3_end = System.currentTimeMillis();
            long t_task3 = (t_task3_end - t_task3_start);

            System.out.println("============>Task 4: DELETING EXCESS DATA<=======================");
            long t_task4_start = System.currentTimeMillis();
            Date endTimeDE;
            Date startTimeDE;
            int task4_hour = mdbDataService.getStartParaService("swmm", "task4_hour");
            ;
            endTimeDE = mdbDataService.getLinkResMT("1");
            if (endTimeDE != null) {
                startTimeDE = tools.getStartDateByEndDate(endTimeDE, task4_hour);
                int i = mdbDataService.deleteLinks(startTimeDE);
                System.out.println("Deleted " + i + " rows of data in table 'link_results'");
            }

            endTimeDE = mdbDataService.getJunResMT(junctions.get(0).getJunId());
            if (endTimeDE != null) {
                startTimeDE = tools.getStartDateByEndDate(endTimeDE, task4_hour);
                int i = mdbDataService.deleteJuns(startTimeDE);
                System.out.println("Delete " + i + " rows of data in table 'jun_results'");
            }

            endTimeDE = mdbDataService.getOutfallResMT(outfalls.get(0).getOutfallId());
            if (endTimeDE != null) {
                startTimeDE = tools.getStartDateByEndDate(endTimeDE, task4_hour);
                int i = mdbDataService.deleteOutfalls(startTimeDE);
                System.out.println("Deleted " + i + " rows of data in table 'outfall_results'");
            }

            endTimeDE = mdbDataService.getSubResMT(subcatchments.get(0).getSubId());
            if (endTimeDE != null) {
                startTimeDE = tools.getStartDateByEndDate(endTimeDE, task4_hour);
                int i = mdbDataService.deleteSubs(startTimeDE);
                System.out.println("Deleted " + i + " rows of data in table 'sub_results'");
            }

            endTimeDE = mdbDataService.getCitySystemMT();
            if (endTimeDE != null) {
                startTimeDE = tools.getStartDateByEndDate(endTimeDE, task4_hour);
                int i = mdbDataService.deleteCitySystems(startTimeDE);
                System.out.println("Deleted " + i + " rows of data in table 'sys_results'");
            }
            long t_task4_end = System.currentTimeMillis();
            long t_task4 = (t_task4_end - t_task4_start);

            System.out.println("============>Task waiting<=======================================");
            Thread.sleep(60000);

            long t_total = t_task1 + t_task3 + t_task4 + 60000;
            System.out.println("         %The time it took for task waiting and task execution%        ");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("|             |  Task 1  | Task 3  | Task 4  |Task waiting| Total time|");
            System.out.println("|   Time(ms)  |   " + t_task1 + "     |   " + t_task3 + "   |   " + t_task4 + "    |     60     |     " + t_total + "   |");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("Status: end   | Number of times: 1");
        }
    }









