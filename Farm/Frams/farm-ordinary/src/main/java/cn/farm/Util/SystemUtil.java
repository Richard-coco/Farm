package cn.farm.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemUtil {

    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}
