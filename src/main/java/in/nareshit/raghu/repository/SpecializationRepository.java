package in.nareshit.raghu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.nareshit.raghu.entity.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

	@Query("select count(specCode) from Specialization where specCode=:code and id!=:id")
	public Integer codeCountForEdit(String code, Long id);

	@Query("select count(specName) from Specialization where specName=:name and id!=:id")
	public Integer nameCountForEdit(String name, Long id);
}
