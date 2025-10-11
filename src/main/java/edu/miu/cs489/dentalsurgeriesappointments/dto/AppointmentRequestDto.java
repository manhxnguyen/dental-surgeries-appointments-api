package edu.miu.cs489.dentalsurgeriesappointments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto {
    private LocalDateTime appointmentDateTime;
    private Long patientId;
    private Long dentistId;
    private Long surgeryId;
}
