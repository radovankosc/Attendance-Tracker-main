package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.models.Holiday;
import com.ctrlaltelite.backend.models.dao.DaoHoliday;
import com.ctrlaltelite.backend.repositories.HolidayRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class HolidayService {
    private final HolidayRepository holidayRepository;
    private final MessageSource messageSource;

    public HolidayService(HolidayRepository holidayRepository, @Qualifier("messageSource") MessageSource messageSource) {
        this.holidayRepository = holidayRepository;
        this.messageSource = messageSource;
    }

    public void submitHoliday(DaoHoliday holiday) throws ValidationException {

        if((holiday.getDate() == null || holiday.getDate().isEmpty()) || (holiday.getTitle() == null || holiday.getTitle().isEmpty()))
            throw new ValidationException(messageSource.getMessage("holiday.err.provideTitleAndDate", null, LocaleContextHolder.getLocale()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startHoliday = LocalDate.parse(holiday.getDate(),formatter);
        Timestamp startTimestamp = Timestamp.valueOf(startHoliday.atTime(LocalTime.MIN).plusHours(1));
        Timestamp endTimestamp = Timestamp.valueOf(startHoliday.atTime(LocalTime.MAX));

        Holiday newHoliday = new Holiday(null,startTimestamp,endTimestamp,holiday.getTitle(),null);

        if(holiday.getDescription() != null)
            newHoliday.setDescription(holiday.getDescription());
        holidayRepository.save(newHoliday);
    }

    public void deleteHoliday(Long id) throws ValidationException {
        Optional<Holiday> optionalHoliday = holidayRepository.findById(id);

        if(!optionalHoliday.isPresent())
        throw new ValidationException(messageSource.getMessage("holiday.err.nullId", null, LocaleContextHolder.getLocale()));

        holidayRepository.deleteById(id);
    }
}
