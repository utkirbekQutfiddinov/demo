package uz.utkirbek.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.User;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll() {
        List<User> userList = mock(List.class);
        when(userRepository.readAll()).thenReturn(userList);

        List<User> result = userService.getAll();

        assertEquals(userList, result);
    }

    @Test
    public void getOneUserFound() {
        User user = new User();
        when(userRepository.readOne(1)).thenReturn(Optional.of(user));

        User result = userService.getOne(1);

        assertEquals(user, result);
    }

    @Test
    public void getOneUserNotFound() {
        when(userRepository.readOne(1)).thenReturn(Optional.empty());

        assertNull(userService.getOne(1));
    }

    @Test
    public void addUserCreated() {
        User user = new User();
        when(userRepository.create(user)).thenReturn(Optional.of(user));

        User result = userService.add(user);

        assertEquals(user, result);
    }

    @Test
    public void addUserCreationFailed() {
        User user = new User();
        when(userRepository.create(user)).thenReturn(Optional.empty());

        assertNull(userService.add(user));
    }

    @Test
    public void updateUserFound() {
        User user = new User();
        when(userRepository.update(user)).thenReturn(Optional.of(user));

        User result = userService.update(user);

        assertEquals(user, result);
    }

    @Test
    public void updateUserNotFound() {
        User user = new User();
        when(userRepository.update(user)).thenReturn(Optional.empty());

        assertNull(userService.update(user));
    }

    @Test
    public void deleteUser() {
        User user = new User();
        when(userRepository.readOne(1)).thenReturn(Optional.of(user));

        userService.delete(1);

        verify(userRepository).delete(user);
    }

    @Test
    public void deleteUserNotFound() {
        when(userRepository.readOne(1)).thenReturn(Optional.empty());

        userService.delete(1);

        verify(userRepository, never()).delete(any());
    }

    @Test
    public void changePasswordSuccess() {
        when(userRepository.changePassword(1, "newPassword")).thenReturn(Optional.of(true));

        assertTrue(userService.changePassword(1, "newPassword"));
    }

    @Test
    public void changePasswordFailure() {
        when(userRepository.changePassword(1, "newPassword")).thenReturn(Optional.of(false));

        assertFalse(userService.changePassword(1, "newPassword"));
    }
}