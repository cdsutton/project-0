package com.revature.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.control.AccountController;
import com.revature.control.ControllerACB;
import com.revature.control.ExceptionController;
import com.revature.control.RealtorController;

import io.javalin.Javalin;

public class MainACB {

	private static Javalin app;
	private static Logger logger = LoggerFactory.getLogger(MainACB.class);

	public static void main(String[] args) {
		
		app = Javalin.create();
		
		app.before(ctx -> {
			String URI = ctx.req.getRequestURI();
			String httpMethod = ctx.req.getMethod();
			logger.info(httpMethod + " request to endpoint " + URI + " received");
		});
		
		mapControl(new AccountController(), new ExceptionController(), new RealtorController());
		
		app.start(7009);
		
	}

	public static void mapControl(ControllerACB... controllers) {
		for (ControllerACB c : controllers) {
			c.mapEndpoints(app);
		}
	}

}