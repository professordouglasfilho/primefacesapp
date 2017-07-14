package br.com.douglasfernandes.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.pojos.LoginResponse;

/**
 * Como manipular perfis no banco de dados.
 * @author douglas.f.filho
 *
 */
public interface PerfilDao {
	public LoginResponse logar(Perfil perfil, HttpSession session);
	public String adicionar(Perfil perfil);
	public String atualizar(Perfil perfil);
	public String remover(long id);
	public Perfil pegarPorId(long id);
	public Perfil pegarPorNome(String nome);
	public Perfil pegarPorEmail(String email);
	public List<Perfil> listar();
	
	public void primeiroAcesso();
}
