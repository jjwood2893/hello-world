package com.techelevator.tenmo.services;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.User;
import com.techelevator.view.ConsoleService;

public class TransactionService {
	private String AUTH_TOKEN = "";
	private String BASE_URL;
	private RestTemplate restTemplate = new RestTemplate();
	private ConsoleService console = new ConsoleService(System.in, System.out);
	
	public TransactionService(String url) {
		BASE_URL = url;
	}
	
	public BigDecimal getBalance(AuthenticatedUser user) {
		BigDecimal balance = new BigDecimal(0);
		AUTH_TOKEN = user.getToken();
		balance = restTemplate.exchange(BASE_URL+"balance/"+user.getUser().getId(),  HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();
		return balance;
	}
	
	public List<User> getUsers() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String users = restTemplate.exchange(BASE_URL+"users", HttpMethod.GET, makeAuthEntity(), String.class).getBody();
		List<User> userList = null;
		try {
			userList = Arrays.asList(mapper.readValue(users, User[].class));
		} catch (JsonMappingException e) {
			System.out.println("There was an error.");
		} catch (JsonProcessingException e) {
			System.out.println("There was an error.");
		}
		return userList;
	}
	
	public String getUsernameById(String toOrFrom, Transfer transfer) {
		Long userId = (long) 0;
		if (toOrFrom.contains("From")) {
			userId = transfer.getIdFrom();
		}
		if (toOrFrom.contains("To")) {
			userId = transfer.getIdTo();
		}
		return restTemplate.exchange(BASE_URL+"/users/"+userId, HttpMethod.GET, makeAuthEntity(), String.class).getBody();
	}
	
	public String transferMoney(AuthenticatedUser user) {
		String response = "";
		int idTo = -1;
		BigDecimal amountToTransfer = null;
		boolean validChoice = false;
		AUTH_TOKEN = user.getToken();
		while (!validChoice) {
			idTo = console.getUserInputInteger("Enter id of user you are sending to (0 to cancel)");
			if (idTo == 0) {
				break;
			}
			try {
				String userInput = console.getUserInput("Enter amount");
				amountToTransfer = new BigDecimal(userInput);
				if (getBalance(user).compareTo(amountToTransfer) < 0 || amountToTransfer.compareTo(BigDecimal.ZERO) <= 0) {
					Exception e = new Exception();
					throw e;
				}
				validChoice = true;
			} catch (NumberFormatException e) {
				System.out.println("That choice is not valid. Please enter a positive number with up to 2 decimal points.");
			} catch (Exception e) {
				System.out.println("You do not have enough money for transfer or you have tried to send a negative number.");
			}
		}
		if (validChoice) {
			try {
				Transfer transfer = new Transfer();
				transfer = createTransfer(user, idTo, amountToTransfer, transfer);
				restTemplate.exchange(BASE_URL+"transfer/"+transfer.getTransferId(), HttpMethod.PUT, makeAuthEntityWithBody(transfer), Transfer.class, transfer.getTransferId());
				response = "Transaction completed.";
			} catch (RestClientResponseException e) {
				response = "There was an error.";
			} catch (ResourceAccessException e) {
				response = "There was an error.";
			}
		}
		return response;
	}
	
	public String requestMoney(AuthenticatedUser user) {
		String response = "";
		int idTo = -1;
		BigDecimal amountToTransfer = null;
		boolean validChoice = false;
		AUTH_TOKEN = user.getToken();
		while (!validChoice) {
			idTo = console.getUserInputInteger("Enter id of user you are requesting from (0 to cancel)");
			if (idTo == 0) {
				break;
			}
			try {
				String userInput = console.getUserInput("Enter amount");
				amountToTransfer = new BigDecimal(userInput);
				if (amountToTransfer.compareTo(BigDecimal.ZERO) <= 0) {
					Exception e = new Exception();
					throw e;
				}
				validChoice = true;
			} catch (Exception e) {
				System.out.println("That choice is not valid. Please enter a positive number with up to 2 decimal points.");
			}
		}
		if (validChoice) {
			try {
				Transfer transfer = new Transfer();
				transfer.setType(1);
				transfer = createTransfer(user, idTo, amountToTransfer, transfer);
				response = "Transfer request completed. Transfer is pending.";
			} catch (RestClientResponseException e) {
				response = "There was an error.";
			} catch (ResourceAccessException e) {
				response = "There was an error.";
			}
		}
		return response;
	}
	
