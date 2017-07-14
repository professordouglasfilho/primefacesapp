package br.com.douglasfernandes.dataservices;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.douglasfernandes.dao.PerfilDao;
import br.com.douglasfernandes.utils.Logs;

/**
 * Classe de serviço de acesso a dados do banco de dados.
 * @author douglas.f.filho
 *
 */
@Controller
@Transactional
public class PerfilService {
	private static PerfilDao perfilDao;
	
	public static PerfilDao getAcesso(){
		return perfilDao;
	}
	
	public static void setAcesso(PerfilDao dao){
		Logs.info("[PerfilService]::setAcesso:::"+perfilDao);
		perfilDao = dao;
	}
	
}
