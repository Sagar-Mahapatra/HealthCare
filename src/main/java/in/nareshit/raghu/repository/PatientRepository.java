package in.nareshit.raghu.repository;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nareshit.raghu.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Serializable> {

	Optional<Patient> findByEmail(String email);

}
