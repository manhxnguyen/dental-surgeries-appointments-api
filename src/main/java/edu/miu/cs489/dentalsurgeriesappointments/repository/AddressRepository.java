package edu.miu.cs489.dentalsurgeriesappointments.repository;

import edu.miu.cs489.dentalsurgeriesappointments.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findAllByOrderByCityAsc();
}
