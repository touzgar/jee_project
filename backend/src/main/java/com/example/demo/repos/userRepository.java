package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.user;

public interface userRepository extends JpaRepository<user, Long> {
 	user findByUsername(String username);
	// test
 	List<user> findByUsernameContaining(String username);
 	user findByEmail(String email);
 	
}
