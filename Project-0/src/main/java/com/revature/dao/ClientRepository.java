package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.dto.PostClientDTO;
import com.revature.exceptions.AddClientException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import com.revature.util.ConnectionUtil;

public class ClientRepository {
	
	public Client addClient(PostClientDTO clientDTO) throws DatabaseException, AddClientException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "INSERT INTO clients (first_name, last_name) VALUES (?, ?)";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, clientDTO.getFirstName());
			pstmt.setString(2, clientDTO.getLastName());
			
			int recordsAdded = pstmt.executeUpdate();
			
			if (recordsAdded != 1) {
				throw new DatabaseException("Couldn't add a client to the database");
			}
			
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				Client newClient = new Client(id, clientDTO.getFirstName(), clientDTO.getLastName());
				newClient.setAccounts(new ArrayList<>());
				return newClient;
			} else {
				throw new DatabaseException("Client id was not generated, and therefore adding a client failed");
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}
	}

	public Client getClientByName(String firstName, String lastName) throws DatabaseException {
		Client client = null;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM clients c WHERE c.first_name = ? AND c.last_name = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				String retrievedFirstName = rs.getString("first_name");
				String retrievedLastName = rs.getString("last_name");
				client = new Client(id, retrievedFirstName, retrievedLastName);
			}
			
			return client;
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public List<Client> getClients() throws DatabaseException {
		Client client = null;
		List<Client> clients = new ArrayList<>();
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM clients";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String retrievedFirstName = rs.getString("first_name");
				String retrievedLastName = rs.getString("last_name");
				client = new Client(id, retrievedFirstName, retrievedLastName);
				
				clients.add(client);
			}
			
			return clients;
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public Client getClientById(int clientId) throws DatabaseException {
		Client client = null;
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "SELECT * FROM clients c WHERE c.id = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, clientId);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				String retrievedFirstName = rs.getString("first_name");
				String retrievedLastName = rs.getString("last_name");
				client = new Client(id, retrievedFirstName, retrievedLastName);
			}
			
			return client;
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public Client updateClient(int clientId, PostClientDTO clientDTO) throws BadParameterException, DatabaseException, ClientNotFoundException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "UPDATE clients c SET first_name = ?, last_name = ? WHERE c.id = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setString(1, clientDTO.getFirstName());
			pstmt.setString(2, clientDTO.getLastName());
			pstmt.setInt(3, clientId);
			
			int recordsUpdated = pstmt.executeUpdate();
			
			if (recordsUpdated != 1) {
				throw new ClientNotFoundException("Couldn't find that client in the database");
			}
			
				Client newClient = new Client(clientId, clientDTO.getFirstName(), clientDTO.getLastName());
				return newClient;
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
	public void deleteClient(int clientId) throws BadParameterException, DatabaseException, ClientNotFoundException {
		try (Connection connection = ConnectionUtil.getConnection()) {
			String sql = "DELETE FROM clients WHERE id = ?";
			
			PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			pstmt.setInt(1, clientId);
			
			int recordsDeleted = pstmt.executeUpdate();
			
			if (recordsDeleted != 1) {
				throw new ClientNotFoundException("Couldn't find that client in the database");
			}
			
		} catch (SQLException e) {
			throw new DatabaseException("Something went wrong when trying to get a connection. "
					+ "Exception message: " + e.getMessage());
		}
	}
	
}