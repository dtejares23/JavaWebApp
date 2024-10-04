package com.example.loginapp;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegistrationController {

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestParam("userId") String userId,
                                               @RequestParam("fullName") String fullName,
                                               @RequestParam("phoneNumber") String phoneNumber,
                                               @RequestParam("email") String email,
                                               @RequestParam("password") String password,
                                               @RequestParam("role") String role,
                                               @RequestParam("branchArea") String branchArea,
                                               @RequestParam("branchNemonic") String branchNemonic,
                                               @RequestParam("branchCode") String branchCode) {
        try {
            // To get the auth token
            String token = getToken();
            if (token == null) {
                return new ResponseEntity<>("Failed to retrieve access token", HttpStatus.UNAUTHORIZED);
            }

            // Step 2: Prepare the API URL and the request body for registration
            String apiUrl = "https://dev-apigw.sbcdom.com:8243/fxp-iam/1.0.0/createUser";
            RestTemplate restTemplate = new RestTemplate();

            // Build the request payload (JSON structure)
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("userId", userId);
            requestBody.put("fullName", fullName);
            requestBody.put("phoneNumber", phoneNumber);
            requestBody.put("email", email);
            requestBody.put("password", password);
            requestBody.put("role", role);

            // Teller form information
            Map<String, String> tellerForm = new HashMap<>();
            tellerForm.put("branchArea", branchArea);
            tellerForm.put("branchNemonic", branchNemonic);
            tellerForm.put("branchCode", branchCode);
            requestBody.put("tellerForm", tellerForm);
            requestBody.put("traderCode", null);
            requestBody.put("segment", null);

            // Set headers with the access token
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send the POST request to the API
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

            // Handle the API response
            if (response.getStatusCode() == HttpStatus.OK) {
                return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Registration failed: " + response.getBody(), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error during registration: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Function to retrieve the access token
    private String getToken() {
        String tokenUrl = "https://10.200.11.40:8243/token";
        RestTemplate restTemplate = new RestTemplate();

        // Prepare the token request payload
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("grant_type", "client_credentials");

        // Set headers for Basic Authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Encode username and password for Basic Auth
        String auth = "sXIcsIjJHxI0yQvsFSkyD9X5OBka:fu_2qtmeFY49gSdfrgmyaYpj1NAa";
        String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
        headers.set("Authorization", "Basic " + encodedAuth);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            // Send POST request to retrieve the token
            ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // Parse the token from the response
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
                return (String) responseBody.get("access_token");  // Extract access_token
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Error getting token: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
