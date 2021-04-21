package com.revature.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.revature.dao.AccountRepository;
import com.revature.dto.PostAccountDTO;
import com.revature.exceptions.AddAccountException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.model.Account;
import com.revature.util.ConnectionUtil;

public class AccountService {
	
	private AccountRepository accountRepository;
	
	public AccountService() {
		this.accountRepository = new AccountRepository();
	}
	
	public AccountService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	public Account addAccount(String stringId, PostAccountDTO accountDTO) throws BadParameterException, DatabaseException, AddAccountException {
		try {
			Connection connection = ConnectionUtil.getConnection();
			this.accountRepository.setConnection(connection);
			connection.setAutoCommit(false);
			
			if (accountDTO.getAccountName().trim().equals("")) {
				throw new AddAccountException("Account types cannot be blank");
			}
			
			try {
				int clientId = Integer.parseInt(stringId);
				
				Account account = accountRepository.addAccount(clientId, accountDTO);
				
				connection.commit();
				return account;
			} catch (NumberFormatException e) {
				throw new BadParameterException("Client id must be an int. User provided " + stringId);
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}

	}
	
	public List<Account> getAccounts(String stringId) throws AccountNotFoundException, DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			List<Account> accounts = accountRepository.getAccounts(id);	
			
			if (stringId == null) {
				throw new ClientNotFoundException("Client with id of " + id + " was not found");
			}
			
			if (accounts == null) {
				throw new AccountNotFoundException("There are no accounts in the database");
			}
			
			return accountRepository.getAccounts(id);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id must be an int. User provided " + stringId);
		}
	}
	
	public List<Account> getAccounts(String stringId, String amountLessThan, String amountGreaterThan) throws AccountNotFoundException, DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			int lessId = Integer.parseInt(amountLessThan);
			int greaterId = Integer.parseInt(amountGreaterThan);
			
			List<Account> accounts = accountRepository.getAccounts(id, lessId, greaterId);	
			
			if (stringId == null) {
				throw new ClientNotFoundException("Client with id of " + id + " was not found");
			}
			
			if (accounts == null) {
				throw new AccountNotFoundException("There are no accounts in the database");
			}
			
			return accounts;
			
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id must be an int. User provided " + stringId);
		}
	}
	
	public Account getAccountById(String clientId, String accountId) throws DatabaseException, BadParameterException, AccountNotFoundException, ClientNotFoundException {
		try {
			int id1 = Integer.parseInt(clientId);
			int id2 = Integer.parseInt(accountId);
			
			Account account = accountRepository.getAccountById(id1, id2);
			
			if (clientId == null) {
				throw new ClientNotFoundException("Client with id of " + id1 + " was not found");
			}
			
			if (accountId == null) {
				throw new AccountNotFoundException("Account with id of " + id2 + " was not found");
			}
			
			return account;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id and Account id must be an int. User provided " + clientId + " and " + accountId);
		}
	
	}
	
	public Account updateAccount(String clientId, String accountId, PostAccountDTO accountDTO) throws DatabaseException, BadParameterException, ClientNotFoundException, AccountNotFoundException {
		try {
			int id1 = Integer.parseInt(clientId);
			int id2 = Integer.parseInt(accountId);
			
			Account account = accountRepository.updateAccount(id1, id2, accountDTO);
			
			if (clientId == null) {
				throw new ClientNotFoundException("Client with id of " + id1 + " was not found");
			}
			
			if (accountId == null) {
				throw new AccountNotFoundException("Account with id of " + id2 + " was not found");
			}
			
			return account;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id and Account id must be an int. User provided " + clientId + " and " + accountId);
		}
	
	}
	
	public void deleteAccount(String clientId, String accountId) throws DatabaseException, BadParameterException, ClientNotFoundException, AccountNotFoundException {
		try {
			int id1 = Integer.parseInt(clientId);
			int id2 = Integer.parseInt(accountId);
			
			accountRepository.deleteAccount(id1, id2);
			
			if (clientId == null) {
				throw new ClientNotFoundException("Client with id of " + id1 + " was not found");
			}
			
			if (accountId == null) {
				throw new AccountNotFoundException("Account with id of " + id2 + " was not found");
			}

		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id and Account id must be an int. User provided " + clientId + " and " + accountId);
		}
	
	}
	
}