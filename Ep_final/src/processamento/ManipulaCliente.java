package processamento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.FileNotFoundException;

import estrutura.Cliente;
import java.util.ArrayList;
import java.util.Iterator;

public class ManipulaCliente
{

	public void incluirCliente(Cliente cliente) throws IOException
	{

		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/clientes.bin", true);
		ObjectOutputStream out = new ObjectOutputStream(fout);

		out.writeObject(cliente);
		System.out.println(cliente.toString());
		out.flush();
		out.close();
	}

	public void incluirCliente(ArrayList clientes) throws IOException, FileNotFoundException
	{
		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/clientes.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);

		for (int i = 0; i < clientes.size(); i++)
		{
			out.writeObject(clientes.get(i));
		}
		out.flush();
		out.close();
	}

	public ArrayList lerTodosClientes() throws IOException, ClassNotFoundException, FileNotFoundException
	{
		try
		{
			ObjectInputStream entrada = null;
			FileInputStream fi = null;
			fi = new FileInputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/clientes.bin");
			entrada = new ObjectInputStream(fi);
			ArrayList clientes = new ArrayList();
			Cliente c;
			try
			{
				while (true)
				{
					c = (Cliente)entrada.readObject();
					clientes.add(c);
				}
			}
			catch (OptionalDataException optinal)
			{
				System.out.println(optinal.toString());
			}
			finally
			{
				entrada.close();
				return clientes;
			}
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			return null;
		}
	}
	public boolean excluirCliente(int id) throws IOException, ClassNotFoundException
	{
		ArrayList clientes = this.lerTodosClientes();
		Cliente c = null;
		boolean achou = false;
		int i = 0;
		while (i<clientes.size() && !achou)
		{
			c = (Cliente)clientes.get(i);
			if (c.getId() == id)
			{
				achou = true;
				clientes.remove(i);
				this.incluirCliente(clientes);
			}
			i++;
		}
		return achou;
	}

	public Cliente buscarCliente(int id)throws IOException, ClassNotFoundException, NullPointerException
	{
		ArrayList clientes = this.lerTodosClientes();
		Cliente c = null;
		boolean achou = false;
		if(clientes!=null){
		Iterator it = clientes.iterator();
		while (it.hasNext() && !achou)
		{
			c = (Cliente)it.next();
			if (c.getId() == id)
			{
				achou = true;
			}
			else
			{
				c = null;
			}
		}
	}
		return c;
	}

	public boolean atualizarCliente(Cliente cliente)
	{
		try
		{
			this.buscarCliente(cliente.getId());
			this.excluirCliente(cliente.getId());
			this.incluirCliente(cliente);
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