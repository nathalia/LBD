package exception;

public class ExceptionPreenchimentoInvalido extends Exception
{
	public ExceptionPreenchimentoInvalido(){
	super("Quantida inv�lida.");
	}
	public ExceptionPreenchimentoInvalido(String msg){
		super(msg);
	}
}
