package aplicacao;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import estrutura.Cliente;
import processamento.ManipulaGenerico;

public class AppCliente implements ActionListener,  KeyListener{
	ManipulaGenerico<Cliente> manipula = new ManipulaGenerico<Cliente>();
	AppSucesso sucesso = new AppSucesso();
	AppErro erro = new AppErro();
	AppConfirmacao confirma = new AppConfirmacao();
	JComboBox comboNome;

	JLabel espaco = new JLabel();
	JLabel id = new JLabel("Id");
	JLabel nome = new JLabel("Nome");
	JLabel tel = new JLabel("Telefone");
	JLabel cpf = new JLabel("CPF");
	JLabel rg = new JLabel("RG");
	JLabel email = new JLabel("Email");
	JLabel pontos = new JLabel("Pontos");
	JLabel texto = new JLabel();

	JTextField txtId = new JTextField();
	JTextField txtNome = new JTextField();
	JTextField txtTel = new JTextField();
	JTextField txtCpf = new JTextField();
	JTextField txtRg = new JTextField();
	JTextField txtEmail = new JTextField();
	JTextField txtPontos = new JTextField();

	JButton ok = new JButton("Ok");	
	JOptionPane janela = new JOptionPane();

	int opcao = 0;

	public Container criarPainelCliente(){
		GridLayout grid = new GridLayout(2,1);
		grid.setVgap(4);
		JPanel painel = new JPanel(grid);
		JLabel cliente = new JLabel("Cliente");
		painel.add(cliente);
		return painel;
	}

