package edu.hust.webswmm.framework.service.mdb;

import edu.hust.webswmm.model.mdb.entity.*;
import org.apache.ibatis.annotations.MapKey;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MdbDataService {
    //
    int deleteRainfallToMDBService(String stationID, Date startTime, Date endTime);
    int deleteDischargeToMDBService(String stationID, Date startTime, Date endTime);
    int deleteWaterlevelToMDBService(String stationID, Date startTime, Date endTime);
    //
    int insertRainfallToMDBService(List<ObsRainfall> obsRainfalls);
    int insertDischargeToMDBService(List<ObsDischarge> obsDischarges);
    int insertWaterlevelToMDBService(List<ObsWaterlevel> obsWaterlevels);
    //
    Date getMaxTimeMDBDisService(String stationID);
    Date getMaxTimeMDBRainService(String stationID);
    Date getMaxTimeMDBWaterlevelService(String stationID);
    int getStartParaService(String modelName,String parameterName);
    List<ObsRainfall> getRainfallService(String stationID, Date startTime, Date endTime);
    List<ObsDischarge> getDischargeService(String stationID, Date startTime, Date endTime);
    List<ObsWaterlevel> getWaterlevelService(String stationID, Date startTime, Date endTime);
    List<NodeMap> selectJunMap();
    @MapKey("serialNum")
    Map<String, NodeMap> selectnodeMaps();
    @MapKey("serialNum")
    Map<String, SubMap> selectSubMap();
    @MapKey("serialNum")
    Map<String, LinkMap> selectlinkMap();
    List<ResSubcatchments> getSubResultsService(String subId, Date startTime, Date endTime);
    int insertSub(List<ResSubcatchments> subResults);
    int deleteSub(String subId, Date startTime, Date endTime);
    int deleteSubs(Date startTime);
    List<ResJunctions> getJunResultsService(String junId, Date startTime, Date endTime);
    int insertJun(List<ResJunctions> junResults);
    int deleteJun(String junId, Date startTime, Date endTime);
    int deleteJuns(Date startTime);
    List<ResOutfalls> getOutfallResultsService(String outfallId, Date startTime, Date endTime);
    List<ResSections> getSectionResultsService(String secId, Date startTime, Date endTime);

    int insertOutfall(List<ResOutfalls> outfallResults);
    int deleteOutfall(String OutfallId, Date startTime, Date endTime);
    int deleteOutfalls(Date startTime);
    List<ResConduits> getLinkResultsSevice(String linkId, Date startTime, Date endTime);
    int insertLink(List<ResConduits> linkResults);
    int deleteLink(String linkId, Date startTime, Date endTime);
    int deleteLinks(Date startTime);
    List<ResSystem> getSystemResults(Date startTime, Date endTime);
    int insertCitySystem(List<ResSystem> linkResults);
    int deleteCitySystem(Date startTime, Date endTime);
    int deleteCitySystems(Date startTime);
    //Max time
    Date getSecResMT(String id);
    Date getSubResMT(String id);
    Date getLinkResMT(String id);
    Date getJunResMT(String id);
    Date getOutfallResMT(String id);
    Date getSectionResMT(String id);
    Date getRainfallMT(String id);
    Date getHydrostationMT(String id);
    Date getSecObsMT(String secID);
    Date getWQStationMT(String id);
    Date getCitySystemMT();
    String getSecId(String intakeId);
    String getinOfLinks(String gId);
    Date getIntakeMaxTimes(String intakeId);
    String getRiverIDByMonitorIds(String monitorId);
    String getMonitorIDByMonitorIds(String secId);
    String getSecidByUpsluiceIds(String sluiceId);
    String getSecidByDsuliceIds(String sluiceId);
    Date getWQSecMaxTimes(String id);
    String getById(String getId);
    List<String> getFeatureAllIDService(String featuresType);
    List<HDRivernet> getHDBys(String riverNetID);
    String getLocationIDByStationIDService(String stationID);
    String getLocationTypeByStationIDService(String stationID);
    List<HydroStation> getHydroResults(String stationId, Date startTime, Date endTime);
    int insetHydro(List<HydroStation> hydroStations);
    int deleteHydroStationData(String stationId, Date startTime, Date endTime);
    int deleteHydroStationDatas(Date startTime);
    List<HDPoints> getHDPtss(String riverNetID, String riverID, String sectionID);
    List<HDSection> getHDSecs(String riverID);
    List<HDRivertopo> getHDRivertopos(String riverNetID);
    Date getMaxTimeHydroService(String stationID);
    Date getMinTimeHydroService(String stationID);
    List<RiverDistance> getRiverDistances(String riverId);
    List<RiverDistance> getRiverDistances2(String riverId);
    List<RiverSec> getRivernetRivers(String secID);

    // Table:parameter_1dhd
    double getParameter1DHDService(String parameterName);
}
