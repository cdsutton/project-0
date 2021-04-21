package com.revature.service;

import java.util.List;

import com.revature.dao.ClientRepository;
import com.revature.dto.PostClientDTO;
import com.revature.exceptions.AddClientException;
import com.revature.exceptions.BadParameterException;
import com.revature.exceptions.DatabaseException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;

public class ClientService {
	
	private ClientRepository clientRepository;
	
	public ClientService() {
		super();
		this.clientRepository = new ClientRepository();
	}
	
	public ClientService(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}
	
	public Client addClient(PostClientDTO clientDTO) throws DatabaseException, AddClientException {
		if (clientRepository.getClientByName(clientDTO.getFirstName(), clientDTO.getLastName()) != null) {
			throw new AddClientException("User tried to add a client that already exists with that name");
		}
		
		if (clientDTO.getFirstName().trim().equals("") || clientDTO.getLastName().trim().equals("")) {
			throw new AddClientException("User tried to add a client with a blank name");
		}
		
		return clientRepository.addClient(clientDTO);
	}
	
	public List<Client> getClients() throws ClientNotFoundException, DatabaseException {
			List<Client> clients = clientRepository.getClients();
			
			if (clients == null) {
				throw new ClientNotFoundException("There are no clients in the database");
			}
			
			return clientRepository.getClients();
	}
	
	public Client getClientById(String stringId) throws DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			Client client = clientRepository.getClientById(id);
			
			if (client == null) {
				throw new ClientNotFoundException("Client with id of " + id + " was not found");
			}
			
			return client;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id must be an int. User provided " + stringId);
		}
	
	}
	
	public Client updateClient(String stringId, PostClientDTO clientDTO) throws DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			Client client = clientRepository.updateClient(id, clientDTO);
			
			if (client == null) {
				throw new ClientNotFoundException("Client with id of " + id + " was not found");
			}
			
			return client;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id must be an int. User provided " + stringId);
		}
	
	}
	
	public void deleteClient(String stringId) throws DatabaseException, BadParameterException, ClientNotFoundException {
		try {
			int id = Integer.parseInt(stringId);
			
			clientRepository.deleteClient(id);
			
			if (stringId == null) {
				throw new ClientNotFoundException("Client with id of " + id + " was not found");
			}

		} catch (NumberFormatException e) {
			throw new BadParameterException("Client id must be an int. User provided " + stringId);
		}
	
	}
	
}