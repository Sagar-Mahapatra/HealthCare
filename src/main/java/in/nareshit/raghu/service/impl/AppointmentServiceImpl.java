package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.exception.AppointmentNotFoundException;
import in.nareshit.raghu.repository.AppointmentRepository;
import in.nareshit.raghu.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
	@Autowired
	private AppointmentRepository repo;

	@Override
	public Long saveAppointment(Appointment appointment) {
		Appointment save = repo.save(appointment);
		return save.getId();
	}

	@Override
	public void updateAppointment(Appointment appointment) {
		Optional<Appointment> appntmnt = repo.findById(appointment.getId());
		if (appntmnt.isPresent()) {
			repo.save(appointment);
		} else {
			throw new AppointmentNotFoundException("APPOINTMENT NOT FOUND");
		}

	}

	@Override
	public void deleteAppointment(Long id) {
		Optional<Appointment> appntmnt = repo.findById(id);
		if (appntmnt.isPresent()) {
			repo.deleteById(id);
		} else {
			throw new AppointmentNotFoundException("APPOINTMENT NOT FOUND WITH ID:: " + id);
		}

	}

	@Override
	public Appointment getOneAppointment(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new AppointmentNotFoundException("APPOINTMENT NOT FOUND WITH ID:: " + id));

	}

	@Override
	public List<Appointment> getAllAppointments() {
		return repo.findAll();
	}

	@Override
	public List<Object[]> getAppoinmentsByDoctor(Long id) {
		return repo.getAppointmentByDoctorId(id);
	}

	@Override
	public List<Object[]> getAppoinmentsByDoctorEmail(String userName) {
		return repo.getAppoinmentsByDoctorEmail(userName);
	}

	@Transactional
	public void updateSlotCountForAppoinment(Long id, int count) {
		repo.updateSlotCountForAppoinment(id, count);
	}

}
