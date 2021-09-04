package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
public class Doctor {

	@Id
	@Column(name = "doctor_id")
	@GeneratedValue
	private Integer doctorId;
}