package com.github.ships.ships.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordEncoderProvider {
    @Bean
    public PasswordEncoder getInstance() {
        return new BCryptPasswordEncoder(13, new SecureRandom());
    }
}
