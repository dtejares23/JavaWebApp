import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, 
                        @RequestParam("password") String password) {
        // Step 1: Get the access token
        String token = getToken();

        // Step 2: Use the token to call the getAllUsers API
        if (token != null && !token.isEmpty()) {
            String url = "https://dev-apigw.sbcdom.com:8243/fxp/um/1.0.0/getAllUsers?pageNumber=1";
            RestTemplate restTemplate = new RestTemplate();

            // Set Authorization header with the token
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Make the API call with the token
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                // Logic to parse JSON response and validate credentials (add logic here)
                console.log("Noiceeee!");
                // If successful, redirect to dashboard
                return "redirect:http://localhost:8080/FXP_Onboarding/Frontend/Dashboard.html";
            }
        }
        
        if (token == null && token.isEmpty()) {
        	console.log("Token Not Found");
        }
        // If login fails, redirect to login page with an error message
        return "redirect:/login?error=true";
    }

    private String getToken() {
        String tokenUrl = "https://10.200.11.40:8243/token";
        RestTemplate restTemplate = new RestTemplate();
        // Set the request body with credentials for token
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "your-client-id");
        body.add("client_secret", "your-client-secret");
        body.add("grant_type", "client_credentials");

        // Set headers (e.g., content type)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        // Build the request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        // Send POST request to get the token
        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();
            // Dummy parsing example (replace with actual parsing logic):
            String accessToken = parseTokenFromResponse(responseBody); 
            return accessToken;
        }
        return null;
    }

    private String parseTokenFromResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);
            return root.path("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
