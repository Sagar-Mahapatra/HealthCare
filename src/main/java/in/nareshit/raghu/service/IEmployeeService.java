package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Employee;

public interface IEmployeeService {

	Integer saveEmployee(Employee employee);
	List<Employee> getAllEmployees();

}
