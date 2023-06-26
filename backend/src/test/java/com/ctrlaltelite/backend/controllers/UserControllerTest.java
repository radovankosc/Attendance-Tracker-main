package com.ctrlaltelite.backend.controllers;

import com.ctrlaltelite.backend.models.dao.AuthRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @InjectMocks
    private UserController userController;

    @Mock
    private AuthenticationManager authenticationManager;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;


    @Test
    public void shouldReturn_isBadRequestMessage_forInvalidInput() throws Exception {
        AuthRequest authRequest = new AuthRequest("", "");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(authRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(messageSource.getMessage("err.loginProvideCredentials", null, LocaleContextHolder.getLocale())))
                .andReturn();
    }

   /* @Test
    public void shouldReturn_requestSuccess_ExistingUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("John","password");

        ResponseEntity<String> response = userController.sendJwtToken(authRequest);
        Assert.isTrue(response.getStatusCode().is2xxSuccessful(), "Login successful");
        Assert.notNull(response.getBody(), "JWT token is not null");
    }*/

   /* @Test
    public void shouldThrow_UsernameNotFoundRequest_NonExistingUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("invalid", "invalid");
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken("invalid", "invalid");

        when(authenticationManager.authenticate(authToken)).thenThrow(new UsernameNotFoundException("Incorrect username or password!"));

        assertThrows(UsernameNotFoundException.class, () -> {
            userController.sendJwtToken(authRequest);
        });
        }*/
}