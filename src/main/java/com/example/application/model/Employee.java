package com.example.application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "employee")
@SequenceGenerator(name = "employee_id_gen", sequenceName = "employee_id_seq", allocationSize = 50)
public class Employee {

	@Id
	@GeneratedValue(generator = "employee_id_gen")
	@Column(name = "id")
	private Integer id;

	@NotEmpty(message = "Dieses Feld darf nicht leer sein")
	@Column(name = "firstName")
	private String firstName = "";

	@NotEmpty(message = "Dieses Feld darf nicht leer sein")
	@Column(name = "lastName")
	private String lastName = "";

	@ManyToOne
	@JoinColumn(name = "position_id")
	@NotNull(message = "Dieses Feld darf nicht leer sein")
	@JsonIgnoreProperties({ "position" })
	private Position position;

	@Email(message = "Geben Sie die Email im richtigen Format")
	@NotEmpty(message = "Dieses Feld darf nicht leer sein")
	private String email = "";

	@Override
	public String toString() {
		return "Employee [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
	}

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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
