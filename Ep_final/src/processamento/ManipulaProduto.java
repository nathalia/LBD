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
import java.util.ArrayList;
import java.util.Iterator;

public class ManipulaProduto
{

	public void incluirProduto(Produto produto) throws IOException
	{

		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/produtos.bin", true);
		ObjectOutputStream out = new ObjectOutputStream(fout);

		out.writeObject(produto);
		System.out.println(produto.toString());
		out.flush();
		out.close();
	}

	public void incluirProduto(ArrayList produtos) throws IOException
	{
		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/produtos.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);

		for (int i = 0; i < produtos.size(); i++)
		{
			out.writeObject(produtos.get(i));
			System.out.println(produtos.get(i).toString());
		}
		out.flush();
		out.close();
	}

	public ArrayList lerTodosProdutos() throws IOException, ClassNotFoundException
	{
		try
		{
			ObjectInputStream entrada = null;
			FileInputStream fi = null;
			fi = new FileInputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/produtos.bin");
			entrada = new ObjectInputStream(fi);
			ArrayList produtos = new ArrayList();
			Produto c;
			try
			{
				while (true)
				{
					c = (Produto)entrada.readObject();
					produtos.add(c);
				}
			}
			catch (OptionalDataException optinal)
			{
				System.out.println(optinal.toString() + "disparou");
			}
			finally
			{
				entrada.close();
				return produtos;
			}
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			return null;
		}
	}
	public boolean excluirProduto(int id) throws IOException, ClassNotFoundException
	{
		ArrayList produtos = this.lerTodosProdutos();
		Produto c = null;
		boolean achou = false;
		int i = 0;
		while (i < produtos.size() && !achou)
		{
			c = (Produto)produtos.get(i);
			if (c.getId() == id)
			{
				achou = true;
				produtos.remove(i);
				this.incluirProduto(produtos);
			}
			i++;
		}
		return achou;
	}

	public Produto buscarProduto(int id)throws IOException, ClassNotFoundException, NullPointerException
	{
		ArrayList produtos = this.lerTodosProdutos();
		Produto c = null;
		boolean achou = false;
		Iterator it = produtos.iterator();
		while (it.hasNext() && !achou)
		{
			c = (Produto)it.next();
			if (c.getId() == id)
			{
				achou = true;
			}
			else
			{
				c = null;
			}
		}
		return c;
	}

	public Produto buscarProduto(String nome)throws IOException, ClassNotFoundException
	{
		ArrayList produtos = this.lerTodosProdutos();
		Produto c = null;
		boolean achou = false;
		Iterator it = produtos.iterator();
		while (it.hasNext() && !achou)
		{
			c = (Produto)it.next();
			if (c.getNome().equals(nome))
			{
				achou = true;
			}
			else
			{
				c = null;
			}
		}
		return c;
	}

	public boolean atualizarProduto(Produto produto)
	{
		try
		{
			this.buscarProduto(produto.getId());
			this.excluirProduto(produto.getId());
			this.incluirProduto(produto);
		}
		catch (NullPointerException nullPointer)
		{
			System.out.println("NullPointerException");
			return false;
		}
		catch (ClassNotFoundException classNotFound)
		{
			System.out.println("classNotFoundException");
			return false;
		}
		catch (IOException io)
		{
			System.out.println("IOException");
			return false;
		}
		return true;

	}
}