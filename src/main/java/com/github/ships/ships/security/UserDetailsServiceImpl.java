package com.github.ships.ships.security;

import com.github.ships.ships.NotFoundException;
import com.github.ships.ships.users.User;
import com.github.ships.ships.users.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userAuthRepository) {
        this.userRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException();
        }

        return new UserDetailsImpl(user.get());
    }

}
