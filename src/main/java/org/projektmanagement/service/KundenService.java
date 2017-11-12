package org.projektmanagement.service;

import org.projektmanagement.config.ApplicationContextProvider;
import org.projektmanagement.handler.KundenHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class KundenService {

	private KundenHandler kundenHandler;
	private ApplicationContext ctx;

	public KundenService() {
		// TODO Auto-generated constructor stub
		ctx = new ApplicationContextProvider().getApplicationContext();
		kundenHandler = ctx.getBean(KundenHandler.class);
	}

	public KundenHandler getKundenHandler() {
		return kundenHandler;
	}
}
