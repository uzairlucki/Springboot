package com.dps.crud.model;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
@Table(name = "employees")
public class Employee {

	@Id // Marks this field as the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments ID, suitable for MSSQL
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String position; // e.g., "Software Engineer", "HR Manager"
	private double salary;
	@CreatedDate
	@Column(nullable = false, updatable = false, name ="created_date" )

	private LocalDateTime createdDate;

	@LastModifiedDate
	@Column(nullable = false)
	private LocalDateTime modifiedDate;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
