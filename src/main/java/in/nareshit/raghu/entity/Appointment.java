package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "P_NAME")
	private String patientName;
	@Column(name = "P_CONTACT")
	private String patientContact;
	@Column(name = "TIME")
	private String appointmentTime;
	@Column(name = "MSG")
	private String message;

	@OneToOne
	@JoinColumn(name = "DOC_ID_FK_COL")
	private Doctor doctor;
}
