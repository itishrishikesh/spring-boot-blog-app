package com.hrishi.blog.service.impl;

import com.hrishi.blog.entity.Role;
import com.hrishi.blog.entity.User;
import com.hrishi.blog.exception.BlogAPIException;
import com.hrishi.blog.payload.LoginDto;
import com.hrishi.blog.payload.RegisterDto;
import com.hrishi.blog.repository.RoleRepository;
import com.hrishi.blog.repository.UserRepository;
import com.hrishi.blog.security.CustomUserDetailsService;
import com.hrishi.blog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "Success! User is logged in now.";
    }

    @Override
    public String register(RegisterDto registerDto) {

        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Account with this email already exists!");
        }

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Account with this username already exists!");
        }

        User user = new User();

        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findRoleByName("ROLE_USER").get();
        roles.add(role);

        user.setRoles(roles);

        userRepository.save(user);

        return "Success! Registered user " + user.getUsername();
    }
}
