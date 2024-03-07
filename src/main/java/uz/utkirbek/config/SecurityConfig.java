package uz.utkirbek.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private AuthenticationFailureHandler failureHandler;


    @Autowired
    private MyLogoutHandler myLogoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/serviceHealth").permitAll()
                        .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                        .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                        .requestMatchers("/actuator/**").permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin()
                .failureHandler(failureHandler)
                .and()
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .logoutSuccessHandler(logoutSuccessHandler())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return myLogoutHandler;
    }

}
