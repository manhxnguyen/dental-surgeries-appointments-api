package edu.miu.cs489.dentalsurgeriesappointments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurgeryRequestDto {
    private String surgeryNumber;
    private String surgeryName;

    // Option 1: Link to existing address
    private Long locationId;

    // Option 2: Create new address
    private AddressRequestDto location;
}

