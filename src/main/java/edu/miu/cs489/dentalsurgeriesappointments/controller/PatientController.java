package edu.miu.cs489.dentalsurgeriesappointments.controller;

import edu.miu.cs489.dentalsurgeriesappointments.dto.DtoMapper;
import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientDto;
import edu.miu.cs489.dentalsurgeriesappointments.dto.PatientRequestDto;
import edu.miu.cs489.dentalsurgeriesappointments.exception.PatientNotFoundException;
import edu.miu.cs489.dentalsurgeriesappointments.model.Patient;
import edu.miu.cs489.dentalsurgeriesappointments.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adsweb/api/v1/patients")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PatientController {

    private final PatientService patientService;
    private final DtoMapper dtoMapper;

    public PatientController(PatientService patientService, DtoMapper dtoMapper) {
        this.patientService = patientService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> listPatients() {
        List<Patient> patients = patientService.getAllPatientsSortedByLastName();
        List<PatientDto> patientDtos = patients.stream()
                .map(dtoMapper::toPatientDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patientDtos);
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
        Optional<Patient> patient = patientService.getPatientById(patientId);
        return patient.map(p -> ResponseEntity.ok(dtoMapper.toPatientDto(p)))
                .orElseThrow(() -> new PatientNotFoundException("Patient with ID " + patientId + " not found"));
    }

    @PostMapping
    public ResponseEntity<PatientDto> registerNewPatient(@Valid @RequestBody PatientRequestDto patientRequestDto) {
        PatientDto savedPatient = patientService.createPatient(patientRequestDto);
        return new ResponseEntity<>(savedPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Long patientId,
                                                     @Valid @RequestBody PatientRequestDto patientRequestDto) {
        PatientDto updatedPatient = patientService.updatePatient(patientId, patientRequestDto);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        Optional<Patient> patient = patientService.getPatientById(patientId);
        if (patient.isPresent()) {
            patientService.deletePatient(patientId);
            return ResponseEntity.noContent().build();
        } else {
            throw new PatientNotFoundException("Patient with ID " + patientId + " not found");
        }
    }

    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<PatientDto>> searchPatients(@PathVariable String searchString) {
        List<Patient> patients = patientService.searchPatients(searchString);
        List<PatientDto> patientDtos = patients.stream()
                .map(dtoMapper::toPatientDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patientDtos);
    }
}
