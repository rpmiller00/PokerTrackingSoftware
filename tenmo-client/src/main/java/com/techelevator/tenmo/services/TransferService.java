package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {
    private final String baseUrl;
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public TransferService(String url) {
        this.baseUrl = url;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Transfer addTransfer(Transfer newTransfer) {
        Transfer returnedTransfer = null;
        try {
            returnedTransfer = restTemplate.postForObject("http://localhost:8080/transfer",
                    makeTransferEntity(newTransfer), Transfer.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }

    public Transfer[] getTransfers() {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange("http://localhost:8080/transfer/list", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public Transfer[] getPendingTransfers() {
        Transfer[] transfers = null;
        try {
            ResponseEntity<Transfer[]> response = restTemplate.exchange("http://localhost:8080/transfer/list/pending", HttpMethod.GET, makeAuthEntity(), Transfer[].class);
            transfers = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return transfers;
    }

    public boolean rejectTransfer(Transfer transfer) {
        boolean success = false;
        try {
            restTemplate.put("http://localhost:8080/transfer/reject",
                    makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean approveTransfer(Transfer transfer) {
        boolean success = false;
        try {
            restTemplate.put("http://localhost:8080/transfer/approve",
                    makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
