package estrutura;

import exception.ExceptionPreenchimentoInvalido;

public class PessoaFisica extends Pessoa{
	private String CPF;
	private String RG;
	
	public PessoaFisica(){
		super();
	}
	
	public PessoaFisica(int id, String email, String nome, String telefone,
			String cpf, String rg)/* throws ExceptionPreenchimentoInvalido*/{
		super(id, email, nome, telefone);
		CPF = cpf;
		RG = rg;
	}
	public String getCPF() {
		return CPF;
	}
	public void setCPF(String cpf) {
		CPF = cpf;
	}
	public String getRG() {
		return RG;
	}
	public void setRG(String rg) {
		RG = rg;
	}
	public static String getTipo(){
		return getTipo();
	}
}
