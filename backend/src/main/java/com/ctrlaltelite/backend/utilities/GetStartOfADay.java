package com.ctrlaltelite.backend.utilities;

import java.sql.Timestamp;
import java.util.Calendar;

public class GetStartOfADay {
      public static Timestamp getStartOfDay(Timestamp timestamp) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timestamp);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return new Timestamp(calendar.getTimeInMillis());
        }
    }
