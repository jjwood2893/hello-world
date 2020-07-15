package com.techelevator.tenmo.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@RestController
public class TransactionController {
	private UserDAO userDAO;
	private AccountDAO accountDAO;
	private TransferDAO transferDAO;
	
	public TransactionController(UserDAO userDAO, AccountDAO accountDAO, TransferDAO transferDAO) {
		this.userDAO = userDAO;
		this.accountDAO = accountDAO;
		this.transferDAO = transferDAO;
	}
	
	@RequestMapping(path = "/balance/{userId}", method = RequestMethod.GET)
	public BigDecimal balance(@PathVariable Long userId) {
		return accountDAO.findBalanceByAccountId(userId);
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userDAO.findAll();
	}
	
	@RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
	public String getUsername(@PathVariable int userId) {
		return userDAO.findUsernameById(userId);
	}
	
	@RequestMapping(path = "/account/{userId}", method = RequestMethod.GET)
	public int getAccountIdByUserId(@PathVariable Long userId) {
		return accountDAO.findByUserId(userId);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path = "/transfer", method = RequestMethod.POST)
	public Transfer createTransfer(@RequestBody Transfer transfer) {
		return transferDAO.createTransfer(transfer.getIdFrom(), transfer.getIdTo(), transfer.getType(), transfer.getAmount());
	}
	

	@RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.PUT)
	public void updateTransfer(@RequestBody Transfer transfer, @PathVariable Long transferId) {
		transferDAO.updateTransfer(transfer);
	}
	
	@RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.GET)
	public Transfer getTransferDetails(@PathVariable Long transferId) {
		return transferDAO.getTransferById(transferId);
	}
	
	@RequestMapping(path = "/transferlog/{userId}", method = RequestMethod.GET)
	public List<Transfer> getAllTransfersForUser(@PathVariable Long userId) {
		return transferDAO.getAllTransfersForUser(userId);
	}
	
	@RequestMapping(path = "/transfer/reject/{transferId}", method = RequestMethod.PUT)
	public void rejectRequest(@RequestBody Transfer transfer, @PathVariable Long transferId) {
		transferDAO.rejectRequest(transfer.getTransferId());
	}
}
