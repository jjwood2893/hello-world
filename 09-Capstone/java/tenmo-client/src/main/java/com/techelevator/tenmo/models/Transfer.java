package com.techelevator.tenmo.models;

import java.math.BigDecimal;

public class Transfer {
	private Long transferId;
	private Long idFrom;
	private Long idTo;
	private int type = 2;
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
	public String getTypeString() {
		if (this.type == 1) return "Request";
		if (this.type == 2) return "Send";
		else return null;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusString() {
		if (this.status == 1) return "Pending";
		if (this.status == 2) return "Approved";
		if (this.status == 3) return "Rejected";
		else return null;
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
