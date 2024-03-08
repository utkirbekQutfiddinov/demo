package uz.utkirbek.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {

    private final String salt = "123Utkirbek!@#Qutfiddinov?Epam:Task";
    private final PasswordEncoder encoder;

    public MyPasswordEncoder() {
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return encoder.encode(salt + rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(salt + rawPassword, encodedPassword);
    }
}
