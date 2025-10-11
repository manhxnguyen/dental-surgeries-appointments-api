package edu.miu.cs489.dentalsurgeriesappointments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurgeryDto {
    private Long surgeryId;
    private String surgeryNumber;
    private String surgeryName;
    private AddressDto location;
}

