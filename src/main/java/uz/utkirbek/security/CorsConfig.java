package uz.utkirbek.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins}")
    private String allowedOrigin;

    @Value("${cors.allowed-methods}")
    private String allowedMethod;

    @Value("${cors.allowed-headers}")
    private String allowedHeader;

    @Value("${cors.max-age}")
    private Long maxAge;

    @Value("${cors.allow-credentials}")
    private Boolean allowCredentieals;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(allowedOrigin);
        configuration.addAllowedMethod(allowedMethod);
        configuration.addAllowedHeader(allowedHeader);
        configuration.setMaxAge(maxAge);
        configuration.setAllowCredentials(allowCredentieals);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
