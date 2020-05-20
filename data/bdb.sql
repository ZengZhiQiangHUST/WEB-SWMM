/*
 Navicat Premium Data Transfer

 Source Server         : MySQLConnect
 Source Server Type    : MySQL
 Source Server Version : 50515
 Source Host           : localhost:3306
 Source Schema         : bdb

 Target Server Type    : MySQL
 Target Server Version : 50515
 File Encoding         : 65001

 Date: 19/05/2020 22:54:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for st_discharge
-- ----------------------------
DROP TABLE IF EXISTS `st_discharge`;
CREATE TABLE `st_discharge`  (
  `stationID` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `discharge_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `discharge_value` decimal(10, 3) NOT NULL,
  `remark` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stationID`, `discharge_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of st_discharge
-- ----------------------------
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:00:00', 1.825, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:05:00', 1.842, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:10:00', 2.040, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:15:00', 2.293, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:20:00', 2.311, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:25:00', 2.365, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:30:00', 2.404, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:35:00', 2.514, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:40:00', 2.606, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:45:00', 2.658, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:50:00', 2.926, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 09:55:00', 3.235, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:00:00', 4.172, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:05:00', 4.573, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:10:00', 4.676, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:15:00', 4.555, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:20:00', 4.331, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:25:00', 4.266, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:30:00', 4.059, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:35:00', 3.967, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:40:00', 3.629, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:45:00', 3.340, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:50:00', 3.171, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 10:55:00', 2.644, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:00:00', 2.628, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:05:00', 2.611, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:10:00', 2.477, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:15:00', 2.514, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:20:00', 2.517, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:25:00', 2.385, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:30:00', 2.458, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:35:00', 2.344, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:40:00', 2.478, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:45:00', 2.354, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:50:00', 2.367, NULL);
INSERT INTO `st_discharge` VALUES ('S01', '2019-06-18 11:55:00', 2.427, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:00:00', 3.132, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:05:00', 3.138, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:10:00', 3.407, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:15:00', 3.547, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:20:00', 3.707, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:25:00', 3.767, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:30:00', 3.792, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:35:00', 3.892, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:40:00', 3.985, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:45:00', 4.047, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:50:00', 4.244, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 09:55:00', 4.395, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:00:00', 4.478, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:05:00', 4.938, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:10:00', 5.467, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:15:00', 7.069, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:20:00', 7.750, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:25:00', 7.938, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:30:00', 7.738, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:35:00', 7.363, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:40:00', 7.277, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:45:00', 6.936, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:50:00', 6.771, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 10:55:00', 6.216, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:00:00', 5.710, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:05:00', 5.635, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:10:00', 5.030, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:15:00', 4.449, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:20:00', 4.329, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:25:00', 4.226, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:30:00', 4.230, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:35:00', 4.219, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:40:00', 4.119, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:45:00', 4.157, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:50:00', 4.070, NULL);
INSERT INTO `st_discharge` VALUES ('S02', '2019-06-18 11:55:00', 4.141, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:00:00', 2.105, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:05:00', 2.265, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:10:00', 2.505, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:15:00', 2.760, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:20:00', 2.800, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:25:00', 2.870, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:30:00', 2.925, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:35:00', 3.115, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:40:00', 3.255, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:45:00', 3.330, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:50:00', 3.765, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 09:55:00', 4.265, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:00:00', 5.775, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:05:00', 6.415, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:10:00', 6.600, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:15:00', 6.415, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:20:00', 6.065, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:25:00', 6.000, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:30:00', 5.685, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:35:00', 5.525, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:40:00', 5.015, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:45:00', 4.540, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:50:00', 4.465, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 10:55:00', 4.000, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:00:00', 3.340, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:05:00', 3.150, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:10:00', 3.125, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:15:00', 3.095, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:20:00', 3.075, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:25:00', 3.055, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:30:00', 3.040, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:35:00', 3.020, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:40:00', 2.995, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:45:00', 2.970, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:50:00', 2.965, NULL);
INSERT INTO `st_discharge` VALUES ('S03', '2019-06-18 11:55:00', 2.955, NULL);

-- ----------------------------
-- Table structure for st_info
-- ----------------------------
DROP TABLE IF EXISTS `st_info`;
CREATE TABLE `st_info`  (
  `station_position` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `stationID` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `station_type` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`station_position`, `stationID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of st_info
-- ----------------------------
INSERT INTO `st_info` VALUES ('suqian', 'R01', 'Meteorological_station', NULL);
INSERT INTO `st_info` VALUES ('suqian', 'S01', 'Stream_gauge_station', NULL);
INSERT INTO `st_info` VALUES ('suqian', 'S02', 'Stream_gauge_station', NULL);
INSERT INTO `st_info` VALUES ('suqian', 'S03', 'Stream_gauge_station', NULL);
INSERT INTO `st_info` VALUES ('suqian', 'W01', 'Water_level_station', NULL);

-- ----------------------------
-- Table structure for st_rainfall
-- ----------------------------
DROP TABLE IF EXISTS `st_rainfall`;
CREATE TABLE `st_rainfall`  (
  `stationID` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `rainfall_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `rainfall_value` decimal(10, 3) NOT NULL,
  `remark` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stationID`, `rainfall_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of st_rainfall
-- ----------------------------
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:00:00', 1.770, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:05:00', 2.040, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:10:00', 1.600, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:15:00', 2.820, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:20:00', 3.420, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:25:00', 4.200, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:30:00', 3.540, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:35:00', 6.870, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:40:00', 6.200, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:45:00', 13.290, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:50:00', 20.550, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 09:55:00', 35.880, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:00:00', 30.480, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:05:00', 22.200, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:10:00', 16.890, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:15:00', 13.290, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:20:00', 8.860, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:25:00', 8.820, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:30:00', 4.940, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:35:00', 6.300, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:40:00', 5.430, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:45:00', 3.140, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:50:00', 4.140, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 10:55:00', 3.660, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:00:00', 3.270, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:05:00', 1.960, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:10:00', 1.760, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:15:00', 2.400, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:20:00', 2.190, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:25:00', 1.340, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:30:00', 0.915, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:35:00', 0.855, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:40:00', 0.980, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:45:00', 0.900, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:50:00', 0.675, NULL);
INSERT INTO `st_rainfall` VALUES ('R01', '2019-06-18 11:55:00', 0.000, NULL);

-- ----------------------------
-- Table structure for st_waterlevel
-- ----------------------------
DROP TABLE IF EXISTS `st_waterlevel`;
CREATE TABLE `st_waterlevel`  (
  `stationID` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `waterlevel_time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `waterlevel_value` decimal(20, 3) NOT NULL,
  `remark` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`stationID`, `waterlevel_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of st_waterlevel
-- ----------------------------
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:00:00', 57.394, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:05:00', 57.395, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:10:00', 57.401, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:15:00', 57.408, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:20:00', 57.410, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:25:00', 57.412, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:30:00', 57.413, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:35:00', 57.414, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:40:00', 57.415, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:45:00', 57.416, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:50:00', 57.419, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 09:55:00', 57.424, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:00:00', 57.434, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:05:00', 57.439, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:10:00', 57.441, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:15:00', 57.442, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:20:00', 57.443, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:25:00', 57.443, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:30:00', 57.439, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:35:00', 57.439, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:40:00', 57.435, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:45:00', 57.435, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:50:00', 57.433, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 10:55:00', 57.430, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:00:00', 57.429, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:05:00', 57.427, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:10:00', 57.425, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:15:00', 57.422, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:20:00', 57.421, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:25:00', 57.419, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:30:00', 57.418, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:35:00', 57.418, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:40:00', 57.418, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:45:00', 57.418, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:50:00', 57.418, NULL);
INSERT INTO `st_waterlevel` VALUES ('W01', '2019-06-18 11:55:00', 57.418, NULL);

SET FOREIGN_KEY_CHECKS = 1;
