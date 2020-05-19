package edu.hust.webswmm.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2018/7/21.
 */
public class TimeUtils
{
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String format(Timestamp timestamp)
    {
        String str = df.format(timestamp);
        return str;
    }
}
