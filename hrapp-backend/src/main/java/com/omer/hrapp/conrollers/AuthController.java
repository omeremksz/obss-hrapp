package com.omer.hrapp.conrollers;

import com.omer.hrapp.entities.Specialist;
import com.omer.hrapp.requests.AuthRequest;
import com.omer.hrapp.responses.AuthResponse;
import com.omer.hrapp.security.JwtTokenProvider;
import com.omer.hrapp.security.LdapAuthProvider;
import com.omer.hrapp.services.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final String USER_DISABLED = "USER DISABLED";
    private static final String INVALID_CREDENTIALS = "INVALID CREDENTIALS";
    private LdapAuthProvider authenticationProvider;
    private JwtTokenProvider jwtTokenProvider;
    private SpecialistService specialistService;


    public AuthController(LdapAuthProvider authenticationProvider, JwtTokenProvider jwtTokenProvider, SpecialistService specialistService) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenProvider = jwtTokenProvider;
        this.specialistService = specialistService;
    }

    @PostMapping
    public ResponseEntity<?> authenticateRequest(@RequestBody AuthRequest authRequest) throws Exception{
        String userName = authRequest.getUserName();
        String password = authRequest.getPassword();
        Authentication auth = new UsernamePasswordAuthenticationToken(userName, password);
        authenticate(auth);
        final String token = JwtTokenProvider.generateToken(auth.getPrincipal().toString());
        Specialist specialist = specialistService.getSpecialistByUserName(userName);
        if (specialist != null) {
            return ResponseEntity.ok(new AuthResponse(specialist.getId(), "Bearer " + token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private void authenticate(Authentication auth) throws Exception {
        try {
            authenticationProvider.authenticate(auth);
        } catch (DisabledException e) {
            throw new Exception(USER_DISABLED, e);
        } catch (BadCredentialsException e) {
            throw new Exception(INVALID_CREDENTIALS, e);
        }
    }
}
