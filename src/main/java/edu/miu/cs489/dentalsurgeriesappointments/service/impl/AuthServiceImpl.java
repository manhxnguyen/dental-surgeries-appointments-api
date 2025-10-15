package edu.miu.cs489.dentalsurgeriesappointments.service.impl;

import edu.miu.cs489.dentalsurgeriesappointments.dto.LoginRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.LoginResponseDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.RegisterRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.model.User;
import edu.miu.cs489.dentalsurgeriesappointments.repository.UserRepository;
import edu.miu.cs489.dentalsurgeriesappointments.security.JwtUtil;
import edu.miu.cs489.dentalsurgeriesappointments.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<String> roles = user.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        String jwt = jwtUtil.generateToken(user.getUserId(), user.getUsername(), roles);

        return new LoginResponseDto(jwt, user.getUserId(), user.getUsername(), user.getEmail(), roles);
    }

    @Override
    @Transactional
    public User register(RegisterRequestDto registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRoles(registerRequest.getRoles());
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }
}

