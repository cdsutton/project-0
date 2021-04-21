package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.dto.PostAccountDTO;
import com.revature.dto.PostClientDTO;
import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.service.AccountService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AccountController implements Controller {

	private Logger logger = LoggerFactory.getLogger(AccountController.class);
	private AccountService accountService;

	public AccountController() {
		this.accountService = new AccountService();
	}

	private Handler addAccount = ctx -> {
		PostAccountDTO accountDTO = ctx.bodyAsClass(PostAccountDTO.class);
		String clientId = ctx.pathParam("clientid");

		Account insertedAccount = this.accountService.addAccount(clientId, accountDTO);

		ctx.status(201);
		ctx.json(insertedAccount);
	};
	
	private Handler getAccounts = ctx -> {
		String id = ctx.pathParam("clientid");
		String lessId = ctx.queryParam("amountLessThan");
		String greaterId = ctx.queryParam("amountGreaterThan");
		
		List<Account> accounts = new ArrayList<>();
		
		if (lessId == null && greaterId == null) {
			accounts = this.accountService.getAccounts(id);
		} else {
			accounts = this.accountService.getAccounts(id, lessId, greaterId);
		}
		
		ctx.json(accounts);
		ctx.status(200);
	};
	
	private Handler getAccountById = ctx -> {
		String id1 = ctx.pathParam("clientid");
		String id2 = ctx.pathParam("accountid");
		
		Account account = accountService.getAccountById(id1, id2);
		
		ctx.json(account);
		ctx.status(200);
	};
	
	private Handler updateAccount = ctx -> {
		String id1 = ctx.pathParam("clientid");
		String id2 = ctx.pathParam("accountid");
		PostAccountDTO accountDTO = ctx.bodyAsClass(PostAccountDTO.class);
		
		Account account = this.accountService.updateAccount(id1, id2, accountDTO);
		
		ctx.json(account);
		ctx.status(200);
	};
	
	private Handler deleteAccount = ctx -> {
		String id1 = ctx.pathParam("clientid");
		String id2 = ctx.pathParam("accountid");
		
		this.accountService.deleteAccount(id1, id2);
		
		ctx.status(200);
	};
	
	@Override
	public void mapEndpoints(Javalin app) {
		app.post("/clients/:clientid/accounts", addAccount);
		app.get("/clients/:clientid/accounts", getAccounts);
		app.get("/clients/:clientid/accounts/:accountid", getAccountById);
		app.put("/clients/:clientid/accounts/:accountid", updateAccount);
		app.delete("/clients/:clientid/accounts/:accountid", deleteAccount);
	}

}