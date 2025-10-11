package edu.miu.cs489.dentalsurgeriesappointments.service.impl;

import edu.miu.cs489.dentalsurgeriesappointments.dto.DtoMapper;
import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.exception.ResourceNotFoundException;
import edu.miu.cs489.dentalsurgeriesappointments.model.Address;
import edu.miu.cs489.dentalsurgeriesappointments.model.Patient;
import edu.miu.cs489.dentalsurgeriesappointments.repository.AddressRepository;
import edu.miu.cs489.dentalsurgeriesappointments.repository.PatientRepository;
import edu.miu.cs489.dentalsurgeriesappointments.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final AddressRepository addressRepository;
    private final DtoMapper dtoMapper;

    public PatientServiceImpl(PatientRepository patientRepository,
                              AddressRepository addressRepository,
                              DtoMapper dtoMapper) {
        this.patientRepository = patientRepository;
        this.addressRepository = addressRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public List<Patient> getAllPatientsSortedByLastName() {
        return patientRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public List<Patient> searchPatients(String searchString) {
        return patientRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrPatientNumberContainingIgnoreCase(
                        searchString, searchString, searchString);
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient addNewPatient(Patient newPatient) {
        return patientRepository.save(newPatient);
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    public PatientDto createPatient(PatientRequestDto requestDto) {
        Address address = resolveAddress(requestDto);

        Patient patient = new Patient();
        patient.setPatientNumber(requestDto.getPatientNumber());
        patient.setFirstName(requestDto.getFirstName());
        patient.setLastName(requestDto.getLastName());
        patient.setPhone(requestDto.getPhone());
        patient.setEmail(requestDto.getEmail());
        patient.setDateOfBirth(requestDto.getDateOfBirth());
        patient.setAddress(address);

        Patient savedPatient = patientRepository.save(patient);
        return dtoMapper.toPatientDto(savedPatient);
    }

    @Override
    public PatientDto updatePatient(Long id, PatientRequestDto requestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));

        Address address = resolveAddress(requestDto);

        patient.setPatientNumber(requestDto.getPatientNumber());
        patient.setFirstName(requestDto.getFirstName());
        patient.setLastName(requestDto.getLastName());
        patient.setPhone(requestDto.getPhone());
        patient.setEmail(requestDto.getEmail());
        patient.setDateOfBirth(requestDto.getDateOfBirth());
        patient.setAddress(address);

        Patient updatedPatient = patientRepository.save(patient);
        return dtoMapper.toPatientDto(updatedPatient);
    }

    private Address resolveAddress(PatientRequestDto requestDto) {
        if (requestDto.getAddressId() != null) {
            // Link to existing address
            return addressRepository.findById(requestDto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + requestDto.getAddressId()));
        } else if (requestDto.getAddress() != null) {
            // Create new address
            Address newAddress = dtoMapper.toAddress(requestDto.getAddress());
            return addressRepository.save(newAddress);
        }
        return null;
    }
}
