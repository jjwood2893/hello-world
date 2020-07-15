package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Account;

@Service
public class AccountSqlDAO implements AccountDAO{
	private JdbcTemplate jdbcTemplate;
	private UserDAO userDAO;
	
	public AccountSqlDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
		this.jdbcTemplate = jdbcTemplate;
		this.userDAO = userDAO;
	}
	@Override
	public BigDecimal findBalanceByAccountId(Long userId) {
		return jdbcTemplate.queryForObject("select balance from accounts where user_id = ?", BigDecimal.class, userId);
	}
	@Override
	public Account findByUsername(String username) {
	    int userId = userDAO.findIdByUsername(username);
	    SqlRowSet results = jdbcTemplate.queryForRowSet("select account_id, user_id from accounts where user_id = ?", userId);
	    Account account = mapRowToAccount(results);
	    return account;
	}
	
	
	@Override 
	public int findByUserId(Long userId){
		String sql = "select account_id from accounts where user_id = ?;";
		int accountId = 0;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			accountId = results.getInt("account_id");
		}
		else if (accountId == 0) {
		}
		return accountId;
	}
	private Account mapRowToAccount(SqlRowSet results) {
		Account account = new Account();
		account.setAccountId(results.getLong("account_id"));
		account.setUserId(results.getLong("user_id"));
		return account;
	}


}
