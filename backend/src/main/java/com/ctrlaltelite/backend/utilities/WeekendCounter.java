package com.ctrlaltelite.backend.utilities;

import java.sql.Timestamp;
import java.util.Calendar;
public class WeekendCounter {
        public static int countWeekendDays(Timestamp startDate, Timestamp endDate) {
            Calendar startCal = Calendar.getInstance();
            startCal.setTimeInMillis(startDate.getTime());
            Calendar endCal = Calendar.getInstance();
            endCal.setTimeInMillis(endDate.getTime());
            int weekends = 0;
            while (startCal.before(endCal)) {
                if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                    weekends++;
                }
                startCal.add(Calendar.DATE, 1);
            }
            return weekends;
        }
}
