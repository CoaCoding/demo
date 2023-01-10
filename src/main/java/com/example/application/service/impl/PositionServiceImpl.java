package com.example.application.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.model.Position;
import com.example.application.repository.PositionRepository;
import com.example.application.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService{
	
	@Autowired
	public PositionRepository positionRepository;

	@Override
	public List<Position> findAllPositions() {
		// TODO Auto-generated method stub
		return positionRepository.findAll();
	}
	
	

}
