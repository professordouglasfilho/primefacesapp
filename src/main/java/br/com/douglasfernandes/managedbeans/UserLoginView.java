package br.com.douglasfernandes.managedbeans;

import javax.faces.application.FacesMessage;
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
        FacesMessage message = null;
        boolean loggedIn = false;
        
        String username = perfil.getNome();
        String password = perfil.getSenha();
         
        if(username != null && username.equals("admin") && password != null && password.equals("admin")) {
        	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        	HttpSession session = request.getSession();
        	session.setAttribute("logado", perfil);
        	
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem Vindo!", username);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de Login.", "Credenciais inválidas!");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }
}
