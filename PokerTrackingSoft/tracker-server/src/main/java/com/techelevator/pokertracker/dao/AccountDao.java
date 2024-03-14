package com.techelevator.pokertracker.dao;

import com.techelevator.pokertracker.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    Account getAccount(String userName);

    List<Account> getAccounts();

    void updateBalance(int fromId, int toId, BigDecimal amount);


    BigDecimal getBalanceByAccountId(int accountId);

    int getAccountIdByUserId(int userId);

    int getUserIdByAccountId(int accountId);

    String getUsernameByAccountId(int accountId);

}
