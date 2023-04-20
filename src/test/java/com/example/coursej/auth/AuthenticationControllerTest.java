package com.example.coursej.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthenticationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    void shouldRegister() throws Exception {
        RegisterRequest request = RegisterRequest.builder()
                .username("johndoe")
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("password")
                .build();

        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("token")
                .build();

        when(authenticationService.register(any(RegisterRequest.class))).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse authenticationResponse = objectMapper.readValue(content, AuthenticationResponse.class);

        assert(authenticationResponse.getToken().equals("token"));
    }

    @Test
    void shouldAuthenticate() throws Exception {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("johndoe@example.com")
                .password("password")
                .build();
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("token")
                .build();
        when(authenticationService.authenticate(any(AuthenticationRequest.class))).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse authenticationResponse = objectMapper.readValue(content, AuthenticationResponse.class);

        assert(authenticationResponse.getToken().equals("token"));
    }
}
