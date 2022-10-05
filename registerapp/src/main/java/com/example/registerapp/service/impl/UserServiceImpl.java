package com.example.registerapp.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.registerapp.dto.UserDto;
import com.example.registerapp.entity.Role;
import com.example.registerapp.entity.User;
import com.example.registerapp.repository.RoleRepository;
import com.example.registerapp.repository.UserRepository;
import com.example.registerapp.service.UserService;


@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
	
	@Override
	public void saveUser(UserDto userDto) {
		User user = new User();
		
		user.setName(userDto.getFirstName() + " " + userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		
		Role role = roleRepository.findByName("ROLE_ADMIN");
		
		if(role == null) {
			role = checkRoleExist();
		}
		user.setRoles(Arrays.asList(role));
		userRepository.save(user);

	}

	private Role checkRoleExist() {
		Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
	}

	@Override
	public User findUserByEmail(String email) {
		
		return userRepository.findByEmail(email);
	}

	@Override
	public List<UserDto> findAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream()
					.map((user) -> mapToUserDto(user))
					.collect(Collectors.toList());
	}
	
	private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

}
