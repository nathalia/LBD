package processamento;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;
import java.util.Iterator;

import estrutura.SuperClasse;

public class ManipulaGenerico <T extends SuperClasse>{

	T objeto;
/*
	public void incluirObjeto(T objeto) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("arquivos/"+T.getTipo()+".bin", true));
		out.writeObject(objeto);
		System.out.println(objeto.toString());
		out.flush();
		out.close();
	}
*/
	public void incluirObjeto(ArrayList<T> objeto, String tipo) throws IOException
	{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("arquivos/"+tipo+".bin"));
		for (int i = 0; i < objeto.size(); i++){
			out.writeObject(objeto.get(i));
		}
		out.flush();
		out.close();
	}

	public ArrayList<T> lerTodosObjetos(String tipo) throws IOException, ClassNotFoundException
	{
		try{
			ObjectInputStream entrada = null;
			FileInputStream fi = null;
			fi = new FileInputStream("arquivos/"+tipo+".bin");
			entrada = new ObjectInputStream(fi);
			ArrayList<T> objeto = new ArrayList<T>();
			T c;
			try{
				while (true){
					c = (T)entrada.readObject();
					objeto.add(c);
				}
			}
			catch (OptionalDataException optinal){
				System.out.println(optinal.toString() + "disparou");
			}
			finally{
				entrada.close();
				return objeto;
			}
		}
		catch (FileNotFoundException fileNotFoundException){			
			return null;
		}
	}

	public boolean excluirObjeto(int id, String tipo) throws IOException, ClassNotFoundException
	{ 
		T objeto = this.buscarObjeto(id, tipo);
		ArrayList<T> objetos = this.lerTodosObjetos(tipo);
		if (objeto!=null){
			objetos.remove(objeto);
			this.incluirObjeto(objetos, tipo);
			return true;
		}
		return false;
		/*
		ArrayList<T> objetos = this.lerTodosObjetos();
		T c = null;
		boolean achou = false;
		Iterator<T> it = objetos.iterator();

		while (it.hasNext() && !achou)
			c = (T) it.next();
		if (c.getId() == id){
			achou = true;
			objetos.remove(c);
			this.incluirObjeto(objetos);
		}	
		return achou;
		 */
	}

	public T buscarObjeto(int id, String tipo)throws IOException, ClassNotFoundException, NullPointerException
	{
		ArrayList<T> objeto = this.lerTodosObjetos(tipo);
		T c = null;
		boolean achou = false;
		Iterator it = objeto.iterator();
		while (it.hasNext() && !achou){
			c = (T)it.next();
			if (c.getId() == id)
				achou = true;
			else
				c = null;
		}
		return c;
	}
	
	public int ultimoId(String tipo)throws IOException, ClassNotFoundException, NullPointerException
	{
		ArrayList<T> objeto = this.lerTodosObjetos(tipo);
		T c = null;
		int id = 0;
		if(objeto!=null){
		Iterator it = objeto.iterator();
		while (it.hasNext()){
			c = (T)it.next();
			if((c!=null)&&(c.getId()>id))
				id = c.getId();
		}
		}
		return id+1;
	}

	public T buscarObjeto(String nome, String tipo)throws IOException, ClassNotFoundException
	{
		ArrayList<T> objeto = this.lerTodosObjetos(tipo);
		T c = null;
		boolean achou = false;
		Iterator it = objeto.iterator();
		while (it.hasNext() && !achou){
			c = (T)it.next();
			if (((SuperClasse)c).getNome().equals(nome))
				achou = true;
			else
				c = null;
		}
		return c;
	}

	public boolean atualizarObjeto(T objeto, String tipo)
	{
		ArrayList<T> objetos = null;
		try {
			objetos = this.lerTodosObjetos(tipo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		objetos.set(posicaoArray(objeto.getId(), objetos), objeto);
		if (objeto!=null){
			try {
				this.incluirObjeto(objetos, tipo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	public int posicaoArray(int id, ArrayList<T> objetos)
	{
		T objeto;
		for (int i = 0; i < objetos.size(); i++)
		{
			objeto = (T)objetos.get(i) ;
			if (id == objeto.getId())
				return i;
		}
		return 0;
	}
}
