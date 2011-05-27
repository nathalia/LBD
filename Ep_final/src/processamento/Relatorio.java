package processamento;

import java.io.IOException;
import java.util.ArrayList;

import estrutura.Cliente;
import estrutura.Fornecedor;
import estrutura.Funcionario;
import estrutura.Produto;
import estrutura.Venda;

public class Relatorio {
	public void relatorioClientes(){
		ManipulaGenerico<Cliente> manipular = new ManipulaGenerico<Cliente>();
		try{
			ArrayList<Cliente> clientes = manipular.lerTodosObjetos("cliente");
			for(int i = 0; i < clientes.size(); i++){
				Cliente c = (Cliente)clientes.get(i);
				c.toString();
			}
		}
		catch(IOException io){
			System.out.println("IOException");
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("ClassNotFoundException");
		}
	}
	
	public void relatorioProdutos(){
		ManipulaGenerico<Produto> manipular = new ManipulaGenerico<Produto>();
		try{
			ArrayList<Produto> produtos = manipular.lerTodosObjetos("produto");
			for(int i = 0; i < produtos.size(); i++){
				Produto p = (Produto)produtos.get(i);
				p.toString();
			}
		}
		catch(IOException io){
			System.out.println("IOException");
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("ClassNotFoundException");
		}
	}
	public void relatorioFornecedores(){
		ManipulaGenerico<Fornecedor> manipular = new ManipulaGenerico<Fornecedor>();
		try{
			ArrayList<Fornecedor> fornecedores = manipular.lerTodosObjetos("fornecedor");
			for(int i = 0; i < fornecedores.size(); i++){
				Fornecedor f = (Fornecedor)fornecedores.get(i);
				f.toString();
			}
		}
		catch(IOException io){
			System.out.println("IOException");
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("ClassNotFoundException");
		}
	}
	public void relatorioFuncionarios(){
		ManipulaGenerico<Funcionario> manipular = new ManipulaGenerico<Funcionario>();
		try{
			ArrayList<Funcionario> funcionarios = manipular.lerTodosObjetos("funcionario");
			for(int i = 0; i < funcionarios.size(); i++){
				Funcionario f = (Funcionario)funcionarios.get(i);
				f.toString();
			}
		}
		catch(IOException io){
			System.out.println("IOException");
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("ClassNotFoundException");
		}
	}
	
	public void relatorioVendas(){
		ManipulaGenerico<Venda> manipular = new ManipulaGenerico<Venda>();
		try{
			ArrayList<Venda> vendas = manipular.lerTodosObjetos("venda");
			for(int i = 0; i < vendas.size(); i++){
				Venda v = (Venda)vendas.get(i);
				v.toString();
			}
		}
		catch(IOException io){
			System.out.println("IOException");
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("ClassNotFoundException");
		}
	}
}
