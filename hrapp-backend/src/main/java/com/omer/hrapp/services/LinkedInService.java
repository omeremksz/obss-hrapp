package com.omer.hrapp.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.exceptions.NotFoundException;
import com.omer.hrapp.security.JwtTokenProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import javax.json.Json;
import javax.json.JsonObject;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class LinkedInService {

    private static final String PROFILE_ENDPOINT = "https://api.linkedin.com/v2/userinfo";
    private static final String CLIENT_ID = "77oimdom7ofswl";
    private static final String CLIENT_SECRET = "DJnkdgI2NMrszLkK";
    private static final String REDIRECT_URI = "http://localhost:8080/linkedin/callback";
    private static final String TOKEN_ENDPOINT = "https://www.linkedin.com/oauth/v2/accessToken";

    private ApplicantService applicantService;

    public LinkedInService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    public Map<Long, String> exchangeLinkedInToken(String authorizationCode) throws Exception {
        String accessToken = exchangeAuthorizationCodeForAccessToken(authorizationCode, TOKEN_ENDPOINT);
        JsonObject profileInfo = getLinkedInProfileInfo(accessToken);

        String email = getEmailFromProfileInfo(profileInfo);
        String fullName = getFullNameFromProfileInfo(profileInfo);
        String profilePhotoUrl = getProfilePhotoUrlFromProfileInfo(profileInfo);

        Applicant applicant;

        if(!isApplicantAlreadySaved(email)) {
            applicant = createAndSaveApplicant(fullName, email, profilePhotoUrl);
        } else {
            applicant = applicantService.getApplicantByEmail(email).orElseThrow(NotFoundException::new);
        }

        Long applicantId = applicant.getId();
        String token = JwtTokenProvider.generateToken(email);
        Map<Long, String> resultMap = new HashMap<>();
        resultMap.put(applicantId,token);

        return resultMap;
    }

    private boolean isApplicantAlreadySaved(String email) {
        return applicantService.getApplicantByEmail(email).isPresent();
    }

    private String exchangeAuthorizationCodeForAccessToken(String authorizationCode, String tokenEndpoint) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> tokenRequest = new LinkedMultiValueMap<>();
        tokenRequest.add("grant_type", "authorization_code");
        tokenRequest.add("code", authorizationCode);
        tokenRequest.add("redirect_uri", REDIRECT_URI);
        tokenRequest.add("client_id", CLIENT_ID);
        tokenRequest.add("client_secret", CLIENT_SECRET);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                TOKEN_ENDPOINT,
                HttpMethod.POST,
                new HttpEntity<>(tokenRequest, new HttpHeaders()),
                JsonNode.class
        );

        return response.getBody().get("access_token").asText();
    }

    private JsonObject getLinkedInProfileInfo(String accessToken) throws IOException {
        String profileResponse = sendGetWithAuthorizationHeader(PROFILE_ENDPOINT, accessToken);
        return Json.createReader(new StringReader(profileResponse)).readObject();
    }

    private String getProfilePhotoUrlFromProfileInfo(JsonObject profileInfo) {
        return profileInfo.getString("picture");
    }

    private String getFullNameFromProfileInfo(JsonObject profileInfo) {
        return profileInfo.getString("name");
    }

    private String getEmailFromProfileInfo(JsonObject profileInfo) {
        return profileInfo.getString("email");
    }

    private Applicant createAndSaveApplicant(String fullName, String email, String profilePhotoUrl) {
        Applicant applicant = new Applicant();

        String[] split = fullName.split(" ");
        String firstName = split[0];
        String lastName = split[1];

        applicant.setFirstName(firstName);
        applicant.setLastName(lastName);
        applicant.setEmail(email);
        applicant.setProfilePhotoUrl(profilePhotoUrl);
        return applicantService.createNewApplicant(applicant);
    }

    private String sendGetWithAuthorizationHeader(String resourceUrl, String accessToken) throws IOException {
        HttpURLConnection con = openAuthorizedConnectionTo(resourceUrl, accessToken);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder jsonString = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }

            return jsonString.toString();
        }
    }

    private HttpsURLConnection openAuthorizedConnectionTo(String resourceUrl, String accessToken) throws IOException {
        URL url = new URL(resourceUrl);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Authorization", "Bearer "+accessToken);
        con.setRequestProperty("cache-control", "no-cache");
        con.setRequestProperty("X-Restli-Protocol-Version", "2.0.0");

        return con;
    }
}
