package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.user;
import com.example.demo.repos.userRepository;
import com.example.demo.service.userService;

@RequestMapping("/api/user")
@RestController
@CrossOrigin("*")
public class userController {
	 @Autowired
	    userService userService;
	 @Autowired
	    userRepository userRepository;

	 @PostMapping("/createUser")
	 public ResponseEntity<?> createUser(@RequestBody user newUser) {
	     
	     if (userService.usernameExists(newUser.getUsername())) {
	         return ResponseEntity.status(HttpStatus.CONFLICT)
	                              .body("Error: Username is already in use!");
	     }
	     
	     
	     // If no conflicts, create the user
	     user savedUser = userService.saveUser(newUser);
	     return ResponseEntity.ok(savedUser);
	 }

	    
	   
	    @RequestMapping(path = "allUser" , method = RequestMethod.GET)
	    public List<user> getAllUsers(){
	    	return userService.findAllUsers();
	    }
	    
	    @GetMapping("/all")
	    public List<user> getAll(){
	    	return userService.findAllUsers();
	    }
	    @DeleteMapping("/deleteUser/{id}")
	    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
	
	        if (!userRepository.existsById(id)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Error: User not found!");
	        }
	        
	        userService.deleteUser(id);
	        return ResponseEntity.ok("User deleted successfully!");
	    }
	    @PutMapping("/updateUser/{id}")
	    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody user updatedUser) {
	       
	        if (!userRepository.existsById(id)) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                 .body("Error: User not found!");
	        }

	        
	        user savedUser = userService.updateUser(id, updatedUser);
	        return ResponseEntity.ok(savedUser);
	    }
	    @PutMapping("/deactivateUser/{userId}")
	    public ResponseEntity<?> deactivateUser(@PathVariable Long userId) {
	        userService.deactivateUser(userId);
	        return ResponseEntity.ok("User account deactivated successfully.");
	    }

	    // Activate user 
	    @PutMapping("/activateUser/{userId}")
	    public ResponseEntity<?> activateUser(@PathVariable Long userId) {
	        userService.activateUser(userId);
	        return ResponseEntity.ok("User account activated successfully.");
	    }
	   
	    @GetMapping("/search")
	    public List<user> searchUsername(@RequestParam("name") String username) {
	        return userService.searchByUserName(username);
	    }

	    
}