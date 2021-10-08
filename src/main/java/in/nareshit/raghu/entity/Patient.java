package in.nareshit.raghu.entity;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "patient_tab")
@Data
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATIENT_ID")
	private long patientId;
	@Column(name = "PATIENT_FIRSTNAME")
	private String patientFirstName;
	@Column(name = "PATIENT_LASTNAME")
	private String patientLastName;
	@Column(name = "PATIENT_GENDER")
	private String patientGender;
	@Column(name = "PATIENT_DOB")
	private LocalDate patientDob;
	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;
	@Column(name = "PRESENT_ADDRESS")
	private String presentAddress;
	@Column(name = "PERMANENT_ADDRESS")
	private String permanentAddress;
	@Column(name = "EMAIL")
	private String email;

	@Column(name = "MEDICAL_HISTORY")
	@ElementCollection
	@CollectionTable(name = "PATIENT_MEDICAL_HISTORY", joinColumns = @JoinColumn(name = "patientId"))
	private Set<String> medicalHistory;

	@Column(name = "OTHER_DETAILS")
	private String otherDetails;
}
