package br.com.douglasfernandes.interceptor;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.utils.Logs;

/**
 * Intercepta solicitações (requests) antes de entregar ao controller
 * @author douglas.f.filho
 *
 */
public class MainInterceptor extends HandlerInterceptorAdapter implements PhaseListener{

	/**
	 * Auto generated default serial.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object controller)
	 {
		  try{
			  String uri = request.getRequestURI();
		      
			  Logs.info("[MainInterceptor INFO]::preHandler:::URL("+uri+")");
			  
			  Perfil logado = (Perfil)request.getSession().getAttribute("logado");
			  
			  if(uri.contains("/resources/")){
				  Logs.info("[MainInterceptor INFO]::preHandler:::is resource file");
				  return true;
			  }
			  
			  if(uri.endsWith("login")){
				  Logs.info("[MainInterceptor INFO]::preHandler:::is login page");
				  return true;
			  }
			  
			  if(logado != null){
				  Logs.info("[MainInterceptor INFO]::preHandler:::you can pass");
				  return true;
			  }
			  else{
				  Logs.info("[MainInterceptor INFO]::preHandler:::redirect to login");
				  response.sendRedirect("login");
				  return false;
			  }
		  }
		  catch(Exception e){
			  Logs.warn("[MainInterceptor]::preHandler:::Erro ao tentar manipular request. Exception: \n");
				e.printStackTrace();
				return false;
		  }
	 }

	@Override
	public void afterPhase(PhaseEvent event) {
		Logs.info("[MainInterceptor INFO]::beforePhase:::phaseId: "+event.getPhaseId());
		try{
			HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			String uri = request.getRequestURI();
			
			Logs.info("[MainInterceptor INFO]::beforePhase:::URL("+uri+")");
			
			HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			Perfil logado = (Perfil)request.getSession().getAttribute("logado");
			
			if(!uri.endsWith("login.xhtml")){
				if(!uri.contains("/resources/")){
					if(logado == null){
						Logs.info("[MainInterceptor INFO]::beforePhase:::redirect to login");
						response.sendRedirect("login");
					}
					else{
						Logs.info("[MainInterceptor INFO]::beforePhase:::you can pass");
					}
				}
				else{
					Logs.info("[MainInterceptor INFO]::beforePhase:::is resource file");
				}
			}
			else{
				Logs.info("[MainInterceptor INFO]::beforePhase:::is login page");
			}
		}
		catch(Exception e){
			Logs.warn("[MainInterceptor]::beforePhase:::Erro ao tentar manipular request. Exception: \n");
			e.printStackTrace();
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
	
}
