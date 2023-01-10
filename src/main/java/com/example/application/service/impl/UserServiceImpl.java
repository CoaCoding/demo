package com.example.application.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import com.example.application.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	public Optional<User> get(Long id) {
		return userRepository.findById(id);
	}

	public User update(User entity) {
		return userRepository.save(entity);
	}

	public void delete(Long id) {
		userRepository.deleteById(id);
	}

	public int count() {
		return (int) userRepository.count();
	}

}
