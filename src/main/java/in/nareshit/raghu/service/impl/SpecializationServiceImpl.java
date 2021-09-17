package in.nareshit.raghu.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Specialization;
import in.nareshit.raghu.repository.SpecializationRepository;
import in.nareshit.raghu.service.ISpecializationService;

@Service
public class SpecializationServiceImpl implements ISpecializationService {

	@Autowired
	private SpecializationRepository repo;

	@Override
	public Long saveSpecialization(Specialization spec) {

		return repo.save(spec).getId();
	}

	@Override
	public List<Specialization> getAllSpecializations() {

		return repo.findAll();
	}

	@Override
	public void removeSpecialization(Long id) {
		repo.deleteById(id);

	}

	@Override
	public Specialization getOneSpecialization(Long id) {
		Optional<Specialization> spec = repo.findById(id);
		if (spec.isPresent()) {
			return spec.get();
		} else {
			return null;
		}

	}

	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);

	}

	@Override
	public boolean isCodeUnique(String specCode) {
		Integer codeCount = repo.codeCount(specCode);
		return (codeCount == 0) ? true : false;
	}

}
