// src/main/java/com/dps/crud/repository/EmployeeRepository.java
package com.dps.crud.repository;

import com.dps.crud.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPositionContainingIgnoreCase(
			String firstName, String lastName, String email, String position, Pageable pageable
	);

	// Methods for specific column filters
	Page<Employee> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
	Page<Employee> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
	Page<Employee> findByEmailContainingIgnoreCase(String email, Pageable pageable);
	Page<Employee> findByPositionContainingIgnoreCase(String position, Pageable pageable);
	// Add similar methods for other filterable fields like salary (e.g., findBySalaryGreaterThanEqual)
}