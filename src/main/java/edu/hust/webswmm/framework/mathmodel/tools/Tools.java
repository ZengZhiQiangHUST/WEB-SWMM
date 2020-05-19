package edu.hust.webswmm.framework.mathmodel.tools;

import edu.hust.webswmm.framework.mathmodel.engine.javaProject.hydraulicModeling.hd1d.HDpoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Tools {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 由结束时间推求固定长度的时间起点
     *
     * @param endtime
     * @param hour
     * @return
     */
    public Date getStartDateByEndDate(Date endtime, int hour) throws ParseException {
        Date starttime;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endtime);
        calendar.add(Calendar.HOUR_OF_DAY, -hour);
        starttime = format.parse(format.format(calendar.getTime()));
        return starttime;
    }

    /**
     * 添加断面的起点距和高程数据
     *
     * @param x
     * @param y
     * @return
     */
    public ArrayList<HDpoint> addSecPoints( double x[],  double y[]) {
        ArrayList<HDpoint> hDpoints = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            double startDistance = x[i];//起点距
            double elevation = y[i];//高程
            int mark = 1;//标记左堤岸、右堤岸（左堤岸1河道2右堤岸）
            HDpoint hDpoint = new HDpoint(startDistance, elevation, mark);
            hDpoints.add(hDpoint);
        }
        return hDpoints;
    }
}
