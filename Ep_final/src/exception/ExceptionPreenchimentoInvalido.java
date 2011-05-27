package exception;

public class ExceptionPreenchimentoInvalido extends Exception
{
	public ExceptionPreenchimentoInvalido(){
	super("Quantida inválida.");
	}
	public ExceptionPreenchimentoInvalido(String msg){
		super(msg);
	}
}
