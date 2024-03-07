package uz.utkirbek.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uz.utkirbek.model.dto.UserChangePasswordDto;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void changePassword_ValidParameters_ReturnsSuccess() {
        UserChangePasswordDto dto = new UserChangePasswordDto();
        dto.setUsername("testUsername");
        dto.setOldPassword("oldPassword");
        dto.setNewPassword("newPassword");

        User mockUser = new User();
        mockUser.setId(1);

        Mockito.when(userService.findByUsernameAndPassword(dto.getUsername(), dto.getOldPassword())).thenReturn(mockUser);
        Mockito.when(userService.changePassword(mockUser.getId(), dto.getNewPassword())).thenReturn(true);

        ResponseEntity<String> response = userController.changePassword(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Success", response.getBody());
        Mockito.verify(userService).findByUsernameAndPassword(dto.getUsername(), dto.getOldPassword());
        Mockito.verify(userService).changePassword(mockUser.getId(), dto.getNewPassword());
    }

    @Test
    public void changePassword_EmptyUsername_ReturnsBadRequest() {
        UserChangePasswordDto dto = new UserChangePasswordDto();
        dto.setUsername(null);
        dto.setOldPassword("oldPassword");
        dto.setNewPassword("newPassword");

        ResponseEntity<String> response = userController.changePassword(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void changePassword_EmptyOldPassword_ReturnsBadRequest() {
        UserChangePasswordDto dto = new UserChangePasswordDto();
        dto.setUsername("testUsername");
        dto.setOldPassword(null);
        dto.setNewPassword("newPassword");

        ResponseEntity<String> response = userController.changePassword(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void changePassword_EmptyNewPassword_ReturnsBadRequest() {
        UserChangePasswordDto dto = new UserChangePasswordDto();
        dto.setUsername("testUsername");
        dto.setOldPassword("oldPassword");
        dto.setNewPassword(null);

        ResponseEntity<String> response = userController.changePassword(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verifyNoInteractions(userService);
    }

    @Test
    public void changePassword_PasswordChangeFailure_ReturnsError() {
        UserChangePasswordDto dto = new UserChangePasswordDto();
        dto.setUsername("testUsername");
        dto.setOldPassword("oldPassword");
        dto.setNewPassword("newPassword");

        User mockUser = new User();
        mockUser.setId(1);

        Mockito.when(userService.findByUsernameAndPassword(dto.getUsername(), dto.getOldPassword())).thenReturn(mockUser);
        Mockito.when(userService.changePassword(mockUser.getId(), dto.getNewPassword())).thenReturn(false);

        ResponseEntity<String> response = userController.changePassword(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Error", response.getBody());
        Mockito.verify(userService).findByUsernameAndPassword(dto.getUsername(), dto.getOldPassword());
        Mockito.verify(userService).changePassword(mockUser.getId(), dto.getNewPassword());
    }

    @Test
    void changePassword_UserNotFound() {
        UserChangePasswordDto dto = new UserChangePasswordDto();
        dto.setUsername("nonExistingUser");
        dto.setOldPassword("oldPassword");
        dto.setNewPassword("newPassword");

        Mockito.when(userService.findByUsernameAndPassword("nonExistingUser", "oldPassword")).thenReturn(null);

        ResponseEntity<String> response = userController.changePassword(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User does not exist", response.getBody());

    }

}
