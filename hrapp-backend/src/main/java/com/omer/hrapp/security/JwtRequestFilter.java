package com.omer.hrapp.security;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.repositories.SpecialistRepository;
import com.omer.hrapp.services.ApplicantService;
import com.omer.hrapp.services.SpecialistService;
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
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private ApplicantService applicantService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("authorization");

        String subject = null;
        String jwtToken = null;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SPECIALIST"));

        if( requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                subject = jwtTokenProvider.getSubjectFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Specialist> specialist = specialistService.getSpecialistByUserName(subject);
            Optional<Applicant> applicant = applicantService.getApplicantByEmail(subject);
            try {
                if(specialist.isPresent()) {
                    Specialist specialistUserDetails = specialist.get();
                    if (jwtTokenProvider.validateToken(jwtToken, specialistUserDetails, null)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(specialistUserDetails, null, grantedAuthorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }
                } else if (applicant.isPresent()) {
                    Applicant applicantUserDetails = applicant.get();
                    if (jwtTokenProvider.validateToken(jwtToken, null, applicantUserDetails)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(applicantUserDetails, null, grantedAuthorities);
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
