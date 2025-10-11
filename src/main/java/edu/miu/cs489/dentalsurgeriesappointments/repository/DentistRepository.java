package edu.miu.cs489.dentalsurgeriesappointments.repository;

import edu.miu.cs489.dentalsurgeriesappointments.model.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {
    List<Dentist> findByLastName(String lastName);
}

