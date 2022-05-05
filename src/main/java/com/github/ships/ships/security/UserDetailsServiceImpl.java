package com.github.ships.ships.security;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.NotUserTurnException;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserRepository;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Validated
class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    UserDetailsServiceImpl(UserRepository userAuthRepository) {
        this.userRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        if (!email.matches(emailRegex)) throw new RuntimeException("Invalid email!");
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException();
        }

        return new UserDetailsImpl(user.get());
    }

}
