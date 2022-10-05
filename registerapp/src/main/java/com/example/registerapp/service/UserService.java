package com.example.registerapp.service;

import java.util.List;

import com.example.registerapp.dto.UserDto;
import com.example.registerapp.entity.User;

public interface UserService {
	
	void saveUser(UserDto userDto);
	User findUserByEmail(String email);
	List<UserDto> findAllUsers();

}
