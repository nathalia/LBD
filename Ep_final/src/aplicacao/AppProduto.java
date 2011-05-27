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

import estrutura.Cliente;
import estrutura.Fornecedor;
import estrutura.Produto;

import processamento.ManipulaGenerico;
import processamento.ManipulaProduto;
import processamento.ManipulaFornecedor;

public class AppProduto implements ActionListener,  KeyListener{
	ManipulaGenerico<Produto> manipula = new ManipulaGenerico<Produto>();
	ManipulaGenerico<Fornecedor> manipulaFornecedor = new ManipulaGenerico<Fornecedor>();

	AppSucesso sucesso = new AppSucesso();
	AppErro erro = new AppErro();
	AppConfirmacao confirma = new AppConfirmacao();

	JLabel espaco = new JLabel();
	JLabel lblId = new JLabel("Id");
	JLabel fornecedor = new JLabel("Fornecedor");
	JLabel nome = new JLabel("Nome");
	JLabel descricao = new JLabel("Descrição");
	JLabel laboratorio = new JLabel("Laboratório");
	JLabel qtd = new JLabel("Quantidade");
	JLabel valorVenda = new JLabel("Valor");
	JLabel texto = new JLabel();

	JTextField txtId = new JTextField();
	JTextField txtNome = new JTextField();
	JTextField txtDescricao = new JTextField();
	JTextField txtFornecedor = new JTextField();
	JTextField txtLaboratorio = new JTextField();
	JTextField txtQtd = new JTextField();
	JTextField txtValorVenda = new JTextField();

	JComboBox comboFornecedor = new JComboBox();
	JComboBox comboNome = new JComboBox();

	JButton ok = new JButton("Ok");	
	JOptionPane janela = new JOptionPane();

	int opcao = 0;

	public Container criarPainelProduto(){
		GridLayout grid = new GridLayout(2,1);
		grid.setVgap(4);
		JPanel painel = new JPanel(grid);

		JLabel produto = new JLabel("Produto");

		painel.add(produto);

		return painel;
	}

