package edu.miu.cs489.dentalsurgeriesappointments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DentistDto {
    private Long dentistId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String specialization;
    private SurgeryDto surgery;
}

