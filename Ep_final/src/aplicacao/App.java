package aplicacao;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;

import estrutura.Funcionario;

public class App implements ActionListener, ItemListener {
    JTextArea output;
    JScrollPane scrollPane;
    String newline = "\n";
    private AppCliente appCliente = new AppCliente();
    private AppProduto appProduto = new AppProduto();
    private AppFuncionario appFuncionario = new AppFuncionario();
    private AppFornecedor appFornecedor = new AppFornecedor();
    private AppVenda appVenda = new AppVenda();
    
    Funcionario funcionarioLogado;
    
    JPasswordField txtSenha;
    JTextField txtUser;
    JLabel mensagem;

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();
        
        menu = new JMenu("Gerenciar");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Cliente");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //colocar um if aqui - se funcionário não for admin, não incluir esta opção
        menuItem = new JMenuItem("Funcionario");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Fornecedor");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menuItem = new JMenuItem("Produto");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Venda");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        menu = new JMenu("Relatório");
        menu.setMnemonic(KeyEvent.VK_R);
        menuBar.add(menu);
        
        return menuBar;
    }

    public Container telaLogin(){
    	GridLayout grid = new GridLayout(6, 2);
    	grid.setHgap(4);
    	grid.setVgap(4);
    	JPanel painel = new JPanel(grid);
    	
    	JLabel login = new JLabel("Login");
    	JLabel espaco = new JLabel("");
    	JLabel user = new JLabel("Usuário");
    	JLabel senha = new JLabel("Senha");
    	mensagem = new JLabel();
    	mensagem.setForeground(Color.red);
    	
    	txtUser = new JTextField();
    	txtSenha = new JPasswordField();
    	
    	JButton ok = new JButton("Ok");
    	ok.addActionListener(new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	        logar();
    	    }
    	});
    	
    	painel.add(login);
    	painel.add(espaco);
    	painel.add(user);
    	painel.add(txtUser);
    	painel.add(senha);
    	painel.add(txtSenha);
    	painel.add(ok);
    	painel.add(mensagem);
    	
    	return painel;
    }
    
    public void logar(){
    	if (txtUser.getText().isEmpty()||txtSenha.getText().isEmpty())
    		mensagem.setText("Preencha os campos corretamente!");
    	else{
    		//procurar nos funcionários esse usuário e compara a senha
    		if("admin".equals(txtUser.getText()))
    			if ("admin".equals(txtSenha.getText())){
    				this.createAndShowGUI(this.createContentPane());
    				//criar instância do funcionarioLogado - variável de classe
    			}
    			else
    				mensagem.setText("Senha incorreta!");
    		else
    			mensagem.setText("Usuário desconhecido!");
    	}
    		
    }
    
    public Container criarBotoes(String tipo){
    	GridLayout grid = new GridLayout(1, 3);
    	JPanel painel = new JPanel(grid);
    	
    	JButton adicionar = new JButton("Add");
    	adicionar.addActionListener(this);
    	adicionar.setActionCommand("add"+tipo);
    	JButton excluir = new JButton("Exc");
    	excluir.addActionListener(this);
    	excluir.setActionCommand("exc"+tipo);
    	JButton alterar = new JButton("Alt");
    	alterar.addActionListener(this);
    	alterar.setActionCommand("alt"+tipo);
    	JLabel espaco = new JLabel();
    	
    	painel.add(adicionar);
    	painel.add(excluir);
    	painel.add(alterar);
    	
    	return painel;
    }

    public Container criarBotaoCancel(String tipo){
    	GridLayout grid = new GridLayout(1, 2);
    	JPanel painel = new JPanel(grid);
    	
    	JButton cancel = new JButton("Cancelar");
    	cancel.addActionListener(this);
    	cancel.setActionCommand("cancel"+tipo);
    	
    	painel.add(cancel);
    	
    	return painel;
    }
    
    public Container createContentPane() {
        //Create the content-pane-to-be.
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setOpaque(true);

        //Create a scrolled text area.
        output = new JTextArea(5, 30);
        output.setEditable(false);
        scrollPane = new JScrollPane(output);

        //Add the text area to the content pane.
        contentPane.add(scrollPane, BorderLayout.CENTER);

        return contentPane;
    }

    public void actionPerformed(ActionEvent e) {
    	JPanel painel;
    	if ("cancelFornecedor".equals(e.getActionCommand())){
    		painel = (JPanel)appFornecedor.criarPainelFornecedor();
	        painel.add(this.criarBotoes("Fornecedor"));
	    	this.createAndShowGUI(painel);
    	}
    	else
	    	if ("addFornecedor".equals(e.getActionCommand())){
	    		painel = (JPanel)appFornecedor.criarAddFornecedor();
	    	    painel.add(this.criarBotaoCancel("Fornecedor"));
	    		this.createAndShowGUI(painel);
	    	}
	    	else
	    		if ("excFornecedor".equals(e.getActionCommand())){
	    			painel = (JPanel)appFornecedor.criarExcFornecedor();
	        	    painel.add(this.criarBotaoCancel("Fornecedor"));
	        		this.createAndShowGUI(painel);
	    		}
	    		else
	    			if ("altFornecedor".equals(e.getActionCommand())){
	    				painel = (JPanel)appFornecedor.criarAltFornecedor();
	            	    painel.add(this.criarBotaoCancel("Fornecedor"));
	            		this.createAndShowGUI(painel);
	    			}
    	else
    	if ("cancelFuncionario".equals(e.getActionCommand())){
    		painel = (JPanel)appFuncionario.criarPainelFuncionario();
	        painel.add(this.criarBotoes("Funcionario"));
	    	this.createAndShowGUI(painel);
    	}
    	else
	    	if ("addFuncionario".equals(e.getActionCommand())){
	    		painel = (JPanel)appFuncionario.criarAddFuncionario();
	    	    painel.add(this.criarBotaoCancel("Funcionario"));
	    		this.createAndShowGUI(painel);
	    	}
	    	else
	    		if ("excFuncionario".equals(e.getActionCommand())){
	    			painel = (JPanel)appFuncionario.criarExcFuncionario();
	        	    painel.add(this.criarBotaoCancel("Funcionario"));
	        		this.createAndShowGUI(painel);
	    		}
	    		else
	    			if ("altFuncionario".equals(e.getActionCommand())){
	    				painel = (JPanel)appFuncionario.criarAltFuncionario();
	            	    painel.add(this.criarBotaoCancel("Funcionario"));
	            		this.createAndShowGUI(painel);
	    			}
    	else
    	if ("cancelCliente".equals(e.getActionCommand())){
    		painel = (JPanel)appCliente.criarPainelCliente();
	        painel.add(this.criarBotoes("Cliente"));
	    	this.createAndShowGUI(painel);
    	}
    	else
	    	if ("addCliente".equals(e.getActionCommand())){
	    		painel = (JPanel)appCliente.criarAddCliente();
	    	    painel.add(this.criarBotaoCancel("Cliente"));
	    		this.createAndShowGUI(painel);
	    	}
	    	else
	    		if ("excCliente".equals(e.getActionCommand())){
	    			painel = (JPanel)appCliente.criarExcCliente();
	        	    painel.add(this.criarBotaoCancel("Cliente"));
	        		this.createAndShowGUI(painel);
	    		}
	    		else
	    			if ("altCliente".equals(e.getActionCommand())){
	    				painel = (JPanel)appCliente.criarAltCliente();
	            	    painel.add(this.criarBotaoCancel("Cliente"));
	            		this.createAndShowGUI(painel);
	    			}
	    			else
	    				if ("cancelProduto".equals(e.getActionCommand())){
	    		    		painel = (JPanel)appProduto.criarPainelProduto();
	    			        painel.add(this.criarBotoes("Produto"));
	    			    	this.createAndShowGUI(painel);
	    		    	}
	    		    	else
	    			    	if ("addProduto".equals(e.getActionCommand())){
	    			    		painel = (JPanel)appProduto.criarAddProduto();
	    			    	    painel.add(this.criarBotaoCancel("Produto"));
	    			    		this.createAndShowGUI(painel);
	    			    	}
	    			    	else
	    			    		if ("excProduto".equals(e.getActionCommand())){
	    			    			painel = (JPanel)appProduto.criarExcProduto();
	    			        	    painel.add(this.criarBotaoCancel("Produto"));
	    			        		this.createAndShowGUI(painel);
	    			    		}
	    			    		else
	    			    			if ("altProduto".equals(e.getActionCommand())){
	    			    				painel = (JPanel)appProduto.criarAltProduto();
	    			            	    painel.add(this.criarBotaoCancel("Produto"));
	    			            		this.createAndShowGUI(painel);
	    			    			}
    	else{
	        JMenuItem source = (JMenuItem)(e.getSource());
	        if (source.getText() == "Cliente"){
	            painel = (JPanel)appCliente.criarPainelCliente();
	            painel.add(this.criarBotoes("Cliente"));
	        	this.createAndShowGUI(painel);
	        }
	        else
	        	if(source.getText() == "Venda"){
	        		painel = (JPanel)appVenda.criarPainelVenda();
	        		this.createAndShowGUI(painel);
	        	}
	        	else
		        	if (source.getText() == "Funcionario"){
		        		painel = (JPanel)appFuncionario.criarPainelFuncionario();
			            painel.add(this.criarBotoes("Funcionario"));
			        	this.createAndShowGUI(painel);
		        	}
		        	else
			        	if (source.getText() == "Produto"){
			        		painel = (JPanel)appProduto.criarPainelProduto();
				            painel.add(this.criarBotoes("Produto"));
				        	this.createAndShowGUI(painel);
			        	}
			        	else
				        	if (source.getText() == "Fornecedor"){
				        		painel = (JPanel)appFornecedor.criarPainelFornecedor();
					            painel.add(this.criarBotoes("Fornecedor"));
					        	this.createAndShowGUI(painel);
				        	}
		        else{
		        String s = "Action event detected."
		                   + newline
		                   + "    Event source: " + source.getText()
		                   + " (an instance of " + getClassName(source) + ")";
		        output.append(s + newline);
		        output.setCaretPosition(output.getDocument().getLength());
		        }
	    	}
    }
    public void itemStateChanged(ItemEvent e) {
        JMenuItem source = (JMenuItem)(e.getSource());
        String s = "Item event detected."
                   + newline
                   + "    Event source: " + source.getText()
                   + " (an instance of " + getClassName(source) + ")"
                   + newline
                   + "    New state: "
                   + ((e.getStateChange() == ItemEvent.SELECTED) ?
                     "selected":"unselected");
        output.append(s + newline);
        output.setCaretPosition(output.getDocument().getLength());
    }

    // Returns just the class name -- no package info.
    protected String getClassName(Object o) {
        String classString = o.getClass().getName();
        int dotIndex = classString.lastIndexOf(".");
        return classString.substring(dotIndex+1);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = App.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI(Container cont) {
        //Create and set up the window.
        JFrame frame = new JFrame("Farmácia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        App demo = new App();
        frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(cont);

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Farmácia");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        App demo = new App();
        //frame.setJMenuBar(demo.createMenuBar());
        frame.setContentPane(demo.telaLogin());

        //Display the window.
        frame.setSize(450, 260);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
