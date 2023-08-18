package com.omer.hrapp.controllers;

import com.omer.hrapp.exceptions.LinkedInAccessTokenExpiredException;
import com.omer.hrapp.exceptions.NotFoundException;
import com.omer.hrapp.requests.SpecialistAuthRequest;
import com.omer.hrapp.responses.AuthResponse;
import com.omer.hrapp.services.LdapService;
import com.omer.hrapp.services.LinkedInService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private LinkedInService linkedInService;
    private LdapService ldapService;

    public AuthController(LinkedInService linkedInService, LdapService ldapService) {
        this.linkedInService = linkedInService;
        this.ldapService = ldapService;
    }

    @PostMapping("/specialist")
    public ResponseEntity<?> authenticateSpecialistRequest(@RequestBody SpecialistAuthRequest specialistAuthRequest) throws Exception{
        return ldapService.specialistAuthResponse(specialistAuthRequest);
    }

    @PostMapping("/applicant")
    public ResponseEntity<?> authenticateApplicantRequest(@RequestParam("code") String authorizationCode) throws Exception{
        try {
            Map<Long, String > resultMap = linkedInService.exchangeLinkedInToken(authorizationCode);
            Long applicantId = resultMap.keySet().iterator().next();
            String token = resultMap.get(applicantId);
            return ResponseEntity.ok(new AuthResponse(applicantId, "Bearer " + token));
        } catch (NotFoundException e1) {
            return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
        } catch (LinkedInAccessTokenExpiredException e2) {
            return new ResponseEntity<>("", HttpStatus.CONFLICT);
        }
    }

}
