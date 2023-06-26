package com.ctrlaltelite.backend.services;

import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.models.Holiday;
import com.ctrlaltelite.backend.models.dao.DaoHoliday;
import com.ctrlaltelite.backend.repositories.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@SpringBootTest
    class HolidayServiceTest {
    private Holiday holidayForTesting = new Holiday(1L,new Timestamp(1671840000000L),new Timestamp(1671926399000L),"Xmas","merry Xmas");

    private final HolidayRepository holidayRepository;

    private final MessageSource messageSource;

@Autowired
   HolidayServiceTest(HolidayRepository holidayRepository, MessageSource messageSource) {
        this.holidayRepository = holidayRepository;
        this.messageSource = messageSource;
    }



    @Test
    void submit_missing_title_throw_Exception() {
        // Create a test holiday object missing title
        DaoHoliday holiday = new DaoHoliday(null,"24/12/2022","merry Xmas");

        // Create a mock object of HolidayRepository
        HolidayRepository holidayRepositoryMock = Mockito.mock(HolidayRepository.class);

        // Set up the mock MessageSource to return a err message when the getMessage method is called
        String errMsg = "You must provide title and date!";
        MessageSource messageSourceMock = Mockito.mock(MessageSource.class);
        when(messageSourceMock.getMessage("holiday.err.provideTitleAndDate", null, LocaleContextHolder.getLocale())).thenReturn(errMsg);

        // Create a HolidayService instance with the mock objects
        HolidayService holidayService = new HolidayService(holidayRepositoryMock, messageSourceMock);

        // Retrieve the thrown ValidationException and assert that it contains the expected error message
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            holidayService.submitHoliday(holiday);
        });
        assertEquals(errMsg, exception.getMessage());
    }

    @Test
    void submit_save_holiday_in_DB(){
            // Arrange
            DaoHoliday holiday = new DaoHoliday("Xmas","24/12/2024","merry Xmas");
            HolidayRepository holidayRepositoryMock = Mockito.mock(HolidayRepository.class);
            MessageSource messageSourceMock = Mockito.mock(MessageSource.class);
            Mockito.when(holidayRepositoryMock.findAll()).thenReturn(List.of(holidayForTesting));

            HolidayService holidayService = new HolidayService(holidayRepositoryMock,messageSourceMock);

            holidayService.submitHoliday(holiday);

            List<Holiday> holidays = (List<Holiday>) holidayRepositoryMock.findAll();
            assertEquals(1, holidays.size());

            Holiday savedHoliday = holidays.get(0);
            assertEquals("Xmas",savedHoliday.getTitle());
            assertEquals("merry Xmas", savedHoliday.getDescription());
        }


    @Test
    void delete_id_not_found_throw_Exception() {
        Long id = 123L;
        HolidayRepository holidayRepositoryMock = Mockito.mock(HolidayRepository.class);
        String errMsg = "Can not find this Id!";
        MessageSource messageSourceMock = Mockito.mock(MessageSource.class);

        when(messageSourceMock.getMessage("holiday.err.nullId", null, LocaleContextHolder.getLocale())).thenReturn(errMsg);

        HolidayService holidayService = new HolidayService(holidayRepositoryMock,messageSourceMock);

        ValidationException exception = assertThrows(ValidationException.class,()->{
            holidayService.deleteHoliday(id);
        });
        assertEquals(errMsg, exception.getMessage());
    }

    @Test
    void delete_holiday_from_DB(){
        Long id = 2L;
        Holiday holiday = new Holiday();
        holiday.setId(id);

        HolidayRepository holidayRepositoryMock = Mockito.mock(HolidayRepository.class);
        MessageSource messageSourceMock = Mockito.mock(MessageSource.class);

        HolidayService holidayService = new HolidayService(holidayRepositoryMock,messageSourceMock);

        when(holidayRepositoryMock.findById(id)).thenReturn(Optional.of(holiday));

        holidayService.deleteHoliday(id);

        verify(holidayRepositoryMock, times(1)).deleteById(id);
    }
}