package in.nareshit.raghu.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.exception.SpecNotFoundException;
import in.nareshit.raghu.repository.SpecializationRepository;
import in.nareshit.raghu.service.ISpecializationService;
import in.nareshit.raghu.utils.ListToMapConverter;

@Service
public class SpecializationServiceImpl implements ISpecializationService {

	@Autowired
	private SpecializationRepository repo;

	@Override
	public Long saveSpecialization(Specialization spec) {

		return repo.save(spec).getId();
	}

	@Override
	public Map<Long, String> getAllSpecializations() {
		List<Specialization> list = repo.findAll();

		List<Specialization> sortedList = list.stream().sorted(Comparator.comparing(Specialization::getSpecCode))
				.collect(Collectors.toList());
		return sortedList.stream().collect(Collectors.toMap(Specialization::getId, Specialization::getSpecName));
	}

	@Override
	public void removeSpecialization(Long id) {
		Optional<Specialization> spec = repo.findById(id);

		if (spec.isPresent()) {
			repo.deleteById(id);
		} else {
			throw new SpecNotFoundException("Specialization not found with this id: " + id);
		}

	}

	@Override
	public Specialization getOneSpecialization(Long id) {

		return repo.findById(id)
				.orElseThrow(() -> new SpecNotFoundException("Specialization not found with this id: " + id));

	}

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);

	}

	@Override
	public boolean isCodeUnique(String specCode) {

		return repo.findAll().stream().filter(a -> a.getSpecCode().equals(specCode)).count() == 0;
	}

	@Override
	public boolean isNameUnique(String name) {
		return repo.findAll().stream().filter(a -> a.getSpecName().equals(name)).count() == 0;
	}

	@Override
	public boolean isCodeUniqueForEdit(String specCode, Long id) {

		return repo.codeCountForEdit(specCode, id) == 0;
	}

	@Override
	public boolean isNameUniqueForEdit(String specCode, Long id) {

		return repo.nameCountForEdit(specCode, id) == 0;
	}

	@Override
	public List<Specialization> getSpecializationsList() {
		List<Specialization> list = repo.findAll();
		Collections.sort(list, Comparator.comparing(Specialization::getSpecName));
		return list;
	}

	@Override
	public Map<Long, String> getSpecIdAndName() {
		List<Object[]> list = repo.getSpecIdAndName();
		Map<Long, String> map = ListToMapConverter.convertListOfObjectArrayToMap(list);
		return map;
	}

}
