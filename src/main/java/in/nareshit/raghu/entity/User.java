package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "USER_DTLS")
@Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	private Integer uId;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "DISPLAY_NAME")
	private String displayName;
	@Column(name = "ROLE")
	private String role;

}