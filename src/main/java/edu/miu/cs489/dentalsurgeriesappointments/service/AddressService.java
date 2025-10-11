package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.model.Address;
import java.util.List;
import java.util.Optional;

public interface AddressService {
    List<Address> getAllAddresses();

    List<Address> getAllAddressesSortedByCity();

    Optional<Address> getAddressById(Long id);

    Address addNewAddress(Address newAddress);

    Address saveAddress(Address address);

    void deleteAddress(Long id);
}
