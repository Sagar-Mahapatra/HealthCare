package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Appointment;

public interface IAppointmentService {

	Long saveAppointment(Appointment appointment);

	void updateAppointment(Appointment appointment);

	void deleteAppointment(Long id);

	Appointment getOneAppointment(Long id);

	List<Appointment> getAllAppointments();

	List<Object[]> getAppoinmentsByDoctor(Long id);

}
