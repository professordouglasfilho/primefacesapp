package br.com.douglasfernandes.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import br.com.douglasfernandes.dataservices.DataService;
import br.com.douglasfernandes.jpa.PerfilJpa;
import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.pojos.DefaultResponse;

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
        
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	HttpSession session = request.getSession();
    	
    	FacesContext fc = FacesContext.getCurrentInstance();
    	WebApplicationContext wac = FacesContextUtils.getWebApplicationContext(fc);
    	PerfilJpa perfilJpa = DataService.getPerfilService(wac).getPerfilJpa();
    	
        DefaultResponse response = perfilJpa.logar(perfil, session);
         
        FacesContext.getCurrentInstance().addMessage(null, response.getMensagem());
        context.addCallbackParam("loggedIn", response.getStatus());
    }
}
