package uz.utkirbek.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.security.JwtProvider;
import uz.utkirbek.service.UserService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

public class AuthControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private JwtProvider jwtProvider;
    @InjectMocks
    private AuthController authController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void login_ValidCredentials_ReturnsOk() {
        String username = "testUsername";
        String password = "testPassword";
        String mockToken = "mockJwtToken";


        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);
        mockUser.setActive(true);

        Mockito.when(userService.findByUsernameAndPassword(username, password)).thenReturn(mockUser);
        Mockito.when(jwtProvider.generateToken(anyString())).thenReturn(mockToken);
        ResponseEntity<Map> response = authController.login(username, password);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        Mockito.verify(userService).findByUsernameAndPassword(username, password);
    }

    @Test
    public void login_EmptyUsername_ReturnsBadRequest() {
        ResponseEntity<Map> response = authController.login(null, "testPassword");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void login_EmptyPassword_ReturnsBadRequest() {
        ResponseEntity<Map> response = authController.login("testUsername", null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void login_UserNotFound_ReturnsNotFound() {
        String username = "testUsername";
        String password = "testPassword";
        Mockito.when(userService.findByUsernameAndPassword(username, password)).thenReturn(null);

        ResponseEntity<Map> response = authController.login(username, password);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(userService).findByUsernameAndPassword(username, password);
    }

    @Test
    public void login_InactiveUser_ReturnsBadRequest() {
        String username = "testUsername";
        String password = "testPassword";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(password);
        mockUser.setActive(false);

        Mockito.when(userService.findByUsernameAndPassword(username, password)).thenReturn(mockUser);

        ResponseEntity<Map> response = authController.login(username, password);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(userService).findByUsernameAndPassword(username, password);
    }
}
