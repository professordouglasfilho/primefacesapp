package br.com.douglasfernandes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.dataservices.dao.PerfilDao;
import br.com.douglasfernandes.dataservices.factory.DataService;

@Controller
public class SiteController {
	
	@RequestMapping(value={"/","home"})
	public String home(HttpSession session){
		return "index";
	}
	
	@RequestMapping("login")
	public String login(HttpSession session){
		PerfilDao perfilJpa = DataService.getPerfilService(session.getServletContext()).getPerfilJpa();
		perfilJpa.primeiroAcesso();
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:login";
	}
	
}
