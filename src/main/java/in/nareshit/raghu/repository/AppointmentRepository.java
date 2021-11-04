package in.nareshit.raghu.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Serializable> {

	@Query("SELECT appt.date,appt.slots,appt.fee,doctor.email,doctor.mobile,appt.id FROM Appointment appt INNER JOIN appt.doctor as doctor WHERE doctor.id=:docId")
	List<Object[]> getAppointmentByDoctorId(Long docId);

	@Query("SELECT aptm.date, aptm.slots, aptm.fee, aptm.details FROM Appointment aptm INNER JOIN aptm.doctor as doctor WHERE doctor.email=:userName AND aptm.slots>0")
	public List<Object[]> getAppoinmentsByDoctorEmail(String userName);

	@Modifying
	@Query("UPDATE Appointment SET slots = slots + :count WHERE id=:id")
	void updateSlotCountForAppoinment(Long id, int count);

}
