package br.com.douglasfernandes.dataservices.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="perfis")
public class Perfil {
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "nome",nullable = false, unique = true)
	private String nome;
	@Column(name = "senha",nullable = false)
	private String senha;
	@Column(name = "email",nullable = false, unique = true)
	private String email;
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Perfil [id=" + id + ", nome=" + nome + ", senha= ********" + ", email=" + email + "]";
	}
}
