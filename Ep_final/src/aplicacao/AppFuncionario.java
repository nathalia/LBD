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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import estrutura.Funcionario;
import processamento.ManipulaGenerico;

public class AppFuncionario implements ActionListener,  KeyListener{
	ManipulaGenerico<Funcionario> manipula = new ManipulaGenerico<Funcionario>();
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
	JLabel endereco = new JLabel("Endereço");
	JLabel senha = new JLabel("Senha");
	JLabel confirmSenha = new JLabel("Confirmar senha");
	JLabel texto = new JLabel();

	JTextField txtId = new JTextField();
	JTextField txtNome = new JTextField();
	JTextField txtTel = new JTextField();
	JTextField txtCpf = new JTextField();
	JTextField txtRg = new JTextField();
	JTextField txtEmail = new JTextField();
	JTextField txtEndereco = new JTextField();
	JPasswordField txtSenha = new JPasswordField();
	JPasswordField txtConfirmSenha = new JPasswordField();

	JButton ok = new JButton("Ok");	
	JOptionPane janela = new JOptionPane();

	int opcao = 0;

	public Container criarPainelFuncionario(){
		GridLayout grid = new GridLayout(2,1);
		grid.setVgap(4);
		JPanel painel = new JPanel(grid);
		JLabel funcionario = new JLabel("Funcionário");
		painel.add(funcionario);
		return painel;
	}

