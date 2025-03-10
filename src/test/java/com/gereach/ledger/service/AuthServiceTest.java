package com.gereach.ledger.service;

import com.gereach.ledger.bean.po.*;
import com.gereach.ledger.bean.web.*;
import com.gereach.ledger.repository.*;
import com.gereach.ledger.bean.UserDetailsImpl;
import com.gereach.ledger.util.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private SignupRequest validSignupRequest;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        validSignupRequest = new SignupRequest();
        validSignupRequest.setUsername("testuser");
        validSignupRequest.setPassword("TestPassword123!");
        validSignupRequest.setEmail("test@example.com");

        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsername("testuser");
        validLoginRequest.setPassword("TestPassword123!");
    }

    @Test
    void registerUser_WithValidRequest_SavesUserWithEncodedPassword() {
        // 模拟角色存在
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));

        // 模拟密码编码
        when(passwordEncoder.encode(validSignupRequest.getPassword())).thenReturn("encodedPassword");

        // 执行注册
        authService.registerUser(validSignupRequest);

        // 验证用户保存
        verify(userRepository).save(argThat(user -> 
            user.getUsername().equals("testuser") &&
            user.getPassword().equals("encodedPassword") &&
            user.getRoles().contains(userRole)
        ));
    }

    @Test
    void authenticateUser_WithValidCredentials_ReturnsJwtResponse() {
        // 模拟认证成功
        UserDetailsImpl userDetails = new UserDetailsImpl(
            1L, "testuser", "test@example.com", "encodedPassword", Collections.emptyList()
        );
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // 模拟生成 Token
        when(jwtUtils.generateToken(userDetails)).thenReturn("fake-jwt-token");

        // 执行登录
        JwtResponse response = authService.authenticateUser(validLoginRequest);

        // 验证返回结果
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals("testuser", response.getUsername());
    }
}
