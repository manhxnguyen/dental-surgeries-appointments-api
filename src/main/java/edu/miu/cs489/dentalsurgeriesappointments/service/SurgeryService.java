package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.model.Surgery;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public interface SurgeryService {
    List<Surgery> getAllSurgeries();

    Optional<Surgery> getSurgeryById(Long id);

    Surgery addNewSurgery(Surgery newSurgery);

    Surgery saveSurgery(Surgery surgery);

    void deleteSurgery(Long id);
}
