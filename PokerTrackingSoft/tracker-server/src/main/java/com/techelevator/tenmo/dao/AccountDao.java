package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.support.rowset.SqlRowSet;

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
