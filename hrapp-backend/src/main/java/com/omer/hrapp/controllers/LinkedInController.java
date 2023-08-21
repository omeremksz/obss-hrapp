package com.omer.hrapp.controllers;

import com.omer.hrapp.exceptions.NotFoundException;
import com.omer.hrapp.services.LinkedInService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/linkedin")
public class LinkedInController {
    private LinkedInService linkedInService;

    public LinkedInController(LinkedInService linkedInService) {
        this.linkedInService = linkedInService;
    }

    @GetMapping("/callback")
    public ResponseEntity<Void> handleLinkedInCallback (@RequestParam("code") String authorizationCode) {
        try {
            Map<Long, String > resultMap = linkedInService.exchangeLinkedInToken(authorizationCode);
            Long applicantId = resultMap.keySet().iterator().next();
            String token = "Bearer "+ resultMap.get(applicantId);
            String frontendRedirectURL = "http://localhost:3000/applicants/welcome-page/";

            String redirectURLWithParams = frontendRedirectURL +
                    "?applicantId=" + applicantId +
                    "&token=" + token;

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", redirectURLWithParams);

            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
}
