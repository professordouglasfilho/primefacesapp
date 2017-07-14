package br.com.douglasfernandes.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;

import br.com.douglasfernandes.dao.PerfilDao;
import br.com.douglasfernandes.dataservices.PerfilService;
import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.pojos.LoginResponse;
import br.com.douglasfernandes.utils.Logs;

/**
 * Apresenta e trata tela de login
 * @author douglas.f.filho
 *
 */
@ManagedBean
@SessionScoped
public class UserLoginView {
	private Perfil perfil = new Perfil();
	
	public Perfil getPerfil(){
		return this.perfil;
	}
   
    public void login(ActionEvent event) {
        RequestContext context = RequestContext.getCurrentInstance();
        Logs.info("[UserLoginView]::login:::Contexto de pagina obtido: "+context);
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	HttpSession session = request.getSession();
    	Logs.info("[UserLoginView]::login:::Sessao de pagina obtida: "+session);
    	
    	PerfilDao perfilDao = PerfilService.getAcesso();
    	Logs.info("[UserLoginView]::login:::Acesso a base de perfis garantido: "+perfilDao);
        LoginResponse response = perfilDao.logar(perfil, session);
         
        FacesContext.getCurrentInstance().addMessage(null, response.getMessage());
        context.addCallbackParam("loggedIn", response.getLoggedIn());
    }
}
