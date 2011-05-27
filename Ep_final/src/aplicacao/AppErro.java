package aplicacao;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
	
public class AppErro {
	JLabel texto = new JLabel();
	public JLabel criarAdicionarErro(Exception e, String tipo){

		texto.setText("Erro ao incluir "+tipo+". "+ e.toString());
		
		return texto;
	}
	
	public JLabel criarAlterarErro(Exception e, String tipo){

		texto.setText("Erro ao alterar "+tipo+". "+ e.toString());
		
		return texto;
	}
	
	public JLabel criarExcluirErro(Exception e, String tipo){
		texto.setText("Erro ao excluir "+tipo+". "+ e.toString());
		
		return texto;
	}
}
