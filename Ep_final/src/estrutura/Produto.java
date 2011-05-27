package estrutura;

import java.io.Serializable;

public class Produto extends SuperClasse implements Serializable{
	static int idAtual;
	private int id;
	private int idFornecedor;
	private String nome;
	private String descricao;
	private String laboratorio;
	private int qtd;
	private double valorVenda;	

	public Produto(int id, int idFornecedor, String nome, String descricao, String laboratorio,
			int qtd, double valorVenda) {
		this.id = id;
		this.idFornecedor = idFornecedor;
		this.nome = nome;
		this.descricao = descricao;
		this.laboratorio = laboratorio;
		this.qtd = qtd;
		this.valorVenda = valorVenda;
	}

	public int getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(int idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getNome(){
		return this.nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	void incrementaQtdProduto(int pQtd){
		this.qtd+= pQtd;
	}
	
	boolean decrementaQtdProduto(int pQtd){
		if(this.qtd >= pQtd){
			this.qtd-= pQtd;
			return true;
		}
		return false;
	}

	public String toString() {
		return this.getId()+" "+this.getNome()+" "+this.getDescricao()+" "+
		this.getLaboratorio()+" "+this.getQtd()+" "+this.getValorVenda()+" "+
		this.getIdFornecedor();
	}
	public static String getTipo(){
		return "produtos";
	}
}