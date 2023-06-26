package com.ctrlaltelite.backend.utilities;

import java.sql.Timestamp;
import java.util.Calendar;

public class IsWeekend {
    public static boolean isWeekend(Timestamp startDate){
            Calendar startCal = Calendar.getInstance();
            startCal.setTimeInMillis(startDate.getTime());

                return (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }
}
