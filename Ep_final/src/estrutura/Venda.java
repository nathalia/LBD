package estrutura;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import processamento.ManipulaGenerico;


public class Venda extends SuperClasse implements Serializable{
	static int idAtual;
	
	private int id;
	private String data;
	private int idCliente;
	private int idFuncionario;
	private ArrayList item;
	
	public Venda(int id, String data, int idCliente,
			int idFuncionario, ArrayList item) {
		super();
		this.id = id;
		this.data = data;
		this.idCliente = idCliente;
		this.idFuncionario = idFuncionario;
		this.item = item;
	}

	void adicionaItem(Produto pProd, int pQtd){
		ItemVenda item = new ItemVenda(pQtd, pProd);
		this.item.add(item);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
	public ArrayList getItem(){
		return this.item;
	}
	
	public double calculaDesconto(){
		ManipulaGenerico<Cliente> manipulador = new ManipulaGenerico<Cliente>();
		try{
			Cliente cliente = manipulador.buscarObjeto(this.getIdCliente(), Cliente.getTipo());
			if (cliente.getPontos() == 10)
				return 10/100;
			return 0;
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("classNotFoundException");
			return 0;
		}
		catch(IOException io){
			System.out.println("IOException");
			return 0;
		}
	}

	public boolean qtdeValida()
	{
		double total=0;
		ItemVenda it;
		for (int i = 0; i< this.item.size(); i++){
			it = (ItemVenda) item.get(i);
			total += it.getQtd()*it.getProduto().getValorVenda();
			if(it.getProduto().getQtd()-it.getQtd()<0)
				return false;
		}
		return true;
	}
	
	public double calcularTotal()
	{
		double total=0;
		ItemVenda it;
		ManipulaGenerico<Venda> manipulador = new ManipulaGenerico<Venda>();
		ManipulaGenerico<Produto> manipuladorP = new ManipulaGenerico<Produto>();
		ArrayList produtosi = new ArrayList();
		ArrayList produtos = new ArrayList();
		for (int i = 0; i< this.item.size(); i++){
			it = (ItemVenda) item.get(i);
			total += it.getQtd()*it.getProduto().getValorVenda();
			if(it.getProduto().getQtd()-it.getQtd()>=0){
				it.getProduto().setQtd(it.getProduto().getQtd()-it.getQtd());
				this.item.set(i, it);
			}else
				return -1;
		}		
		
		for (int i = 0; i< this.item.size(); i++){
			it = (ItemVenda) item.get(i);
			produtosi.add(it.getProduto());
		}

		try {
			produtos = manipuladorP.lerTodosObjetos(Produto.getTipo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Produto p1, p2;
		System.out.println(produtos.size()+" "+produtosi.size());
		for(int i = 0; i < produtos.size(); i++)
		{
			p1 = (Produto) produtos.get(i);
			for(int j = 0; j < produtosi.size(); j++)
			{
				p2 = (Produto) produtosi.get(j);
				if(p1.getId() == p2.getId())
					produtos.set(i, p2);
			}
		}
		
		try {
			manipuladorP.incluirObjeto(produtos, Produto.getTipo());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total - total*this.calculaDesconto();
	}
	
	public String toString(){
		return "Id: "+this.getId()+" Data: "+this.getData()+" Desconto: "+this.calculaDesconto()+" Id Cliente: "+
		this.getIdCliente()+" Id Funcionário: "+this.getIdFuncionario();//+" "+this.getItem().getProduto()+" "+
		//this.getItem().getQtd();
	}
	
	private static int posicaoArray(int id, ArrayList produtos)
	{
		Produto p;
		for (int i = 0; i < produtos.size(); i++)
		{
			p = (Produto)produtos.get(i);
			if (id == p.getId())
				return i;
		}
		return 0;
	}
}
