package com.omer.hrapp.security;

import com.omer.hrapp.services.SpecialistDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private SpecialistDetailsService specialistDetailsService;

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring("Bearer".length()+1);
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)){
                String username = jwtTokenProvider.getUserNameFromJwt(jwtToken);
                UserDetails specialist = specialistDetailsService.loadUserByUsername(username);
                if(specialist != null){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(specialist, null, specialist.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (Exception e){
            return;
        }
        filterChain.doFilter(request, response);
    }
}