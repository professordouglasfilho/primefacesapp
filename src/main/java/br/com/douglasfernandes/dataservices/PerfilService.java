package br.com.douglasfernandes.dataservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.douglasfernandes.jpa.PerfilJpa;
import br.com.douglasfernandes.utils.Logs;

@Controller
@Transactional
public class PerfilService {
	
	PerfilJpa perfilJpa;

	public PerfilJpa getPerfilJpa() {
		return perfilJpa;
	}
	
	@Autowired
	public PerfilService(PerfilJpa perfilJpa) {
		this.perfilJpa = perfilJpa;
		if(perfilJpa == null)
			Logs.info("[PerfilDaoService]::construtor::perfilJpa nulo");
		else
			Logs.info("[PerfilDaoService]::construtor::perfilJpa setado");
	}
}
