package br.com.douglasfernandes.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import br.com.douglasfernandes.dao.PerfilDao;
import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.pojos.LoginResponse;

/**
 * Apresenta e trata tela de login
 * @author douglas.f.filho
 *
 */
@ManagedBean
@SessionScoped
@Transactional
public class UserLoginView {
	@Autowired
	@Qualifier("perfilJpa")
	PerfilDao perfilDao;
	
	private Perfil perfil = new Perfil();
	
	public Perfil getPerfil(){
		return this.perfil;
	}
   
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	HttpSession session = request.getSession();
        LoginResponse response = perfilDao.logar(perfil, session);
         
        FacesContext.getCurrentInstance().addMessage(null, response.getMessage());
        context.addCallbackParam("loggedIn", response.getLoggedIn());
    }
}