	public String approveOrReject(AuthenticatedUser user) {
		String response = "";
		boolean validChoice = false;
		Transfer transfer = new Transfer();
		while (!validChoice) {
			int transferId = console.getUserInputInteger("Enter transfer ID to approve or reject (0 to cancel)");
			if (transferId == 0) {
				break;
			}
			try {
				transfer = restTemplate.exchange(BASE_URL+"transfer/"+transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
			} catch (RestClientResponseException e) {
				System.out.println("There was an error.");
			} catch (ResourceAccessException e) {
				System.out.println("There was an error.");
			} catch (NullPointerException e) {
				System.out.println("This is not a valid pending transfer for your account.");
			}
			if (transfer.getStatus() == 1 && transfer.getType() == 1 && transfer.getIdTo() == (long) user.getUser().getId()) {
				validChoice = true;
			}
			else {
				validChoice = false;
				System.out.println("This is not a valid pending transfer for your account.");
			}
		}
		if (validChoice) {
			System.out.println("1: Approve");
			System.out.println("2: Reject");
			System.out.println("0: Don't approve or reject");
			System.out.println("-------------");
			
			boolean validApproveChoice = false;
			while (!validApproveChoice) {
				int choice = console.getUserInputInteger("Please choose an option");
				if (choice == 1) {
					if (getBalance(user).compareTo(transfer.getAmount()) >= 0) {
						restTemplate.exchange(BASE_URL+"transfer/"+transfer.getTransferId(), HttpMethod.PUT, makeAuthEntityWithBody(transfer), Transfer.class, transfer.getTransferId());
						response = "Transaction completed. Request approved.";
						validApproveChoice = true;
					} else {
						System.out.println("Not enough funds in account to approve transfer.");
					}

				}
				if (choice == 2) {
					restTemplate.exchange(BASE_URL+"transfer/reject/"+transfer.getTransferId(), HttpMethod.PUT, makeAuthEntityWithBody(transfer), Transfer.class, transfer.getTransferId());
					response = "Transaction completed. Request denied.";
					validApproveChoice = true;
				}
				if (choice == 0) {
					break;
				}
				else {
					System.out.println("Please choose one of the options above.");
				}
			}

		}
		return response;
	}
	
	public List<Transfer> getTransfers(AuthenticatedUser user) {
		AUTH_TOKEN = user.getToken();
		ObjectMapper mapper = new ObjectMapper();
		String transfers = restTemplate.exchange(BASE_URL+"transferlog/"+user.getUser().getId(), HttpMethod.GET, makeAuthEntity(), String.class).getBody();
		List<Transfer> transferList = null;
		try {
			transferList = Arrays.asList(mapper.readValue(transfers, Transfer[].class));
		} catch (JsonMappingException e) {
			System.out.println("There was an error.");
		} catch (JsonProcessingException e) {
			System.out.println("There was an error.");
		}
		return transferList;
	}
	
	public String isTransferToOrFrom(Transfer transfer, Integer userId) {
		Long longId = userId.longValue();
		String response = "";
		if (transfer.getIdFrom().compareTo(longId) == 0) {
			response = "To: ";
		}
		if (transfer.getIdTo().compareTo(longId) == 0) {
			response = "From: ";
		}
		return response;
	}
	
	public Transfer seeTransferDetails(AuthenticatedUser user) {
		AUTH_TOKEN = user.getToken();
		int transferId = -1;
		Transfer transfer = new Transfer();
		boolean validChoice = false;
		while (!validChoice) {
			transferId = console.getUserInputInteger("Enter transfer ID to view details (0 to cancel)");
			if (transferId == 0) {
				return null;
			}
			else {
				try {
					transfer = restTemplate.exchange(BASE_URL+"transfer/"+transferId, HttpMethod.GET, makeAuthEntity(), Transfer.class).getBody();
					validChoice = true;
				} catch (RestClientResponseException e) {
					System.out.println("There was an error.");
				} catch (ResourceAccessException e) {
					System.out.println("There was an error.");
				} catch (NullPointerException e) {
					System.out.println("The transfer you are looking for doesn't exist.");
				}
			}
		}
		return transfer;
	}
	
	public boolean belongsToUser(AuthenticatedUser user, Transfer transfer) {
		if (transfer != null) {
			boolean fromCurrentUser = transfer.getIdFrom() == (long) user.getUser().getId();
			boolean toCurrentUser = transfer.getIdTo() == (long) user.getUser().getId();
			return fromCurrentUser||toCurrentUser;
		}
		else return false;
	}
	
	private HttpEntity makeAuthEntity() {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(headers);
		return entity;
	}
	
	private HttpEntity makeAuthEntityWithBody(Object obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(AUTH_TOKEN);
		HttpEntity entity = new HttpEntity<>(obj, headers);
		return entity;
	}
	
	private Transfer createTransfer(AuthenticatedUser user, int idTo, BigDecimal amountToTransfer, Transfer transfer) {
		AUTH_TOKEN = user.getToken();
		long accountIdTo = restTemplate.exchange(BASE_URL+"account/"+idTo, HttpMethod.GET, makeAuthEntityWithBody(idTo), long.class).getBody();
		transfer.setIdTo(accountIdTo);
		long accountIdFrom = restTemplate.exchange(BASE_URL+"account/"+user.getUser().getId(), HttpMethod.GET, makeAuthEntityWithBody(user.getUser().getId()), long.class).getBody();
		transfer.setIdFrom(accountIdFrom);
		transfer.setAmount(amountToTransfer);
		transfer = restTemplate.exchange(BASE_URL+"transfer", HttpMethod.POST, makeAuthEntityWithBody(transfer), Transfer.class).getBody();
		return transfer;
	}
}
