package br.com.douglasfernandes.jpa;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.dao.PerfilDao;
import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.pojos.LoginResponse;
import br.com.douglasfernandes.utils.Logs;

/**
 * Esta classe identifica como proceder com atualizações no banco de dados na tablela de Perfil
 * @author douglas.f.filho
 *
 */
@Repository
public class PerfilJpa implements PerfilDao{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public LoginResponse logar(Perfil perfil, HttpSession session) {
		LoginResponse response = new LoginResponse();
		try{
			if(perfil != null){
				String nomeOuEmail = perfil.getNome();
				Perfil encontrado = pegarPorNome(nomeOuEmail);
				if(encontrado == null)
					encontrado = pegarPorEmail(nomeOuEmail);
				
				if(encontrado != null){
					String senha = perfil.getSenha();
					if(senha.equals(encontrado.getSenha())){
						session.setAttribute("logado", perfil);
						Logs.info("[PerfilJpa]::logar::Usuario logado");
						response.setLoggedIn(true);
						response.setMessage(new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem Vindo!", "Seja bem vindo, "+perfil.getNome()));
						return response;
					}
					else{
						Logs.warn("[PerfilJpa]::logar::Usuario ou senha incorretos: "+perfil.toString());
						response.setLoggedIn(false);
						response.setMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de login!", "Usuário e/ou senha incorretos."));
						return response;
					}
				}
				else{
					Logs.warn("[PerfilJpa]::logar::Usuario ou senha incorretos: "+perfil.toString());
					response.setLoggedIn(false);
					response.setMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de login!", "Usuário e/ou senha incorretos."));
					return response;
				}
			}
			else{
				Logs.warn("[PerfilJpa]::logar::objeto perfil nulo.");
				response.setLoggedIn(false);
				response.setMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login!", "Usuário e/ou senha nulos."));
				return response;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::logar::Falha fatal ao tentar logar perfil. Excecao:");
			e.printStackTrace();
			response.setLoggedIn(false);
			response.setMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login!", "Usuário e/ou senha nulos."));
			return response;
		}
	}

	@Override
	public String adicionar(Perfil perfil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String atualizar(Perfil perfil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String remover(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Perfil pegarPorId(long id) {
		try{
			Query query = manager.createQuery("select p from Perfil as p where p.id = :id");
			query.setParameter("id", id);
			Perfil encontrado = (Perfil)query.getSingleResult();
			if(encontrado != null){
				Logs.info("[PerfilJpa]::pegarPorId:::Perfil encontrado: " + encontrado.toString());
				return encontrado;
			}
			else{
				Logs.warn("[PerfilJpa]::pegarPorId:::Não existe perfil com este ID. [" + id + "]");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::pegarPorId:::Erro ao tentar pegar perfil por ID. Excecao:");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Perfil pegarPorNome(String nome) {
		try{
			Query query = manager.createQuery("select p from Perfil as p where p.nome = :nome");
			query.setParameter("nome", nome);
			Perfil encontrado = (Perfil)query.getSingleResult();
			if(encontrado != null){
				Logs.info("[PerfilJpa]::pegarPorNome:::Perfil encontrado: " + encontrado.toString());
				return encontrado;
			}
			else{
				Logs.warn("[PerfilJpa]::pegarPorNome:::Não existe perfil com este NOME. [" + nome + "]");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::pegarPorNome:::Erro ao tentar pegar perfil por NOME. Excecao:");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Perfil pegarPorEmail(String email) {
		try{
			Query query = manager.createQuery("select p from Perfil as p where p.email = :email");
			query.setParameter("email", email);
			Perfil encontrado = (Perfil)query.getSingleResult();
			if(encontrado != null){
				Logs.info("[PerfilJpa]::pegarPorEmail:::Perfil encontrado: " + encontrado.toString());
				return encontrado;
			}
			else{
				Logs.warn("[PerfilJpa]::pegarPorEmail:::Não existe perfil com este EMAIL. [" + email + "]");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::pegarPorEmail:::Erro ao tentar pegar perfil por EMAIL. Excecao:");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Perfil> listar() {
		try{
			Query query = manager.createQuery("select p from Perfil as p");
			@SuppressWarnings("unchecked")
			List<Perfil> perfis = query.getResultList();
			if(perfis != null && perfis.size() > 0){
				Logs.info("[PerfilJpa]::listar:::Perfis encontrado: " + perfis.size());
				return perfis;
			}
			else{
				Logs.warn("[PerfilJpa]::listar:::Nenhum perfil listado.");
				return null;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::listar:::Erro ao tentar listar perfis. Excecao:");
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean jaTemComEsteNome(String nome){
		try{
			Perfil encontrado = pegarPorNome(nome);
			if(encontrado != null){
				Logs.info("[PerfilJpa]::jaTemComEsteNome:::Ja existe perfil cadastrado com este nome [" + nome + "]");
				return true;
			}
			else{
				Logs.info("[PerfilJpa]::jaTemComEsteNome:::Nao existe perfil cadastrado com este nome [" + nome + "]");
				return false;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::jaTemComEsteNome:::Erro ao tentar verificar se ja existe perfil com este nome [" + nome + "]. Excecao:");
			e.printStackTrace();
			return true;
		}
	}
	
	private boolean jaTemComEsteEmail(String email){
		try{
			Perfil encontrado = pegarPorEmail(email);
			if(encontrado != null){
				Logs.info("[PerfilJpa]::jaTemComEsteEmail:::Ja existe perfil cadastrado com este email [" + email + "]");
				return true;
			}
			else{
				Logs.info("[PerfilJpa]::jaTemComEsteEmail:::Nao existe perfil cadastrado com este email [" + email + "]");
				return false;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::jaTemComEsteEmail:::Erro ao tentar verificar se ja existe perfil com este email [" + email + "]. Excecao:");
			e.printStackTrace();
			return true;
		}
	}
	
	@Override
	public void primeiroAcesso(){
		try{
			String nome = "admin";
			String email = "admin@exemplo.com.br";
			
			if(!jaTemComEsteNome(nome)){
				if(!jaTemComEsteEmail(email)){
					Perfil perfil = new Perfil();
					perfil.setNome(nome);
					perfil.setSenha(nome);
					perfil.setEmail(email);
					
					manager.persist(perfil);
					
					Logs.info("[PerfilJpa]::primeiroAcesso:::Novo usuario cadastrado [" + perfil.getNome() + "]");
				}
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::primeiroAcesso:::Erro ao tentar executar primeiro acesso ao banco de dados. Excecao:");
			e.printStackTrace();
		}
	}

}