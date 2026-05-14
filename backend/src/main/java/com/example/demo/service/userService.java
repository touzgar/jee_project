package com.example.demo.service;

import java.util.List;

import com.example.demo.model.role;
import com.example.demo.model.user;

public interface userService {
	user saveUser(user user);
	user findUserById(Long id);
	user findUserByUsername(String username);
	role addRole(role role);
	user addRoleToUser(String username,String roleName);
	List<user> findAllUsers();
	boolean usernameExists(String username);
	boolean emailExists(String email);
	void deleteUser(Long userId);
	user updateUser(Long id, user updatedUser);
	void deactivateUser(Long userId);
	void activateUser(Long userId);
	 List<user> searchByUserName(String username);
}
