package in.nareshit.raghu.service.impl;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.constants.UserRoles;
import in.nareshit.raghu.entity.Doctor;
import in.nareshit.raghu.entity.User;
import in.nareshit.raghu.exception.DoctorNotFoundException;
import in.nareshit.raghu.repository.DoctorRepository;
import in.nareshit.raghu.service.IDoctorService;
import in.nareshit.raghu.service.IUserService;
import in.nareshit.raghu.utils.MyMailUtil;
import in.nareshit.raghu.utils.PasswordGenerator;

@Service
public class DoctorServiceImpl implements IDoctorService {

	@Autowired
	private DoctorRepository repo;

	@Autowired
	private IUserService userService;

	@Autowired
	private PasswordGenerator passwordGenerator;

	@Autowired
	private MyMailUtil mailUtil;

	@Override
	@Transactional
	public Long saveDoctor(Doctor doc) {
		Long id = repo.save(doc).getId();
		if (id != null) {
			String pwd = passwordGenerator.genPwd();
			User user = new User();
			user.setDisplayName(doc.getFirstName() + " " + doc.getLastName());
			user.setUserName(doc.getEmail());
			user.setPassword(pwd);
			user.setRole(UserRoles.DOCTOR.name());
			Integer genId = userService.saveUser(user);
			if (genId != null)
				new Thread(new Runnable() {
					public void run() {
						String text = "Your uname is " + doc.getEmail() + ", password is " + pwd;
						try {
							mailUtil.send(doc.getEmail(), "DOCTOR ADDED", text);
						} catch (MessagingException e) {
							e.printStackTrace();
						}
					}
				}).start();
		}
		return id;

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