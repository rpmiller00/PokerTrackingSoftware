package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

    }

    public void printTransferHeader() {
        System.out.println("*********************");
        System.out.println("Users");
        System.out.println("Id            Name");
        System.out.println("*********************");

    }

    public void printLineBreak() {
        System.out.println("*********************");
    }

    public int promptForSendId(Account account, Account[] accounts) {
        boolean keepGoing = true;
        int accountTo = 0;
        while (keepGoing) {
            accountTo = promptForInt("Please enter ID you'd like to send to (0 to cancel): ");
            if(accountTo == 0) {
                keepGoing = false;
                continue;
            }
            if (accountTo == account.getUserId()) {
                System.out.println("You cannot send money to yourself");
            } else {
                for (int i = 0; i < accounts.length; i++) {
                    if (accountTo == accounts[i].getUserId()) {
                        keepGoing = false;
                    }
                }
                if (keepGoing != false) {
                    System.out.println("User Id not valid");
                }
            }
        }
        return accountTo;
    }

    public int promptForRequestId(Account account, Account[] accounts) {
        boolean keepGoing = true;
        int accountFrom = 0;
        while (keepGoing) {
            accountFrom = promptForInt("Please enter ID you'd like to request from (0 to cancel): ");
            if(accountFrom == 0) {
                keepGoing = false;
                continue;
            }
            if (accountFrom == account.getUserId()) {
                System.out.println("You cannot request money from yourself");
            } else {
                for (int i = 0; i < accounts.length; i++) {
                    if (accountFrom == accounts[i].getUserId()) {
                        keepGoing = false;
                    }
                }
                if (keepGoing != false) {
                    System.out.println("User Id not valid");
                }
            }
        }
        return accountFrom;
    }

    public BigDecimal promptForAmount(Account account) {
        boolean keepGoing = true;
        BigDecimal amount = null;
        while (keepGoing) {
            amount = promptForBigDecimal("Enter an amount to transfer: ");
            if ((amount.compareTo(account.getBalance()) <= 0) && (amount.compareTo(BigDecimal.ZERO) > 0)) {
                keepGoing = false;
            } else {
                System.out.println("Invalid Input, Try Again");
            }
        }
        return amount;
    }

    public BigDecimal promptForAmount() {
        boolean keepGoing = true;
        BigDecimal amount = null;
        while (keepGoing) {
            amount = promptForBigDecimal("Enter an amount to request: ");
            if ((amount.compareTo(BigDecimal.ZERO) > 0)) {
                keepGoing = false;
            } else {
                System.out.println("Invalid Input, Try Again");
            }
        }
        return amount;
    }

    public int promptForTransferId(Transfer[] transfers) {
        boolean keepGoing = true;
        int transferToView = 0;
        while (keepGoing) {
            transferToView = promptForInt("Please enter transfer ID to view details (0 to cancel): ");
            if(transferToView == 0) {
                keepGoing = false;
                continue;
            }

            for (int i = 0; i < transfers.length; i++) {
                if (transferToView == transfers[i].getTransferId()) {
                    keepGoing = false;
                }
            }
            if (keepGoing != false) {
                System.out.println("Please enter a valid transfer id");
            }

        }
        return transferToView;
    }

    public int promptForPendingId(Transfer[] transfers) {
        boolean keepGoing = true;
        int pendingToView = 0;
        while (keepGoing) {
            pendingToView = promptForInt("Please enter transfer ID to approve/reject (0 to cancel): ");
            if(pendingToView == 0) {
                keepGoing = false;
                continue;
            }

            for (int i = 0; i < transfers.length; i++) {
                if (pendingToView == transfers[i].getTransferId()) {
                    keepGoing = false;
                }
            }
            if (keepGoing != false) {
                System.out.println("Please enter a valid pending id");
            }
        }
        return pendingToView;
    }

    public void viewTransferHeader() {
        System.out.println("*********************************");
        System.out.println("Transfers");
        System.out.println("ID       From/To          Amount");
        System.out.println("*********************************");
    }
    public void transferDetailsHeader(){
        System.out.println("*********************************");
        System.out.println("Transfer Details");
        System.out.println("*********************************");


    }

    public void pendingRequestHeader() {
        System.out.println("*********************************");
        System.out.println("Pending Transfers");
        System.out.println("ID       To                Amount");
        System.out.println("*********************************");
    }

    public void approveOrRejectPendingTransferHeader() {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("**************************");
    }

    public int selectAction() {
        int inputConverted = 0;
        boolean badInput = true;
        while (badInput) {
            System.out.print("Please choose an option: ");
            String initialInput = scanner.nextLine();
            try {
                inputConverted = Integer.parseInt(initialInput);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (inputConverted == 1 || inputConverted == 2 || inputConverted == 0) {
                badInput = false;
            } else {
                System.out.println("Invalid Selection try again");
            }
        }
        return inputConverted;
    }

    public void printTransfer(Transfer transfer, Account account) {
        //If i'm the to Id I received it, If I'm the from Id i sent it
        // Were thinking you would have to handle sends and requests differently but settled on not needing to
       // if (transfer.getTransferTypeId()== 2) {

            if (account.getAccountId() == transfer.getAccountTo()) {
                System.out.println(transfer.getTransferId() + "     " + "From: " + transfer.getUsernameFrom() + "       " + "$" + transfer.getAmount());
            }
            if (account.getAccountId() == transfer.getAccountFrom()) {
                System.out.println(transfer.getTransferId() + "     " + "To: " + transfer.getUsernameTo() + "         " + "$" + transfer.getAmount());
            }
       // }
       /* else{
            if (account.getAccountId() == transfer.getAccountTo()) {
                System.out.println(transfer.getTransferId() + "     " + "To: " + transfer.getUsernameFrom() + "       " + "$" + transfer.getAmount());
            }
            if (account.getAccountId() == transfer.getAccountFrom()) {
                System.out.println(transfer.getTransferId() + "     " + "From: " + transfer.getUsernameTo() + "         " + "$" + transfer.getAmount());
            }

        }*/

    }

    public void printPendingRequest(Transfer transfer) {
        System.out.println(transfer.getTransferId() + "     " + "To: " + transfer.getUsernameTo() + "         " + "$" + transfer.getAmount());
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }



}
