package estrutura;

import java.io.IOException;
import exception.ExceptionPreenchimentoInvalido;

import processamento.ManipulaGenerico;

public class Administrador extends Funcionario{

	public Administrador(int id, String email, String nome, String telefone,
			String cpf, String rg, String endereco, String senha)/* throws ExceptionPreenchimentoInvalido*/{
		super(id, email, nome, telefone, cpf, rg, endereco, senha);
	}

	/*boolean inserirFuncionario(Funcionario funcionario){
		ManipulaGenerico<Funcionario> manipulador = new ManipulaGenerico<Funcionario>();
		try{
			Funcionario f = manipulador.buscarObjeto(funcionario.getCPF());
			if(f == null){
				manipulador.incluirObjeto(funcionario);
				return true;
			}
			return false;
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
	
	boolean excluirFuncionario(int id){
		ManipulaGenerico<Funcionario> manipulador = new ManipulaGenerico<Funcionario>();
		try{
			manipulador.buscarObjeto(id);
			manipulador.excluirObjeto(id);
		}
		catch(NullPointerException nullPointer){
			System.out.println("NullPointerException");
			return false;
		}
		catch(ClassNotFoundException classNotFound){
			System.out.println("classNotFoundException");
			return false;
		}
		catch(IOException io){
			System.out.println("IOException");
			return false;
		}
		return true;
	}
	
	boolean alterarFuncionario(Funcionario funcionario){
		ManipulaGenerico<Funcionario> manipulador = new ManipulaGenerico<Funcionario>();
		return manipulador.atualizarFuncionario(funcionario);
	}
	*/
}
