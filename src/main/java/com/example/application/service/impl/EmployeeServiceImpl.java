package com.example.application.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.model.Employee;
import com.example.application.repository.EmployeeRepository;
import com.example.application.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Optional<Employee> get(Integer id) {
		return employeeRepository.findById(id);
	}

	public Employee update(Employee entity) {
		return employeeRepository.save(entity);
	}

	public void delete(Employee employee) {
		employeeRepository.delete(employee);
	}

	@Override
	public Employee save(Employee entity) {
		// TODO Auto-generated method stub
		return employeeRepository.save(entity);
	}
	
	@Override
	public List<Employee> findAllEmployees(String searchString) {
		// TODO Auto-generated method stub
		if (searchString.isEmpty() || searchString == null) {
			return employeeRepository.findAll();

		} else {
			return employeeRepository.search(searchString);
		}

	}

}
