package uz.utkirbek.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.utkirbek.model.entity.User;
import uz.utkirbek.repository.UserRepository;
import uz.utkirbek.service.impl.UserDetailsServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Mock user data
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        // Mock UserRepository behavior
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        // Call loadUserByUsername method
        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        // Assertions
        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals("testUser", userDetails.getUsername(), "Username should match");
        assertEquals("encodedPassword", userDetails.getPassword(), "Password should match");

        // Verify that UserRepository's findByUsername method was called
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        // Mock UserRepository behavior
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        // Call loadUserByUsername method and expect an exception
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentUser"));

        // Verify that UserRepository's findByUsername method was called
        verify(userRepository, times(1)).findByUsername("nonexistentUser");
    }
}
