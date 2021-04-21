package com.revature.controller;

import java.util.List;

import com.revature.dto.PostClientDTO;
import com.revature.model.Client;
import com.revature.service.ClientService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ClientController implements Controller {

	private ClientService clientService;
	
	public ClientController() {
		this.clientService = new ClientService();
	}
	
	private Handler addClient = ctx -> {
		PostClientDTO clientDTO = ctx.bodyAsClass(PostClientDTO.class);
		
		Client client = this.clientService.addClient(clientDTO);
		
		ctx.json(client);
		ctx.status(201);
	};
	
	private Handler getClients = ctx -> {
		List<Client> clients = this.clientService.getClients();
		
		ctx.json(clients);
		ctx.status(200);
	};
	
	private Handler getClientById = ctx -> {
		String id = ctx.pathParam("clientid");
		
		Client client = this.clientService.getClientById(id);
		
		ctx.json(client);
		ctx.status(200);
	};
	
	private Handler updateClient = ctx -> {
		String id = ctx.pathParam("clientid");
		PostClientDTO clientDTO = ctx.bodyAsClass(PostClientDTO.class);
		
		Client client = this.clientService.updateClient(id, clientDTO);
		
		ctx.json(client);
		ctx.status(200);
	};
	
	private Handler deleteClient = ctx -> {
		String id = ctx.pathParam("clientid");
		
		this.clientService.deleteClient(id);
		
		ctx.status(200);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/clients", addClient);
		app.get("/clients", getClients);
		app.get("/clients/:clientid", getClientById);
		app.put("/clients/:clientid", updateClient);
		app.delete("/clients/:clientid", deleteClient);
	}

}