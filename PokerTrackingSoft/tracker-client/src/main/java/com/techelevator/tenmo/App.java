package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final int PENDING = 1;
    private final int APPROVED = 2;
    private final int REJECTED = 3;

    private final int REQUEST = 1;
    private final int SEND = 2;

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private final AccountService accountservice = new AccountService(API_BASE_URL);
    private final UserService userservice = new UserService(API_BASE_URL);

    private final TransferService transferservice = new TransferService(API_BASE_URL);

    private final EntryService entryService = new EntryService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private Account account = new Account();

    private Transfer transfer = new Transfer();

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser != null) {
            String token = currentUser.getToken();
            accountservice.setAuthToken(token);
            userservice.setAuthToken(token);
            transferservice.setAuthToken(token);
            entryService.setAuthToken(token);
            //Any Service needs an Auth Token
        }
        else{
            consoleService.printErrorMessage();
        }


    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                addSesssion();
            } else if (menuSelection == 2) {
                viewPastSessions();
            } else if (menuSelection == 3) {
                //viewPendingRequests();
            } else if (menuSelection == 4) {
                //sendBucks();
            } else if (menuSelection == 5) {
                //requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


    public void addSesssion (){
        int userId = currentUser.getUser().getId();
        BigDecimal amount = consoleService.promptForBigDecimal("Session win/lose (Negative number for loss): ");
        String gameSize = consoleService.promptForString("Please enter the blinds for the game EX(1/3): ");
        String gameType = consoleService.promptForString("Please enter a game type EX(NLH): ");
        String location = consoleService.promptForString("Please enter a location for the session: ");

        Entry sessionEntry = new Entry(userId, amount, gameSize, gameType, location);

        Entry addedSession = entryService.addSession(sessionEntry);

    }

    private void viewPastSessions(){
        int userId = currentUser.getUser().getId();
        Entry[] entries = entryService.getEntries(userId);

        if(entries != null){
            for (int i = 0; i < entries.length; i++){
                System.out.println(entries[i]);
            }
        }
        else{
            System.out.println("Entries is null");
        }

    }


/*    private void viewTransferHistory() {
        account = accountservice.getAccount();
        Transfer[] transfers = transferservice.getTransfers();
        //We'll need to loop through the list of transfers to the logged in user/account
        //ToString to display each transfer as needed
        if(transfers != null) {
            consoleService.viewTransferHeader();

            for (int i = 0; i < transfers.length; i++) {
                consoleService.printTransfer(transfers[i], account);
            }
            consoleService.printLineBreak();

            int transferToPrint = consoleService.promptForTransferId(transfers);
            if(transferToPrint != 0) {
                consoleService.transferDetailsHeader();
                for (int i = 0; i < transfers.length; i++) {
                    if (transfers[i].getTransferId() == transferToPrint) {
                        System.out.println(transfers[i]);
                    }
                }
            }
        }
        else{
            System.out.println("You have no past transfers!");
        }


    }

    private void viewPendingRequests() {
        account = accountservice.getAccount();
        Transfer[] transfers = transferservice.getPendingTransfers();
        //The transfer has a pending status, and you are the accountFrom
        if(transfers != null) {
            consoleService.pendingRequestHeader();
            for (int i = 0; i < transfers.length; i++) {
                consoleService.printPendingRequest(transfers[i]);
            }
            int transferToAction = consoleService.promptForPendingId(transfers);
            if (transferToAction != 0) { //0 to cancel
                consoleService.approveOrRejectPendingTransferHeader();
                int action = consoleService.selectAction();

                if (action == 1) {
                    // they want to approve transfer to action
                    // we need to, at the database, update transfer to action to APPROVED, which will update balances
                    for (int x = 0; x < transfers.length; x++) {
                        if (transfers[x].getTransferId() == transferToAction) {
                            if (account.getBalance().compareTo(transfers[x].getAmount()) >= 0) {
                                //We can accept transfer
                                //Only if you have enough money to process the transfer will we bother sending from client to server to database
                                transferservice.approveTransfer(transfers[x]);
                            } else {
                                System.out.println("Insufficient balance to process the transfer");
                            }
                        }
                    }
                }
                if (action == 2) {
                    // they want to reject transfer to action
                    // we need to, at the database, update transfer to action to REJECTED, no update to balances
                    for (int x = 0; x < transfers.length; x++) {
                        if (transfers[x].getTransferId() == transferToAction) {

                            transferservice.rejectTransfer(transfers[x]);
                        }
                    }
                }
            }
        }
        else {
            System.out.println("You have no pending transfers!");
        }
    }

    private void sendBucks() {
    account = accountservice.getAccount();
    User[] users = userservice.listUsers();
    Account[] accounts = accountservice.listAccounts();

    consoleService.printTransferHeader();
    for(int i = 0; i < users.length; i++){
        System.out.println(users[i].getId() + "          " + users[i].getUsername());
    }
    consoleService.printLineBreak();

    int toId = consoleService.promptForSendId(account, accounts);
    if (toId != 0) {
        BigDecimal transferAmt = consoleService.promptForAmount(account);
        Transfer transfer = transferservice.addTransfer(new Transfer(transferAmt, SEND, APPROVED, account.getUserId(), toId));
    }
    }

    private void requestBucks() {
        account = accountservice.getAccount();
        User[] users = userservice.listUsers();
        Account[] accounts = accountservice.listAccounts();

        consoleService.printTransferHeader();
        for(int i = 0; i < users.length; i++){
            System.out.println(users[i].getId() + "          " + users[i].getUsername());
        }
        consoleService.printLineBreak();

        int fromId = consoleService.promptForRequestId(account, accounts);
        if(fromId != 0 ) {
            BigDecimal transferAmt = consoleService.promptForAmount();
            Transfer transfer = transferservice.addTransfer(new Transfer(transferAmt, REQUEST, PENDING, fromId, account.getUserId()));
        }
    }*/

}
