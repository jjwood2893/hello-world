package com.techelevator.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.techelevator.model.User;

@Service
public class UserSqlDAO implements UserDAO {

	private JdbcTemplate jdbcTemplate;

	private static final String ALL_FIELDS = "user_id, username, password_hash, role";

	public UserSqlDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public User findByUsername(String username) throws UsernameNotFoundException {
		String sql = "SELECT " + ALL_FIELDS + " FROM users WHERE username = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);
		if (rs.next()) {
			return mapRowToUser(rs);
		} else {
			throw new UsernameNotFoundException("User " + username + " was not found.");
		}
	}

	@Override
	public boolean usernameExists(String username) {
		String sql = "SELECT 1 FROM users WHERE username = ?";
		SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, username);
		return rs.next();
	}

	@Override
	public boolean create(String username, String password, String role) {
		boolean userCreated = false;
		String sql = "INSERT INTO users (username,password_hash,role) VALUES (?,?,?)";
		String password_hash = new BCryptPasswordEncoder().encode(password);
		String ssRole = "ROLE_" + role.toUpperCase();

		try {
			int count = jdbcTemplate.update(sql, username, password_hash, ssRole);
			userCreated = (count == 1);
		} catch (DataAccessException e) {
			// userCreated remains false
		}
		return userCreated;
	}

	private User mapRowToUser(SqlRowSet rs) {
		User user = new User();
		user.setId(rs.getLong("user_id"));
		user.setUsername(rs.getString("username"));
		user.setPassword(rs.getString("password_hash"));
		user.setAuthorities(rs.getString("role"));
		user.setActivated(true);
		return user;
	}

	public void updateUserRole(String username) {
		String sql = "UPDATE users SET role = 'ROLE_PREMIUM' WHERE username = ?";
		jdbcTemplate.update(sql, username);

	}
}
