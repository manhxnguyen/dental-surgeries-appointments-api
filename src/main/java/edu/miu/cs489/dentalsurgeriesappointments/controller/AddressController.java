package edu.miu.cs489.dentalsurgeriesappointments.controller;

import edu.miu.cs489.dentalsurgeriesappointments.model.Address;
import edu.miu.cs489.dentalsurgeriesappointments.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adsweb/api/v1/addresses")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<Address>> listAddresses() {
        List<Address> addresses = addressService.getAllAddressesSortedByCity();
        return ResponseEntity.ok(addresses);
    }
}
