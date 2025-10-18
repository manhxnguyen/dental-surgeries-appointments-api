package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.model.Address;
import edu.miu.cs489.dentalsurgeriesappointments.model.Patient;
import edu.miu.cs489.dentalsurgeriesappointments.repository.PatientRepository;
import edu.miu.cs489.dentalsurgeriesappointments.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit Tests for PatientService.getPatientById() method
 * Uses Mockito to mock dependencies
 */
@ExtendWith(MockitoExtension.class)
public class PatientServiceUnitTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    private Patient testPatient;
    private Address testAddress;

    @BeforeEach
    public void setUp() {
        // Create test address
        testAddress = new Address(
                "123 Main Street",
                "Fairfield",
                "Iowa",
                "52557"
        );
        testAddress.setAddressId(1L);

        // Create test patient
        testPatient = new Patient(
                "P1001",
                "John",
                "Doe",
                "641-555-1234",
                "john.doe@example.com",
                LocalDate.of(1985, 5, 15),
                testAddress
        );
        testPatient.setPatientId(1L);
    }

    /**
     * Test Case 1: getPatientById() when patient exists
     * Expected: Should return Optional containing the patient
     */
    @Test
    public void testGetPatientById_WhenPatientExists_ShouldReturnPatient() {
        // Arrange
        Long patientId = 1L;
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(testPatient));

        // Act
        Optional<Patient> result = patientService.getPatientById(patientId);

        // Assert
        assertTrue(result.isPresent(), "Patient should be found");
        assertEquals(patientId, result.get().getPatientId());
        assertEquals("P1001", result.get().getPatientNumber());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        assertEquals("641-555-1234", result.get().getPhone());
        assertEquals("john.doe@example.com", result.get().getEmail());
        assertEquals(LocalDate.of(1985, 5, 15), result.get().getDateOfBirth());
        assertNotNull(result.get().getAddress());

        // Verify repository method was called exactly once
        verify(patientRepository, times(1)).findById(patientId);
    }

    /**
     * Test Case 2: getPatientById() when patient does not exist
     * Expected: Should return empty Optional
     */
    @Test
    public void testGetPatientById_WhenPatientDoesNotExist_ShouldReturnEmptyOptional() {
        // Arrange
        Long invalidPatientId = 99999L;
        when(patientRepository.findById(invalidPatientId)).thenReturn(Optional.empty());

        // Act
        Optional<Patient> result = patientService.getPatientById(invalidPatientId);

        // Assert
        assertFalse(result.isPresent(), "Patient should not be found");
        assertTrue(result.isEmpty(), "Result should be empty Optional");

        // Verify repository method was called exactly once
        verify(patientRepository, times(1)).findById(invalidPatientId);
    }
}

