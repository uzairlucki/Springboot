// src/main/java/com/dps/crud/controller/EmployeeController.java
package com.dps.crud.controller;

import com.dps.crud.model.Employee;
import com.dps.crud.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// Import your Employee model if not already imported
// import com.dps.crud.model.Employee;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = {"http://localhost:5171", "http://localhost:3000"}, allowCredentials = "true") // Ensure your frontend origins are here
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping // This method will handle lazy loading requests
	public ResponseEntity<Page<Employee>> getAllEmployees(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(required = false) String sort,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String position
	) {

		Sort sortObj = Sort.unsorted();
		if (sort != null && !sort.isEmpty()) {
			String[] sortParts = sort.split(",");
			String sortField = sortParts[0];
			Sort.Direction sortDirection = sortParts.length > 1 && sortParts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
			sortObj = Sort.by(sortDirection, sortField);
		}


		Pageable pageable = PageRequest.of(page, size, sortObj);

		Page<Employee> employeesPage;

		if (search != null && !search.isEmpty()) {
			employeesPage = employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPositionContainingIgnoreCase(
					search, search, search, search, pageable );
		} else if (firstName != null && !firstName.isEmpty()) {
			employeesPage = employeeRepository.findByFirstNameContainingIgnoreCase(firstName, pageable);
		} else if (lastName != null && !lastName.isEmpty()) {
			employeesPage = employeeRepository.findByLastNameContainingIgnoreCase(lastName, pageable);
		} else if (email != null && !email.isEmpty()) {
			employeesPage = employeeRepository.findByEmailContainingIgnoreCase(email, pageable);
		} else if (position != null && !position.isEmpty()) {
			employeesPage = employeeRepository.findByPositionContainingIgnoreCase(position, pageable);
		}
		else {
			employeesPage = employeeRepository.findAll(pageable);
		}

		return ResponseEntity.ok(employeesPage);
	}

	//  Create Employee
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee savedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(savedEmployee);
	}

	// Update Employee
	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
		return employeeRepository.findById(id)
				.map(employee -> {
					employee.setFirstName(employeeDetails.getFirstName());
					employee.setLastName(employeeDetails.getLastName());
					employee.setEmail(employeeDetails.getEmail());
					employee.setPosition(employeeDetails.getPosition());
					employee.setSalary(employeeDetails.getSalary());
					// Update other fields as necessary
					Employee updatedEmployee = employeeRepository.save(employee);
					return ResponseEntity.ok(updatedEmployee);
				})
				.orElse(ResponseEntity.notFound().build());
	}

	// Delete Employee
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
		return employeeRepository.findById(id)
				.map(employee -> {
					employeeRepository.delete(employee);
					return ResponseEntity.ok().build();
				})
				.orElse(ResponseEntity.notFound().build());
	}
	@PostMapping("/batch-delete")
	public ResponseEntity<?> deleteMultipleEmployees(@RequestBody Long[] ids) {
		for (Long id : ids) {
			employeeRepository.deleteById(id);
		}
		return ResponseEntity.ok().build();
	}
}
//package com.dps.crud.controller;
//
//import com.dps.crud.model.Employee;
//import com.dps.crud.service.EmployeeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/employees")
//@Slf4j
//@CrossOrigin(origins = {"http://localhost:5171", "http://localhost:3000"})
//public class EmployeeController {
//
//	@Autowired
//	private EmployeeService employeeService;
//
//
//	@GetMapping("/checkAuthStatus")
//	public ResponseEntity<String> checkAuthStatus() {
//		return ResponseEntity.ok("Authenticated");
//	}
//	// POST http://localhost:8080/api/employees
//	@PostMapping
//	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
//		log.info("Received request to create employee: {}", employee);
//		Employee createdEmployee = employeeService.createEmployee(employee);
//		return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
//	}
//
//	// GET http://localhost:8080/api/employees
//	@GetMapping
//	public ResponseEntity<List<Employee>> getAllEmployees() {
//		log.info("Received request to get all employees.");
//		List<Employee> employees = employeeService.getAllEmployees();
//		return ResponseEntity.ok(employees); // Returns 200 OK status
//	}
//
//	// GET http://localhost:8080/api/employees/{id}
//	@GetMapping("/{id}")
//	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
//		log.info("Received request to get employee with ID: {}", id);
//		Employee employee = employeeService.getEmployeeById(id);
//		return ResponseEntity.ok(employee); // Returns 200 OK status
//	}
//
//	// PUT http://localhost:8080/api/employees/{id}
//	@PutMapping("/{id}")
//
//	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
//		log.info("Received request to update employee with ID: {}. Details: {}", id, employeeDetails);
//		Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);
//		return ResponseEntity.ok(updatedEmployee); // Returns 200 OK status
//	}
//
//	// DELETE http://localhost:8080/api/employees/{id}
//	@DeleteMapping("/{id}")
//
//	public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable Long id) {
//		log.info("Received request to delete employee with ID: {}", id);
//		employeeService.deleteEmployee(id);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns 204 No Content status
//	}
//}