package edu.miu.cs489.dentalsurgeriesappointments.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dentists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dentist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dentistId;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String specialization;

    @ManyToOne
    @JoinColumn(name = "surgery_id")
    private Surgery surgery;

    @OneToMany(mappedBy = "dentist", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    public Dentist(String firstName, String lastName, String phone, String email,
                   String specialization) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
    }
}

