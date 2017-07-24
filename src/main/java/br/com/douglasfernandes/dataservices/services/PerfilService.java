package br.com.douglasfernandes.dataservices.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.douglasfernandes.dataservices.dao.PerfilDao;
import br.com.douglasfernandes.utils.Logs;

@Controller
@Transactional
public class PerfilService {
	
	PerfilDao perfilJpa;

	public PerfilDao getPerfilJpa() {
		return perfilJpa;
	}
	
	@Autowired
	public PerfilService(PerfilDao perfilJpa) {
		this.perfilJpa = perfilJpa;
		if(perfilJpa == null)
			Logs.info("[PerfilDaoService]::construtor::perfilJpa nulo");
		else
			Logs.info("[PerfilDaoService]::construtor::perfilJpa setado");
	}
}
