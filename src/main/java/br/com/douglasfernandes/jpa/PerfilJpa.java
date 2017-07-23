package br.com.douglasfernandes.jpa;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.model.Perfil;
import br.com.douglasfernandes.pojos.DefaultResponse;
import br.com.douglasfernandes.utils.Logs;

/**
 * Esta classe identifica como proceder com atualizações no banco de dados na tablela de Perfil
 * @author douglas.f.filho
 *
 */
@Repository
public class PerfilJpa{

	@PersistenceContext
	private EntityManager manager;
	
	public DefaultResponse logar(Perfil perfil, HttpSession session) {
		DefaultResponse response = new DefaultResponse();
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
						response.setStatus(true);
						response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_INFO, "Bem Vindo!", "Seja bem vindo, "+perfil.getNome()));
						return response;
					}
					else{
						Logs.warn("[PerfilJpa]::logar::Usuario ou senha incorretos: "+perfil.toString());
						response.setStatus(false);
						response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de login!", "Usuário e/ou senha incorretos."));
						return response;
					}
				}
				else{
					Logs.warn("[PerfilJpa]::logar::Usuario ou senha incorretos: "+perfil.toString());
					response.setStatus(false);
					response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro de login!", "Usuário e/ou senha incorretos."));
					return response;
				}
			}
			else{
				Logs.warn("[PerfilJpa]::logar::objeto perfil nulo.");
				response.setStatus(false);
				response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login!", "Usuário e/ou senha nulos."));
				return response;
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::logar::Falha fatal ao tentar logar perfil. Excecao:");
			e.printStackTrace();
			response.setStatus(false);
			response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de login!", "Usuário e/ou senha nulos."));
			return response;
		}
	}

	public DefaultResponse adicionar(Perfil perfil) {
		DefaultResponse response = new DefaultResponse();
		try{
			if(perfil != null){
				String nome = perfil.getNome();
				String email = perfil.getEmail();
				String senha = perfil.getSenha();
				if(!"".equals(nome) && !"".equals(email) && !"".equals(senha)){
					if(jaTemComEsteNome(nome)){
						Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar adicionar perfil: ja tem cadastrado.");
						response.setStatus(false);
						response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Já existe um perfil com este nome."));
					}
					else{
						if(jaTemComEsteEmail(email)){
							Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar adicionar perfil: ja tem cadastrado.");
							response.setStatus(false);
							response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Já existe um perfil com este email."));
						}
						else{
							manager.persist(perfil);
							Logs.warn("[PerfilJpa]::adicionar:::Perfil [" + perfil.getNome() + "] cadastrado com exito.");
							response.setStatus(true);
							response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso","Perfil cadastrado!"));
						}
					}
				}
				else{
					Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar adicionar perfil. NULO");
					response.setStatus(false);
					response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Perfil com nome ou senha inválidos."));
				}
			}
			else{
				Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar adicionar perfil. NULO");
				response.setStatus(false);
				response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Perfil com nome ou senha inválidos."));
			}
			
			return response;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar adicionar perfil. Excecao:");
			e.printStackTrace();
			return response;
		}
	}

	public DefaultResponse atualizar(Perfil perfil) {
		DefaultResponse response = new DefaultResponse();
		try{
			if(perfil != null){
				long id = perfil.getId();
				String nome = perfil.getNome();
				String email = perfil.getEmail();
				String senha = perfil.getSenha();
				if(!"".equals(nome) && !"".equals(email) && !"".equals(senha)){
					Perfil conflito = pegarPorNome(nome);
					if(conflito != null && conflito.getId() != id){
						Logs.warn("[PerfilJpa]::atualizar:::Erro ao tentar atualizar perfil: ja tem cadastrado.");
						response.setStatus(false);
						response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Já existe um perfil com este nome."));
					}
					else{
						conflito = pegarPorEmail(email);
						if(conflito != null && conflito.getId() != id){
							Logs.warn("[PerfilJpa]::atualizar:::Erro ao tentar atualizar perfil: ja tem cadastrado.");
							response.setStatus(false);
							response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Já existe um perfil com este email."));
						}
						else{
							manager.merge(perfil);
							Logs.warn("[PerfilJpa]::atualizar:::Perfil [" + perfil.getNome() + "] atualizado com exito.");
							response.setStatus(true);
							response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso","Perfil atualizado!"));
						}
					}
				}
				else{
					Logs.warn("[PerfilJpa]::atualizar:::Erro ao tentar atualizar perfil. nome e/ou senha nulos");
					response.setStatus(false);
					response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Perfil com nome ou senha inválidos."));
				}
			}
			else{
				Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar atualizar perfil. nulo");
				response.setStatus(false);
				response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","Perfil com nome ou senha inválidos."));
			}
			
			return response;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::adicionar:::Erro ao tentar atualizar perfil. Excecao:");
			e.printStackTrace();
			return response;
		}
	}

	public DefaultResponse remover(long id) {
		DefaultResponse response = new DefaultResponse();
		
		try{
			Perfil perfil = pegarPorId(id);
			manager.remove(perfil);
			response.setStatus(true);
			response.setMensagem(new FacesMessage(FacesMessage.SEVERITY_INFO,"Sucesso","Perfil removido com sucesso."));
			
			Logs.info("[PerfilJpa]::remover::perfil removido.");
			
			return response;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::remover::Erro ao tentar remover perfil. Excecao:");
			e.printStackTrace();
			return response;
		}
	}

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
		catch(NoResultException e){
			Logs.warn("[PerfilJpa]::pegarPorId:::Erro ao tentar pegar perfil por ID. NULO");
			return null;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::pegarPorId:::Erro ao tentar pegar perfil por ID. Excecao:");
			e.printStackTrace();
			return null;
		}
	}

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
		catch(NoResultException e){
			Logs.warn("[PerfilJpa]::pegarPorNome:::Erro ao tentar pegar perfil por Nome. NULO");
			return null;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::pegarPorNome:::Erro ao tentar pegar perfil por NOME. Excecao:");
			e.printStackTrace();
			return null;
		}
	}
	
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
		catch(NoResultException e){
			Logs.warn("[PerfilJpa]::pegarPorEmail:::Erro ao tentar pegar perfil por Email. NULO");
			return null;
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::pegarPorEmail:::Erro ao tentar pegar perfil por EMAIL. Excecao:");
			e.printStackTrace();
			return null;
		}
	}

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
	
	public void primeiroAcesso(){
		String nome = "admin";
		String email = "admin@exemplo.com.br";
		
		Perfil perfil = new Perfil();
		perfil.setNome(nome);
		perfil.setSenha(nome);
		perfil.setEmail(email);
		
		adicionar(perfil);
	}
}