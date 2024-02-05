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
    void testGetAll() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<User> result = userService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetOne() {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        User result = userService.getOne(1);

        assertNotNull(result);
    }

    @Test
    void testGetOneNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User result = userService.getOne(1);

        assertNull(result);
    }


    @Test
    void testAddRepositoryError() {
        when(userRepository.create(any())).thenReturn(Optional.empty());

        User result = userService.add(new User());

        assertNull(result);
    }

    @Test
    void testUpdate() {
        when(userRepository.update(any())).thenReturn(Optional.of(new User()));

        User result = userService.update(new User());

        assertNotNull(result);
    }

    @Test
    void testUpdateRepositoryError() {
        when(userRepository.update(any())).thenReturn(Optional.empty());

        User result = userService.update(new User());

        assertNull(result);
    }

    @Test
    void testDelete() {
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        Boolean result = userService.delete(1);

        assertTrue(result);
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        Boolean result = userService.delete(1);

        assertFalse(result);
        verify(userRepository, never()).delete(any());
    }

    @Test
    void testChangePassword() {
        when(userRepository.changePassword(1, "newPassword")).thenReturn(Optional.of(true));

        Boolean result = userService.changePassword(1, "newPassword");

        assertTrue(result);
    }

    @Test
    void testChangePasswordRepositoryError() {
        when(userRepository.changePassword(1, "newPassword")).thenReturn(Optional.empty());

        Boolean result = userService.changePassword(1, "newPassword");

        assertFalse(result);
    }


    @Test
    void testFindByUsernameAndPasswordNotFound() {
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        User result = userService.findByUsernameAndPassword("nonexistentUser", "testPassword");

        assertNull(result);
    }


}
