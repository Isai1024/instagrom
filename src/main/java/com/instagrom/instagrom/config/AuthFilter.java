package com.instagrom.instagrom.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{
    
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // * the token is taken in the request header
        final String jwtAccessToken = request.getHeader("Authorization");

        if (jwtAccessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // * Token validate and return the email
        String email = null;
        try{
            email = jwtUtil.validateTokenAndRetrieveSubject(jwtAccessToken);
        }catch(Exception e){
            handlerExceptionResolver.resolveException(request, response, null, e);
            return;
        }

        if(SecurityContextHolder.getContext().getAuthentication() == null && email != null){
            final UserDetails userDetails;

            try{
                userDetails = userDetailsService.loadUserByUsername(email);
            }catch(Exception e){
                handlerExceptionResolver.resolveException(request, response, null, e);
                return;
            }

            // * Credential authentication is created
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }

}
