package estrutura;
import exception.ExceptionPreenchimentoInvalido;

public class PessoaJuridica extends Pessoa{
	private String CNPJ;
	private String razaoSocial;
	
	public PessoaJuridica(int id, String email, String nome, String telefone,
			String cnpj, String razaoSocial) /*throws ExceptionPreenchimentoInvalido*/{
		super(id, email, nome, telefone);
		CNPJ = cnpj;
		this.razaoSocial = razaoSocial;
	}
	
	public String getCNPJ() {
		return CNPJ;
	}
	public void setCNPJ(String cnpj) {
		CNPJ = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	
}
