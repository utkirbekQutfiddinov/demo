package uz.utkirbek.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {

        loginAttemptService.loginFailed();

        if (loginAttemptService.isBlocked()) {
            response.getWriter().write("You are temporarily blocked due to too many failed login attempts. Please try again after 5 minutes.");
            response.getWriter().flush();
            response.setStatus(HttpStatus.SC_TOO_MANY_REQUESTS);
        }
    }
}