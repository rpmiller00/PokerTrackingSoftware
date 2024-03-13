package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class DefaultController {
    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDao transferDao;

    public DefaultController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "/account", method = RequestMethod.GET)
    public Account getAccount(Principal principal) {
        Account account;
        try {
            account = accountDao.getAccount(principal.getName());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (account == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found");
        }
        return account;
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> getUsers() { // will we need to implement Principal here ?
        List<User> users = new ArrayList<>();
        try {
            users = userDao.getUsers();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (users.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Users Found");
        }
        return users;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer transfer) {
        // should we implement Principal as an argument so we can use .getName to establish username of person creating the transfer ?
        Transfer createdTransfer = null;
        if (transfer.getTransferTypeId() == 2) {
            transfer.setAccountFrom(accountDao.getAccountIdByUserId(transfer.getAccountFrom()));
            transfer.setAccountTo(accountDao.getAccountIdByUserId(transfer.getAccountTo()));

            try {
                createdTransfer = transferDao.createTransfer(transfer);
                accountDao.updateBalance(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return createdTransfer;

        }
        else {
            transfer.setAccountFrom(accountDao.getAccountIdByUserId(transfer.getAccountFrom()));
            transfer.setAccountTo(accountDao.getAccountIdByUserId(transfer.getAccountTo()));
            try {
                createdTransfer = transferDao.createTransfer(transfer);
                //accountDao.updateBalanceForSend(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
            } catch (DaoException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return createdTransfer;
        }
    }

    // call on another method to update the balance - if create transfer occurs, then, you can update the balance
    // or, back to back in the try ^^^

    @RequestMapping(path = "/account/list", method = RequestMethod.GET)
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try {
            accounts = accountDao.getAccounts();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (accounts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Users Found");
        }
        return accounts;
    }

    @RequestMapping(path = "/transfer/list", method = RequestMethod.GET)
    public List<Transfer> getTransfers(Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        try {
            Account account = accountDao.getAccount(principal.getName());
            transfers = transferDao.getTransfersByAccount(account.getAccountId());

        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (transfers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Transfer Found");
        }
        return transfers;

    }

    @RequestMapping(path = "/transfer/list/pending", method = RequestMethod.GET)
    public List<Transfer> getPendingTransfers(Principal principal) {
        List<Transfer> transfers = new ArrayList<>();
        try {
            Account account = accountDao.getAccount(principal.getName());
            transfers = transferDao.getPendingTransfersByAccount(account.getAccountId());

        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (transfers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Transfer Found");
        }
        return transfers;

    }

    @RequestMapping(path = "transfer/reject", method = RequestMethod.PUT)
    public Transfer rejectTransfer(@RequestBody Transfer transfer) {
        try {
            return transferDao.rejectTransfer(transfer);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "transfer/approve", method = RequestMethod.PUT)
    public Transfer approveTransfer(@RequestBody Transfer transfer) {
        Transfer returnedTransfer = null;
        try {
            returnedTransfer = transferDao.approveTransfer(transfer);
            accountDao.updateBalance(transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());

        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return returnedTransfer;
    }



}
