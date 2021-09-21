package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Patient;

public interface IpatientService {

	public Long savePatient(Patient patient);

	public List<Patient> getAllPatients();

	public void deletePatient(Long patientId);

	public Patient getPatientById(Long patientId);

	public void updatePatient(Patient patient);

}
