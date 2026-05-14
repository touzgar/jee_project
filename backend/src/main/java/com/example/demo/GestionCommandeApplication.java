package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.model.role;
import com.example.demo.model.user;
import com.example.demo.service.userService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
@EnableScheduling


@SpringBootApplication
public class GestionCommandeApplication {
	 @Autowired
	    userService userService;
	/*@PostConstruct
	void init_users() {
		//ajouter les rôles
		role r1 = new role();
		r1.setRole("ADMIN");
		userService.addRole(r1);
		
		role r2 = new role();
		r2.setRole("USER");
		userService.addRole(r2);
		        
		//ajouter les users
		user u1 = new user();
		u1.setUsername("admin");
		u1.setPassword("123");
		u1.setEnabled(true);
		u1.setEmail("admin@example.com");
		userService.saveUser(u1);
		
		user u2 = new user();
		u2.setUsername("user1");
		u2.setPassword("123");
		u2.setEnabled(true);
		u2.setEmail("user1@example.com");
		userService.saveUser(u2);
		
		//ajouter les rôles aux users
		userService.addRoleToUser("admin", "ADMIN");
		userService.addRoleToUser("user1", "USER");
	}
*/
	public static void main(String[] args) {
		SpringApplication.run(GestionCommandeApplication.class, args);
	}

}
