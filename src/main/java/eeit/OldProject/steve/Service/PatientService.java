package eeit.OldProject.steve.Service;

import eeit.OldProject.steve.Entity.Patient;


import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient createPatient(Patient patient, Long userId);
    Patient updatePatient(Long patientId, Patient patient, Long userId);
    void deletePatient(Long patientId, Long userId);
    List<Patient> getAllPatientsByUserId(Long userId);
    Optional<Patient> getPatientById(Long patientId, Long userId);
}
