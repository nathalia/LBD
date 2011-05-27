package estrutura;

import exception.ExceptionPreenchimentoInvalido;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import processamento.ManipulaGenerico;


public class Funcionario extends PessoaFisica{
	static int idAtual;
	private String endereco;
	private String senha;
	
	public Funcionario(){
		super();
	}
	
	public Funcionario(int id, String email, String nome, String telefone,
			String cpf, String rg, String endereco, String senha)/* throws ExceptionPreenchimentoInvalido*/ {
		super(id, email, nome, telefone, cpf, rg);
		this.endereco = endereco;
		this.senha = senha;
		super.setTipo("funcionarios");
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	boolean inserirProduto(Produto produto){
		ManipulaGenerico<Produto> manipulador = new ManipulaGenerico<Produto>();
		ArrayList produtos = new ArrayList();
		produtos.add(produto);
		try{
			manipulador.incluirObjeto(produtos, Produto.getTipo());
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
		
		return true;
	}
	
	boolean excluirProduto(int id){
		ManipulaGenerico<Produto> manipulador = new ManipulaGenerico<Produto>();
		try{
			return manipulador.excluirObjeto(id, Produto.getTipo());
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
		catch(ClassNotFoundException io){
			System.out.println("ClassNotfoundException");
			return false;
		}
		
	}
	
	boolean alterarProduto(Produto produto){
		ManipulaGenerico<Produto> manipulador = new ManipulaGenerico<Produto>();
		return manipulador.atualizarObjeto(produto, Produto.getTipo());
	}
	
	boolean inserirCliente(Cliente cliente){
		ManipulaGenerico<Cliente> manipulador = new ManipulaGenerico<Cliente>();
		ArrayList clientes = new ArrayList();
		clientes.add(cliente);
		try{
			manipulador.incluirObjeto(clientes, Cliente.getTipo());
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
		
		return true;
	}
	
	boolean excluirCliente(int id){
		ManipulaGenerico<Cliente> manipulador = new ManipulaGenerico<Cliente>();
		try{
			return manipulador.excluirObjeto(id, Cliente.getTipo());
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
		catch(ClassNotFoundException io){
			System.out.println("ClassNotfoundException");
			return false;
		}
		
	}
	
	boolean alterarCliente(Cliente cliente){
		ManipulaGenerico<Cliente> manipulador = new ManipulaGenerico<Cliente>();
		return manipulador.atualizarObjeto(cliente, Cliente.getTipo());
	}
	
	boolean inserirFornecedor(Fornecedor fornecedor){
		ManipulaGenerico<Fornecedor> manipulador = new ManipulaGenerico<Fornecedor>();
		ArrayList fornecedores = new ArrayList();
		fornecedores.add(fornecedor);
		try{
			manipulador.incluirObjeto(fornecedores, Fornecedor.getTipo());
			return true;
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
	}
	
	boolean excluirFornecedor(int id){
		ManipulaGenerico<Fornecedor> manipulador = new ManipulaGenerico<Fornecedor>();
		try{
			return manipulador.excluirObjeto(id, Fornecedor.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("classNotFoundException");
			return false;
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
	}
	
	boolean alterarFornecedor(Fornecedor fornecedor){
		ManipulaGenerico<Fornecedor> manipulador = new ManipulaGenerico<Fornecedor>();
		return manipulador.atualizarObjeto(fornecedor, Fornecedor.getTipo());
	}

	public String toString() {
		return super.getId()+" "+super.getNome()+" "+super.getCPF()+" "+super.getRG()+
		" "+super.getTelefone()+" "+super.getEmail()+" "+this.getEndereco()+" "+
		this.getSenha();
	}
	public static String getTipo(){
		return "funcionarios";
	}
}
