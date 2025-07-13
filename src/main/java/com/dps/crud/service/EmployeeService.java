package com.dps.crud.service;

import com.dps.crud.model.Employee;
import com.dps.crud.repository.EmployeeRepository;
import com.dps.crud.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	// --- CREATE Operation ---
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee); // Saves a new employee to the database
	}

	// --- READ Operations ---
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll(); // Retrieves all employees
	}

	public Employee getEmployeeById(Long id) {
		// findById returns an Optional, use orElseThrow to handle not found cases
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
	}

	// --- UPDATE Operation ---
	public Employee updateEmployee(Long id, Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		// Update existing employee's details
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setPosition(employeeDetails.getPosition());
		employee.setSalary(employeeDetails.getSalary());

		return employeeRepository.save(employee); // Saves the updated employee
	}

	// --- DELETE Operation ---
	public void deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

		employeeRepository.delete(employee);
	}
}