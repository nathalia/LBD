package aplicacao;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import processamento.ManipulaProduto;
import estrutura.ItemVenda;
import estrutura.Produto;

import java.sql.Date;  
import java.text.SimpleDateFormat; 

public class AppVenda implements ActionListener {
	JLabel data = new JLabel("Data:");
	JLabel dia = new JLabel(); //pegar data atual
	JLabel cliente = new JLabel("Cliente");
	JLabel funcionario = new JLabel("Funcionário");
	JLabel item = new JLabel("Item");
	JLabel venda = new JLabel("Venda");
	JLabel espaco = new JLabel();
	JLabel qtd = new JLabel("Qtd");
	JLabel texto = new JLabel();
	
	JTextField txtQtd = new JTextField();
	
	Date data2 = new Date(System.currentTimeMillis());    
	SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");   
	
	JComboBox comboCliente = new JComboBox();
	JComboBox comboFuncionario = new JComboBox();
	JComboBox comboProduto = new JComboBox();
	
	JTextArea itens = new JTextArea();
	
	JButton ok = new JButton("Ok");
	JButton add = new JButton("add");
	
	JOptionPane janela = new JOptionPane();
	
	ArrayList<ItemVenda> listaItem = new ArrayList<ItemVenda>();
	ArrayList<Produto> produtos = new ArrayList<Produto>();
	
	int opcao = 0;
	
	ManipulaProduto manipula = new ManipulaProduto();
	AppErro erro = new AppErro();
	AppSucesso sucesso = new AppSucesso();
	
	public void actionPerformed(ActionEvent e) {
    }

	public void adicionarProduto(){
		String nome = (String)comboProduto.getSelectedItem();
		try{
			Produto p = manipula.buscarProduto(nome);
			ItemVenda itemVenda = new ItemVenda(Integer.parseInt(qtd.getText()), p);
		}
		catch(ClassNotFoundException classNot){
			texto = erro.criarAdicionarErro(classNot, "produto");
			janela.showMessageDialog(null, texto);
		}
		catch(IOException io){
			texto = erro.criarAdicionarErro(io, "produto");
			janela.showMessageDialog(null, texto);
		}
	}
	
	public Container criarPainelVenda(){
		GridLayout grid = new GridLayout(8,2);
    	grid.setVgap(4);
    	grid.setHgap(4);
    	JPanel painel = new JPanel(grid);
    	
    	itens.setEditable(false);
    	dia.setText(data2+"");
    	
    	//try{
    		//teste
    		Produto t = new Produto(1, 1, "algodão", "branco", "x", 5, 4.5);
    		//produtos = manipula.lerTodosProdutos();
    		produtos.add(t);
    		if (produtos != null){
    			Produto p;
	    		for (int i = 0; i < produtos.size(); i++){
	    			p = (Produto) produtos.get(i);
	    			comboProduto.addItem(p.getId()+" "+p.getNome());
	    		}
    		}
    		else{
    			comboProduto.addItem("Não há produtos cadastrados");
    			comboProduto.setEnabled(false);
    			add.setEnabled(false);
    		}
    	//}
    	/*catch(IOException io){
    		erro.criarAlterarErro(io, "venda");
    		janela.showMessageDialog(null, erro);
    	}
    	catch(ClassNotFoundException classNot){
    		erro.criarAdicionarErro(classNot, "venda");
    		janela.showMessageDialog(null, erro);
    	}
    	*/
    	add.addActionListener(new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	        adicionarProduto();
    	    }
    	});
    	
		painel.add(venda);
		//painel.add(espaco);
		painel.add(data);
		painel.add(dia);
		//painel.add(funcionario);
		//painel.add(comboFuncionario);
		painel.add(item);
		painel.add(comboProduto);
		painel.add(add);
		painel.add(itens);
		
		return painel;
		
	}
}
