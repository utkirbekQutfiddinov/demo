package uz.utkirbek.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAll() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void getOne() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        User result = userService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void getOne_Exception() throws Exception {
        when(userRepository.findById(1)).thenThrow(RuntimeException.class);

        User result = userService.getOne(1);

        assertNull(result);
    }

    @Test
    void getOneNotFound() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User result = userService.getOne(1);

        assertNull(result);
    }


    @Test
    void addRepositoryError() throws Exception {
        when(userRepository.create(any())).thenReturn(Optional.empty());

        User result = userService.add(new User());

        assertNull(result);
    }

    @Test
    void update() throws Exception {
        when(userRepository.update(any())).thenReturn(Optional.of(new User()));

        User result = userService.update(new User());

        assertNull(result);
    }

    @Test
    void update_Exception() throws Exception {
        when(userRepository.update(any())).thenThrow(RuntimeException.class);

        User result = userService.update(new User());

        assertNull(result);
    }

    @Test
    void updateRepositoryError() throws Exception {
        when(userRepository.update(any())).thenReturn(Optional.empty());

        User result = userService.update(new User());

        assertNull(result);
    }

    @Test
    void delete() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        Boolean result = userService.delete(1);

        assertTrue(result);
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void delete_Exception() throws Exception {
        User user = mock(User.class);
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.delete(user)).thenThrow(RuntimeException.class);

        Boolean result = userService.delete(1);

        assertFalse(result);
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void deleteNotFound() throws Exception {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Boolean result = userService.delete(1);

        assertFalse(result);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void changePassword() {
        when(userRepository.changePassword(1, "newPassword")).thenReturn(Optional.of(true));

        Boolean result = userService.changePassword(1, "newPassword");

        assertTrue(result);
    }

    @Test
    void changePassword_Exception() {
        when(userRepository.changePassword(1, "newPassword")).thenThrow(RuntimeException.class);

        Boolean result = userService.changePassword(1, "newPassword");

        assertFalse(result);
    }

    @Test
    void changePasswordRepositoryError() {
        when(userRepository.changePassword(1, "newPassword")).thenReturn(Optional.empty());

        Boolean result = userService.changePassword(1, "newPassword");

        assertFalse(result);
    }


    @Test
    void findByUsernameAndPasswordNotFound() {
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        User result = userService.findByUsernameAndPassword("nonexistentUser", "testPassword");

        assertNull(result);
    }

    @Test
    void findByUsernameAndPasswordNotFound_Exception() {
        when(userRepository.findByUsername("nonexistentUser")).thenThrow(RuntimeException.class);

        User result = userService.findByUsernameAndPassword("nonexistentUser", "testPassword");

        assertNull(result);
    }


}
