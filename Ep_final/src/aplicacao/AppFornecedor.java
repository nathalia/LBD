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


import estrutura.Fornecedor;
import processamento.ManipulaGenerico;

public class AppFornecedor implements ActionListener,  KeyListener{
	ManipulaGenerico<Fornecedor> manipula = new ManipulaGenerico<Fornecedor>();
	AppSucesso sucesso = new AppSucesso();
	AppErro erro = new AppErro();
	AppConfirmacao confirma = new AppConfirmacao();
	JComboBox comboNome;

	JLabel espaco = new JLabel();
	JLabel id = new JLabel("Id");
	JLabel nome = new JLabel("Nome");
	JLabel email = new JLabel("Email");
	JLabel tel = new JLabel("Telefone");
	JLabel cnpj = new JLabel("CNPJ");
	JLabel razao = new JLabel("Razão Social");
	JLabel contato = new JLabel("Contato");
	JLabel texto = new JLabel();

	JTextField txtId = new JTextField();
	JTextField txtNome = new JTextField();
	JTextField txtEmail = new JTextField();
	JTextField txtTel = new JTextField();
	JTextField txtCnpj = new JTextField();
	JTextField txtRazao = new JTextField();
	JTextField txtContato = new JTextField();

	JButton ok = new JButton("Ok");	
	JOptionPane janela = new JOptionPane();

	int opcao = 0;

	public Container criarPainelFornecedor(){
		GridLayout grid = new GridLayout(2,1);
		grid.setVgap(4);
		JPanel painel = new JPanel(grid);

		JLabel forn = new JLabel("Fornecedor");

		painel.add(forn);

		return painel;
	}

	public void adicionarFornecedorCombo(){
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
		try{
			fornecedores = manipula.lerTodosObjetos(Fornecedor.getTipo());
			if(fornecedores!= null){
				comboNome.addItem("");
				for(int i=0; i< fornecedores.size(); i++){
					Fornecedor forn = fornecedores.get(i);
					comboNome.addItem(forn.getId()+" "+forn.getNome());
				}
			}
			else
				comboNome.addItem("Não há fornecedores para excluir!");
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void excluirFornecedor(){
		txtRazao.setText("");
		txtCnpj.setText("");
		txtContato.setText("");
		txtTel.setText("");
		txtEmail.setText("");
		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Fornecedor c = manipula.buscarObjeto(Integer.parseInt(id), Fornecedor.getTipo());
				txtRazao.setText(c.getRazaoSocial());
				txtCnpj.setText(c.getCNPJ());
				txtEmail.setText(c.getEmail());
				txtTel.setText(c.getTelefone());
				txtContato.setText(c.getContato());
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarExcluirErro(classNotFound, "fornecedor");
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarExcluirErro(fileNotFound, "fornecedor");
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarExcluirErro(io, "fornecedor");
				janela.showMessageDialog(null, texto);
			}
			catch(NumberFormatException numberFormatException){
			}
		}
		catch(StringIndexOutOfBoundsException stringOut){
			txtRazao.setText("");
			txtCnpj.setText("");
			txtEmail.setText("");
			txtTel.setText("");
			txtContato.setText("");
		}
	}

