package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.role;
import com.example.demo.model.user;
import com.example.demo.repos.userRepository;

import javax.transaction.Transactional;

import com.example.demo.repos.roleRepository;
@Transactional
@Service
public class userServiceImpl implements userService {
	@Autowired
	userRepository userRepository;
	@Autowired
	roleRepository roleRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public user saveUser(user user) {
		  user.setEnabled(true);
	        String rawPassword = user.getPassword();
	        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));  // Encrypt the password
	        user savedUser = userRepository.save(user);
	        return savedUser;
	}


	@Override
	public user findUserById(Long id) {
		
		return userRepository.findById(id).get();
	}

	@Override
	public user findUserByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}

	@Override
	public role addRole(role role) {
		return roleRepository.save(role);
	}

	@Override
	public user addRoleToUser(String username, String roleName) {
	    user user = userRepository.findByUsername(username);
	    
	    // Fetch the role from the database to ensure it's managed
	    role role = roleRepository.findByRole(roleName);
	    
	    if (role != null) {
	        user.getRoles().add(role);
	        userRepository.save(user); // Save the user entity with updated roles
	    }
	    
	    return user;
	}




	@Override
	public List<user> findAllUsers() {
		
		return userRepository.findAll();
	}
	@Override
	public boolean usernameExists(String username) {
	    return userRepository.findByUsername(username) != null;
	}

	@Override
	public boolean emailExists(String email) {
	    return userRepository.findByEmail(email) != null;
	}

	


	@Override
	public void deleteUser(Long userId) {
		 userRepository.deleteById(userId);
		
	}
	
	@Override
	public user updateUser(Long id, user updatedUser) {
	    // Check if the user exists
	    user existingUser = userRepository.findById(id).orElse(null);
	    
	    if (existingUser == null) {
	        return null; 
	    }

	    // Update the fields you want to allow changing
	    existingUser.setUsername(updatedUser.getUsername());
	    existingUser.setEmail(updatedUser.getEmail());
	    existingUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword())); // Encrypt the new password

	    // If you want to update roles, you can clear the existing roles and set new ones
	    existingUser.getRoles().clear();
	    existingUser.getRoles().addAll(updatedUser.getRoles());

	    
	    user savedUser = userRepository.save(existingUser);

	  
		    return savedUser;
	}


	@Override
	public void deactivateUser(Long userId) {
	    user existingUser = userRepository.findById(userId).orElse(null);
	    
	    if (existingUser != null) {
	        existingUser.setEnabled(false); 
	        userRepository.save(existingUser); 
	    }
	}

	@Override
	public void activateUser(Long userId) {
	    user existingUser = userRepository.findById(userId).orElse(null);
	    
	    if (existingUser != null) {
	        existingUser.setEnabled(true); 
	        userRepository.save(existingUser); 
	    }
	}


	@Override
	public List<user> searchByUserName(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsernameContaining(username);
	}



}

