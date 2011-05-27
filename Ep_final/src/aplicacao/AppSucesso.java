package aplicacao;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AppSucesso {
	JLabel texto = new JLabel();
	public JLabel criarAdicionarSucesso(String tipo){
		texto.setText("Novo "+tipo+" criado com sucesso!");
		
		return texto;
	}
	
	public JLabel criarAlterarSucesso(String tipo){
		texto.setText(tipo+" alterado com sucesso!");
		
		return texto;
	}
	
	public JLabel criarExcluirSucesso(String tipo){
		texto.setText(tipo+" excluído com sucesso!");
		
		return texto;
	}

}
