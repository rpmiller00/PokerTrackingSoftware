package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;


@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    private AccountDao accountDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, AccountDao accountDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
    }
    @Override
    public Transfer getTransferByTransferId(int id) {
        Transfer transfer = null;
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE transfer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if (results.next()) {
                transfer = mapRowToTransfer(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfer;
    }

    public List<Transfer> getTransfersByAccount(int accountId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE account_from = ? OR account_to = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId, accountId);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                transfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfers;
    }

    public List<Transfer> getPendingTransfersByAccount(int accountId){
        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfer WHERE account_from = ? AND transfer_type_id = 1 AND transfer_status_id = 1;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
            while (results.next()) {
                Transfer transfer = mapRowToTransfer(results);
                transfers.add(transfer);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return transfers;
    }

    @Override
    public Transfer createTransfer (Transfer transfer){
        Transfer newTransfer = null;
        String sql = "INSERT INTO transfer (transfer_type_id, transfer_status_id, account_from, account_to, amount) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";

        try{
            int transferId = jdbcTemplate.queryForObject(sql, int.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(),
                    transfer.getAccountFrom(), transfer.getAccountTo(), transfer.getAmount());
            newTransfer = getTransferByTransferId(transferId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newTransfer;
    }

    @Override
    public Transfer rejectTransfer (Transfer transfer) {
        int transferId = transfer.getTransferId();
        Transfer updatedTransfer = null;
        String sql = "UPDATE transfer SET transfer_status_id = 3 WHERE transfer_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, transferId);
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedTransfer = getTransferByTransferId(transfer.getTransferId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedTransfer;
    }

    @Override
    public Transfer approveTransfer (Transfer transfer) {
        int transferId = transfer.getTransferId();
        Transfer updatedTransfer = null;
        String sql = "UPDATE transfer SET transfer_status_id = 2 WHERE transfer_id = ?;";
        try {
            int rowsAffected = jdbcTemplate.update(sql, transferId);
            if (rowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one");
            }
            updatedTransfer = getTransferByTransferId(transfer.getTransferId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedTransfer;
    }

    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setAccountFrom(rs.getInt("account_from"));
        transfer.setAccountTo(rs.getInt("account_to"));
        transfer.setAmount(rs.getBigDecimal("amount"));

        int accountTo = rs.getInt("account_to");
        int accountFrom = rs.getInt("account_from");

        transfer.setUsernameTo(accountDao.getUsernameByAccountId(accountTo));
        transfer.setUsernameFrom(accountDao.getUsernameByAccountId(accountFrom));

        int transferStatusId = rs.getInt("transfer_status_id");
        int transferTypeId = rs.getInt("transfer_type_id");

        if (transferTypeId == 1){
            transfer.setTransferType("Request");
        }
        else{
            transfer.setTransferType("Send");
        }

        if(transferStatusId == 1){
            transfer.setTransferStatus("Pending");
        }
        else if (transferStatusId == 2){
            transfer.setTransferStatus("Approved");
        }
        else{
            transfer.setTransferStatus("Rejected");
        }


        return transfer;
    }


}
