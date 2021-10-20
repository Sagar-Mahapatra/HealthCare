package in.nareshit.raghu.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Serializable> {

	@Query("SELECT appt.date,appt.slots,appt.fee,doctor.email,doctor.mobile FROM Appointment appt INNER JOIN appt.doctor as doctor WHERE doctor.id=:docId")
	List<Object[]> getAppointmentByDoctorId(Long docId);

}
