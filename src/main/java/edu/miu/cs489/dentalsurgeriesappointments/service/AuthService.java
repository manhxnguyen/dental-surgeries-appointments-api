package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.dto.LoginRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.LoginResponseDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.RegisterRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.model.User;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto loginRequest);
    User register(RegisterRequestDto registerRequest);
    User getUserByUsername(String username);
}

