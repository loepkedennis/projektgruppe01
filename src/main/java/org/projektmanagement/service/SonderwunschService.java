package org.projektmanagement.service;

import org.projektmanagement.config.ApplicationContextProvider;
import org.projektmanagement.handler.SonderwuenschHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SonderwunschService {

	private SonderwuenschHandler sonderwunschHandler;
	private ApplicationContext ctx;

	public SonderwunschService() {
		// TODO Auto-generated constructor stub
		ctx = new ApplicationContextProvider().getApplicationContext();
		sonderwunschHandler = ctx.getBean(SonderwuenschHandler.class);
		// kundenHandler = new KundenHandler();
	}

	public SonderwuenschHandler getSonderwunschHandler() {
		return sonderwunschHandler;
	}
}
