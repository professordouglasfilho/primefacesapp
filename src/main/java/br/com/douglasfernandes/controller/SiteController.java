package br.com.douglasfernandes.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.douglasfernandes.dao.PerfilDao;

@Controller
@Transactional
public class SiteController {
	
	@Autowired
	@Qualifier("perfilJpa")
	PerfilDao perfilDao;
	
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
	
}
