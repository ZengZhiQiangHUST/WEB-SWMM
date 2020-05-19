package edu.hust.webswmm.framework.mathmodel.tools;
import edu.hust.webswmm.model.mdb.entity.ResSections;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class ReadTxtFile {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 读取水动力模型的结果
     * @param filePath
     * @param dates
     * @return
     * @throws Exception
     */
    public  List<ResSections> readHDWQResults(String filePath, List<Date> dates) throws Exception {
        List<ResSections> hdwqData = new ArrayList<>();
        InputStreamReader in = new InputStreamReader(new FileInputStream(filePath));
        BufferedReader bf = new BufferedReader(in);
        String line = "";
        line = bf.readLine();
        int i = 0;
        String riverID = null;
        String secID = null;
        String time = null;
         double waterLevel = 0;
         double flow = 0;
         double velocity = 0;
         double pollutant = 0;
        while (line != null) {
            line = bf.readLine();
            if (line == null) {
                continue;
            }
            line.trim();//去除首末的空格
            String[] splitLine = line.split("\\s+");//字符分割
            secID=splitLine[1];
            riverID=splitLine[2];
            time=splitLine[3];
            waterLevel =  Double.parseDouble(splitLine[4]);
            flow =  Double.parseDouble(splitLine[5]);
            velocity = Double.parseDouble(splitLine[6]);
            pollutant = Double.parseDouble(splitLine[7]);
            if(i>=dates.size())
                i = 0;
            ResSections hdwqData1=new ResSections(secID,dates.get(i),flow,waterLevel,velocity);
            hdwqData.add(hdwqData1);
            i++;
        }
        return hdwqData;
    }

    /**
     * 预热计算结果的读取
     * @param filePath
     * @return
     * @throws Exception
     */
    public  List<ResSections> readHDWQResultsWarm(String filePath) throws Exception {
        List<ResSections> hdwqData = new ArrayList<>();
        InputStreamReader in = new InputStreamReader(new FileInputStream(filePath));
        BufferedReader bf = new BufferedReader(in);
        String line = "";
        line = bf.readLine();
        String riverID = null;
        String secID = null;
        String time = null;
         double waterLevel = 0;
         double flow = 0;
         double velocity = 0;
         double pollutant = 0;
        while (line != null) {
            line = bf.readLine();
            if (line == null) {
                continue;
            }
            line.trim();//去除首末的空格
            String[] splitLine = line.split("\\s+");//字符分割
            secID=splitLine[1];
            riverID=splitLine[2];
            time=splitLine[3];
            waterLevel =  Double.parseDouble(splitLine[4]);
            flow = Double.parseDouble(splitLine[5]);
            velocity = Double.parseDouble(splitLine[6]);
            pollutant = Double.parseDouble(splitLine[7]);
            ResSections hdwqData1=new ResSections(secID,flow,waterLevel,velocity);
            hdwqData.add(hdwqData1);
        }
        return hdwqData;
    }
}
