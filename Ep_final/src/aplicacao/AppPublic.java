package aplicacao;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class AppPublic implements ActionListener{
	public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = App.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
    public void actionPerformed(ActionEvent e) {
    	
    }
    
	public Container criarBotoes(String tipo){
    	GridLayout grid = new GridLayout(1, 3);
    	JPanel painel = new JPanel(grid);
    	
    	ImageIcon iconAdd = createImageIcon("images/middle.gif");
    	JButton adicionar = new JButton(iconAdd);
    	adicionar.addActionListener(this);
    	adicionar.setActionCommand("add"+tipo);
    	JButton excluir = new JButton("Exc");
    	excluir.addActionListener(this);
    	excluir.setActionCommand("exc"+tipo);
    	JButton alterar = new JButton("Alt");
    	alterar.addActionListener(this);
    	alterar.setActionCommand("alt"+tipo);
    	
    	painel.add(adicionar);
    	painel.add(excluir);
    	painel.add(alterar);
    	
    	return painel;
    }

}
