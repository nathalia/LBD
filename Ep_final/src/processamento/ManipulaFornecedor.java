package processamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import estrutura.Cliente;
import estrutura.Fornecedor;
import java.util.ArrayList;
import java.util.Iterator;

public class ManipulaFornecedor
{

	public void incluirFornecedor(Fornecedor fornecedor) throws IOException
	{

		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/fornecedores.bin", true);
		ObjectOutputStream out = new ObjectOutputStream(fout);

		out.writeObject(fornecedor);
		System.out.println(fornecedor.toString());
		out.flush();
		out.close();
	}

	public void incluirFornecedor(ArrayList fornecedores) throws IOException
	{
		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/fornecedores.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);

		for (int i = 0; i < fornecedores.size(); i++)
		{
			out.writeObject(fornecedores.get(i));
			System.out.println(fornecedores.get(i).toString());
		}
		out.flush();
		out.close();
	}

	public ArrayList lerTodosFornecedores() throws IOException, ClassNotFoundException
	{
		try
		{
			ObjectInputStream entrada = null;
			FileInputStream fi = null;
			fi = new FileInputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/fornecedores.bin");
			entrada = new ObjectInputStream(fi);
			ArrayList fornecedores = new ArrayList();
			Fornecedor c;
			try
			{
				while (true)
				{
					c = (Fornecedor)entrada.readObject();
					fornecedores.add(c);
				}
			}
			catch (OptionalDataException optinal)
			{
				System.out.println(optinal.toString() + "disparou");
			}
			finally
			{
				entrada.close();
				return fornecedores;
			}
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			return null;
		}
	}

	public boolean excluirFornecedor(int id) throws IOException, ClassNotFoundException
	{
		ArrayList fornecedores = this.lerTodosFornecedores();
		Fornecedor c = null;
		boolean achou = false;
		int i = 0;
		while (i < fornecedores.size() && !achou)
		{
			c = (Fornecedor)fornecedores.get(i);
			if (c.getId() == id)
			{
				achou = true;
				fornecedores.remove(i);
				this.incluirFornecedor(fornecedores);
			}
			i++;
		}
		return achou;
	}

	public Fornecedor buscarFornecedor(int id)throws IOException, ClassNotFoundException, NullPointerException
	{
		ArrayList fornecedores = this.lerTodosFornecedores();
		Fornecedor c = null;
		boolean achou = false;
		Iterator it = fornecedores.iterator();
		while (it.hasNext() && !achou)
		{
			c = (Fornecedor)it.next();
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

	public boolean atualizarFornecedor(Fornecedor fornecedor)
	{
		try
		{
			this.buscarFornecedor(fornecedor.getId());
			this.excluirFornecedor(fornecedor.getId());
			this.incluirFornecedor(fornecedor);
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