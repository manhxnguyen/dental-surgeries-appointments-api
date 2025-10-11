package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.model.Patient;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    List<Patient> getAllPatients();

    List<Patient> getAllPatientsSortedByLastName();

    List<Patient> searchPatients(String searchString);

    Optional<Patient> getPatientById(Long id);

    Patient addNewPatient(Patient newPatient);

    PatientDto createPatient(PatientRequestDto requestDto);

    PatientDto updatePatient(Long id, PatientRequestDto requestDto);

    Patient savePatient(Patient patient);

    void deletePatient(Long id);
}
