package edu.miu.cs489.dentalsurgeriesappointments.dto;

import edu.miu.cs489.dentalsurgeriesappointments.model.*;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    // Address mappings
    public AddressDto toAddressDto(Address address) {
        if (address == null) return null;
        return new AddressDto(
                address.getAddressId(),
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode()
        );
    }

    public Address toAddress(AddressRequestDto dto) {
        if (dto == null) return null;
        return new Address(
                dto.getStreet(),
                dto.getCity(),
                dto.getState(),
                dto.getZipCode()
        );
    }

    // Patient mappings
    public PatientDto toPatientDto(Patient patient) {
        if (patient == null) return null;
        return new PatientDto(
                patient.getPatientId(),
                patient.getPatientNumber(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getPhone(),
                patient.getEmail(),
                patient.getDateOfBirth(),
                toAddressDto(patient.getAddress())
        );
    }

    // Surgery mappings
    public SurgeryDto toSurgeryDto(Surgery surgery) {
        if (surgery == null) return null;
        return new SurgeryDto(
                surgery.getSurgeryId(),
                surgery.getSurgeryNumber(),
                surgery.getSurgeryName(),
                toAddressDto(surgery.getLocation())
        );
    }

    // Dentist mappings
    public DentistDto toDentistDto(Dentist dentist) {
        if (dentist == null) return null;
        return new DentistDto(
                dentist.getDentistId(),
                dentist.getFirstName(),
                dentist.getLastName(),
                dentist.getPhone(),
                dentist.getEmail(),
                dentist.getSpecialization(),
                toSurgeryDto(dentist.getSurgery())
        );
    }

    // Appointment mappings
    public AppointmentDto toAppointmentDto(Appointment appointment) {
        if (appointment == null) return null;
        return new AppointmentDto(
                appointment.getAppointmentId(),
                appointment.getAppointmentDateTime(),
                toPatientDto(appointment.getPatient()),
                toDentistDto(appointment.getDentist()),
                toSurgeryDto(appointment.getSurgery())
        );
    }
}

