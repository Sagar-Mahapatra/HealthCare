package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Patient;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.exception.PatientNotFoundException;
import in.nareshit.raghu.repository.PatientRepository;
import in.nareshit.raghu.service.IPatientService;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.utils.PasswordGenerator;

@Service
public class PatientServiceImpl implements IPatientService {

	Logger log = LoggerFactory.getLogger(PatientServiceImpl.class);

	@Autowired
	private PatientRepository patientRepo;

	@Autowired
	private PasswordGenerator util;

	@Autowired
	private IUserService userService;

	@Override
	@Transactional
	public Long savePatient(Patient patient) {
		Long id = patientRepo.save(patient).getPatientId();
		if (id != null) {
			String pwd = util.genPwd();
			User user = new User();
			user.setDisplayName(patient.getPatientFirstName() + " " + patient.getPatientLastName());
			user.setUserName(patient.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.PATIENT.name());
			userService.saveUser(user);
			log.info("Your name is " + patient.getEmail() + ", password is " + pwd);
			/*
			 * if(genId!=null) new Thread(new Runnable() { public void run() { String text =
			 * "Your name is " + patient.getEmail() +", password is "+ pwd;
			 * mailUtil.send(patient.getEmail(), "PATIENT ADDED", text); } }).start();
			 */
		}
		return id;
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
		boolean p = patientRepo.existsById(patient.getPatientId());
		if (p) {
			patientRepo.save(patient);
		} else
			throw new PatientNotFoundException(patient.getPatientId() + ", not exist");
	}

	@Override
	public Patient getOneByEmail(String email) {
		Optional<Patient> opt = patientRepo.findByEmail(email);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new PatientNotFoundException("PATIENT NOT FOUND WITH THIS EMAIL:: " + email);
		}
	}

}
