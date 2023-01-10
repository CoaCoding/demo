package com.example.application.service;

import java.util.Optional;
import com.example.application.model.User;

public interface UserService {

	public Optional<User> get(Long id);

	public User update(User entity);

	public void delete(Long id);

	public int count();

}
