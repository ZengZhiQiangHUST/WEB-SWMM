package edu.hust.webswmm.framework.mathmodel.tools;

import edu.hust.webswmm.model.mdb.entity.ObsRainfall;
import edu.hust.webswmm.model.mdb.entity.HydroStation;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WriteTxtFile {
    /**
     *将降雨写入inp文件，为SWHHM模型做准备
     * @param rainfallStationList
     * @param file
     * @param startDateTime
     * @param endDateTime
     * @throws IOException
     * @throws ParseException
     */
    public void writeInputFile(List<ObsRainfall> rainfallStationList, String file, Date startDateTime, Date endDateTime) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String charSet = "UTF-8";
        String startDT=formatter.format(startDateTime);
        String endDT=formatter.format(endDateTime);
        String startDate=new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDT));
        String startTime=new SimpleDateFormat("HH:mm").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDT));
        String endDate=new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDT));
        String endtTime=new SimpleDateFormat("HH:mm").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDT));
        FileOutputStream fileWriter = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileWriter, charSet);
        try {
            writer.write("[TIMESERIES]\r\n");
            writer.write(";;Name           Date       Time       Value\r\n");
            writer.write(";;-------------- ---------- ---------- ----------\r\n");
            for (int i = 0; i <rainfallStationList.size() ; i++) {
                String dateTime=formatter.format(new Date(startDateTime.getTime()+i*300000));
                String date=new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
                String time=new SimpleDateFormat("HH:mm").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime));
                String line = rainfallStationList.get(i).getStationId() + "              " + date + " " + time + "       " + rainfallStationList.get(i).getRainfall() + "\r\n";
                writer.write(line);
            }
            writer.write("\r\n");
            writer.write("[OPTIONS]\r\n");
            writer.write("FLOW_UNITS             CMS\r\n");
            writer.write("INFILTRATION           HORTON\r\n");
            writer.write("FLOW_ROUTING           DYNWAVE\r\n");
            writer.write("LINK_OFFSETS           DEPTH\r\n");
            writer.write("MIN_SLOPE              0\r\n");
            writer.write("SKIP_STEADY_STATE      NO\r\n");
            writer.write("ALLOW_PONDING          NO\r\n");
            writer.write("START_DATE             " + startDate + "\r\n");
            writer.write("START_TIME             " + startTime + "\r\n");
            writer.write("REPORT_START_DATE      " + startDate + "\r\n");
            writer.write("REPORT_START_TIME      " + startTime + "\r\n");
            writer.write("END_DATE               " + endDate + "\r\n");
            writer.write("END_TIME               " + endtTime + "\r\n");
            writer.write("DRY_DAYS               0\r\n");
            writer.write("DRY_STEP               01:00:00\r\n");
            writer.write("WET_STEP               00:05:00\r\n");
            writer.write("ROUTING_STEP           00:00:30 \r\n");
            writer.write("REPORT_STEP            00:05:00\r\n");
            writer.write("INERTIAL_DAMPING       PARTIAL\r\n");
            writer.write("NORMAL_FLOW_LIMITED    BOTH\r\n");
            writer.write("FORCE_MAIN_EQUATION    H-W\r\n");
            writer.write("VARIABLE_STEP          0.75\r\n");
            writer.write("LENGTHENING_STEP       0\r\n");
            writer.write("MIN_SURFAREA           1.167\r\n");
            writer.write("MAX_TRIALS             8\r\n");
            writer.write("HEAD_TOLERANCE         0.0015\r\n");
            writer.write("SYS_FLOW_TOL           5\r\n");
            writer.write("LAT_FLOW_TOL           5\r\n");
            writer.write("MINIMUM_STEP           0.5\r\n");
            writer.write("THREADS                1\r\n");
            writer.write("\r\n");
            /**
             * Merge
             */
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            writer.close();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream, charSet);
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[64];
        int count =0;
        try {
            while ((count = reader.read(buf)) != -1) {
                builder.append(buf, 0, count);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            reader.close();
        }
    }
    /**
     *水动力模型边界条件的获取
     * @param file
     * @param hydroStationListUp
     * @param hydroStationListDown
     * @throws IOException
     */
    public void writeFile(String file, List<HydroStation> hydroStationListUp, List<HydroStation> hydroStationListDown) throws IOException {
        String charSet = "UTF-8";
        FileOutputStream fileWriter = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileWriter, charSet);
        try {
            for (int i = 0; i < hydroStationListUp.size(); i++) {
                String line = (i + 1) + "  " + hydroStationListUp.get(i).getWatValue() + "   " + hydroStationListDown.get(i).getWatValue()+"\r\n";
                writer.write(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            writer.close();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream, charSet);
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[64];
        int count =0;
        try {
            while ((count = reader.read(buf)) != -1) {
                builder.append(buf, 0, count);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            reader.close();
        }
    }

    /**
     * 水动力模型边界条件的获取
     * @param file
     * @param hydroStationListUp1
     * @param hydroStationListUp2
     * @param hydroStationListDown
     * @throws IOException
     */
    public void writeFileHD2(String file, List<HydroStation> hydroStationListUp1, List<HydroStation> hydroStationListUp2, List<HydroStation> hydroStationListDown) throws IOException {
        String charSet = "UTF-8";
        FileOutputStream fileWriter = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fileWriter, charSet);
        try {
            for (int i = 0; i < hydroStationListUp1.size(); i++) {
                String line = (i + 1) + "  " + hydroStationListUp1.get(i).getFlowValue() + "   " +  hydroStationListUp2.get(i).getFlowValue()+"   "+hydroStationListDown.get(i).getWatValue()+"\r\n";
                writer.write(line);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            writer.close();
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(fileInputStream, charSet);
        StringBuilder builder = new StringBuilder();
        char[] buf = new char[64];
        int count =0;
        try {
            while ((count = reader.read(buf)) != -1) {
                builder.append(buf, 0, count);
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            reader.close();
        }
    }
}
