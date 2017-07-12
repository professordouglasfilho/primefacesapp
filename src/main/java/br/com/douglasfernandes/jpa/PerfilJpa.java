package br.com.douglasfernandes.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import br.com.douglasfernandes.dao.PerfilDao;
import br.com.douglasfernandes.model.Perfil;
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
	public Login logar(Perfil perfil, HttpSession session) {
		try{
			if(perfil != null){
				Query query = manager.createQuery("select p from Perfil as p where p.nome = :nome and p.senha = :senha");
				query.setParameter("nome", perfil.getNome());
				query.setParameter("senha", perfil.getSenha());
				Perfil perfilOk = (Perfil) query.getSingleResult();
				if(perfilOk != null){
					perfil.setSenha("");
					session.setAttribute("logado", perfil);
					Logs.info("[PerfilJpa]::logar::Usuario logado");
					return "Seja bem vindo, "+perfil.getNome();
				}
				else{
					Logs.warn("[PerfilJpa]::logar::Usuario ou senha incorretos: "+perfil.toString());
					return "Usuário ou senha incorretos.";
				}
			}
			else{
				Logs.warn("[PerfilJpa]::logar::objeto perfil nulo.");
				return "Usuário ou senha nulos.";
			}
		}
		catch(Exception e){
			Logs.warn("[PerfilJpa]::logar::Falha fatal ao tentar logar perfil. Excecao:");
			e.printStackTrace();
			return "Erro ao tentar logar: Falha no servidor.";
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Perfil pegarPorNome(String nome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Perfil> listar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void primeiroAcesso(){
		try{
			Perfil perfil = new Perfil();
			perfil.setNome("admin");
			perfil.setSenha("admin");
			perfil.setEmail("admin@exemplo.com.br");
			
			manager.persist(perfil);
		}
		catch(Exception e){
			e.printStackTrace();
			Logs.warn("[PerfilJpa]::primeiroAcesso:::Erro ao tentar executar primeiro acesso ao banco de dados.");
		}
	}

}