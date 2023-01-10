package com.example.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.application.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query(value = "SELECT * FROM proba.employee WHERE proba.employee.first_name LIKE %?1% OR proba.employee.last_name LIKE %?1%", nativeQuery = true)
	public List<Employee> search(@Param(value = "search") String search);

}
