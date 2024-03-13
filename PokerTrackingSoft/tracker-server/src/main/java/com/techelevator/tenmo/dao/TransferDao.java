package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface TransferDao {
    Transfer createTransfer (Transfer transfer);

    Transfer getTransferByTransferId(int id);

    List<Transfer> getTransfersByAccount(int accountId);

    List<Transfer> getPendingTransfersByAccount(int accountId);

    Transfer rejectTransfer (Transfer transfer);

    Transfer approveTransfer (Transfer transfer);


}
