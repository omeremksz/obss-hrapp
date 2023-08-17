package com.omer.hrapp.security;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.SpecialistRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private SpecialistRepository specialistRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("authorization");

        String userName = null;
        String jwtToken = null;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));

        if( requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userName = jwtTokenProvider.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Specialist userDetails = this.specialistRepository.findByUserName(userName);
            try {
                if (jwtTokenProvider.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, grantedAuthorities);
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
