package com.example.coursej.auth;

import com.example.coursej.config.JwtService;
import com.example.coursej.token.Token;
import com.example.coursej.token.TokenRepository;
import com.example.coursej.user.User;
import com.example.coursej.user.UserRole;
import com.example.coursej.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;

public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    @Spy
    private AuthenticationService authenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void register_shouldCreateUserAndReturnToken() {
        RegisterRequest request = RegisterRequest.builder()
                .username("user")
                .firstName("First")
                .lastName("Last")
                .email("user@example.com")
                .password("password")
                .build();

        User user = User.builder()
                .username(request.getUsername())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .build();

        when(userService.addUser(any(User.class))).thenReturn(user);

        String token = "jwt-token";
        when(jwtService.generateToken(user)).thenReturn(token);

        authenticationService.register(request);

        verify(userService).addUser(any(User.class));
        verify(tokenRepository).save(any(Token.class));
    }

    @Test
    public void authenticate_shouldAuthenticateUserAndReturnToken() {
        String email = "user@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        User user = User.builder()
                .username("user")
                .firstName("First")
                .lastName("Last")
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(UserRole.USER)
                .build();

        when(userService.getUserByEmail(email)).thenReturn(user);

        String token = "jwt-token";
        when(jwtService.generateToken(user)).thenReturn(token);

        authenticationService.authenticate(request);

        verify(authenticationManager).authenticate(any());
        verify(tokenRepository).save(any(Token.class));
    }
}
