package com.ctrlaltelite.backend;

import com.ctrlaltelite.backend.dto.CreateTrackingPeriodDTO;
import com.ctrlaltelite.backend.models.AppUser;
import com.ctrlaltelite.backend.models.PeriodStatus;
import com.ctrlaltelite.backend.models.TrackPeriod;
import com.ctrlaltelite.backend.models.dao.AuthRequest;
import com.ctrlaltelite.backend.models.dao.DaoAppUser;
import com.ctrlaltelite.backend.models.dao.DaoPeriodIdRequest;
import com.ctrlaltelite.backend.repositories.PeriodRepository;
import com.ctrlaltelite.backend.repositories.UserRepository;
import com.ctrlaltelite.backend.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
class BackendApplicationTests {

    private AppUser userForTesting = new AppUser(1L, "emil@email.cz", "Michael", "Heslo.123", "", false, "", 2L, "");
    private String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNaWNoYWVsIiwiaWF0IjoxNjc3MDY0NDQzLCJleHAiOjE3ODcwNjYyNDN9.bJ8Z6LoMH4qvt8xU56kS-Oor-ZS5vAiHjHIuLFUR8d4";
    private CreateTrackingPeriodDTO createdPeriod = new CreateTrackingPeriodDTO(1677196800000L, 1677283199000L);
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;//functionality for reading/writing JSON
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AuthRequest authRequest;
    @MockBean
    private RegistrationService registrationService;
    @MockBean
    private UserProfileService userProfileService;
    @MockBean
    private PeriodService periodService;
    @MockBean
    private TrackingPeriodService trackingPeriodService;
    @MockBean
    private PeriodRepository periodRepository;
    @Qualifier("messageSource")
    @Autowired
    private MessageSource messageSource;

    //-------HOME CONTROLLER-------
    //(GET/hello)
    @Test
    public void shouldReturnStringHello() throws Exception {
        Map<String, Object> helloMessage = new HashMap<>();
        helloMessage.put(messageSource.getMessage("home.helloKey", null, LocaleContextHolder.getLocale()), messageSource.getMessage("home.helloMessage", null, LocaleContextHolder.getLocale()));
        String responseJson = objectMapper.writeValueAsString(helloMessage);
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                        .content(responseJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(responseJson));
    }

    //(GET/user)
    @Test
    @WithMockUser(roles = "USER")
    public void shouldReturnStringForUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(messageSource.getMessage("home.helloUser", null, LocaleContextHolder.getLocale())));
    }

    //(GET/admin)
    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldReturnStringOnlyForAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().string(messageSource.getMessage("home.helloAdmin", null, LocaleContextHolder.getLocale())));
    }

//    //-------USER CONTROLLER-------
    //(POST/login)
    @Test
    public void shouldNotLoginInForNullPasword() throws Exception {
     Mockito.when(authRequest.getUsername()).thenReturn(userForTesting.getUsername());
     Mockito.when(authRequest.getPassword()).thenReturn(null);
       AuthRequest myAuthRequest = new AuthRequest(authRequest.getUsername(), authRequest.getPassword());
       String requestJson = objectMapper.writeValueAsString(myAuthRequest); // Convert the request sample to JSON
       mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(messageSource.getMessage("err.loginProvideCredentials", null, LocaleContextHolder.getLocale())));
    }

    //(GET/all)
    @Test
    public void shouldReturnListOfAllUsers() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(userForTesting));
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(userForTesting));
        mockMvc.perform(MockMvcRequestBuilders.get("/all")
                        .header("Authorization","Bearer " + validToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("emil@email.cz"));
    }

    //(GET/{id})
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    public void shouldReturnUserInfo() throws Exception {
//        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(userForTesting));
//        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(userForTesting));
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/{id}", 1L)
//                        .content(String.valueOf(userRepository.findById(1L)))
//                        .header("Authorization", "Bearer " + validToken))
//
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }

    //-------USER PROFILE CONTROLLER-------
    //(GET/profile)
    @Test
    public void shouldReturnUserProfile() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(userForTesting));
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(userForTesting));

        Set<String> roles = new HashSet<String>(Arrays.asList("ROLE_USER"));
        OngoingStubbing result = Mockito.when(userProfileService.getUserProfile(userForTesting.getUsername())).thenReturn(new DaoAppUser(2L, "Dorticek","dorticek@caramel.cz", roles, ""));

        mockMvc.perform(MockMvcRequestBuilders.get("/profile")
                        .header("Authorization","Bearer " + validToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(6))) //amount of provided details
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("Dorticek"));
    }

    //-------TRACK PERIOD CONTROLLER-------
    //(POST/request/tracked/period)
    @Test
    public void testCreateTrackingPeriod() throws Exception {
        String requestBody = objectMapper.writeValueAsString(createdPeriod);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/request/tracked/period")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    //-------PERIOD CONTROLLER-------
    //(PATCH/submit-period)
    @Test
    public void submitTrackPeriod_ShouldReturnSuccess() throws Exception {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(userForTesting));
        Mockito.when(userRepository.findByUsername(Mockito.any())).thenReturn(Optional.of(userForTesting));
        Mockito.when(periodRepository.save(Mockito.any())).thenReturn(null);

        TrackPeriod period = new TrackPeriod(
                1L,
                userForTesting,
                userForTesting,
                PeriodStatus.REQUESTED,
                new Timestamp(1676851200000L),
                new Timestamp(1677456000000L)
        );
        Mockito.when(periodRepository.findById(Mockito.any())).thenReturn(Optional.of(period));

        DaoPeriodIdRequest daoPeriodIdRequest = new DaoPeriodIdRequest(period.getId());
        String requestJson = objectMapper.writeValueAsString(daoPeriodIdRequest);

        ResultActions result = mockMvc.perform(patch("/submit-period", daoPeriodIdRequest, String.class)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
                                .header("Authorization","Bearer " + validToken));
           result.andExpect(status().isOk())
                .andExpect(content().string(messageSource.getMessage("period.submitSuccess", null, LocaleContextHolder.getLocale())));
        }
 }



