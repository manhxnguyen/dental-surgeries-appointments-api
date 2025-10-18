package edu.miu.cs489.dentalsurgeriesappointments.controller;

import edu.miu.cs489.dentalsurgeriesappointments.dto.AddressDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.DtoMapper;
import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientDto;
import edu.miu.cs489.dentalsurgeriesappointments.model.Address;
import edu.miu.cs489.dentalsurgeriesappointments.model.Patient;
import edu.miu.cs489.dentalsurgeriesappointments.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit Test for PatientController.listPatients() method
 * Uses Mockito to mock dependencies
 */
@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatientService patientService;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private PatientController patientController;

    private List<Patient> mockPatients;
    private List<PatientDto> mockPatientDtos;

    @BeforeEach
    public void setUp() {
        // Set up MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();

        // Create mock addresses
        Address address1 = new Address("123 Main St", "Fairfield", "Iowa", "52557");
        address1.setAddressId(1L);

        Address address2 = new Address("456 Oak Ave", "Des Moines", "Iowa", "50309");
        address2.setAddressId(2L);

        // Create mock patients (sorted by last name)
        Patient patient1 = new Patient(
                "P1001",
                "Alice",
                "Anderson",
                "641-555-1111",
                "alice.anderson@example.com",
                LocalDate.of(1990, 3, 15),
                address1
        );
        patient1.setPatientId(1L);

        Patient patient2 = new Patient(
                "P1002",
                "Bob",
                "Brown",
                "641-555-2222",
                "bob.brown@example.com",
                LocalDate.of(1985, 7, 20),
                address2
        );
        patient2.setPatientId(2L);

        Patient patient3 = new Patient(
                "P1003",
                "Charlie",
                "Clark",
                "641-555-3333",
                "charlie.clark@example.com",
                LocalDate.of(1992, 11, 10),
                address1
        );
        patient3.setPatientId(3L);

        mockPatients = Arrays.asList(patient1, patient2, patient3);

        // Create mock patient DTOs
        AddressDto addressDto1 = new AddressDto(1L, "123 Main St", "Fairfield", "Iowa", "52557");
        AddressDto addressDto2 = new AddressDto(2L, "456 Oak Ave", "Des Moines", "Iowa", "50309");

        PatientDto patientDto1 = new PatientDto(
                1L,
                "P1001",
                "Alice",
                "Anderson",
                "641-555-1111",
                "alice.anderson@example.com",
                LocalDate.of(1990, 3, 15),
                addressDto1
        );

        PatientDto patientDto2 = new PatientDto(
                2L,
                "P1002",
                "Bob",
                "Brown",
                "641-555-2222",
                "bob.brown@example.com",
                LocalDate.of(1985, 7, 20),
                addressDto2
        );

        PatientDto patientDto3 = new PatientDto(
                3L,
                "P1003",
                "Charlie",
                "Clark",
                "641-555-3333",
                "charlie.clark@example.com",
                LocalDate.of(1992, 11, 10),
                addressDto1
        );

        mockPatientDtos = Arrays.asList(patientDto1, patientDto2, patientDto3);
    }

    /**
     * Test Case 1: listPatients() should return list of all patients sorted by last name
     * Expected: Should return 200 OK with list of patient DTOs
     */
    @Test
    public void testListPatients_ShouldReturnListOfPatientsSortedByLastName() throws Exception {
        // Arrange
        when(patientService.getAllPatientsSortedByLastName()).thenReturn(mockPatients);
        when(dtoMapper.toPatientDto(mockPatients.get(0))).thenReturn(mockPatientDtos.get(0));
        when(dtoMapper.toPatientDto(mockPatients.get(1))).thenReturn(mockPatientDtos.get(1));
        when(dtoMapper.toPatientDto(mockPatients.get(2))).thenReturn(mockPatientDtos.get(2));

        // Act & Assert
        mockMvc.perform(get("/adsweb/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].patientId", is(1)))
                .andExpect(jsonPath("$[0].patientNumber", is("P1001")))
                .andExpect(jsonPath("$[0].firstName", is("Alice")))
                .andExpect(jsonPath("$[0].lastName", is("Anderson")))
                .andExpect(jsonPath("$[0].phone", is("641-555-1111")))
                .andExpect(jsonPath("$[0].email", is("alice.anderson@example.com")))
                .andExpect(jsonPath("$[0].address.street", is("123 Main St")))
                .andExpect(jsonPath("$[0].address.city", is("Fairfield")))
                .andExpect(jsonPath("$[1].patientId", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Bob")))
                .andExpect(jsonPath("$[1].lastName", is("Brown")))
                .andExpect(jsonPath("$[2].patientId", is(3)))
                .andExpect(jsonPath("$[2].firstName", is("Charlie")))
                .andExpect(jsonPath("$[2].lastName", is("Clark")));

        // Verify that the service methods were called
        verify(patientService, times(1)).getAllPatientsSortedByLastName();
        verify(dtoMapper, times(3)).toPatientDto(any(Patient.class));
    }

    /**
     * Test Case 2: listPatients() when no patients exist
     * Expected: Should return 200 OK with empty list
     */
    @Test
    public void testListPatients_WhenNoPatientsExist_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(patientService.getAllPatientsSortedByLastName()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/adsweb/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        // Verify that the service method was called but dtoMapper was never called
        verify(patientService, times(1)).getAllPatientsSortedByLastName();
        verify(dtoMapper, never()).toPatientDto(any(Patient.class));
    }
}
