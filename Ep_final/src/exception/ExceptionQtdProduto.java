package exception;

public class ExceptionQtdProduto extends Exception{
	public ExceptionQtdProduto(){
		super("Quantida inválida.");
	}
	public ExceptionQtdProduto(String msg){
		super(msg);
	}
}
