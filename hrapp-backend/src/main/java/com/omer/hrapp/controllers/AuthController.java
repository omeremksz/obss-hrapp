package com.omer.hrapp.controllers;

import com.omer.hrapp.requests.SpecialistAuthRequest;
import com.omer.hrapp.services.LdapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private LdapService ldapService;

    public AuthController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @PostMapping("/specialist")
    public ResponseEntity<?> authenticateSpecialistRequest(@RequestBody SpecialistAuthRequest specialistAuthRequest) throws Exception{
        return ldapService.specialistAuthResponse(specialistAuthRequest);
    }

}
