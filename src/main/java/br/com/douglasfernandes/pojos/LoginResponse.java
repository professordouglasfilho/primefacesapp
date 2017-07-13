package br.com.douglasfernandes.pojos;

import javax.faces.application.FacesMessage;

/**
 * Representa a resposta à tentativa de login
 * @author douglas.f.filho
 *
 */
public class LoginResponse {
	private boolean loggedIn;
	private FacesMessage message;
	
	public LoginResponse(){
		loggedIn = false;
		message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário ou senha incorretos.", "");
	}
	
	public boolean getLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	public FacesMessage getMessage() {
		return message;
	}
	public void setMessage(FacesMessage message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "LoginResponse [loggedIn=" + loggedIn + ", message=" + message + "]";
	}
}
