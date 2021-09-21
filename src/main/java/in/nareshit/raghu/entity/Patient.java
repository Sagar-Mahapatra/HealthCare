package in.nareshit.raghu.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "patient_tab")
@Data
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Patient_Id")
	private long patinetId;
	@Column(name = "Patient_FirstName")
	private String patientFirstName;
	@Column(name = "Patient_LastName")
	private String patientLastName;
	@Column(name = "Patient_Gender")
	private String patientGender;
	@Column(name = "Patient_DOB")
	private LocalDate patientDob;
	@Column(name = "Marital_Status")
	private String maritalStatus;
	@Column(name = "Present_Address")
	private String presentAddress;
	@Column(name = "PermanentAddress")
	private String permanentAddress;
	@Column(name = "Medical_History")
	private String medicalHistory;
	@Column(name = "Other_Details")
	private String otherDetails;
}
