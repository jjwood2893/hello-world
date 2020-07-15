package com.techelevator.tenmo;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransactionService;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private TransactionService transactionService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL), new TransactionService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService, TransactionService transactionService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.transactionService = transactionService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		BigDecimal balance = transactionService.getBalance(currentUser);
		System.out.format("Your current balance is: $"+"%.2f",balance);
	}

	private void viewTransferHistory() {
		System.out.println("---------------------------------------");
		System.out.println("Transfers");
		System.out.format("%-10s %-15s %s\n", "ID", "From/To", "Amount");
		System.out.println("---------------------------------------");
		List<Transfer> transfers = transactionService.getTransfers(currentUser);
		if (transfers.size() == 0) {
			System.out.println("You have no transfers in your history.");
		}
		for (Transfer transfer:transfers) {
			String toOrFrom = transactionService.isTransferToOrFrom(transfer, currentUser.getUser().getId());
			toOrFrom = toOrFrom+transactionService.getUsernameById(toOrFrom, transfer);
			System.out.format("%-10s %-15s %s\n", transfer.getTransferId(), toOrFrom , transfer.getAmount());
		}
		System.out.println("-------------");
		if (transfers.size() > 0) {
			Transfer transfer = transactionService.seeTransferDetails(currentUser);
			boolean transferBelongsToUser = transactionService.belongsToUser(currentUser, transfer);
			if (transfer != null && transferBelongsToUser) {
				System.out.println("-------------------------------");
				System.out.println("Transfer Details");
				System.out.println("-------------------------------");
				System.out.println("Id: "+transfer.getTransferId());
				System.out.println("From: "+transactionService.getUsernameById("From", transfer));
				System.out.println("To: "+transactionService.getUsernameById("To", transfer));
				System.out.println("Type: "+transfer.getTypeString());
				System.out.println("Status: "+transfer.getStatusString());
				System.out.format("Amount: $"+"%.2f \n",transfer.getAmount());
				System.out.println("-------------------------------");
			} else {
				System.out.println("No transfer available for current user to view.");
			}
		}

	}

	private void viewPendingRequests() {
		System.out.println("---------------------------------------");
		System.out.println("Pending Transfers");
		System.out.format("%-10s %-15s %s\n", "ID", "From/To", "Amount");
		System.out.println("---------------------------------------");
		List<Transfer> transfers = transactionService.getTransfers(currentUser);
		boolean hasPending = false;
		for (Transfer transfer: transfers) {
			if (transfer.getStatus() == 1) {
				hasPending = true;
				String toOrFrom = transactionService.isTransferToOrFrom(transfer, currentUser.getUser().getId());
				toOrFrom = toOrFrom+transactionService.getUsernameById(toOrFrom, transfer);
				System.out.format("%-10s %-15s %s\n", transfer.getTransferId(), toOrFrom , transfer.getAmount());
			}
		}
		if (!hasPending) {
			System.out.println("You have no pending transfers.");
		}
		System.out.println("-------------");
		if (hasPending) {
			System.out.println(transactionService.approveOrReject(currentUser));
		}
		
		
	}

	private void sendBucks() {
		System.out.println("------------------------------");
		System.out.println("Users");
		System.out.format("%-10s %s\n", "ID", "Name");
		System.out.println("------------------------------");
		List<User> users = transactionService.getUsers();
		for (User user:users) {
			if (user.getId() != currentUser.getUser().getId()) {
				System.out.format("%-10s %s\n", user.getId(), user.getUsername());
			}
		}
		
		System.out.println("--------");
		String response = transactionService.transferMoney(currentUser);
		System.out.println(response);
	}

	private void requestBucks() {
		System.out.println("------------------------------");
		System.out.println("Users");
		System.out.format("%-10s %s\n", "ID", "Name");
		System.out.println("------------------------------");
		List<User> users = transactionService.getUsers();
		for (User user:users) {
			if (user.getId() != currentUser.getUser().getId()) {
				System.out.format("%-10s %s\n", user.getId(), user.getUsername());
			}
		}
		
		System.out.println("--------");
		String response = transactionService.requestMoney(currentUser);
	}
	
	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
