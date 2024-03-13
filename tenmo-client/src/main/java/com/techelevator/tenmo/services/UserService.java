package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public UserService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public User[] listUsers(){
        //List<User> users = new ArrayList<>();
        User[] users = null;
        try {
            ResponseEntity <User[]> response = restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, makeAuthEntity(), User[].class);
            users = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return users;
    }
    //Get username from userid 

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
