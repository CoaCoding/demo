package com.example.application.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "position")
@SequenceGenerator(name = "position_id_gen", sequenceName = "position_id_seq", allocationSize = 50)
public class Position {

	@Id
	@Column(name = "position_id")
	@GeneratedValue(generator = "position_id_gen")
	private Integer position_id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "position")
	private List<Employee> position = new LinkedList<>();

	public Position() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return name;
	}

	public Position(Integer position_id, String name, List<Employee> position) {
		super();
		this.position_id = position_id;
		this.name = name;
		this.position = position;
	}

	public void setEmployee_position(List<Employee> position) {
		this.position = position;
	}

	public Integer getPosition_id() {
		return position_id;
	}

	public void setPosition_id(Integer position_id) {
		this.position_id = position_id;
	}

	public String getPosition_name() {
		return name;
	}

	public void setPosition_name(String name) {
		this.name = name;
	}

	public List<Employee> getPosition() {
		return position;
	}

	public void setPosition(List<Employee> position) {
		this.position = position;
	}

}
