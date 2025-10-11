package edu.miu.cs489.dentalsurgeriesappointments.repository;

import edu.miu.cs489.dentalsurgeriesappointments.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByPatientNumber(String patientNumber);

    List<Patient> findAllByOrderByLastNameAsc();

    List<Patient> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPatientNumberContainingIgnoreCase(
            String firstName, String lastName, String patientNumber);
}
