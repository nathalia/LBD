package estrutura;

public class Cliente extends PessoaFisica
{
	private int pontos;
	
	public Cliente(){
		
	}
	public Cliente(int id, String email, String nome, String telefone,
			String cpf, String rg, int pontos)
	{
		super(id, email, nome, telefone, cpf, rg);
		this.pontos = pontos;
		super.setTipo("clientes");
	}

	public void zeraPontos()
	{
		this.pontos = 0;
	}

	public void incrementaPontos()
	{
		this.pontos++;
	}

	public int getPontos()
	{
		return this.pontos;
	}

	public String toString()
	{
		return super.getId() + " " + super.getNome() + " " + super.getCPF() + " " + super.getRG() +
				" " + super.getTelefone() + " " + super.getEmail() + " " + this.getPontos();
	}
	public static String getTipo(){
		return "clientes";
	}
}
