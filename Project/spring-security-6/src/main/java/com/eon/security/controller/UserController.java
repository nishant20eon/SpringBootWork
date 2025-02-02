package com.eon.security.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eon.security.entity.User;
import com.eon.security.repo.UserRepository;
import com.eon.security.service.UserService;


@RestController
@RequestMapping("/user")

//  http://localhost:8080/user/

public class UserController {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserService userService;

	
	/**
	 * http://localhost:8080/user/register
	 *     
     {
        "id": 2,
        "userName": "",
        "password": ""
     }
	 */
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return userService.register(user);
	}
	
	@PostMapping("/login")
	//http://localhost:8080/user/getAllUser
	// ashish - ashish
	public String login(@RequestBody User user) {
		var u = userRepo.findByUserName(user.getUserName());
		return (!Objects.isNull(u)) ? "Sucess" : "failure";
	}
	
	@PostMapping("/jwt/login")
	//http://localhost:8080/user/getAllUser
	// ashish - ashish
	public String loginJWT(@RequestBody User user) {
		String token = userService.verify(user);
		System.out.println("Controller : "+token);
		return token;
	}
	
	@GetMapping("/getAllUser")
	public List<User> getUserDetails() {
		 List<User> all = userRepo.findAll();
		 System.out.println(all);
		return all;
	}
	
//	@DeleteMapping("/delete")
//	//[User [id=1, userName=nishant, password=Eon@9934]]
//	public void getUserDetails1() {
//		 userRepo.deleteById(1);
//	}
	


}