	public void adicionarClientesCombo(){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		try{
			clientes = manipula.lerTodosObjetos(Cliente.getTipo());
			if(clientes!= null){
				comboNome.addItem("");
				for(int i=0; i< clientes.size(); i++){
					Cliente cli = clientes.get(i);
					comboNome.addItem(cli.getId()+" "+cli.getNome());
				}
			}
			else
				comboNome.addItem("Não há clientes para excluir!");
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "cliente");
			janela.showMessageDialog(null, texto);
		}
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void excluirCliente(){
		txtRg.setText("");
		txtCpf.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtPontos.setText("");
		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Cliente c = manipula.buscarObjeto(Integer.parseInt(id),Cliente.getTipo());
				txtRg.setText(c.getRG());
				txtCpf.setText(c.getCPF());
				txtEmail.setText(c.getEmail());
				txtTel.setText(c.getTelefone());
				txtPontos.setText(c.getPontos()+"");
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarExcluirErro(classNotFound, "cliente");
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarExcluirErro(fileNotFound, "cliente");
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarExcluirErro(io, "cliente");
				janela.showMessageDialog(null, texto);
			}
			catch(NumberFormatException numberFormatException){
			}
		}
		catch(StringIndexOutOfBoundsException stringOut){
			txtRg.setText("");
			txtCpf.setText("");
			txtEmail.setText("");
			txtTel.setText("");
			txtPontos.setText("");
		}
	}

	public void excluirOk(){
		String text = (String)comboNome.getSelectedItem();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();;
		try{
			clientes = manipula.lerTodosObjetos(Cliente.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "cliente");
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um cliente para excluir!");
		else{
			texto = confirma.confirmaExclusao("cliente", text);
			opcao = janela.showConfirmDialog(null, texto, "Alterar Cliente", JOptionPane.YES_NO_OPTION);
			if(opcao == 0){
				//fazer a exclusão
				texto = sucesso.criarExcluirSucesso("cliente");
				janela.showMessageDialog(null, texto);
				comboNome = new JComboBox();
				this.adicionarClientesCombo();
				comboNome.addActionListener (new ActionListener () {
					public void actionPerformed(ActionEvent e) {
						excluirCliente();
					}
				});
				String textE = (String)comboNome.getSelectedItem();
				String idE= "0";
				int i = 0;
				try{
					while(text.charAt(i)!= ' '){
						idE = text.charAt(i)+"";
						i++;
					}
					clientes.remove(manipula.posicaoArray(Integer.parseInt(idE), clientes));
					manipula.incluirObjeto(clientes, Cliente.getTipo());
				}
				catch(Exception e){
				}
				//comboNome.setSelectedIndex(0);
				txtRg.setText("");
				txtCpf.setText("");
				txtEmail.setText("");
				txtTel.setText("");
				txtPontos.setText("");
			}
		}
	}

	public Container criarExcCliente(){
		GridLayout grid = new GridLayout(8,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);

		JLabel cliente = new JLabel("Excluir Cliente");
		txtRg.setText("");
		txtCpf.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtPontos.setText("");
		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);

		ArrayList<Cliente> clientes = null;
		try{
			clientes = manipula.lerTodosObjetos(Cliente.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "cliente");
			janela.showMessageDialog(null, texto);
		}

		comboNome = new JComboBox(); 
		this.adicionarClientesCombo();
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				excluirOk();
			}
		});
		txtRg.setEditable(false);
		txtCpf.setEditable(false);
		txtTel.setEditable(false);
		txtEmail.setEditable(false);
		txtPontos.setEditable(false);

		painel.add(cliente);
		painel.add(espaco);
		painel.add(nome);
		painel.add(comboNome);
		painel.add(rg);
		painel.add(txtRg);
		painel.add(cpf);
		painel.add(txtCpf);
		painel.add(tel);
		painel.add(txtTel);
		painel.add(email);
		painel.add(txtEmail);
		painel.add(pontos);
		painel.add(txtPontos);
		painel.add(ok);

		return painel;
	}

	public void alterarOk(){
		String text = (String)comboNome.getSelectedItem();
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();;
		try{
			clientes = manipula.lerTodosObjetos(Cliente.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "cliente");
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um cliente para alterar!");
		else{
			if (txtTel.getText().isEmpty()){
				janela.showMessageDialog(null, "Preencha o campo Telefone!");
				txtTel.requestFocus();
			}
			else
				if (txtRg.getText().isEmpty()){
					janela.showMessageDialog(null, "Preencha o campo RG!");
					txtRg.requestFocus();
				}
				else
					if (txtCpf.getText().isEmpty()){
						janela.showMessageDialog(null, "Preencha o campo CPF!");
						txtCpf.requestFocus();
					}
					else
						if (txtEmail.getText().isEmpty()){
							janela.showMessageDialog(null, "Preencha o campo Email!");
							txtEmail.requestFocus();
						}
						else{    		
							texto = confirma.confirmaAlteracao("cliente", text);
							opcao = janela.showConfirmDialog(null, texto, "Alterar Cliente", JOptionPane.YES_NO_OPTION);
							if (opcao == 0){
								//altera cliente
								texto = sucesso.criarAlterarSucesso("cliente");
								janela.showMessageDialog(null, texto);
								//limpar campos
								String textE = (String)comboNome.getSelectedItem();
								String idE= "0";
								int i = 0;
								try{
									while(text.charAt(i)!= ' '){
										idE = text.charAt(i)+"";
										i++;
									}
									Cliente clienteN = new Cliente(manipula.buscarObjeto(Integer.parseInt(idE), "clientes").getId(), txtEmail.getText(),
											manipula.buscarObjeto(Integer.parseInt(idE), "clientes").getNome(),txtTel.getText(),txtCpf.getText(),txtRg.getText(),0);
									
									clientes.set(manipula.posicaoArray(Integer.parseInt(idE), clientes), clienteN);
									manipula.incluirObjeto(clientes, Cliente.getTipo());
								}

								catch(Exception e){

								}
							}
							else{
								//pegar as informações originais do cliente
							}
						}
		}
	}

	public Container criarAltCliente(){
		GridLayout grid = new GridLayout(8,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel cliente = new JLabel("Alterar Cliente");

		txtRg.setText("");
		txtCpf.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtPontos.setText("");

		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);
		comboNome = new JComboBox(); 

		try{
			ArrayList<Cliente> clientes = manipula.lerTodosObjetos(Cliente.getTipo());
			if(clientes!= null){
				comboNome.addItem("");
				for(int i=0; i< clientes.size(); i++){
					Cliente cli = clientes.get(i);
					comboNome.addItem(cli.getId()+" "+cli.getNome());
				}
			}
			else
				this.comboNome.addItem("Não há clientes para alterar!");	
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarAlterarErro(classNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarAlterarErro(fileNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarAlterarErro(io, "cliente");
			janela.showMessageDialog(null, texto);
		}
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarCliente();
			}
		});
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarOk();
			}
		});
		txtTel.setEditable(false);
		txtRg.setEditable(false);
		txtCpf.setEditable(false);
		txtEmail.setEditable(false);
		txtPontos.setEditable(false);

		painel.add(cliente);
		painel.add(espaco);
		painel.add(nome);
		painel.add(comboNome);
		painel.add(rg);
		painel.add(txtRg);
		painel.add(cpf);
		painel.add(txtCpf);
		painel.add(tel);
		painel.add(txtTel);
		painel.add(email);
		painel.add(txtEmail);
		painel.add(pontos);
		painel.add(txtPontos);
		painel.add(ok);

		return painel;
	}

	public void alterarCliente(){
		//comboNome.setEnabled(false);
		txtRg.setText("");
		txtRg.addKeyListener(this);
		txtCpf.setText("");
		txtCpf.addKeyListener(this);
		txtEmail.setText("");
		txtTel.setText("");
		txtTel.addKeyListener(this);
		txtPontos.setText("");
		txtRg.setEditable(true);
		txtCpf.setEditable(true);
		txtTel.setEditable(true);
		txtEmail.setEditable(true);

		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Cliente c = manipula.buscarObjeto(Integer.parseInt(id),Cliente.getTipo());
				txtRg.setText(c.getRG());
				txtCpf.setText(c.getCPF());
				txtTel.setText(c.getTelefone());
				txtEmail.setText(c.getEmail());
				txtPontos.setText(c.getPontos()+"");			
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarAlterarErro(classNotFound, "cliente");
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarAlterarErro(fileNotFound, "cliente");
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarAlterarErro(io, "cliente");
				janela.showMessageDialog(null, texto);
			}
			catch(NumberFormatException numberFormatException){
				txtRg.setText("");
				txtCpf.setText("");
				txtEmail.setText("");
				txtTel.setText("");
				txtPontos.setText("");
				txtRg.setEditable(false);
				txtCpf.setEditable(false);
				txtEmail.setEditable(false);
				txtTel.setEditable(false);
				txtPontos.setEditable(false);
			}
		}
		catch(StringIndexOutOfBoundsException stringOut){
			txtRg.setText("");
			txtCpf.setText("");
			txtEmail.setText("");
			txtTel.setText("");
			txtPontos.setText("");
			txtRg.setEditable(false);
			txtCpf.setEditable(false);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			txtPontos.setEditable(false);
		}
	}

	public void incluirCliente(){
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();;
		try{
			clientes = manipula.lerTodosObjetos(Cliente.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "cliente");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "cliente");
			janela.showMessageDialog(null, texto);
		}
		if (txtNome.getText().isEmpty()){
			janela.showMessageDialog(null, "Preencha o campo Nome!");
			txtNome.requestFocus();
		}
		else
			if (txtTel.getText().isEmpty()){
				janela.showMessageDialog(null, "Preencha o campo Telefone!");
				txtTel.requestFocus();
			}
			else
				if (txtRg.getText().isEmpty()){
					janela.showMessageDialog(null, "Preencha o campo RG!");
					txtRg.requestFocus();
				}
				else
					if (txtCpf.getText().isEmpty()){
						janela.showMessageDialog(null, "Preencha o campo CPF!");
						txtCpf.requestFocus();
					}
					else
						if (txtEmail.getText().isEmpty()){
							janela.showMessageDialog(null, "Preencha o campo Email!");
							txtEmail.requestFocus();
						}
						else{    		
							try{
								if(clientes == null)
									clientes = new ArrayList<Cliente>();
								Cliente cliente = new Cliente(manipula.ultimoId(Cliente.getTipo()), txtEmail.getText(),
										txtNome.getText(),txtTel.getText(),txtCpf.getText(),txtRg.getText(),0);
								clientes.add(cliente);
								manipula.incluirObjeto(clientes, Cliente.getTipo());
								texto = sucesso.criarAdicionarSucesso("cliente");
								janela.showMessageDialog(null, texto);

								txtId.setEnabled(false);
								txtNome.setText("");
								txtRg.setText("");
								txtRg.addKeyListener(this);
								txtCpf.setText("");
								txtCpf.addKeyListener(this);
								txtEmail.setText("");
								txtEmail.addKeyListener(this);
								txtTel.setText("");
								txtTel.addKeyListener(this);
								//txtPontos.setText("");
								txtNome.requestFocus();
								txtId.setText(Integer.toString(manipula.ultimoId(Cliente.getTipo())));
								txtId.addKeyListener(this);
							}
							catch(IOException io){
								System.out.print(io.toString());
								texto = erro.criarAdicionarErro(io, "cliente");
								janela.showMessageDialog(null, texto);
								txtNome.setText("");
								txtRg.setText("");
								txtRg.addKeyListener(this);
								txtCpf.setText("");
								txtCpf.addKeyListener(this);
								txtEmail.setText("");
								txtTel.setText("");
								txtTel.addKeyListener(this);
								txtNome.requestFocus();
								//txtPontos.setText("0");
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NullPointerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
	}

	public void ehNumero(JTextField text){
		String str = text.getText();
		String nova = ""; 
		if (str != null)
			for (int i = 0; i < str.length(); i++) {
				if (Character.isDigit(str.charAt(i)))
					nova += str.charAt(i);
			}
		text.setText(nova);
	}

	public Container criarAddCliente(){
		GridLayout grid = new GridLayout(9,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel cliente = new JLabel("Incluir Cliente");

		txtNome.setText("");
		txtRg.setText("");
		txtRg.addKeyListener(this);
		txtCpf.setText("");
		txtCpf.addKeyListener(this);
		txtEmail.setText("");
		txtTel.setText("");
		txtTel.addKeyListener(this);
		txtPontos.setText("0");
		try {
			txtId.setText(Integer.toString(manipula.ultimoId(Cliente.getTipo())));
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtNome.setEditable(true);
		txtRg.setEditable(true);
		txtCpf.setEditable(true);
		txtEmail.setEditable(true);
		txtTel.setEditable(true);
		txtPontos.setEditable(false);
		txtId.setEditable(false);

		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				incluirCliente();
			}
		});

		painel.add(cliente);
		painel.add(espaco);
		painel.add(id);
		painel.add(txtId);
		painel.add(nome);
		painel.add(txtNome);
		painel.add(tel);
		painel.add(txtTel);
		painel.add(rg);
		painel.add(txtRg);
		painel.add(cpf);
		painel.add(txtCpf);
		painel.add(email);
		painel.add(txtEmail);
		painel.add(pontos);
		painel.add(txtPontos);
		painel.add(ok);

		return painel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		JTextField text = (JTextField)e.getSource();
		ehNumero(text);
	}

	@Override
	public void keyTyped(KeyEvent e) {		
	}
}
