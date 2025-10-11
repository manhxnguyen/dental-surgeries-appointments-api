package edu.miu.cs489.dentalsurgeriesappointments.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "surgeries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Surgery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surgeryId;

    private String surgeryNumber;
    private String surgeryName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address location;

    @OneToMany(mappedBy = "surgery", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Dentist> dentists = new ArrayList<>();

    @OneToMany(mappedBy = "surgery", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();

    public Surgery(String surgeryNumber, String surgeryName, Address location) {
        this.surgeryNumber = surgeryNumber;
        this.surgeryName = surgeryName;
        this.location = location;
    }
}

