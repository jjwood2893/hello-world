package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {
	private Long transferId;
	private Long idFrom;
	private Long idTo;
	private int type;
	//1 = request
	//2 = send
	private int status = 1;
	//1 = pending
	//2 = approved
	//3 = rejected
	private BigDecimal amount;
	
	public Transfer(Long idFrom, Long idTo, int type, BigDecimal amount) {
		this.idFrom = idFrom;
		this.idTo = idTo;
		this.type = type;
		this.amount = amount;
	}
	
	public Transfer() {
		
	}
	
	public Long getTransferId() {
		return transferId;
	}
	public void setTransferId(Long transferId) {
		this.transferId = transferId;
	}
	public Long getIdFrom() {
		return idFrom;
	}
	public void setIdFrom(Long idFrom) {
		this.idFrom = idFrom;
	}
	public Long getIdTo() {
		return idTo;
	}
	public void setIdTo(Long idTo) {
		this.idTo = idTo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
