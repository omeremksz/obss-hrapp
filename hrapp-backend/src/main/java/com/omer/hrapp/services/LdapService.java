package com.omer.hrapp.services;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.requests.SpecialistAuthRequest;
import com.omer.hrapp.responses.AuthResponse;
import com.omer.hrapp.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LdapService {

    private static final String USER_DISABLED = "USER DISABLED";
    private static final String INVALID_CREDENTIALS = "INVALID CREDENTIALS";
    private AuthenticationManager authenticationManager;
    private SpecialistService specialistService;

    public LdapService(AuthenticationManager authenticationManager, SpecialistService specialistService) {
        this.authenticationManager = authenticationManager;
        this.specialistService = specialistService;
    }

    public ResponseEntity<?> specialistAuthResponse(SpecialistAuthRequest specialistAuthRequest) throws Exception {
        String userName = specialistAuthRequest.getUserName();
        String password = specialistAuthRequest.getPassword();
        authenticate(userName, password);
        Optional<Specialist> specialist = specialistService.getSpecialistByUserName(userName);
        if (specialist.isPresent()) {
            final String token = JwtTokenProvider.generateToken(specialist.get().getUserName());
            return ResponseEntity.ok(new AuthResponse(specialist.get().getId(), "Bearer " + token));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public void authenticate(String userName, String password) throws Exception {
        try {
            Authentication auth = new UsernamePasswordAuthenticationToken(userName, password);
            authenticationManager.authenticate(auth);
        } catch (DisabledException e) {
            throw new Exception(USER_DISABLED, e);
        } catch (BadCredentialsException e) {
            throw new Exception(INVALID_CREDENTIALS, e);
        }
    }
}
