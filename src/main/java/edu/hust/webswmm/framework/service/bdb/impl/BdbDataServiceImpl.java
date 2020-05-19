package edu.hust.webswmm.framework.service.bdb.impl;

import edu.hust.webswmm.mapper.bdb.BdbDataMapper;
import edu.hust.webswmm.model.bdb.entity.StInfo;
import edu.hust.webswmm.model.bdb.entity.StRainfall;
import edu.hust.webswmm.model.bdb.entity.StWaterlevel;
import edu.hust.webswmm.framework.service.bdb.BdbDataService;
import edu.hust.webswmm.model.bdb.entity.StDischarge;
import edu.hust.webswmm.model.mdb.entity.ObsDischarge;
import edu.hust.webswmm.model.mdb.entity.ObsWaterlevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BdbDataServiceImpl implements BdbDataService {
    @Autowired
    BdbDataMapper bdbDataMapper;

    public List<StInfo> getMonStationService(String stationID){
        return bdbDataMapper.getMonStationMapper(stationID);
    }
    public List<StRainfall> getRainfallFromBDBService(String stationID, Date startTime, Date endTime){
        return bdbDataMapper.getRainfallFromBDBMapper(stationID,startTime,endTime);
}
    public List<StDischarge>  getDischargeFromBDBService(String stationID, Date startTime, Date endTime){
        return bdbDataMapper.getDischargeFromBDBMapper(stationID,startTime,endTime);
    }
    public List<StWaterlevel>  getWaterlevelFromBDBService(String stationID, Date startTime, Date endTime){
        return bdbDataMapper.getWaterlevelFromBDBMapper(stationID,startTime,endTime);
    }
    public Date getMaxTimeBDBDisService(String stationID){
        return bdbDataMapper.getMaxTimeBDBDisMapper(stationID);
    }
    public Date getMinTimeBDBDisService(String stationID){
        return bdbDataMapper.getMinTimeBDBDisMapper(stationID);
    }

    public Date getMaxTimeBDBRainService(String stationID){
        return bdbDataMapper.getMaxTimeBDBRainMapper(stationID);
    }
    public Date getMinTimeBDBRainService(String stationID){
        return bdbDataMapper.getMinTimeBDBRainMapper(stationID);
    }
    public Date getMaxTimeBDBWaterlevelService(String stationID){
        return bdbDataMapper.getMaxTimeBDBWaterlevelMapper(stationID);
    }
    public Date getMinTimeBDBWaterlevelService(String stationID){
        return bdbDataMapper.getMinTimeBDBWaterlevelMapper(stationID);
    }
}
