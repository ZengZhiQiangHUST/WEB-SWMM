package edu.hust.webswmm.mapper.bdb;

import edu.hust.webswmm.model.bdb.entity.StInfo;
import edu.hust.webswmm.model.bdb.entity.StRainfall;
import edu.hust.webswmm.model.bdb.entity.StWaterlevel;
import edu.hust.webswmm.model.bdb.entity.StDischarge;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BdbDataMapper {
    List<StInfo> getMonStationMapper(String stationID);
    List<StRainfall> getRainfallFromBDBMapper(@Param("stationID") String stationID, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<StDischarge>  getDischargeFromBDBMapper(@Param("stationID") String stationID, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    List<StWaterlevel>  getWaterlevelFromBDBMapper(@Param("stationID") String stationID, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
    Date getMaxTimeBDBDisMapper(String stationID);
    Date getMinTimeBDBDisMapper(String stationID);
    Date getMaxTimeBDBRainMapper(String stationID);
    Date getMinTimeBDBRainMapper(String stationID);
    Date getMaxTimeBDBWaterlevelMapper(String stationID);
    Date getMinTimeBDBWaterlevelMapper(String stationID);
}
