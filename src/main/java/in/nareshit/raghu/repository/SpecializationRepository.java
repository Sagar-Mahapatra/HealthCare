package in.nareshit.raghu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

	@Query("select count(specCode) from Specialization where specCode=:code and id!=:id")
	public Integer codeCountForEdit(String code, Long id);

	@Query("select count(specName) from Specialization where specName=:name and id!=:id")
	public Integer nameCountForEdit(String name, Long id);

	@Query("SELECT id,specName FROM Specialization")
	List<Object[]> getSpecIdAndName();
}
