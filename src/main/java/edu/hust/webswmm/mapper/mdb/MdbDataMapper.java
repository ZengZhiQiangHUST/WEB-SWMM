package edu.hust.webswmm.mapper.mdb;

import edu.hust.webswmm.model.mdb.entity.*;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MdbDataMapper {
    // Table:obs_rainfall
    int deleteRainfallToMDBMapper(@Param("stationID")String stationID, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
    int insertRainfallToMDBMapper(List<ObsRainfall> obsRainfalls);
    Date getMaxTimeMDBRainMapper(String stationID);
    List<ObsRainfall> getRainfallMapper(@Param("stationID")String stationID, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    // Table:obs_discharge
    int deleteDischargeToMDBMapper(@Param("stationID")String stationID, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
    int insertDischargeToMDBMapper(List<ObsDischarge> obsDischarges);
    Date getMaxTimeMDBDisMapper(String stationID);
    List<ObsDischarge> getDischargeMapper(@Param("stationID")String stationID, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    // Table:obs_waterlevel
    int deleteWaterlevelToMDBMapper(@Param("stationID")String stationID, @Param("startTime")Date startTime, @Param("endTime")Date endTime);
    int insertWaterlevelToMDBMapper(List<ObsWaterlevel> obsWaterlevels);
    Date getMaxTimeMDBWaterlevelMapper(String stationID);
    List<ObsWaterlevel> getWaterlevelMapper(@Param("stationID")String stationID, @Param("startTime")Date startTime, @Param("endTime")Date endTime);

    // Table:hydrostation
    int deleteHydroData(@Param("stationId") String stationId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<HydroStation> getHydroStation(@Param("stationId") String stationId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int insertHydroData(List<HydroStation> hydroStations);
    int deleteHydroData2(Date startTime);

    // Table:parameter_start
    int getStartParaMapper(@Param("modelName") String modelName,@Param("parameterName") String parameterName);

    List<NodeMap> selectJunctionmap();
    @MapKey("serialNum")
    Map<String, NodeMap> selectnodemap();
    @MapKey("serialNum")
    Map<String, SubMap> selectSubmap();
    @MapKey("serialNum")
    Map<String, LinkMap> selectlinkmap();
    List<ResSubcatchments> getSubResults(@Param("subId") String subId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int insertSubResults(List<ResSubcatchments> subResults);
    int deleteSubResults(@Param("subId") String subId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int deleteSubResults2(@Param("startTime") Date startTime);
    List<ResJunctions> getJunResults(@Param("junId") String junId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int insertJunResults(List<ResJunctions> junResults);
    int deleteJunResults(@Param("junId") String junId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int deleteJunResults2(Date startTime);
    List<ResOutfalls> getOutfallResults(@Param("outfallId") String outfallId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<ResSections> getSectionResults(@Param("secId") String secId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int insertOutfallResults(List<ResOutfalls> outfallResults);
    int deleteOutfallResults(@Param("outfallId") String outfallId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int deleteOutfallResults2(Date startTime);
    List<ResConduits> getLinkResults(@Param("linkId") String linkId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int insertLinkResults(List<ResConduits> linkResults);
    int deleteLinkResults(@Param("linkId") String linkId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int deleteLinkResults2(Date startTime);
    List<ResSystem> getCitySystemResults(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int insertCitySystem(List<ResSystem> junResults);
    int deleteCitySystem(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
    int deleteCitySystem2(Date startTime);
    //Max time
    Date getSecResMaxTime(String id);
    Date getSubResMaxTime(String id);
    Date getLinkResMaxTime(String id);
    Date getJunResMaxTime(String id);
    Date getOutfallResMaxTime(String id);
    Date getSectionResMaxTime(String id);
    Date getRainfallMaxTime(String id);
    Date getHydroMaxTime(String id);
    Date getSecObMT(String secID);
    Date getWQMaxTime(String id);
    Date getSystemMaxTime();
    String getSecIdByIntakeId(String intakeId);
    String getidofLink(String gId);
    Date getIntakeMaxTime(String intakeId);
    String getRiverIDByMonitorId(String monitorId);
    String getMonitorIDByMonitorId(String secId);
    String getSecidByUpsluiceId(String sluiceId);
    String getSecidByDsuliceId(String sluiceId);
    Date getWQSecMaxTime(String id);
    //通过获取到的ID得到ID对应的要素类型
    String getFeatureById(String getId);
    List<String> getFeatureAllID(String featuresType);
    List<HDRivernet> getHDBy(String riverNetID);
    String getLocationIDByStationIDMapper(String stationID);
    String getLocationTypeByStationIDMapper(String stationID);

    List<HDSection> getHDSec(String riverID);
    List<HDPoints> getHDPts(@Param("riverNetID") String riverNetID, @Param("riverID") String riverID, @Param("sectionID") String sectionID);
    List<HDRivertopo> getHDRivertopo(String riverNetID);
    Date getMaxTimeHydroMapper(String stationID);
    Date getMinTimeHydroMapper(String stationID);
    List<RiverDistance> getRiverDistance(String riverId);
    List<RiverDistance> getRiverDistance2(String riverId);
    List<RiverSec> getRivernetRiver(String secID);

    // Table:parameter_1dhd
    double getParameter1DHDMapper(String parameterName);

    //
}
