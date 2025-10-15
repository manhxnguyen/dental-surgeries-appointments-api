package edu.miu.cs489.dentalsurgeriesappointments.controller;

import edu.miu.cs489.dentalsurgeriesappointments.dto.LoginRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.LoginResponseDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.RegisterRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.model.User;
import edu.miu.cs489.dentalsurgeriesappointments.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequestDto registerRequest) {
        User user = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}

