package estrutura;

import java.io.Serializable;
import exception.ExceptionPreenchimentoInvalido;

public class Pessoa extends SuperClasse implements Serializable{
	private int id;
	private String nome;
	private String telefone;
	private String email;
	private static String tipo;
	
	public Pessoa(){
		
	}

	public Pessoa(int id, String email, String nome, String telefone) /*throws ExceptionPreenchimentoInvalido*/{
			this.email = email;
			this.id = id;
			this.nome = nome;
			this.telefone = telefone;
			super.setTipo(tipo);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static String getTipo(){
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
