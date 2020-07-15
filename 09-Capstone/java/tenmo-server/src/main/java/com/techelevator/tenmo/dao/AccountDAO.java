package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

import com.techelevator.tenmo.model.Account;

public interface AccountDAO {
	
	BigDecimal findBalanceByAccountId(Long accountId);
	
	Account findByUsername(String username);
	
	public int findByUserId(Long userId);
}
