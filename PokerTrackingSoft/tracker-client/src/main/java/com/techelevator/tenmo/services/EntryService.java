package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Entry;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;


public class EntryService {

    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public EntryService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Entry addSession(Entry newEntry) {
        Entry returnedEntry = null;
        try {
            returnedEntry = restTemplate.postForObject(baseUrl+"entry",
                    makeEntryEntity(newEntry), Entry.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedEntry;
    }

    public Entry[] getEntries(int userId) {
        Entry[] entries = null;
        try {
            ResponseEntity<Entry[]> response = restTemplate.exchange(baseUrl+ "entry/list/"+userId, HttpMethod.GET, makeAuthEntity(), Entry[].class);
            entries = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return entries;
    }

    private HttpEntity<Entry> makeEntryEntity(Entry entry) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(entry, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
