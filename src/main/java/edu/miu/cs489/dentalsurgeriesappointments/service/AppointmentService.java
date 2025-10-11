package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.model.Appointment;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface AppointmentService {
    List<Appointment> getAllAppointments();

    Optional<Appointment> getAppointmentById(Long id);

    Appointment addNewAppointment(Appointment newAppointment);

    Appointment saveAppointment(Appointment appointment);

    void deleteAppointment(Long id);
}
