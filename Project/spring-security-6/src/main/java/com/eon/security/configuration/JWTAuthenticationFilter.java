package com.eon.security.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eon.security.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWTAuthenticationFilter is responsible for intercepting incoming HTTP requests
 * and validating the JWT token before allowing access to secured resources.
 * This filter runs once per request, ensuring security checks are applied.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * This method intercepts HTTP requests to check for a valid JWT token.
     * If a valid token is found, it sets up authentication in the SecurityContext.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Extract the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");

        // If there is no Authorization header or it does not start with "Bearer ", skip processing and continue the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token from the Authorization header (skipping the "Bearer " prefix)
        final String jwt = authHeader.substring(7); 

        // Extract username from the token
        final String userName = jwtService.extractUserName(jwt);

        // Get current authentication from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If username is not null and authentication has not yet occurred, proceed with authentication
        if (userName != null && authentication == null) {
            
            // Retrieve user details from the database using the extracted username
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName); 

            // Validate the JWT token against the retrieved user details
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // Create an authentication token containing user details and authorities
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                // Attach request-specific authentication details (like IP address, session ID, etc.)
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                
                // Store the authentication object in the SecurityContext, marking the user as authenticated
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        
        // Continue the filter chain execution
        filterChain.doFilter(request, response);
    }
}
