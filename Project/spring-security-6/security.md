# Web Security Configuration

This documentation explains the configuration of Spring Security in a Spring Boot application. The project demonstrates authentication, authorization, and user detail handling using `SecurityFilterChain` and `UserDetailsService`.

---

## **1. Overview**

The `WebSecurityConfig` class is a configurable class that takes responsibility for security. This ensures that instead of relying on Spring Boot's default configuration, we define custom security rules and behavior.

---

## **2. Key Components in the Code**

### **2.1. Disabling CSRF**
```java
httpSecurity
    .csrf(csrf -> csrf.disable());
```
- CSRF (Cross-Site Request Forgery) is disabled for testing purposes.
- When inserting or modifying data, CSRF tokens ensure that communication is happening from the same channel.

---

### **2.2. Authorizing HTTP Requests**
```java
.authorizeHttpRequests(request -> request
    .requestMatchers("/user/register", "/user/login").permitAll()
    .anyRequest().authenticated())
```
- Endpoints `/user/register` and `/user/login` are publicly accessible.
- All other endpoints require authentication.

---

### **2.3. Login Configurations**
```java
.formLogin(Customizer.withDefaults())
.httpBasic(Customizer.withDefaults());
```
- `.httpBasic`: Provides a browser popup for entering username and password.
- `.formLogin`: Provides a default login form for user authentication.

---

### **2.4. Configuring User Details**
```java
@Bean
public UserDetailsService userDetailsService() {
    UserDetails nishant = User.withUsername("nishant")
            .password("{noop}Eon@9934")
            .roles("USER")
            .build();

    UserDetails oriya = User.withUsername("oriya")
            .password("{noop}Oriya@9934")
            .roles("USER")
            .build();

    return new InMemoryUserDetailsManager(nishant, oriya);
}
```
- Configures two in-memory users (`nishant` and `oriya`).
- `{noop}` disables password encoding for testing purposes. **This approach is not recommended for production**.

---

## **3. How Spring Security Works**

- When hitting any API, the request first goes through the filter chain.
- Spring Security, being one of the components in the filter chain, handles authentication before passing the request to the DispatcherServlet.
- If the user is authenticated, the request is processed; otherwise, the security layer intercepts it.

---

## **4. Security Configuration Flow**

1. The `securityFilterChain` method defines security rules:
   - Disables CSRF for testing.
   - Allows public access to registration and login endpoints.
   - Requires authentication for all other requests.

2. The `UserDetailsService` method configures user credentials.

3. Both `httpBasic` and `formLogin` mechanisms are set up for authentication.

---

## **5. Notes**

- **Disabling CSRF**:
  - CSRF is disabled only for testing purposes. It should be enabled for production to ensure secure communication.

- **Password Encoding**:
  - Using `{noop}` to disable password encoding is only for testing. Use a password encoder in production environments.

---

## **6. Running the Application**

1. Clone the repository.
2. Build the project using Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. Use Postman or any API testing tool to hit the endpoints.

---

## **7. Future Enhancements**

- Replace in-memory user details with database-backed authentication.
- Implement role-based access control (RBAC).
- Enable CSRF protection for enhanced security.
