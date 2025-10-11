package edu.miu.cs489.dentalsurgeriesappointments.repository;

import edu.miu.cs489.dentalsurgeriesappointments.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_PatientId(Long patientId);
    List<Appointment> findByDentist_DentistId(Long dentistId);
    List<Appointment> findByAppointmentDateTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT a FROM Appointment a ORDER BY a.appointmentDateTime")
    List<Appointment> findAllSortedByDate();
}

