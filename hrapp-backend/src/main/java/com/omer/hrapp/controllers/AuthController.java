package com.omer.hrapp.controllers;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.requests.AuthRequest;
import com.omer.hrapp.responses.AuthResponse;
import com.omer.hrapp.security.JwtTokenProvider;
import com.omer.hrapp.services.AuthService;
import com.omer.hrapp.services.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String USER_DISABLED = "USER DISABLED";
    private static final String INVALID_CREDENTIALS = "INVALID CREDENTIALS";
    private AuthenticationManager authenticationManager;
    private SpecialistService specialistService;
    private AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, SpecialistService specialistService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.specialistService = specialistService;
        this.authService = authService;
    }

    @PostMapping("/specialist")
    public ResponseEntity<?> authenticateSpecialistRequest(@RequestBody AuthRequest authRequest) throws Exception{
        String userName = authRequest.getUserName();
        String password = authRequest.getPassword();
        Authentication auth = new UsernamePasswordAuthenticationToken(userName, password);
        authenticate(auth);
        Specialist specialist = specialistService.getSpecialistByUserName(userName);
        if (specialist != null) {
            final String token = JwtTokenProvider.generateToken(specialist.getFirstName());
            return ResponseEntity.ok(new AuthResponse(specialist.getId(), "Bearer " + token));
        } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private void authenticate(Authentication auth) throws Exception {
        try {
            authenticationManager.authenticate(auth);
        } catch (DisabledException e) {
            throw new Exception(USER_DISABLED, e);
        } catch (BadCredentialsException e) {
            throw new Exception(INVALID_CREDENTIALS, e);
        }
    }

    @PostMapping("/applicant")
    public ResponseEntity<?> authenticateApplicantRequest(@RequestParam("code") String authorizationCode) throws Exception{
        try {
            String token = authService.exchangeLinkedInToken(authorizationCode);

        } catch () {

        }
    }

}
