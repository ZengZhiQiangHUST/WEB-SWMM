package edu.hust.webswmm.framework.service.mdb.impl;

import edu.hust.webswmm.framework.service.mdb.MdbDataService;
import edu.hust.webswmm.mapper.mdb.MdbDataMapper;
import edu.hust.webswmm.model.mdb.entity.*;
import org.apache.ibatis.annotations.MapKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MdbDataServiceImpl implements MdbDataService {
    @Autowired
    MdbDataMapper mdbDataMapper;
    public int deleteRainfallToMDBService(String stationID, Date startTime, Date endTime){
        return mdbDataMapper.deleteRainfallToMDBMapper(stationID,startTime,endTime);
    }
    public int deleteDischargeToMDBService(String stationID, Date startTime, Date endTime){
        return mdbDataMapper.deleteDischargeToMDBMapper(stationID,startTime,endTime);
    }
    public int deleteWaterlevelToMDBService(String stationID, Date startTime, Date endTime){
        return mdbDataMapper.deleteWaterlevelToMDBMapper(stationID,startTime,endTime);
    }
    public int insertRainfallToMDBService(List<ObsRainfall> obsRainfalls) {
        return mdbDataMapper.insertRainfallToMDBMapper(obsRainfalls);
        }
    public int insertDischargeToMDBService(List<ObsDischarge> obsDischarges){
        return mdbDataMapper.insertDischargeToMDBMapper(obsDischarges);
        }
    public int insertWaterlevelToMDBService(List<ObsWaterlevel> obsWaterlevels){
        return mdbDataMapper.insertWaterlevelToMDBMapper(obsWaterlevels);
        }
    public Date getMaxTimeMDBDisService(String stationID){
        return mdbDataMapper.getMaxTimeMDBDisMapper(stationID);
        }
    public Date getMaxTimeMDBRainService(String stationID) {
        return mdbDataMapper.getMaxTimeMDBRainMapper(stationID);
        }
    public Date getMaxTimeMDBWaterlevelService(String stationID){
        return mdbDataMapper.getMaxTimeMDBWaterlevelMapper(stationID);
        }
    public int getStartParaService(String modelName,String parameterName){
        return mdbDataMapper.getStartParaMapper(modelName,parameterName);
    }
    public List<ObsRainfall> getRainfallService(String stationID, Date startTime, Date endTime){
        return mdbDataMapper.getRainfallMapper(stationID,startTime,endTime);
    }
    public List<ObsDischarge> getDischargeService(String stationID, Date startTime, Date endTime){
        return mdbDataMapper.getDischargeMapper(stationID,startTime,endTime);
    }
    public List<ObsWaterlevel> getWaterlevelService(String stationID, Date startTime, Date endTime){
        return mdbDataMapper.getWaterlevelMapper(stationID,startTime,endTime);
    }
    public List<NodeMap> selectJunMap() {
        return mdbDataMapper.selectJunctionmap();
    }
    @MapKey("serialNum")
    public Map<String, NodeMap> selectnodeMaps(){return  mdbDataMapper.selectnodemap();}
    @MapKey("serialNum")
    public Map<String, SubMap> selectSubMap() {
        return mdbDataMapper.selectSubmap();
    }
    @MapKey("serialNum")
    public Map<String, LinkMap> selectlinkMap() {
        return mdbDataMapper.selectlinkmap();
    }
    public List<ResSubcatchments> getSubResultsService(String subId, Date startTime, Date endTime){
        return mdbDataMapper.getSubResults(subId, startTime, endTime);
    }
    public int insertSub(List<ResSubcatchments> subResults){
        return mdbDataMapper.insertSubResults(subResults);
    }
    public int deleteSub(String subId, Date startTime, Date endTime){
        return mdbDataMapper.deleteSubResults(subId,startTime,endTime);

    }
    public int deleteSubs(Date startTime){
        return mdbDataMapper.deleteSubResults2(startTime);
    }
    public List<ResJunctions> getJunResultsService(String junId, Date startTime, Date endTime){
        return mdbDataMapper.getJunResults(junId,startTime,endTime);
    }
    public int insertJun(List<ResJunctions> junResults){
        return mdbDataMapper.insertJunResults(junResults);
    }
    public int deleteJun(String junId, Date startTime, Date endTime){
        return mdbDataMapper.deleteJunResults(junId,startTime,endTime);
    }
    public int deleteJuns(Date startTime){
        return mdbDataMapper.deleteJunResults2(startTime);
    }
    public List<ResOutfalls> getOutfallResultsService(String outfallId, Date startTime, Date endTime){
        return mdbDataMapper.getOutfallResults(outfallId,startTime,endTime);
    }
    public List<ResSections> getSectionResultsService(String secId, Date startTime, Date endTime){
        return mdbDataMapper.getSectionResults(secId,startTime,endTime);
    }
    public int insertOutfall(List<ResOutfalls> outfallResults){
        return mdbDataMapper.insertOutfallResults(outfallResults);
    }
    public int deleteOutfall(String outfallId, Date startTime, Date endTime){
        return mdbDataMapper.deleteOutfallResults(outfallId,startTime,endTime);
    }
    public int deleteOutfalls(Date startTime){
        return mdbDataMapper.deleteOutfallResults2(startTime);
    }
    public List<ResConduits> getLinkResultsSevice(String linkId, Date startTime, Date endTime){
        return mdbDataMapper.getLinkResults(linkId,startTime,endTime);
    }
    public int insertLink(List<ResConduits> linkResults) {
        return mdbDataMapper.insertLinkResults(linkResults);
    }
    public int deleteLink(String linkId, Date startTime, Date endTime){
        return mdbDataMapper.deleteLinkResults(linkId,startTime,endTime);
    }
    public int deleteLinks(Date startTime){
        return mdbDataMapper.deleteLinkResults2(startTime);
    }
    public List<ResSystem> getSystemResults(Date startTime, Date endTime){
        return mdbDataMapper.getCitySystemResults(startTime,endTime);
    }
    public int insertCitySystem(List<ResSystem> linkResults){
        return mdbDataMapper.insertCitySystem(linkResults);
    }
    public int deleteCitySystem(Date startTime, Date endTime){
        return mdbDataMapper.deleteCitySystem(startTime, endTime);
    }
    public int deleteCitySystems(Date startTime){
        return mdbDataMapper.deleteCitySystem2(startTime);
    }
    public Date getSecResMT(String id){
        return mdbDataMapper.getSecResMaxTime(id);
    }
    public Date getSubResMT(String id){
        return mdbDataMapper.getSubResMaxTime(id);
    }
    public Date getLinkResMT(String id){
        return mdbDataMapper.getLinkResMaxTime(id);
    }
    public Date getJunResMT(String id) {
        return mdbDataMapper.getJunResMaxTime(id);
    }
    public Date getOutfallResMT(String id){
        return mdbDataMapper.getOutfallResMaxTime(id);
    }
    public Date getSectionResMT(String id){
        return mdbDataMapper.getSectionResMaxTime(id);
    }
    public Date getRainfallMT(String id){
        return mdbDataMapper.getRainfallMaxTime(id);
    }
    public Date getHydrostationMT(String id){
        return mdbDataMapper.getHydroMaxTime(id);
    }
    public Date getSecObsMT(String secID){
        return mdbDataMapper.getSecObMT(secID);
    }
    public Date getWQStationMT(String id){
        return mdbDataMapper.getWQMaxTime(id);
    }
    public Date getCitySystemMT(){
        return mdbDataMapper.getSystemMaxTime();
    }
    public String getSecId(String intakeId){
        return mdbDataMapper.getSecIdByIntakeId(intakeId);
    }
    public String getinOfLinks(String gId){
        return mdbDataMapper.getSecIdByIntakeId(gId);
    }
    public Date getIntakeMaxTimes(String intakeId){
        return mdbDataMapper.getIntakeMaxTime(intakeId);
    }
    public String getRiverIDByMonitorIds(String monitorId){
        return mdbDataMapper.getRiverIDByMonitorId(monitorId);
    }
    public String getMonitorIDByMonitorIds(String secId){
        return mdbDataMapper.getMonitorIDByMonitorId(secId);
    }
    public String getSecidByUpsluiceIds(String sluiceId){
        return mdbDataMapper.getSecidByUpsluiceId(sluiceId);
    }
    public String getSecidByDsuliceIds(String sluiceId){
        return mdbDataMapper.getSecidByDsuliceId(sluiceId);
    }
    public Date getWQSecMaxTimes(String id){
        return mdbDataMapper.getWQSecMaxTime(id);
    }
    public String getById(String getId){
        return mdbDataMapper.getFeatureById(getId);
    }
    public List<String> getFeatureAllIDService(String featuresType){
        return mdbDataMapper.getFeatureAllID(featuresType);
    }
    public List<HDRivernet> getHDBys(String riverNetID){
        return mdbDataMapper.getHDBy(riverNetID);
    }
    public String getLocationIDByStationIDService(String stationID){
        return mdbDataMapper.getLocationIDByStationIDMapper(stationID);
    }
    public String getLocationTypeByStationIDService(String stationID){
        return mdbDataMapper.getLocationTypeByStationIDMapper(stationID);
    }
    public List<HydroStation> getHydroResults(String stationId, Date startTime, Date  endTime){
        return  mdbDataMapper.getHydroStation(stationId,startTime,endTime);
    }
    public int insetHydro(List<HydroStation>hydroStations){
        return mdbDataMapper.insertHydroData(hydroStations);
    }
    public int deleteHydroStationData(String stationId, Date startTime,Date endTime){
        return mdbDataMapper.deleteHydroData(stationId, startTime,endTime);
    }
    public int deleteHydroStationDatas(Date startTime){
        return mdbDataMapper.deleteHydroData2(startTime);
    }
    public List<HDPoints> getHDPtss(String riverNetID, String riverID, String sectionID){
        return mdbDataMapper.getHDPts(riverNetID,riverID,sectionID);
    }
    public List<HDSection> getHDSecs(String riverID){
        return mdbDataMapper.getHDSec(riverID);
    }
    public List<HDRivertopo> getHDRivertopos(String riverNetID){
        return mdbDataMapper.getHDRivertopo(riverNetID);
    }
    public Date getMaxTimeHydroService(String stationID){
        return mdbDataMapper.getMaxTimeHydroMapper(stationID);
    }
    public Date getMinTimeHydroService(String stationID){
        return mdbDataMapper.getMinTimeHydroMapper(stationID);
    }
    public List<RiverDistance> getRiverDistances(String riverId) {
        return mdbDataMapper.getRiverDistance(riverId);
    }
    public List<RiverDistance> getRiverDistances2(String riverId) {
        return mdbDataMapper.getRiverDistance2(riverId);
    }
    public List<RiverSec> getRivernetRivers(String secID) {
        return mdbDataMapper.getRivernetRiver(secID);
    }

    // Table:parameter_1dhd
    public double getParameter1DHDService(String parameterName){
        return mdbDataMapper.getParameter1DHDMapper(parameterName);
    }
}
