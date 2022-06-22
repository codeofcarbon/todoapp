package com.codeofcarbon.todoapp.services;

import com.codeofcarbon.todoapp.entities.*;
import com.codeofcarbon.todoapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String userLogin) {
        return userRepository.findByUserLoginIgnoreCase(userLogin)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    public void addNewUser(User newUser) {
        if (userRepository.existsUserByUserLoginIgnoreCase(newUser.getUsername()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exist!");

        newUser.setUserLogin(newUser.getUserLogin());
        newUser.setPassword(encoder.encode(newUser.getPassword()));
        newUser.grantAuthority(Role.ROLE_USER);

        userRepository.save(newUser);
    }
}
