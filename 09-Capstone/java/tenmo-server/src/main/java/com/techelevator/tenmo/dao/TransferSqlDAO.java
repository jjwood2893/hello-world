package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

@Component
public class TransferSqlDAO implements TransferDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Transfer updateTransfer(Transfer transfer) {
		String sql = "BEGIN TRANSACTION;\r\n" + 
				"update accounts set balance = balance - ? WHERE account_id = ?;\r\n" + 
				"update accounts set balance = balance + ? WHERE account_id = ?;\r\n" +
				"update transfers set transfer_status_id = 2 WHERE transfer_id = ?;\r\n" +
				"COMMIT;";
		jdbcTemplate.update(sql, transfer.getAmount(), transfer.getIdFrom(), transfer.getAmount(), transfer.getIdTo(), transfer.getTransferId());
		transfer.setStatus(2);
		return transfer;
	}

	@Override
	public Transfer createTransfer(Long idFrom, Long idTo, int type, BigDecimal amount) {
		Transfer transfer = new Transfer(idFrom, idTo, type, amount);
		String sql = "INSERT INTO transfers (account_from, account_to, transfer_type_id, transfer_status_id, amount) VALUES (?,?,?,?,?) RETURNING transfer_id;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, idFrom, idTo, type, transfer.getStatus(), amount);
		if (results.next()) {
			transfer.setTransferId(results.getLong("transfer_id"));
		}
		return transfer;
	}

	@Override
	public List<Transfer> getAllTransfersForUser(Long userId) {
		List<Transfer> transfers = new ArrayList<>();
		String sql = "select transfer_id, account_from, account_to, transfer_type_id, transfer_status_id, amount from transfers WHERE account_from = ? OR account_to = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
		while (results.next()) {
			Transfer currentTransfer = mapRowToTransfer(results);
			transfers.add(currentTransfer);
		}
		return transfers;
	}
	
	@Override
	public Transfer getTransferById(Long transferId) {
		String sql = "select transfer_id, account_from, account_to, transfer_type_id, transfer_status_id, amount from transfers WHERE transfer_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
		if (results.next()) return mapRowToTransfer(results);
		else return null;
	}
	
	@Override
	public void rejectRequest(Long transferId) {
		String sql = "update transfers set transfer_status_id = 3 WHERE transfer_id = ?;";
		jdbcTemplate.update(sql, transferId);
		
	}
	
    private Transfer mapRowToTransfer(SqlRowSet results) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getLong("transfer_id"));
        transfer.setIdFrom(results.getLong("account_from"));
        transfer.setIdTo(results.getLong("account_to"));
        transfer.setAmount(results.getBigDecimal("amount"));
        transfer.setStatus(results.getInt("transfer_status_id"));
        transfer.setType(results.getInt("transfer_type_id"));
        return transfer;
    }



}
