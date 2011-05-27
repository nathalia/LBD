package processamento;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;

import estrutura.Cliente;
import estrutura.Funcionario;
import java.util.ArrayList;
import java.util.Iterator;

public class ManipulaFuncionario
{

	public void incluirFuncionario(Funcionario funcionario) throws IOException
	{

		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/funcionarios.bin", true);
		ObjectOutputStream out = new ObjectOutputStream(fout);

		out.writeObject(funcionario);
		System.out.println(funcionario.toString());
		out.flush();
		out.close();
	}

	public void incluirFuncionario(ArrayList funcionarios) throws IOException
	{
		FileOutputStream fout = new FileOutputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/funcionarios.bin");
		ObjectOutputStream out = new ObjectOutputStream(fout);

		for (int i = 0; i < funcionarios.size(); i++)
		{
			out.writeObject(funcionarios.get(i));
			System.out.println(funcionarios.get(i).toString());
		}
		out.flush();
		out.close();
	}

	public ArrayList lerTodosFuncionarios() throws IOException, ClassNotFoundException
	{
		try
		{
			ObjectInputStream entrada = null;
			FileInputStream fi = null;
			fi = new FileInputStream("C:/Users/_Lú_/Desktop/VersaoConsole/EP/arquivos/funcionarios.bin");
			entrada = new ObjectInputStream(fi);
			ArrayList funcionarios = new ArrayList();
			Funcionario c;
			try
			{
				while (true)
				{
					c = (Funcionario)entrada.readObject();
					funcionarios.add(c);
				}
			}
			catch (OptionalDataException optinal)
			{
				System.out.println(optinal.toString() + "disparou");
			}
			finally
			{
				entrada.close();
				return funcionarios;
			}
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			return null;
		}
	}

	public boolean excluirFuncionario(int id) throws IOException, ClassNotFoundException
	{
		ArrayList funcionarios = this.lerTodosFuncionarios();
		Funcionario c = null;
		boolean achou = false;
		int i = 0;
		while (i < funcionarios.size() && !achou)
		{
			c = (Funcionario)funcionarios.get(i);
			if (c.getId() == id)
			{
				achou = true;
				funcionarios.remove(i);
				this.incluirFuncionario(funcionarios);
			}
			i++;
		}
		return achou;
	}

	public Funcionario buscarFuncionario(int id)throws IOException, ClassNotFoundException, NullPointerException
	{
		ArrayList funcionarios = this.lerTodosFuncionarios();
		Funcionario c = null;
		boolean achou = false;
		Iterator it = funcionarios.iterator();
		while (it.hasNext() && !achou)
		{
			c = (Funcionario)it.next();
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

	public boolean atualizarFuncionario(Funcionario funcionario)
	{
		try
		{
			this.buscarFuncionario(funcionario.getId());
			this.excluirFuncionario(funcionario.getId());
			this.incluirFuncionario(funcionario);
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
