package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.exception.ApplicationError;
import in.nareshit.raghu.exception.DoctorNotFoundException;
import in.nareshit.raghu.repository.DoctorRepository;
import in.nareshit.raghu.repository.UserRepository;
import in.nareshit.raghu.service.IDoctorService;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private DoctorRepository repo;

	@Autowired
	private UserRepository userRepo;

	@Override
	@Transactional
	public Long saveDoctor(Doctor doc) {
		Long id = repo.save(doc).getId();
		if (id != null) {
			userRepo.save(new User(null, doc.getEmail(), doc.getFirstName() + " " + doc.getLastName(), "DOCTOR"));
			return id;
		} else {
			throw new ApplicationError("SOMETHONG WENT WRONG!!! PLEASE TRY AGAIN");
		}

	}

	@Override
	public List<Doctor> getAllDoctors() {
		return repo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		repo.delete(getOneDoctor(id));
	}

	@Override
	public Doctor getOneDoctor(Long id) {
		return repo.findById(id).orElseThrow(() -> new DoctorNotFoundException(id + ", not exist"));
	}

	@Override
	public void updateDoctor(Doctor doc) {
		if (repo.existsById(doc.getId()))
			repo.save(doc);
		else
			throw new DoctorNotFoundException(doc.getId() + ", not exist");
	}

}