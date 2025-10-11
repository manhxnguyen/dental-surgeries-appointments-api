package edu.miu.cs489.dentalsurgeriesappointments.service;

import edu.miu.cs489.dentalsurgeriesappointments.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Order(1)
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

        private final PatientService patientService;
        private final DentistService dentistService;
        private final SurgeryService surgeryService;
        private final AppointmentService appointmentService;
        private final AddressService addressService;

        @Override
        @Transactional
        public void run(String... args) throws Exception {
                System.out.println("\n========== Starting Data Loading ==========\n");

                // Create Surgeries with Addresses (let cascade handle addresses)
                Address surgery15Address = new Address("123 Main St", "Fairfield", "IA", "52556");
                Surgery surgery15 = surgeryService
                                .addNewSurgery(new Surgery("S15", "Dental Care Clinic", surgery15Address));

                Address surgery10Address = new Address("456 Oak Ave", "Fairfield", "IA", "52556");
                Surgery surgery10 = surgeryService.addNewSurgery(new Surgery("S10", "Smile Dental", surgery10Address));

                Address surgery13Address = new Address("789 Elm St", "Fairfield", "IA", "52557");
                Surgery surgery13 = surgeryService
                                .addNewSurgery(new Surgery("S13", "Family Dentistry", surgery13Address));

                System.out.println("✓ Created " + surgeryService.getAllSurgeries().size() + " surgeries");

                // Create Dentists
                Dentist tonySmith = new Dentist("Tony", "Smith", "555-1234", "tony.smith@dental.com",
                                "General Dentistry");
                tonySmith.setSurgery(surgery15);
                tonySmith = dentistService.addNewDentist(tonySmith);

                Dentist helenPearson = new Dentist("Helen", "Pearson", "555-2345", "helen.pearson@dental.com",
                                "Orthodontics");
                helenPearson.setSurgery(surgery10);
                helenPearson = dentistService.addNewDentist(helenPearson);

                Dentist robinPlevin = new Dentist("Robin", "Plevin", "555-3456", "robin.plevin@dental.com",
                                "Pediatric Dentistry");
                robinPlevin.setSurgery(surgery15);
                robinPlevin = dentistService.addNewDentist(robinPlevin);

                System.out.println("✓ Created " + dentistService.getAllDentists().size() + " dentists");

                // Create Patients with Addresses (let cascade handle addresses)
                Address gillianAddress = new Address("101 Park Ave", "Fairfield", "IA", "52556");
                Patient gillianWhite = patientService.addNewPatient(new Patient("P100", "Gillian", "White", "555-4567",
                                "gillian.white@email.com", LocalDate.of(1985, 3, 15), gillianAddress));

                Address jillAddress = new Address("202 Cedar Ln", "Fairfield", "IA", "52556");
                Patient jillBell = patientService.addNewPatient(new Patient("P105", "Jill", "Bell", "555-5678",
                                "jill.bell@email.com", LocalDate.of(1990, 7, 22), jillAddress));

                Address ianAddress = new Address("303 Maple Dr", "Fairfield", "IA", "52557");
                Patient ianMacKay = patientService.addNewPatient(new Patient("P108", "Ian", "MacKay", "555-6789",
                                "ian.mackay@email.com", LocalDate.of(1978, 11, 8), ianAddress));

                Address johnAddress = new Address("404 Pine St", "Fairfield", "IA", "52556");
                Patient johnWalker = patientService.addNewPatient(new Patient("P110", "John", "Walker", "555-7890",
                                "john.walker@email.com", LocalDate.of(1982, 5, 30), johnAddress));

                System.out.println("✓ Created " + patientService.getAllPatients().size() + " patients");

                // Create Appointments based on sample data
                // Tony Smith with Gillian White on 12-Sep-13 at 10:00 at S15
                appointmentService.addNewAppointment(new Appointment(
                                LocalDateTime.of(2013, 9, 12, 10, 0),
                                gillianWhite, tonySmith, surgery15));

                // Tony Smith with Jill Bell on 12-Sep-13 at 12:00 at S15
                appointmentService.addNewAppointment(new Appointment(
                                LocalDateTime.of(2013, 9, 12, 12, 0),
                                jillBell, tonySmith, surgery15));

                // Helen Pearson with Ian MacKay on 12-Sep-13 at 10:00 at S10
                appointmentService.addNewAppointment(new Appointment(
                                LocalDateTime.of(2013, 9, 12, 10, 0),
                                ianMacKay, helenPearson, surgery10));

                // Helen Pearson with Ian MacKay on 14-Sep-13 at 14:00 at S10
                appointmentService.addNewAppointment(new Appointment(
                                LocalDateTime.of(2013, 9, 14, 14, 0),
                                ianMacKay, helenPearson, surgery10));

                // Robin Plevin with Jill Bell on 14-Sep-13 at 16:30 at S15
                appointmentService.addNewAppointment(new Appointment(
                                LocalDateTime.of(2013, 9, 14, 16, 30),
                                jillBell, robinPlevin, surgery15));

                // Robin Plevin with John Walker on 15-Sep-13 at 18:00 at S13
                appointmentService.addNewAppointment(new Appointment(
                                LocalDateTime.of(2013, 9, 15, 18, 0),
                                johnWalker, robinPlevin, surgery13));

                System.out.println("✓ Created " + appointmentService.getAllAppointments().size() + " appointments");

                System.out.println("\n========== Data Loading Complete ==========\n");
        }
}
