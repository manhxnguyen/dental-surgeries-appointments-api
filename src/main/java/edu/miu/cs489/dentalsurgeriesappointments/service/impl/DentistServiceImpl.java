package edu.miu.cs489.dentalsurgeriesappointments.service.impl;

import edu.miu.cs489.dentalsurgeriesappointments.model.Dentist;
import edu.miu.cs489.dentalsurgeriesappointments.repository.DentistRepository;
import edu.miu.cs489.dentalsurgeriesappointments.service.DentistService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DentistServiceImpl implements DentistService {

    private final DentistRepository dentistRepository;

    public DentistServiceImpl(DentistRepository dentistRepository) {
        this.dentistRepository = dentistRepository;
    }

    @Override
    public List<Dentist> getAllDentists() {
        return dentistRepository.findAll();
    }

    @Override
    public Optional<Dentist> getDentistById(Long id) {
        return dentistRepository.findById(id);
    }

    @Override
    public Dentist addNewDentist(Dentist newDentist) {
        return dentistRepository.save(newDentist);
    }

    @Override
    public Dentist saveDentist(Dentist dentist) {
        return dentistRepository.save(dentist);
    }

    @Override
    public void deleteDentist(Long id) {
        dentistRepository.deleteById(id);
    }
}
