package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.models.dao.DaoHoliday;
import com.ctrlaltelite.backend.services.HolidayService;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class HolidayControllerTest {

    // Create mock objects for the dependencies of the controller
    private final HolidayService holidayServiceMock = mock(HolidayService.class);
    private final MessageSource messageSourceMock = mock(MessageSource.class);

    // Create an instance of the controller using the mock dependencies
    private final HolidayController holidayController = new HolidayController(holidayServiceMock, messageSourceMock);

    @Test
    void submit_return_success_response() {
        // Create a test holiday object
        DaoHoliday holiday = new DaoHoliday("Xmas","24/12/2022","merry Xmas");

        // Set up the mock MessageSource to return a success message when the getMessage method is called
        String successMessage = "Holiday submitted successfully";
        when(messageSourceMock.getMessage("holiday.submitSuccess", null, LocaleContextHolder.getLocale())).thenReturn(successMessage);

        // Call the submitHoliday method on the controller with the test holiday object
        ResponseEntity<?> response = holidayController.submitHoliday(holiday);

        // Verify that the submitHoliday method on the HolidayService is called with the correct argument
        verify(holidayServiceMock, times(1)).submitHoliday(holiday);

        // Assert that the response body contains the success message and statusCode 200
        assertEquals(successMessage, response.getBody());
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    void delete_return_success_response() {
        Long id = 1L;
        String successMessage = "Deleted successfully!";

        when(messageSourceMock.getMessage("holiday.deleteSuccess",null,LocaleContextHolder.getLocale())).thenReturn(successMessage);

        ResponseEntity<String> response = holidayController.deleteHoliday(id);

        verify(holidayServiceMock, times(1)).deleteHoliday(id);
        assertEquals(successMessage, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}


