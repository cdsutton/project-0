package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.PostAccountDTO;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DatabaseException;
import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountRepository {

	private Connection connection;
		
		public AccountRepository() {
			super();
		}
		
		public void setConnection(Connection connection) {
			this.connection = connection;
		}
		
		public Account addAccount(int clientId, PostAccountDTO accountDTO) throws DatabaseException {
			
			try (Connection connection = ConnectionUtil.getConnection()) {
				String sql = "INSERT INTO accounts (account_name, amount, client_id) VALUES (?, ?, ?)";
				
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, accountDTO.getAccountName());
				pstmt.setInt(2, accountDTO.getAmount());
				pstmt.setInt(3, clientId);
				
				int recordsAdded = pstmt.executeUpdate();
				
				if (recordsAdded != 1) {
					throw new DatabaseException("Couldn't add an account to the database");
				}
				
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					Account newAccount = new Account(id, accountDTO.getAccountName(), accountDTO.getAmount());
					return newAccount;
				} else {
					throw new DatabaseException("Account id was not generated, and therefore adding an account failed");
				}
				
			} catch (SQLException e) {
				throw new DatabaseException("Something went wrong with the database. "
						+ "Exception message: " + e.getMessage());
			}
			
		}
		
		public List<Account> getAccounts(int clientId) throws DatabaseException {
			Account account = null;
			List<Account> accounts = new ArrayList<>();
			try (Connection connection = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM accounts WHERE client_id = ?";
				
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setInt(1, clientId);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String retrievedAccountName = rs.getString("account_name");
					int retrievedAmount = rs.getInt("amount");
					account = new Account(id, retrievedAccountName, retrievedAmount);
					
					accounts.add(account);
				}
				
				return accounts;
				
			} catch (SQLException e) {
				throw new DatabaseException("Something went wrong with the database. "
						+ "Exception message: " + e.getMessage());
			}
			
		}
		
		public List<Account> getAccounts(int clientId, int amountLessThan, int amountGreaterThan) throws DatabaseException {
			Account account = null;
			List<Account> accounts = new ArrayList<>();
			try (Connection connection = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM accounts WHERE client_id = ? AND amount <= ? AND amount >= ?";
				
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setInt(1, clientId);
				pstmt.setInt(2, amountLessThan);
				pstmt.setInt(3, amountGreaterThan);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String retrievedAccountName = rs.getString("account_name");
					int retrievedAmount = rs.getInt("amount");
					account = new Account(id, retrievedAccountName, retrievedAmount);
					
					accounts.add(account);
				}
				
			} catch (SQLException e) {
				throw new DatabaseException("Something went wrong with the database. "
						+ "Exception message: " + e.getMessage());
			}
			
			return accounts;
			
		}
		
		public Account getAccountById(int clientId, int accountId) throws DatabaseException {
			Account account = null;
			try (Connection connection = ConnectionUtil.getConnection()) {
				String sql = "SELECT * FROM accounts WHERE client_id = ? AND id = ?";
				
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setInt(1, clientId);
				pstmt.setInt(2, accountId);
				
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					int id = rs.getInt("id");
					String retrievedAccountName = rs.getString("account_name");
					int retrievedAmount = rs.getInt("amount");
					account = new Account(id, retrievedAccountName, retrievedAmount);
				}
				
				return account;
				
			} catch (SQLException e) {
				throw new DatabaseException("Something went wrong when trying to get a connection. "
						+ "Exception message: " + e.getMessage());
			}
		}
		
		public Account updateAccount(int clientId, int accountId, PostAccountDTO accountDTO) throws BadParameterException, DatabaseException, AccountNotFoundException {
			try (Connection connection = ConnectionUtil.getConnection()) {
				String sql = "UPDATE accounts SET account_name = ?, amount = ? WHERE client_id = ? AND id = ?";
				
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				
				pstmt.setString(1, accountDTO.getAccountName());
				pstmt.setInt(2, accountDTO.getAmount());
				pstmt.setInt(3, clientId);
				pstmt.setInt(4, accountId);
				
				int recordsUpdated = pstmt.executeUpdate();
				
				if (recordsUpdated != 1) {
					throw new AccountNotFoundException("Couldn't find that account in the database");
				}
				
					Account newAccount = new Account(accountId, accountDTO.getAccountName(), accountDTO.getAmount());
					return newAccount;
				
			} catch (SQLException e) {
				throw new DatabaseException("Something went wrong when trying to get a connection. "
						+ "Exception message: " + e.getMessage());
			}
		}
		
		public void deleteAccount(int clientId, int accountId) throws BadParameterException, DatabaseException, AccountNotFoundException {
			try (Connection connection = ConnectionUtil.getConnection()) {
				String sql = "DELETE FROM accounts WHERE client_id = ? AND id = ?";
				
				PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				pstmt.setInt(1, clientId);
				pstmt.setInt(2, accountId);
				
				int recordsDeleted = pstmt.executeUpdate();
				
				if (recordsDeleted != 1) {
					throw new AccountNotFoundException("Couldn't find that account in the database");
				}
				
			} catch (SQLException e) {
				throw new DatabaseException("Something went wrong when trying to get a connection. "
						+ "Exception message: " + e.getMessage());
			}
		}
		
}