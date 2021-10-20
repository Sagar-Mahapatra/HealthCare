package in.nareshit.raghu.service;

import java.util.List;
import java.util.Map;

import in.nareshit.raghu.entity.Specialization;

public interface ISpecializationService {

	public Long saveSpecialization(Specialization spec);

	public Map<Long, String> getAllSpecializations();

	public void removeSpecialization(Long id);

	public Specialization getOneSpecialization(Long id);

	public void updateSpecialization(Specialization spec);

	public boolean isCodeUnique(String specCode);

	public boolean isCodeUniqueForEdit(String specCode, Long id);

	public boolean isNameUnique(String name);

	public boolean isNameUniqueForEdit(String specCode, Long id);

	public List<Specialization> getSpecializationsList();

	Map<Long, String> getSpecIdAndName();
}
