package com.omer.hrapp.services;

import com.omer.hrapp.entities.Applicant;
import com.omer.hrapp.exceptions.LinkedInAccessTokenExpiredException;
import com.omer.hrapp.exceptions.NotFoundException;
import com.omer.hrapp.security.JwtTokenProvider;
import org.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import org.json.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final String PROFILE_ENDPOINT = "https://api.linkedin.com/v2/me";
    private static final String IMAGE_ENDPOINT = "https://api.linkedin.com/v2/me?projection=" +
            "(id,profilePicture(displayImage~:playableStreams))&oauth2_access_token=";
    private static final String EMAIL_ENDPOINT = "https://api.linkedin.com/v2/emailAddress?q=members&projection=(elements*(handle~))";
    private static final String CLIENT_ID = "77oimdom7ofswl";
    private static final String CLIENT_SECRET = "DJnkdgI2NMrszLkK";
    private static final String REDIRECT_URI = "http://localhost:8080";
    private ApplicantService applicantService;

    public AuthService(ApplicantService applicantService) {
        this.applicantService = applicantService;
    }

    public Map<Long, String> exchangeLinkedInToken(String authorizationCode) throws Exception {
        String accessTokenUri = "https://www.linkedin.com/oauth/v2/accessToken?grant_type=authorization_code&code="
                + authorizationCode + "&redirect_uri=" + REDIRECT_URI + "&client_id="
                + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "";
        
        String accessToken = getAccessToken(accessTokenUri);

        String fullName = getName(accessToken);
        String profilePhotoUrl = getProfilePhotoUrl(accessToken);
        String email = getEmail(accessToken);

        Applicant applicant;

        if(!isApplicantAlreadySaved(email)) {
            applicant = new Applicant();

            String[] split = fullName.split(" ");
            String firstName = split[0];
            String lastName = split[1];

            applicant.setFirstName(firstName);
            applicant.setLastName(lastName);
            applicant.setEmail(email);
            applicant.setProfilePhotoUrl(profilePhotoUrl);
            applicantService.createNewApplicant(applicant);

            Long applicantId = applicantService.getApplicantByEmail(email).get().getId();
            String token = JwtTokenProvider.generateToken(email);
            Map<Long, String> resultMap = new HashMap<>();
            resultMap.put(applicantId,token);

            return resultMap;
        } else if (applicantService.getApplicantByEmail(email).isPresent()) {
            applicant = applicantService.getApplicantByEmail(email).get();

            Long applicantId = applicant.getId();
            String token = JwtTokenProvider.generateToken(email);
            Map<Long, String> resultMap = new HashMap<>();
            resultMap.put(applicantId,token);

            return resultMap;
        } else {
            throw new NotFoundException();
        }
    }

    private boolean isApplicantAlreadySaved(String email) {
        return applicantService.getApplicantByEmail(email).isPresent();
    }

    private String getEmail(String accessToken) throws Exception {
        String response = sendGetWithAuthorizationHeader(EMAIL_ENDPOINT, accessToken);
        
        JSONObject responseAsJSON = new JSONObject(response);
        
        return parseEmailApiResponse(responseAsJSON);
    }

    private String parseEmailApiResponse(JSONObject responseAsJSON) throws JSONException {
        return responseAsJSON.getJSONArray("elements")
                .getJSONObject(0)
                .getJSONObject("handle~")
                .getString("emailAddress");
    }

    private String getAccessToken(String accessTokenUri) throws JSONException, LinkedInAccessTokenExpiredException {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String accessTokenRequest = restTemplate.getForObject(accessTokenUri, String.class);
            JSONObject jsonObjectOfAccessToken = new JSONObject(accessTokenRequest);
            return jsonObjectOfAccessToken.get("access_token").toString();
        } catch (HttpClientErrorException e) {
            throw new LinkedInAccessTokenExpiredException();
        }
    }
    private String getName(String accessToken) throws IOException {
        String jsonstring = sendGetWithAuthorizationHeader(PROFILE_ENDPOINT, accessToken);

        JsonReader jsonReader = Json.createReader(new StringReader(jsonstring));
        JsonObject jsonObject = jsonReader.readObject();

        getProfilePhotoUrl(accessToken);

        String localizedFirstName = jsonObject.get("localizedFirstName").toString()
                .replaceAll("^\"|\"$", "").replaceAll("[ğ]", "g")
                .replaceAll("[İ]", "I");
        String localizedLastName = jsonObject.get("localizedLastName").toString()
                .replaceAll("^\"|\"$", "").replaceAll("[ğ]", "g")
                .replaceAll("[İ]", "I");

        return localizedFirstName + " " + localizedLastName;
    }

    private String getProfilePhotoUrl(String accessToken) throws IOException {
        HttpsURLConnection connection = getHttpsURLConnectionWithAccessTokenConcatenated(IMAGE_ENDPOINT, accessToken);

        StringBuilder jsonString = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while((line = br.readLine()) != null) {
            jsonString.append(line);
        }

        br.close();

        return parseProfilePhotoApiResponse(new JSONObject(jsonString.toString()));
    }

    private String parseProfilePhotoApiResponse(JSONObject profilePhotoJson) throws JSONException {
        return profilePhotoJson.getJSONObject("profilePicture")
                .getJSONObject("displayImage~")
                .getJSONArray("elements")
                .getJSONObject(3)
                .getJSONArray("identifiers")
                .getJSONObject(0)
                .getString("identifier");
    }

    private HttpsURLConnection getHttpsURLConnectionWithAccessTokenConcatenated(String url, String accessToken) throws IOException {
        URL urlWithToken = new URL(url + accessToken);
        return (HttpsURLConnection) urlWithToken.openConnection();
    }

    private String sendGetWithAuthorizationHeader(String resourceUrl, String accessToken) throws IOException {
        HttpsURLConnection con = openAuthorizedConnectionTo(resourceUrl, accessToken);

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder jsonString = new StringBuilder();

        String line;
        while((line = br.readLine()) != null) {
            jsonString.append(line);
        }

        br.close();

        return jsonString.toString();
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
