package processamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import estrutura.Cliente;
import estrutura.Produto;
import estrutura.Venda;
import estrutura.ItemVenda;
import exception.ExceptionQtdProduto;

import java.util.ArrayList;
import java.util.Iterator;

public class ManipulaVenda {

	void incluirVenda(Venda venda) throws IOException, ClassNotFoundException{

		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/vendas.bin", true);
		ObjectOutputStream out = new ObjectOutputStream(fout);

		out.writeObject(venda);
		System.out.println(venda.toString()+ "venda");

		out.flush();
		out.close();

		ManipulaCliente manipulaCliente = new ManipulaCliente();
		Cliente c = manipulaCliente.buscarCliente(venda.getIdCliente());
		c.incrementaPontos();
		manipulaCliente.atualizarCliente(c);

		ManipulaProduto manipulaProduto = new ManipulaProduto();
		ArrayList vendas;
		ItemVenda it;
		for(int i = 0; i < venda.getItem().size(); i++){
			vendas = venda.getItem();
			it = (ItemVenda)vendas.get(i); 
			Produto p = manipulaProduto.buscarProduto(it.getProduto().getId());
			try{
				if (p.getQtd()-it.getQtd() < 0)
					throw new ExceptionQtdProduto();
				p.setQtd(p.getQtd()-it.getQtd());
			}
			catch(ExceptionQtdProduto qtdProduto){
				System.out.println(qtdProduto.getMessage());
			}
			manipulaProduto.atualizarProduto(p);
		}
	}
	public void incluirVenda(ArrayList vendas) throws IOException{
		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/vendas.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);

		for(int i = 0; i < vendas.size(); i++){
			out.writeObject(vendas.get(i));
			System.out.println(vendas.get(i).toString());
		}
		out.flush();
		out.close();	
	}

	public ArrayList lerTodasVendas() throws IOException, ClassNotFoundException{
		try
		{
			ObjectInputStream entrada = null;
			FileInputStream fi = null;
			fi = new FileInputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/vendas.bin");
			entrada = new ObjectInputStream(fi);
			ArrayList vendas = new ArrayList();
			Venda c;
			try
			{
				while(true)
				{		
					c = (Venda) entrada.readObject();
					vendas.add(c);
				}
			}
			catch(OptionalDataException optinal)
			{
				System.out.println(optinal.toString()+ "disparou"); 
			}
			finally
			{
				entrada.close();
				return vendas;
			}
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			return null;
		}
	}
	boolean excluirVenda(int id) throws IOException, ClassNotFoundException
	{
		ArrayList vendas = this.lerTodasVendas();
		Venda c = null;
		boolean achou = false;
		Iterator it = vendas.iterator();
		while(it.hasNext()&& !achou)
			c = (Venda) it.next();
		if (c.getId() == id)
		{
			achou = true;
			vendas.remove(c);
			this.incluirVenda(vendas);
		}
		return achou;
	}

	public Venda buscarVenda(int id)throws IOException, ClassNotFoundException{
		ArrayList vendas = this.lerTodasVendas();
		Venda c = null;
		boolean achou = false;
		Iterator it = vendas.iterator();
		while(it.hasNext()&& !achou)
		{
			c = (Venda) it.next();
			if (c.getId() == id)
			{
				achou = true;
			}
		}
		return c; 
	}
}