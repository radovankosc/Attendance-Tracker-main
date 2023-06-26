package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.repositories.HolidayRepository;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.RecordRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class HomeControllerTest {
    @MockBean
    private final MessageSource messageSource;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder encoder;
    @MockBean
    private RecordRepository recordRepository;
    @MockBean
    private PeriodRepository periodRepository;
    @MockBean
    private HolidayRepository holidayRepository;

    public HomeControllerTest(@Qualifier("messageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Test
    public void testReturnHello() { //since this method doesn't have any dependencies, we don't need to mock any objects.
        // create an instance of the UserProfileController class with all parameters in class constructor
        HomeController homeController = new HomeController( userRepository, encoder, recordRepository, periodRepository, holidayRepository, messageSource);
        // call the method
        ResponseEntity<Map<String, Object>> responseEntity = homeController.returnHello();
        //assertation
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).containsEntry(messageSource.getMessage("home.helloKey", null, LocaleContextHolder.getLocale()), messageSource.getMessage("home.helloMessage", null, LocaleContextHolder.getLocale()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testReturnHelloAdmin() {
        HomeController homeController = new HomeController( userRepository, encoder, recordRepository, periodRepository, holidayRepository, messageSource);
        String response = homeController.admin();
        assertEquals(messageSource.getMessage("home.helloAdmin", null, LocaleContextHolder.getLocale()), response);
    }

    @Test
    public void testReturnHelloUser() {
        HomeController homeController = new HomeController( userRepository, encoder, recordRepository, periodRepository, holidayRepository, messageSource);
        // call the method
        String response = homeController.user();
        assertEquals(messageSource.getMessage("home.helloUser", null, LocaleContextHolder.getLocale()), response);
    }
}