	public void adicionarFuncionariosCombo(){
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
		try{
			funcionarios = manipula.lerTodosObjetos(Funcionario.getTipo());
			if(funcionarios!= null){
				comboNome.addItem("");
				for(int i=0; i< funcionarios.size(); i++){
					Funcionario fun = funcionarios.get(i);
					comboNome.addItem(fun.getId()+" "+fun.getNome());
				}
			}
			else
				comboNome.addItem("Não há funcionários para excluir!");
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "funcionário");
			janela.showMessageDialog(null, texto);
		}
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void excluirFuncionario(){
		txtRg.setText("");
		txtCpf.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtEndereco.setText("");
		txtSenha.setText("");
		txtConfirmSenha.setText("");
		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Funcionario f = manipula.buscarObjeto(Integer.parseInt(id), Funcionario.getTipo());
				txtRg.setText(f.getRG());
				txtCpf.setText(f.getCPF());
				txtEmail.setText(f.getEmail());
				txtTel.setText(f.getTelefone());
				txtEndereco.setText(f.getEndereco()+"");
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarExcluirErro(classNotFound, "funcionário");
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarExcluirErro(fileNotFound, "funcionário");
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarExcluirErro(io, "funcionário");
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
			txtEndereco.setText("");
		}
	}

	public void excluirOk(){
		String text = (String)comboNome.getSelectedItem();
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();;
		try{
			funcionarios = manipula.lerTodosObjetos(Funcionario.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um funcionario para excluir!");
		else{
			texto = confirma.confirmaExclusao("funcionario", text);
			opcao = janela.showConfirmDialog(null, texto, "Alterar Funcionário", JOptionPane.YES_NO_OPTION);
			if(opcao == 0){
				//fazer a exclusão
				texto = sucesso.criarExcluirSucesso("funcionário");
				janela.showMessageDialog(null, texto);
				comboNome = new JComboBox();
				this.adicionarFuncionariosCombo();
				comboNome.addActionListener (new ActionListener () {
					public void actionPerformed(ActionEvent e) {
						excluirFuncionario();
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
					funcionarios.remove(manipula.posicaoArray(Integer.parseInt(idE), funcionarios));
					manipula.incluirObjeto(funcionarios, Funcionario.getTipo());
				}
				catch(Exception e){

				}
				//comboNome.setSelectedIndex(0);
				txtRg.setText("");
				txtCpf.setText("");
				txtEmail.setText("");
				txtTel.setText("");
				txtEndereco.setText("");
			}
		}
	}

	public Container criarExcFuncionario(){
		GridLayout grid = new GridLayout(8,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);

		JLabel funcionario = new JLabel("Excluir Funcionário");
		txtRg.setText("");
		txtCpf.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtEndereco.setText("");
		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);

		ArrayList<Funcionario> funcionarios = null;
		try{
			funcionarios = manipula.lerTodosObjetos(Funcionario.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "funcionário");
			janela.showMessageDialog(null, texto);
		}

		comboNome = new JComboBox(); 
		this.adicionarFuncionariosCombo();
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				excluirFuncionario();
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
		txtEndereco.setEditable(false);
		txtSenha.setEditable(false);

		painel.add(funcionario);
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
		painel.add(endereco);
		painel.add(txtEndereco);
		painel.add(ok);

		return painel;
	}

	public void alterarOk(){
		String text = (String)comboNome.getSelectedItem();
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();;
		try{
			funcionarios = manipula.lerTodosObjetos(Funcionario.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um funcionario para alterar!");
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
						else
							if (txtEndereco.getText().isEmpty()){
								janela.showMessageDialog(null, "Preencha o campo Endereco!");
								txtEndereco.requestFocus();
							}
							else
								if (txtSenha.getText().isEmpty()){
									janela.showMessageDialog(null, "Preencha o campo senha!");
									txtSenha.requestFocus();
								}
								else{    		
									texto = confirma.confirmaAlteracao("funcionario", text);
									opcao = janela.showConfirmDialog(null, texto, "Alterar Funcionário", JOptionPane.YES_NO_OPTION);
									if (opcao == 0){
										//altera funcionario
										texto = sucesso.criarAlterarSucesso("funcionario");
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
											Funcionario funcionarioN = new Funcionario(manipula.buscarObjeto(Integer.parseInt(idE), "funcionarios").getId(), 
													txtEmail.getText(), manipula.buscarObjeto(Integer.parseInt(idE), "funcionarios").getNome(),
													txtTel.getText(),txtCpf.getText(),txtRg.getText(), txtEndereco.getText(), txtSenha.getText());

											funcionarios.set(manipula.posicaoArray(Integer.parseInt(idE), funcionarios), funcionarioN);
											manipula.incluirObjeto(funcionarios, Funcionario.getTipo());
										}
										catch(Exception e){
										}
									}
									else{
										//pegar as informações originais do funcionario
									}
								}
		}
	}

	public Container criarAltFuncionario(){
		GridLayout grid = new GridLayout(10,2);
		grid.setVgap(3);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel funcionario = new JLabel("Alterar Funcionário");

		txtRg.setText("");
		txtCpf.setText("");
		txtEmail.setText("");
		txtTel.setText("");
		txtEndereco.setText("");
		txtSenha.setText("");
		txtConfirmSenha.setText("");

		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);
		comboNome = new JComboBox(); 

		try{
			ArrayList<Funcionario> funcionarios = manipula.lerTodosObjetos(Funcionario.getTipo());
			if(funcionarios!= null){
				comboNome.addItem("");
				for(int i=0; i< funcionarios.size(); i++){
					Funcionario cli = funcionarios.get(i);
					comboNome.addItem(cli.getId()+" "+cli.getNome());
				}
			}
			else
				this.comboNome.addItem("Não há funcionários para alterar!");	
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarAlterarErro(classNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarAlterarErro(fileNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarAlterarErro(io, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarFuncionario();
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
		txtEndereco.setEditable(false);
		txtSenha.setEditable(false);
		txtConfirmSenha.setEditable(false);

		painel.add(funcionario);
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
		painel.add(endereco);
		painel.add(txtEndereco);
		painel.add(senha);
		painel.add(txtSenha);
		painel.add(confirmSenha);
		painel.add(txtConfirmSenha);
		painel.add(ok);

		return painel;
	}

	public void alterarFuncionario(){
		//comboNome.setEnabled(false);
		txtRg.setText("");
		txtRg.addKeyListener(this);
		txtCpf.setText("");
		txtCpf.addKeyListener(this);
		txtEmail.setText("");
		txtTel.setText("");
		txtTel.addKeyListener(this);
		txtEndereco.setText("");
		txtEndereco.addKeyListener(this);
		txtSenha.setText("");
		txtSenha.addKeyListener(this);
		txtRg.setEditable(true);
		txtCpf.setEditable(true);
		txtTel.setEditable(true);
		txtEmail.setEditable(true);
		txtEndereco.setEditable(true);
		txtSenha.setEditable(true);

		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Funcionario f = manipula.buscarObjeto(Integer.parseInt(id), Funcionario.getTipo());
				txtRg.setText(f.getRG());
				txtCpf.setText(f.getCPF());
				txtTel.setText(f.getTelefone());
				txtEmail.setText(f.getEmail());
				txtEndereco.setText(f.getEndereco());
				txtSenha.setText(f.getSenha()+"");
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarAlterarErro(classNotFound, "funcionário");
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarAlterarErro(fileNotFound, "funcionário");
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarAlterarErro(io, "funcionário");
				janela.showMessageDialog(null, texto);
			}
			catch(NumberFormatException numberFormatException){
				txtRg.setText("");
				txtCpf.setText("");
				txtEmail.setText("");
				txtTel.setText("");
				txtEndereco.setText("");
				txtSenha.setText("");
				txtConfirmSenha.setText("");
				txtRg.setEditable(false);
				txtCpf.setEditable(false);
				txtEmail.setEditable(false);
				txtTel.setEditable(false);
				txtEndereco.setEditable(false);
				txtSenha.setEditable(false);
				txtConfirmSenha.setEditable(false);
			}
		}
		catch(StringIndexOutOfBoundsException stringOut){
			txtRg.setText("");
			txtCpf.setText("");
			txtEmail.setText("");
			txtTel.setText("");
			txtEndereco.setText("");
			txtSenha.setText("");
			txtConfirmSenha.setText("");
			txtRg.setEditable(false);
			txtCpf.setEditable(false);
			txtEmail.setEditable(false);
			txtTel.setEditable(false);
			txtEndereco.setEditable(false);
			txtSenha.setEditable(false);
			txtConfirmSenha.setEditable(false);
		}
	}

	public void incluirFuncionario(){
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();;
		try{
			funcionarios = manipula.lerTodosObjetos(Funcionario.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, "funcionário");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, "funcionário");
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
						else
							if (txtEndereco.getText().isEmpty()){
								janela.showMessageDialog(null, "Preencha o campo Endereço!");
								txtEndereco.requestFocus();
							}
							else
								if (txtSenha.getText().isEmpty()){
									janela.showMessageDialog(null, "Preencha o campo Senha!");
									txtSenha.requestFocus();
								}
		//Arrumar!!!!
								/*else
									if (txtConfirmSenha.getText().isEmpty()){
										janela.showMessageDialog(null, "Confirme a senha!");
										txtConfirmSenha.requestFocus();
									}
									/*else
										if (txtSenha.getText() != txtConfirmSenha.getText()){
											janela.showMessageDialog(null, "Senha confirmada incorretamente!");
											txtSenha.setText("");
											txtConfirmSenha.setText("");
											txtSenha.requestFocus();
										}*/
										else{    		   		
											try{
												if(funcionarios == null)
													funcionarios = new ArrayList<Funcionario>();
												Funcionario funcionario = new Funcionario(manipula.ultimoId(Funcionario.getTipo()), txtEmail.getText(),
														txtNome.getText(),txtTel.getText(),txtCpf.getText(),txtRg.getText(),txtEndereco.getText(), txtSenha.getText());
												funcionarios.add(funcionario);
												manipula.incluirObjeto(funcionarios, Funcionario.getTipo());
												texto = sucesso.criarAdicionarSucesso("funcionário");
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
												txtEndereco.setText("");
												txtEndereco.addKeyListener(this);
												txtSenha.setText("");
												txtSenha.addKeyListener(this);
												txtNome.requestFocus();
												txtId.setText(Integer.toString(manipula.ultimoId(Funcionario.getTipo())));
												txtId.addKeyListener(this);
											}
											catch(IOException io){
												System.out.print(io.toString());
												texto = erro.criarAdicionarErro(io, "funcionário");
												janela.showMessageDialog(null, texto);
												txtNome.setText("");
												txtRg.setText("");
												txtRg.addKeyListener(this);
												txtCpf.setText("");
												txtCpf.addKeyListener(this);
												txtEmail.setText("");
												txtTel.setText("");
												txtTel.addKeyListener(this);
												txtEndereco.setText("");
												txtEndereco.addKeyListener(this);
												txtSenha.setText("");
												txtSenha.addKeyListener(this);
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

	public Container criarAddFuncionario(){
		GridLayout grid = new GridLayout(11,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel funcionario = new JLabel("Incluir Funcionário");

		txtNome.setText("");
		txtRg.setText("");
		txtRg.addKeyListener(this);
		txtCpf.setText("");
		txtCpf.addKeyListener(this);
		txtEmail.setText("");
		txtEmail.addKeyListener(this);
		txtTel.setText("");
		txtTel.addKeyListener(this);
		txtEndereco.setText("");
		txtEndereco.addKeyListener(this);
		txtSenha.setText("");
		txtSenha.addKeyListener(this);
		txtConfirmSenha.setText("");
		txtConfirmSenha.addKeyListener(this);
		try {
			txtId.setText(Integer.toString(manipula.ultimoId(Funcionario.getTipo())));
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
		txtEndereco.setEditable(true);
		txtSenha.setEditable(true);
		txtConfirmSenha.setEditable(true);
		txtId.setEditable(false);

		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				incluirFuncionario();
			}
		});

		painel.add(funcionario);
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
		painel.add(endereco);
		painel.add(txtEndereco);
		painel.add(senha);
		painel.add(txtSenha);
		painel.add(confirmSenha);
		painel.add(txtConfirmSenha);
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
