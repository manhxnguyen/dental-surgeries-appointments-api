package edu.miu.cs489.dentalsurgeriesappointments.exception;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(String message) {
        super(message);
    }
}

