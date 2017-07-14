package br.com.douglasfernandes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.dao.PerfilDao;
import br.com.douglasfernandes.dataservices.PerfilService;
import br.com.douglasfernandes.utils.Logs;

@Controller
public class SiteController {
	@Qualifier("perfilJpa")
	PerfilDao perfilDao;
	
	@Autowired
	public SiteController(PerfilDao dao) {
		this.perfilDao = dao;
		setServicos();
	}
	
	@RequestMapping(value={"/","home"})
	public String home(){
		return "index";
	}
	
	@RequestMapping("login")
	public String login(){
		perfilDao.primeiroAcesso();
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:login";
	}
	
	private void setServicos(){
		Logs.info("[SiteController]::setServicos:::Primeira chamada ao controller.");
		PerfilService.setAcesso(perfilDao);
	}
	
}
