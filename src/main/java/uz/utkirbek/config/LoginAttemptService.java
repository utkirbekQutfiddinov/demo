package uz.utkirbek.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    public static final int MAX_ATTEMPT = 3;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAttemptService.class);
    private final LoadingCache<String, Integer> attemptsCache;

    @Autowired
    private HttpServletRequest request;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).build(new CacheLoader<>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginFailed() {
        String clientIP = getClientIP();
        int attempts;
        try {
            attempts = attemptsCache.get(clientIP);
        } catch (final ExecutionException e) {
            LOGGER.error("Error on: " + e.getMessage());
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(clientIP, attempts);
    }

    public boolean isBlocked() {
        try {
            String clientIP = getClientIP();
            Integer i = attemptsCache.get(clientIP);
            return i >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            LOGGER.error("Error on: " + e.getMessage());
            return false;
        }
    }

    private String getClientIP() {
        final String header = request.getHeader("X-Forwarded-For");
        if (header != null) {
            return header.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}