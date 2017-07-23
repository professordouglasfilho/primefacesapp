package br.com.douglasfernandes.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.douglasfernandes.dataservices.DataService;
import br.com.douglasfernandes.jpa.PerfilJpa;

@Controller
@Transactional
public class SiteController {
	
	@RequestMapping(value={"/","home"})
	public String home(HttpSession session){
		return "index";
	}
	
	@RequestMapping("login")
	public String login(HttpSession session){
		ServletContext sc = session.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(sc);
		PerfilJpa perfilJpa = DataService.getPerfilService(wac).getPerfilJpa();
		perfilJpa.primeiroAcesso();
		return "login";
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:login";
	}
	
}
