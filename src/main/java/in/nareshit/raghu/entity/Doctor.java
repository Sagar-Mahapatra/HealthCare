package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

	@Id
	@Column(name = "doctor_id")
	@GeneratedValue
	private Integer doctorId;
}