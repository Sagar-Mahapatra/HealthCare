package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.exception.PatientNotFoundException;
import in.nareshit.raghu.repository.PatientRepository;
import in.nareshit.raghu.service.IpatientService;

@Service
public class PatientServiceImpl implements IpatientService {
	@Autowired
	private PatientRepository patientRepo;

	@Override
	public Long savePatient(Patient patient) {
		return patientRepo.save(patient).getPatinetId();

	}

	@Override
	public List<Patient> getAllPatients() {
		return patientRepo.findAll();

	}

	@Override
	public void deletePatient(Long patientId) {
		Optional<Patient> patient = patientRepo.findById(patientId);

		if (patient.isPresent()) {
			patientRepo.deleteById(patientId);
		} else
			throw new PatientNotFoundException("Patient with Id: " + patientId + ", not exist");
	}

	@Override
	public Patient getPatientById(Long patientId) {

		return patientRepo.findById(patientId).orElseThrow(() -> new PatientNotFoundException());
	}

	@Override
	public void updatePatient(Patient patient) {
		boolean p = patientRepo.existsById(patient.getPatinetId());
		if (p) {
			patientRepo.save(patient);
		} else
			throw new PatientNotFoundException(patient.getPatinetId() + ", not exist");
	}

}
