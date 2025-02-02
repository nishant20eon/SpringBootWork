package com.eon.security.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eon.security.entity.User;
import com.eon.security.repo.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTService jwtService;
	
	private String token;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	public User register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}


	public String verify(User user) {
	    // Authenticate the user based on username and password.
	    // Since we store an encrypted password in the database using BCryptPasswordEncoder,
	    // authenticationManager will automatically compare the user-provided password 
	    // (in plain text) with the stored hashed password.

	    Authentication authenticate = authenticationManager
	        .authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

	    // Note: We are not manually checking the password in the database.
	    // The authenticationManager handles it by fetching the stored hash and verifying it.
	    // var u = userRepo.findByUserName(user.getUserName());

	    if (authenticate.isAuthenticated()) {
	        // Hardcoded token (used previously for testing)
	        // String token = "67784678126348124812348";

	        // Now, we are dynamically generating a JWT token using jwtService.generateToken().
	        // This requires jjwt dependencies (api, impl, jackson) and a proper JWTService implementation.
	        System.out.println("Service before token generation: " + token);

	        token = jwtService.generateToken(user);  // Generating JWT token for authenticated user

	        System.out.println("Service after token generation: " + token);
	        return token;  // Returning the generated JWT token
	    } else {
	        return "Failure";  // If authentication fails, return failure message
	    }
	}

	
	
	

}
