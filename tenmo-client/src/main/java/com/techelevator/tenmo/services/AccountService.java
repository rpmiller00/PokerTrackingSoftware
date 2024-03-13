package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.ArrayList;

import java.math.BigDecimal;

public class AccountService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public AccountService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // bridge from client side to server side
    public Account[] listAccounts(){
        //List<User> users = new ArrayList<>();
        Account[] accounts = null;
        try {
            ResponseEntity <Account[]> response = restTemplate.exchange("http://localhost:8080/account/list", HttpMethod.GET, makeAuthEntity(), Account[].class);
            accounts = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return accounts;
    }

    public Account getAccount(){
        Account account = null;
        try {
            ResponseEntity<Account> response = restTemplate.exchange("http://localhost:8080/account", HttpMethod.GET, makeAuthEntity(), Account.class);
            account = response.getBody();
        } catch(RestClientResponseException | ResourceAccessException e){
            BasicLogger.log(e.getMessage());
        }
        return account;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
