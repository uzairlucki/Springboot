package com.dps.crud.controller;

import com.dps.crud.model.Employee;
import com.dps.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	// POST http://localhost:8080/api/employees
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		log.info("Received request to create employee: {}", employee);
		Employee createdEmployee = employeeService.createEmployee(employee);
		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
	}

	// GET http://localhost:8080/api/employees
	@GetMapping
	public ResponseEntity<List<Employee>> getAllEmployees() {
		log.info("Received request to get all employees.");
		List<Employee> employees = employeeService.getAllEmployees();
		return ResponseEntity.ok(employees); // Returns 200 OK status
	}

	// GET http://localhost:8080/api/employees/{id}
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		log.info("Received request to get employee with ID: {}", id);
		Employee employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee); // Returns 200 OK status
	}

	// PUT http://localhost:8080/api/employees/{id}
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		log.info("Received request to update employee with ID: {}. Details: {}", id, employeeDetails);
		Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
		return ResponseEntity.ok(updatedEmployee); // Returns 200 OK status
	}

	// DELETE http://localhost:8080/api/employees/{id}
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
		log.info("Received request to delete employee with ID: {}", id);
		employeeService.deleteEmployee(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns 204 No Content status
	}
}