package com.ctrlaltelite.backend.utilities;

import com.ctrlaltelite.backend.models.Holiday;
import com.ctrlaltelite.backend.repositories.HolidayRepository;
import java.sql.Timestamp;
public class IsHoliday {
    public static boolean isHolidays(HolidayRepository holidayRepository, Timestamp startDay, Timestamp endDay){
    boolean doIntervalsOverlap = false;
        Iterable<Holiday> allHolidays = holidayRepository.findAll();
        for(Holiday holiday : allHolidays) {
            doIntervalsOverlap = DoIntervalsOverlap.doIntervalsOverlap(holiday.getStartTimestamp(), holiday.getEndTimestamp(), startDay, endDay);
            if(doIntervalsOverlap)
                break;
        }
        return doIntervalsOverlap;
    }
}
