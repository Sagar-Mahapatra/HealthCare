package in.nareshit.raghu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Appointment;
import in.nareshit.raghu.repository.AppointmentRepository;
import in.nareshit.raghu.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
	@Autowired
	private AppointmentRepository repo;

	@Override
	public String saveAppointment(Appointment appointment) {
		Appointment save = repo.save(appointment);
		if (save != null) {
			return "Your Appointment saved successfully on " + save.getAppointmentTime();
		}
		return "Something went WRONG!!!";
	}

}
