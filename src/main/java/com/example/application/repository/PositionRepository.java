package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.model.Position;

public interface PositionRepository extends JpaRepository<Position, Integer>{

}
