package com.example.application.service;

import java.util.List;
import java.util.Optional;

import com.example.application.model.Employee;

public interface EmployeeService {

	public Optional<Employee> get(Integer id);

	public Employee save(Employee entity);

	public Employee update(Employee entity);

	public void delete(Employee employee);

	public List<Employee> findAllEmployees(String searchField);

}
