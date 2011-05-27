package estrutura;

import java.io.Serializable;

public class ItemVenda implements Serializable{
	private int qtd;
	private Produto produto;
	
	public ItemVenda(int qtd, Produto produto) {
		this.qtd = qtd;
		this.produto = produto;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
}
