package com.eon.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfig is the configuration class for setting up web security.
 * It includes the setup for authentication, authorization, password encoding, 
 * and the security filter chain for handling incoming HTTP requests.
 */
@Configuration
@EnableWebSecurity  // This annotation enables Spring Security's web security features
public class WebSecurityConfig {

    // Injecting UserDetailsService bean (used for fetching user details from DB)
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired    
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configures HTTP security, which is responsible for authorizing requests.
     * - Disables CSRF for simplicity (but should be enabled in production).
     * - Defines which URLs are public and which require authentication.
     * - Configures form-based login and HTTP basic authentication.
     * 
     * @param httpSecurity The HTTP security configuration object
     * @return The configured SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        // Disabling CSRF (Cross-Site Request Forgery) protection (disable for APIs)
        httpSecurity.csrf(csrf -> csrf.disable()) 

            // Authorizing HTTP requests
            .authorizeHttpRequests(request -> request
                .requestMatchers("/user/register", "/user/login", "/user/getAllUser", "/user/jwt/login") // Allow public access
                .permitAll()  // No authentication required for these URLs
                .anyRequest().authenticated())  // All other requests require authentication

            // Configuring form login
            .formLogin(Customizer.withDefaults())  // Using default form-based login

            // Configuring HTTP Basic Authentication (pop-up for username and password)
            .httpBasic(Customizer.withDefaults())
            // pls add comment here
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();  // Return the built SecurityFilterChain
    }

    /**
     * Provides an in-memory user store for testing purposes.
     * It returns two in-memory users with usernames and passwords (no password encoding for testing).
     * 
     * @return UserDetailsService that returns in-memory user details
     */
    // @Bean (Commented out since we are using custom user details service)
    public UserDetailsService userDetailsService() {
        // Creating an in-memory user "nishant" with a hardcoded password
        UserDetails nishant = User.withUsername("nishant")
            .password("{noop}Eon@9934")  // Using NoOpPasswordEncoder to disable password encoding
            .roles("USER")
            .build();

        // Creating another in-memory user "oriya"
        UserDetails oriya = User.withUsername("oriya")
            .password("{noop}Oriya@9934")
            .roles("USER")
            .build();

        // Returning the in-memory user store
        return new InMemoryUserDetailsManager(nishant, oriya);
    }

    /**
     * Bean for BCryptPasswordEncoder to encode passwords securely.
     * This password encoder is used to encode passwords for authentication and authorization.
     * 
     * @return BCryptPasswordEncoder instance for password encoding
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // Returning an encoder with strength 14 (higher strength = more secure)
        return new BCryptPasswordEncoder(14);
    }

    /**
     * Bean to provide a custom AuthenticationProvider for the application.
     * It uses DaoAuthenticationProvider, which authenticates users based on user details from a custom UserDetailsService.
     * The provider uses BCryptPasswordEncoder to verify passwords.
     * 
     * @return AuthenticationProvider instance for user authentication
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        
        // Setting the custom UserDetailsService to load user details during authentication
        provider.setUserDetailsService(userDetailsService);

        // Logging the encoder instance for debugging purposes (if needed)
        System.out.println("Encode::: " + NoOpPasswordEncoder.getInstance().toString());

        // Setting the password encoder for the provider (using BCryptPasswordEncoder for secure password hashing)
        provider.setPasswordEncoder(bCryptPasswordEncoder());

        return provider;
    }

    /**
     * Configures the AuthenticationManager, which is used to authenticate users.
     * This manager is responsible for delegating the authentication process to an AuthenticationProvider.
     * 
     * @param authenticationConfiguration The authentication configuration object
     * @return The AuthenticationManager bean
     * @throws Exception If an error occurs while creating the manager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Returning the configured AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    
}
