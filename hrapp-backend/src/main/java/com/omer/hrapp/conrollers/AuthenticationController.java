package com.omer.hrapp.conrollers;

import com.omer.hrapp.responses.AuthenticationResponse;
import com.omer.hrapp.security.JwtTokenProvider;
import com.omer.hrapp.security.LdapAuthenticationProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String USER_DISABLED = "USER DISABLED";
    private static final String INVALID_CREDENTIALS = "INVALID CREDENTIALS";
    private LdapAuthenticationProvider authenticationProvider;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(LdapAuthenticationProvider authenticationProvider, JwtTokenProvider jwtTokenProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<?> authenticateRequest() throws Exception{
        authenticate(SecurityContextHolder.getContext().getAuthentication());
        final String token = JwtTokenProvider.generateToken(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ResponseEntity.ok(new AuthenticationResponse(token));
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