	public void excluirOk(){

		String text = (String)comboNome.getSelectedItem();
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();;
		try{
			fornecedores = manipula.lerTodosObjetos(Fornecedor.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um fornecedor para excluir!");
		else{
			texto = confirma.confirmaExclusao("fornecedor", text);
			opcao = janela.showConfirmDialog(null, texto, "Alterar Fornecedor", JOptionPane.YES_NO_OPTION);
			if(opcao == 0){
				//fazer a exclusão
				texto = sucesso.criarExcluirSucesso("fornecedor");
				janela.showMessageDialog(null, texto);
				comboNome = new JComboBox();
				this.adicionarFornecedorCombo();
				comboNome.addActionListener (new ActionListener () {
					public void actionPerformed(ActionEvent e) {
						excluirFornecedor();
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
					fornecedores.remove(manipula.posicaoArray(Integer.parseInt(idE), fornecedores));
					manipula.incluirObjeto(fornecedores, Fornecedor.getTipo());
				}
				catch(Exception e){
				}
				//comboNome.setSelectedIndex(0);
				txtRazao.setText("");
				txtCnpj.setText("");
				txtEmail.setText("");
				txtTel.setText("");
				txtEmail.setText("");
				txtContato.setText("");
			}
		}
	}

	public Container criarExcFornecedor(){
		GridLayout grid = new GridLayout(8,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);

		JLabel fornecedor = new JLabel("Excluir Fornecedor");
		txtRazao.setText("");
		txtCnpj.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtContato.setText("");
		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);


		ArrayList<Fornecedor> fornecedores = null;
		try{
			fornecedores = manipula.lerTodosObjetos(Fornecedor.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "fornecedor");
			janela.showMessageDialog(null, texto);
		}

		comboNome = new JComboBox(); 
		this.adicionarFornecedorCombo();
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor();
			}
		});
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				excluirOk();
			}
		});
		txtRazao.setEditable(false);
		txtCnpj.setEditable(false);
		txtTel.setEditable(false);
		txtEmail.setEditable(false);
		txtContato.setEditable(false);

		painel.add(fornecedor);
		painel.add(espaco);
		painel.add(nome);
		painel.add(comboNome);
		painel.add(razao);
		painel.add(txtRazao);
		painel.add(cnpj);
		painel.add(txtCnpj);
		painel.add(tel);
		painel.add(txtTel);
		painel.add(email);
		painel.add(txtEmail);
		painel.add(contato);
		painel.add(txtContato);
		painel.add(ok);

		return painel;
	}

	public void alterarOk(){
		String text = (String)comboNome.getSelectedItem();
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();;
		try{
			fornecedores = manipula.lerTodosObjetos(Fornecedor.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um fornecedor para alterar!");
		else{
			if (txtTel.getText().isEmpty()){
				janela.showMessageDialog(null, "Preencha o campo Telefone!");
				txtTel.requestFocus();
			}
			else
				if (txtCnpj.getText().isEmpty()){
					janela.showMessageDialog(null, "Preencha o campo CNPJ!");
					txtCnpj.requestFocus();
				}
				else
					if (txtRazao.getText().isEmpty()){
						janela.showMessageDialog(null, "Preencha o campo Razão Social!");
						txtRazao.requestFocus();
					}
					else
						if (txtEmail.getText().isEmpty()){
							janela.showMessageDialog(null, "Preencha o campo Email!");
							txtEmail.requestFocus();
						}
						else
							if (txtContato.getText().isEmpty()){
								janela.showMessageDialog(null, "Preencha o campo Contato!");
								txtContato.requestFocus();
							}
							else{    		
								texto = confirma.confirmaAlteracao("fornecedor", text);
								opcao = janela.showConfirmDialog(null, texto, "Alterar Fornecedor", JOptionPane.YES_NO_OPTION);
								if (opcao == 0){

									//altera fornecedor
									texto = sucesso.criarAlterarSucesso("fornecedor");
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
										Fornecedor fornecedorN = new Fornecedor(manipula.buscarObjeto(Integer.parseInt(idE), Fornecedor.getTipo()).getId(), 
												txtEmail.getText(),manipula.buscarObjeto(Integer.parseInt(idE), Fornecedor.getTipo()).getNome(),
												txtTel.getText(),txtCnpj.getText(),txtRazao.getText(),txtContato.getText());
										
										fornecedores.set(manipula.posicaoArray(Integer.parseInt(idE), fornecedores), fornecedorN);
										manipula.incluirObjeto(fornecedores, Fornecedor.getTipo());
									}
									catch(Exception e){
									}
								}
								else{
									//pegar as informações originais do fornecedor
								}
							}
		}
	}

	public Container criarAltFornecedor(){
		GridLayout grid = new GridLayout(8,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel fornecedor = new JLabel("Alterar Fornecedor");

		txtRazao.setText("");
		txtCnpj.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtContato.setText("");

		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);
		comboNome = new JComboBox(); 

		try{
			ArrayList<Fornecedor> fornecedores = manipula.lerTodosObjetos(Fornecedor.getTipo());
			if(fornecedores!= null){
				comboNome.addItem("");
				for(int i=0; i< fornecedores.size(); i++){
					Fornecedor forn = fornecedores.get(i);
					comboNome.addItem(forn.getId()+" "+forn.getNome());
				}
			}
			else{
				this.comboNome.addItem("Não há fornecedores para alterar!");
			}
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarFornecedor();
			}
		});
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarOk();
			}
		});
		txtTel.setEditable(false);
		txtRazao.setEditable(false);
		txtCnpj.setEditable(false);
		txtEmail.setEditable(false);
		txtContato.setEditable(false);

		painel.add(fornecedor);
		painel.add(espaco);
		painel.add(nome);
		painel.add(comboNome);
		painel.add(razao);
		painel.add(txtRazao);
		painel.add(cnpj);
		painel.add(txtCnpj);
		painel.add(tel);
		painel.add(txtTel);
		painel.add(email);
		painel.add(txtEmail);
		painel.add(contato);
		painel.add(txtContato);
		painel.add(ok);

		return painel;
	}

	public void alterarFornecedor(){
		//comboNome.setEnabled(false);
		txtRazao.setText("");
		txtRazao.addKeyListener(this);
		txtCnpj.setText("");
		txtCnpj.addKeyListener(this);
		txtEmail.setText("");
		txtEmail.addKeyListener(this);
		txtTel.setText("");
		txtTel.addKeyListener(this);
		txtContato.setText("");
		txtRazao.setEditable(true);
		txtCnpj.setEditable(true);
		txtTel.setEditable(true);
		txtEmail.setEditable(true);
		txtContato.setEditable(true);

		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Fornecedor c = manipula.buscarObjeto(Integer.parseInt(id), Fornecedor.getTipo());
				txtRazao.setText(c.getRazaoSocial());
				txtCnpj.setText(c.getCNPJ());
				txtTel.setText(c.getTelefone());
				txtEmail.setText(c.getEmail());
				txtContato.setText(c.getContato());			
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarAlterarErro(classNotFound, "fornecedor");
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarAlterarErro(fileNotFound, "fornecedor");
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarAlterarErro(io, "fornecedor");
				janela.showMessageDialog(null, texto);
			}
			catch(NumberFormatException numberFormatException){
				txtRazao.setText("");
				txtCnpj.setText("");
				txtEmail.setText("");
				txtTel.setText("");
				txtContato.setText("");
				txtRazao.setEditable(false);
				txtCnpj.setEditable(false);
				txtEmail.setEditable(false);
				txtTel.setEditable(false);
				txtContato.setEditable(false);
			}
		}
		catch(StringIndexOutOfBoundsException stringOut){
			txtRazao.setText("");
			txtCnpj.setText("");
			txtEmail.setText("");
			txtTel.setText("");
			txtContato.setText("");
			txtRazao.setEditable(false);
			txtCnpj.setEditable(false);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			txtContato.setEditable(false);
		}
	}

	public void incluirFornecedor() {
		ArrayList<Fornecedor> fornecedores = new ArrayList<Fornecedor>();;
		try{
			fornecedores = manipula.lerTodosObjetos(Fornecedor.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "fornecedor");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "fornecedor");
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
				if (txtRazao.getText().isEmpty()){
					janela.showMessageDialog(null, "Preencha o campo Razão Social!");
					txtRazao.requestFocus();
				}
				else
					if (txtCnpj.getText().isEmpty()){
						janela.showMessageDialog(null, "Preencha o campo CNPJ!");
						txtCnpj.requestFocus();
					}
					else
						if (txtEmail.getText().isEmpty()){
							janela.showMessageDialog(null, "Preencha o campo Email!");
							txtEmail.requestFocus();
						}
						else
							if (txtContato.getText().isEmpty()){
								janela.showMessageDialog(null, "Preencha o campo Contato!");
								txtContato.requestFocus();
							}
							else{    		
								try{	 
									if(fornecedores == null)
										fornecedores = new ArrayList<Fornecedor>();
									Fornecedor fornecedor = new Fornecedor(manipula.ultimoId(Fornecedor.getTipo()), txtEmail.getText(),
											txtNome.getText(),txtTel.getText(),txtCnpj.getText(),txtRazao.getText(),txtContato.getText());
									fornecedores.add(fornecedor);
									manipula.incluirObjeto(fornecedores, Fornecedor.getTipo());
									texto = sucesso.criarAdicionarSucesso("fornecedor");
									janela.showMessageDialog(null, texto);

									txtId.setEnabled(false);
									txtNome.setText("");
									txtRazao.setText("");
									txtRazao.addKeyListener(this);
									txtCnpj.setText("");
									txtCnpj.addKeyListener(this);
									txtEmail.setText("");
									txtEmail.addKeyListener(this);
									txtTel.setText("");
									txtTel.addKeyListener(this);
									txtNome.requestFocus();
									txtId.setText(Integer.toString(manipula.ultimoId(Fornecedor.getTipo())));
									txtId.addKeyListener(this); 
								}
								catch(IOException io){
									System.out.print(io.toString());
									texto = erro.criarAdicionarErro(io, "fornecedor");
									janela.showMessageDialog(null, texto);
									txtNome.setText("");
									txtRazao.setText("");
									txtRazao.addKeyListener(this);
									txtCnpj.setText("");
									txtCnpj.addKeyListener(this);
									txtEmail.setText("");
									txtTel.setText("");
									txtTel.addKeyListener(this);
									txtContato.setText("");
									txtContato.addKeyListener(this);
									txtNome.requestFocus();
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

	public Container criarAddFornecedor(){
		GridLayout grid = new GridLayout(9,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel fornecedor = new JLabel("Incluir Fornecedor");

		txtNome.setText("");
		txtRazao.setText("");
		txtRazao.addKeyListener(this);
		txtCnpj.setText("");
		txtCnpj.addKeyListener(this);
		txtEmail.setText("");
		txtTel.setText("");
		txtTel.addKeyListener(this);
		txtContato.setText("");
		try {
			txtId.setText(Integer.toString(manipula.ultimoId(Fornecedor.getTipo())));
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
		txtRazao.setEditable(true);
		txtCnpj.setEditable(true);
		txtEmail.setEditable(true);
		txtTel.setEditable(true);
		txtContato.setEditable(true);
		txtId.setEditable(false);

		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				incluirFornecedor();
			}
		});

		painel.add(fornecedor);
		painel.add(espaco);
		painel.add(id);
		painel.add(txtId);
		painel.add(nome);
		painel.add(txtNome);
		painel.add(tel);
		painel.add(txtTel);
		painel.add(razao);
		painel.add(txtRazao);
		painel.add(cnpj);
		painel.add(txtCnpj);
		painel.add(email);
		painel.add(txtEmail);
		painel.add(contato);
		painel.add(txtContato);
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
