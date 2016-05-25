package com.binge.present.util;

import org.joda.time.DateTime;
import org.joda.time.Days;

/**
 * Created by Administrator on 2016/5/25.
 */
public class CommonUtil {
    public static int getDays() {
        DateTime sT = new DateTime(2016, 5, 1, 0, 0, 0);
        DateTime eT = new DateTime(System.currentTimeMillis());
        Days days = Days.daysBetween(sT, eT);
        return days.getDays();
    }

}