	public void adicionarProdutosCombo(){
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		try{
			produtos = manipula.lerTodosObjetos(Produto.getTipo());
			if(produtos!= null){
				comboNome.addItem("");
				for(int i=0; i< produtos.size(); i++){
					Produto pro = produtos.get(i);
					comboNome.addItem(pro.getId()+" "+pro.getNome());
				}
			}
			else
				comboNome.addItem("Não há produtos para excluir!");
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
	}

	public void actionPerformed(ActionEvent e) {
	}

	public void excluirProduto(){

		txtFornecedor.setText("");
		txtDescricao.setText("");
		txtLaboratorio.setText("");
		txtQtd.setText("");
		txtValorVenda.setText("");
		txtFornecedor.setEditable(false);
		txtDescricao.setEditable(false);
		txtLaboratorio.setEditable(false);
		txtQtd.setEditable(false);
		txtValorVenda.setEditable(false);

		String text = (String)comboNome.getSelectedItem();
		String id= "";
		int i = 0;
		try{
			while(text.charAt(i)!= ' '){
				id = text.charAt(i)+"";
				i++;
			}
			try{
				Produto p = manipula.buscarObjeto(Integer.parseInt(id), Produto.getTipo());
				txtNome.setText(p.getNome());
				txtFornecedor.setText(p.getIdFornecedor()+"");
				txtDescricao.setText(p.getDescricao());
				txtLaboratorio.setText(p.getLaboratorio());
				txtQtd.setText(p.getQtd()+"");
				txtValorVenda.setText(p.getValorVenda()+"");
			}
			catch(ClassNotFoundException classNotFound){
				texto = erro.criarExcluirErro(classNotFound, Produto.getTipo());
				janela.showMessageDialog(null, texto);
			}
			catch(FileNotFoundException fileNotFound){
				texto = erro.criarExcluirErro(fileNotFound, Produto.getTipo());
				janela.showMessageDialog(null, texto);
			}
			catch(IOException io){
				texto = erro.criarExcluirErro(io, Produto.getTipo());
				janela.showMessageDialog(null, texto);
			}
			catch(NumberFormatException numberFormatException){
			}
		}
		catch(StringIndexOutOfBoundsException stringOut){
			txtNome.setText("");
			txtFornecedor.setText("");
			txtDescricao.setText("");
			txtLaboratorio.setText("");
			txtQtd.setText("");
			txtValorVenda.setText("");
		}
	}

	public void excluirOk(){

		String text = (String)comboNome.getSelectedItem();
		ArrayList<Produto> produtos = new ArrayList<Produto>();;
		try{
			produtos = manipula.lerTodosObjetos(Produto.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um produto para excluir!");
		else{
			texto = confirma.confirmaExclusao(Produto.getTipo(), text);
			opcao = janela.showConfirmDialog(null, texto, "Alterar Produto", JOptionPane.YES_NO_OPTION);
			if(opcao == 0){
				if(produtos!= null){
					comboNome.addItem("");
					for(int i=0; i< produtos.size(); i++){
						Produto func = produtos.get(i);
						comboNome.addItem(func.getId()+" "+func.getNome());
					}
				}
				//fazer a exclusão
				texto = sucesso.criarExcluirSucesso(Produto.getTipo());
				janela.showMessageDialog(null, texto);
				comboNome = new JComboBox();
				this.adicionarProdutosCombo();

				comboNome.addActionListener (new ActionListener () {
					public void actionPerformed(ActionEvent e) {
						excluirProduto();
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

					produtos.remove(manipula.posicaoArray(Integer.parseInt(idE), produtos));
					manipula.incluirObjeto(produtos, Cliente.getTipo());
				}
				catch(Exception e){

				}
				//comboNome.setSelectedIndex(0);
				txtFornecedor.setText("");
				txtDescricao.setText("");
				txtLaboratorio.setText("");
				txtQtd.setText("");
				txtValorVenda.setText("");
			}
		}
	}


	public Container criarExcProduto(){
		GridLayout grid = new GridLayout(8,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);

		JLabel produto = new JLabel("Excluir Produto");

		txtFornecedor.setText("");
		txtDescricao.setText("");
		txtLaboratorio.setText("");
		txtQtd.setText("");
		txtValorVenda.setText("");

		JTextField txtId = new JTextField();

		comboNome = new JComboBox(); 
		try{
			ArrayList<Produto> produtos = manipula.lerTodosObjetos(Produto.getTipo());
			if(produtos!= null){
				comboNome.addItem("");
				for(int i=0; i< produtos.size(); i++){
					Produto func = produtos.get(i);
					comboNome.addItem(func.getId()+" "+func.getNome());
				}
				comboNome.addActionListener (new ActionListener () {
					public void actionPerformed(ActionEvent e) {
						excluirProduto();
					}
				});
			}
			else
				this.comboNome.addItem("Não há produtos para excluir!");
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				excluirOk();
			}
		});
		txtNome.setEditable(false);
		txtFornecedor.setEditable(false);
		txtDescricao.setEditable(false);
		txtLaboratorio.setEditable(false);
		txtQtd.setEditable(false);
		txtValorVenda.setEditable(false);

		painel.add(produto);
		painel.add(espaco);
		painel.add(fornecedor);
		painel.add(txtFornecedor);
		painel.add(nome);
		painel.add(comboNome);
		painel.add(descricao);
		painel.add(txtDescricao);
		painel.add(laboratorio);
		painel.add(txtLaboratorio);
		painel.add(qtd);
		painel.add(txtQtd);
		painel.add(valorVenda);
		painel.add(txtValorVenda);
		painel.add(ok);

		return painel;
	}

	public void alterarOk(){
		String text = (String)comboNome.getSelectedItem();
		ArrayList<Produto> produtos = new ArrayList<Produto>();;
		try{
			produtos = manipula.lerTodosObjetos(Produto.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		if(text.isEmpty())
			janela.showMessageDialog(null, "Escolha um produto para alterar!");
		else{
			txtNome.setEditable(false);
			txtFornecedor.setEditable(false);
			txtDescricao.setEditable(false);
			txtLaboratorio.setEditable(false);
			txtQtd.setEditable(false);
			txtValorVenda.setEditable(false);

			if (txtFornecedor.getText().isEmpty()){
				janela.showMessageDialog(null, "Preencha o campo Fornecedor!");
				txtFornecedor.requestFocus();
			}
			else
				if (txtDescricao.getText().isEmpty()){
					janela.showMessageDialog(null, "Preencha o campo Descrição!");
					txtDescricao.requestFocus();
				}
				else
					if (txtLaboratorio.getText().isEmpty()){
						janela.showMessageDialog(null, "Preencha o campo Laboratório!");
						txtLaboratorio.requestFocus();
					}
					else
						if (txtQtd.getText().isEmpty()){
							janela.showMessageDialog(null, "Preencha o campo Quantidade!");
							txtQtd.requestFocus();
						}
						else
							if (txtValorVenda.getText().isEmpty()){
								janela.showMessageDialog(null, "Preencha o campo Valor Venda!");
								txtValorVenda.requestFocus();
							}
							else{    		

								texto = confirma.confirmaAlteracao(Produto.getTipo(), text);
								opcao = janela.showConfirmDialog(null, texto, "Alterar Produto", JOptionPane.YES_NO_OPTION);
								if (opcao == 0){

									//altera produto
									texto = sucesso.criarAlterarSucesso(Produto.getTipo());
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
										Produto produtoN = new Produto(manipula.buscarObjeto(Integer.parseInt(idE), Produto.getTipo()).getId(), manipula.buscarObjeto(Integer.parseInt(idE),
												Fornecedor.getTipo()).getId(), manipula.buscarObjeto(Integer.parseInt(idE), Produto.getTipo()).getNome(),txtDescricao.getText(),
												txtLaboratorio.getText(), Integer.parseInt(txtQtd.getText()),Integer.parseInt( txtValorVenda.getText()));
										produtos.set(manipula.posicaoArray(Integer.parseInt(idE), produtos), produtoN);
										manipula.incluirObjeto(produtos, Produto.getTipo());
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


	public Container criarAltProduto(){
		GridLayout grid = new GridLayout(10,2);
		grid.setVgap(3);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel produto = new JLabel("Alterar Produto");

		txtDescricao.setText("");
		txtLaboratorio.setText("");
		txtQtd.setText("");
		txtQtd.addKeyListener(this);
		txtValorVenda.setText("");

		JTextField txtId = new JTextField();
		txtId.setSize(2, 2);
		comboNome = new JComboBox(); 

		try{
			ArrayList<Produto> produtos = manipula.lerTodosObjetos(Produto.getTipo());
			if(produtos!= null){
				comboNome.addItem("");
				for(int i=0; i< produtos.size(); i++){
					Produto func = produtos.get(i);
					comboNome.addItem(func.getId()+" "+func.getNome());
				}
			}
			else
				this.comboNome.addItem("Não há produtos para alterar!");
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarAlterarErro(classNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarAlterarErro(fileNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarAlterarErro(io, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		comboNome.addActionListener (new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarProduto();
			}
		});
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				alterarOk();
			}
		});

		comboFornecedor.setEditable(false);
		txtDescricao.setEditable(false);
		txtLaboratorio.setEditable(false);
		txtQtd.setEditable(false);
		txtValorVenda.setEditable(false);

		painel.add(produto);
		painel.add(espaco);
		painel.add(nome);
		painel.add(comboNome);
		painel.add(fornecedor);
		painel.add(comboFornecedor);
		painel.add(laboratorio);
		painel.add(txtLaboratorio);
		painel.add(qtd);
		painel.add(txtQtd);
		painel.add(valorVenda);
		painel.add(txtValorVenda);

		painel.add(ok);

		return painel;
	}

	public void alterarProduto(){
		//comboNome.setEnabled(false);
		//carregar fornecedores
		txtDescricao.setText("");
		txtLaboratorio.setText("");
		txtQtd.setText("");
		txtValorVenda.setText("");
		txtDescricao.setEditable(true);
		txtLaboratorio.setEditable(true);
		txtQtd.setEditable(true);
		txtQtd.addKeyListener(this);
		txtValorVenda.setEditable(true);

		String text = (String)comboNome.getSelectedItem();

		if (!text.isEmpty()){
			String id= "";
			int i = 0;
			try{
				while(text.charAt(i)!= ' '){
					id = text.charAt(i)+"";
					i++;
				}
				try{
					Produto p = manipula.buscarObjeto(Integer.parseInt(id),Produto.getTipo());
					txtNome.setText(p.getNome());
					Fornecedor forn = manipulaFornecedor.buscarObjeto(p.getIdFornecedor(),Produto.getTipo());
					comboFornecedor.addItem(forn.getId()+" "+forn.getNome());
					ArrayList<Fornecedor> forns = new ArrayList<Fornecedor>();
					if (forns != null){
						for (i = 0; i < forns.size(); i++){
							Fornecedor forne = forns.get(i);
							comboFornecedor.addItem(forne.getId()+" "+forne.getNome());
						}
					}

					txtDescricao.setText(p.getDescricao());
					txtLaboratorio.setText(p.getLaboratorio());
					txtQtd.setText(p.getQtd()+"");
					txtValorVenda.setText(p.getValorVenda()+"");			
				}
				catch(ClassNotFoundException classNotFound){
					texto = erro.criarAlterarErro(classNotFound, Produto.getTipo());
					janela.showMessageDialog(null, texto);
				}
				catch(FileNotFoundException fileNotFound){
					texto = erro.criarAlterarErro(fileNotFound, Produto.getTipo());
					janela.showMessageDialog(null, texto);
				}
				catch(IOException io){
					texto = erro.criarAlterarErro(io, Produto.getTipo());
					janela.showMessageDialog(null, texto);
				}
				catch(NumberFormatException numberFormatException){
					txtDescricao.setText("");
					txtLaboratorio.setText("");
					txtQtd.setText("");
					txtValorVenda.setText("");
					txtNome.setEditable(false);
					txtDescricao.setEditable(false);
					txtLaboratorio.setEditable(false);
					txtQtd.setEditable(false);
					txtValorVenda.setEditable(false);
				}
			}
			catch(StringIndexOutOfBoundsException stringOut){
				txtDescricao.setText("");
				txtLaboratorio.setText("");
				txtQtd.setText("");
				txtValorVenda.setText("");
				txtNome.setEditable(false);
				txtDescricao.setEditable(false);
				txtLaboratorio.setEditable(false);
				txtQtd.setEditable(false);
				txtValorVenda.setEditable(false);
			}
		}
	}

	public void incluirProduto(){

		ArrayList<Produto> produtos = new ArrayList<Produto>();;
		try{
			produtos = manipula.lerTodosObjetos(Produto.getTipo());
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, Produto.getTipo());
			janela.showMessageDialog(null, texto);
		}

		if (txtNome.getText().isEmpty()){
			janela.showMessageDialog(null, "Preencha o campo Nome!");
			txtNome.requestFocus();
		}
		else
			if ("".equals(comboFornecedor.getSelectedItem())){
				janela.showMessageDialog(null, "Escolha um fornecedor para este produto!");
				txtDescricao.requestFocus();
			}
			else
				if (txtDescricao.getText().isEmpty()){
					janela.showMessageDialog(null, "Preencha o campo Descrição!");
					txtDescricao.requestFocus();
				}
				else
					if (txtLaboratorio.getText().isEmpty()){
						janela.showMessageDialog(null, "Preencha o campo Laboratório!");
						txtLaboratorio.requestFocus();
					}
					else
						if (txtQtd.getText().isEmpty()){
							janela.showMessageDialog(null, "Preencha o campo Quantidade!");
							txtQtd.requestFocus();
						}
						else
							if (txtValorVenda.getText().isEmpty()){
								janela.showMessageDialog(null, "Preencha o campo Valor!");
								txtValorVenda.requestFocus();
							}
							else{    		
								String item = (String)comboFornecedor.getSelectedItem();
								String idFornecedor = "";
								int i = 0;
								try{
									while(item.charAt(i)!= ' '){
										idFornecedor = item.charAt(i)+"";
										i++;
									}

									if(produtos == null)
										produtos = new ArrayList<Produto>();
									Produto produto = new Produto(Integer.parseInt(txtId.getText()), Integer.parseInt(idFornecedor), txtNome.getText(),txtDescricao.getText(), 
											txtLaboratorio.getText(), Integer.parseInt(txtQtd.getText()), Double.parseDouble(txtValorVenda.getText()));
									produtos.add(produto);
									manipula.incluirObjeto(produtos, Produto.getTipo());
									texto = sucesso.criarAdicionarSucesso(Produto.getTipo());
									janela.showMessageDialog(null, texto);


									manipula.incluirObjeto(produtos, Produto.getTipo());
									texto = sucesso.criarAdicionarSucesso(Produto.getTipo());
									janela.showMessageDialog(null, texto);
									//incrementar id
									txtNome.setText("");
									txtDescricao.setText("");
									txtLaboratorio.setText("");
									txtQtd.setText("");
									txtQtd.addKeyListener(this);
									txtValorVenda.setText("");
									txtId.setText(Integer.toString(manipula.ultimoId(Produto.getTipo())));
									txtId.addKeyListener(this);
									//fazer novo método ehNumero para double
								}
								catch(IOException io){
									System.out.print(io.toString());
									texto = erro.criarAdicionarErro(io, "produto");
									janela.showMessageDialog(null, texto);
									txtNome.setText("");
									txtDescricao.setText("");
									txtLaboratorio.setText("");
									txtQtd.setText("");
									txtQtd.addKeyListener(this);
									txtValorVenda.setText("");
									//colocar método ehNumero para double
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

	public Container criarAddProduto(){
		GridLayout grid = new GridLayout(11,2);
		grid.setVgap(4);
		grid.setHgap(4);
		JPanel painel = new JPanel(grid);
		JLabel produto = new JLabel("Incluir Produto");

		try{
			ArrayList<Fornecedor> fornecedores = manipulaFornecedor.lerTodosObjetos(Fornecedor.getTipo());
			if(fornecedores!= null){
				for(int i=0; i< fornecedores.size(); i++){
					Fornecedor forn = fornecedores.get(i);
					comboFornecedor.addItem(forn.getId()+" "+forn.getNome());
				}
			}
			else{
				this.comboFornecedor.addItem("Não há fornecedores cadastrados!");
				this.ok.setEnabled(false);
			}
		}
		catch(ClassNotFoundException classNotFound){
			texto = erro.criarExcluirErro(classNotFound, Fornecedor.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(FileNotFoundException fileNotFound){
			texto = erro.criarExcluirErro(fileNotFound, Fornecedor.getTipo());
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarExcluirErro(io, Fornecedor.getTipo());
			janela.showMessageDialog(null, texto);
		}

		txtNome.setText("");
		txtDescricao.setText("");
		txtLaboratorio.setText("");
		txtQtd.setText("");
		txtQtd.addKeyListener(this);
		txtValorVenda.setText("");
		
		try {
			txtId.setText(Integer.toString(manipula.ultimoId(Produto.getTipo())));
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
		txtDescricao.setEditable(true);
		txtLaboratorio.setEditable(true);
		txtQtd.setEditable(true);
		txtValorVenda.setEditable(true);
		txtId.setEditable(false);

		
		ok.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				incluirProduto();
			}
		});

		painel.add(produto);
		painel.add(espaco);
		painel.add(lblId);
		painel.add(txtId);
		painel.add(nome);
		painel.add(txtNome);
		painel.add(fornecedor);
		painel.add(comboFornecedor);
		painel.add(descricao);
		painel.add(txtDescricao);
		painel.add(laboratorio);
		painel.add(txtLaboratorio);
		painel.add(qtd);
		painel.add(txtQtd);
		painel.add(valorVenda);
		painel.add(txtValorVenda);
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
