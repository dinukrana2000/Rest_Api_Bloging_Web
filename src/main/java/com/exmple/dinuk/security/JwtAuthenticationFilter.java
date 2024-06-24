package com.exmple.dinuk.security;

import com.exmple.dinuk.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getJwtFromRequest(request);//extaract the token form req header

        // Check if the token is present and valid
        if(StringUtils.hasText(token)&&jwtTokenProvider.validateToken(token)){
            String username = jwtTokenProvider.getUsernameFromToken(token);
            // Load user details using the username
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            // Create an authentication token using the user details
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Continue the filter chain
        filterChain.doFilter(request,response);

    }
       
        private String getJwtFromRequest(HttpServletRequest request){
            // Get the Authorization header from the request
            String bearerToken = request.getHeader("Authorization");
            // Check if the header contains a Bearer token
            if (StringUtils .hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                // Return the token without the "Bearer " prefix
                return bearerToken.substring(7,bearerToken.length());
            }
            // Return null if no valid token is found
            return null;
        }

}
