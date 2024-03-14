package com.techelevator.pokertracker.dao;

import com.techelevator.pokertracker.model.Transfer;

import java.util.List;

public interface TransferDao {
    Transfer createTransfer (Transfer transfer);

    Transfer getTransferByTransferId(int id);

    List<Transfer> getTransfersByAccount(int accountId);

    List<Transfer> getPendingTransfersByAccount(int accountId);

    Transfer rejectTransfer (Transfer transfer);

    Transfer approveTransfer (Transfer transfer);


}
