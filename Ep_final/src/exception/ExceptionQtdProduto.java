package exception;

public class ExceptionQtdProduto extends Exception{
	public ExceptionQtdProduto(){
		super("Quantida inv�lida.");
	}
	public ExceptionQtdProduto(String msg){
		super(msg);
	}
}
