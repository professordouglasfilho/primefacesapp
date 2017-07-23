package br.com.douglasfernandes.dataservices;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import br.com.douglasfernandes.utils.Logs;

public class DataService {
	public static PerfilService getPerfilService(WebApplicationContext webApplicationContext){
		ApplicationContext applicationContext = webApplicationContext;
		Logs.info("[DBService]::getPerfilService::app name: "+applicationContext.getDisplayName());
		
		PerfilService service = (PerfilService)applicationContext.getBean("perfilService");
		if(service != null)
			Logs.info("[DBService]::getPerfilService::service: PerfilService");
		else
			Logs.info("[DBService]::getPerfilService::service: null");
		
		return service;
	}
}
