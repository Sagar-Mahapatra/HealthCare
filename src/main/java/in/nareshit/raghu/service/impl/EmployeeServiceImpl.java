package in.nareshit.raghu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nareshit.raghu.entity.Employee;
import in.nareshit.raghu.repository.EmployeeRepository;
import in.nareshit.raghu.service.IEmployeeService;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository repo;

	@Override
	public Integer saveEmployee(Employee employee) {

		return repo.save(employee).getId();
	}

	public List<Employee> getAllEmployees() {
		List<Employee> list = repo.findAll();
		return list;
	}

	@Override
	public void deleteEmployee(Integer id) {
		repo.deleteById(id);

	}

}
