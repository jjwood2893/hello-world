package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDAO {
	
	public Transfer createTransfer(Long idFrom, Long idTo, int type, BigDecimal amount);
	
	public Transfer updateTransfer(Transfer transfer);
	
	public List<Transfer> getAllTransfersForUser(Long userId);
	
	public Transfer getTransferById(Long transferId);
	
	public void rejectRequest(Long transferId);
}
