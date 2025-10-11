package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.model.Dentist;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface DentistService {
    List<Dentist> getAllDentists();

    Optional<Dentist> getDentistById(Long id);

    Dentist addNewDentist(Dentist newDentist);

    Dentist saveDentist(Dentist dentist);

    void deleteDentist(Long id);
}
