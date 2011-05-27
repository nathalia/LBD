package estrutura;
import exception.ExceptionPreenchimentoInvalido;

public class Fornecedor extends PessoaJuridica{
	static int idAtual;
	private String contato;
	
	public Fornecedor(int id, String email, String nome, String telefone,
			String cnpj, String razaoSocial, String contato)/* throws ExceptionPreenchimentoInvalido*/ {
		super(id, email, nome, telefone, cnpj, razaoSocial);
		this.contato = contato;
		super.setTipo("fornecedores");
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String toString() {
		return super.getId()+" "+super.getNome()+" "+super.getRazaoSocial()+" "+
		super.getCNPJ()+" "+super.getTelefone()+" "+super.getEmail()+" "+
		this.getContato();
	}
	public static String getTipo(){
		return "fornecedores";
	}
	
}
