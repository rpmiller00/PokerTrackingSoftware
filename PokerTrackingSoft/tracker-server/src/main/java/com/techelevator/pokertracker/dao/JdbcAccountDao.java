package com.techelevator.pokertracker.dao;

import com.techelevator.pokertracker.exception.DaoException;
import com.techelevator.pokertracker.model.Account;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Account getAccount(String userName){
        Account account = null;
        String sql = "SELECT a.account_id, a.user_id, a.balance " +
                "FROM account a " +
                "INNER JOIN tenmo_user t " +
                "ON a.user_id = t.user_id " +
                "WHERE t.username = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);
            if (results.next()) {
                account = mapRowToAccount(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return account;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id, balance FROM account";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Account account = mapRowToAccount(results);
                accounts.add(account);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return accounts;
    }

    public int getAccountIdByUserId(int userId) {
        int accountId = 0;
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                accountId = mapRowToInt(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return accountId;
    }

    public int getUserIdByAccountId(int accountId){
        int userId = 0;
        String sql = "SELECT user_id FROM account WHERE account_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                userId = mapRowToInt(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return userId;
    }
    public String getUsernameByAccountId(int accountId){
        String username = "";
        String sql = "SELECT u.username FROM tenmo_user u JOIN account a on u.user_id = a.user_id WHERE a.account_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                username = mapRowToString(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return username;
    }

    public BigDecimal getBalanceByAccountId(int accountId) {
        BigDecimal balance = null;
        String sql = "SELECT balance from ACCOUNT where account_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            if (results.next()) {
                balance = mapRowToBigDecimal(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }

        return balance;
    }

    @Override
    public void updateBalance(int fromId, int toId, BigDecimal amount) {
        BigDecimal currentFromAccountBalance = getBalanceByAccountId(fromId);
        BigDecimal newFromBalance = currentFromAccountBalance.subtract(amount);

        BigDecimal currentToAccountBalance = getBalanceByAccountId(toId);
        BigDecimal newToBalance = currentToAccountBalance.add(amount);


        String sql = "UPDATE account SET balance = ? WHERE account_id = ?";

        // We'll have an amount of the transfer, need to know each accounts current balance to determine what new balance should be
        try {
            jdbcTemplate.update(sql, newFromBalance, fromId);
            jdbcTemplate.update(sql, newToBalance, toId);

        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }



    private Account mapRowToAccount(SqlRowSet rs) {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
    private String mapRowToString(SqlRowSet rs) {
        String username = rs.getString("username");
        return username;
    }

    private int mapRowToInt(SqlRowSet rs) {
        int i = rs.getInt("account_id");
        return i;
    }

    private BigDecimal mapRowToBigDecimal(SqlRowSet rs){
        BigDecimal i = rs.getBigDecimal("balance");
        return i;
    }

}
