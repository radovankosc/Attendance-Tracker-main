package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.dto.UserDto;
import com.ctrlaltelite.backend.exceptions.ValidationException;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.services.RegistrationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RegistrationController registrationController;

    @Mock
    RegistrationService registrationService;

    @Mock
    UserRepository userRepository;

    @Test
    public void test() throws Exception {
        doThrow(new ValidationException("User already exists")).when(registrationService).register(new UserDto());
        registrationController.register(new UserDto("username","password","email","role"));
    }
}
