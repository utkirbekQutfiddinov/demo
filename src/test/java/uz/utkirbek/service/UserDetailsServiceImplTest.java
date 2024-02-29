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
        User user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails, "UserDetails should not be null");
        assertEquals("testUser", userDetails.getUsername(), "Username should match");
        assertEquals("encodedPassword", userDetails.getPassword(), "Password should match");

        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsUsernameNotFoundException() {
        when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("nonexistentUser"));

        verify(userRepository, times(1)).findByUsername("nonexistentUser");
    }
}
