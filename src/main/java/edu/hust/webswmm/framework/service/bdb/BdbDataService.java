package edu.hust.webswmm.framework.service.bdb;

import edu.hust.webswmm.model.bdb.entity.StInfo;
import edu.hust.webswmm.model.bdb.entity.StRainfall;
import edu.hust.webswmm.model.bdb.entity.StWaterlevel;
import edu.hust.webswmm.model.bdb.entity.StDischarge;

import java.util.Date;
import java.util.List;

public interface BdbDataService {
    List<StInfo> getMonStationService(String stationID);
    List<StRainfall> getRainfallFromBDBService(String stationID, Date startTime, Date endTime);
    List<StDischarge>  getDischargeFromBDBService(String stationID, Date startTime, Date endTime);
    List<StWaterlevel>  getWaterlevelFromBDBService(String stationID, Date startTime, Date endTime);
    Date getMaxTimeBDBDisService(String stationID);
    Date getMinTimeBDBDisService(String stationID);
    Date getMaxTimeBDBRainService(String stationID);
    Date getMinTimeBDBRainService(String stationID);
    Date getMaxTimeBDBWaterlevelService(String stationID);
    Date getMinTimeBDBWaterlevelService(String stationID);
}
