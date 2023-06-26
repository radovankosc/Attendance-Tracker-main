package com.ctrlaltelite.backend.utilities;

import com.ctrlaltelite.backend.models.Holiday;
import com.ctrlaltelite.backend.models.TrackPeriod;
import com.ctrlaltelite.backend.repositories.HolidayRepository;
public class HolidayCounter {
public static int countHolidayDays(TrackPeriod trackPeriod, HolidayRepository holidayRepository) {
    int holidayCounter = 0;
    Iterable<Holiday> allHolidays = holidayRepository.findAll();
    for (Holiday holiday : allHolidays) {
        boolean doIntervalsOverlap = DoIntervalsOverlap.doIntervalsOverlap(trackPeriod.getStartTimestamp(), trackPeriod.getEndTimestamp(), holiday.getStartTimestamp(), holiday.getEndTimestamp());
        if (doIntervalsOverlap)
             holidayCounter++;
    }
    return holidayCounter;
}
}
