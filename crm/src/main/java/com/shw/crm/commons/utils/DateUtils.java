package com.shw.crm.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String formatDateTime(Date data){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr1=sdf.format(data);
        return  dateStr1;
    }
    public static String formatDate(Date data){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr2=sdf.format(data);
        return  dateStr2;
    }
    public static String formatTime(Date data){
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String dateStr3=sdf.format(data);
        return  dateStr3;
    }
}
