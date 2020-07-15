package com.techelevator.tenmo.model;

public class Account {
	private Long accountId;
	private Long userId;
	
	public Account(Long accountId, Long userId) {
		this.accountId = accountId;
		this.userId = userId;
	}
	
	public Account() {
		
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
