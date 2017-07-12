package br.com.douglasfernandes.dao;

import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.douglasfernandes.model.Perfil;

/**
 * Como manipular perfis no banco de dados.
 * @author douglas.f.filho
 *
 */
public interface PerfilDao {
	public String logar(Perfil perfil, HttpSession session);
	public String adicionar(Perfil perfil);
	public String atualizar(Perfil perfil);
	public String remover(long id);
	public Perfil pegarPorId(long id);
	public Perfil pegarPorNome(String nome);
	public List<Perfil> listar();
	
	public void primeiroAcesso();
}
