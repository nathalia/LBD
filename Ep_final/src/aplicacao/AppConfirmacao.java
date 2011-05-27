package aplicacao;

import javax.swing.JLabel;

public class AppConfirmacao {
	JLabel texto = new JLabel();
	
	public JLabel confirmaExclusao(String tipo, String nome){
		texto.setText("Tem certeza que deseja excluir o "+tipo+" "+nome+"?");
		
		return texto;
	}
	
	public JLabel confirmaAlteracao(String tipo, String nome){
		texto.setText("Tem certeza que deseja alterar o "+tipo+" "+nome+"?");
		
		return texto;
	}
}
