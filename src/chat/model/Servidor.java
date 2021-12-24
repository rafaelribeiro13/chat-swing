package chat.model;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Essa classe é responsável por abrir um socket de conexão, onde 
 * clientes poderão estabelecer um canal de comunição cliente/servidor.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante
 */
public class Servidor {

	public static final int PORT = 5000;
	private ServerSocket servidor;


	public static void main(String[] args) {
		try {
			new Servidor().iniciar();
		} catch (IOException e) {
			System.out.println("Não foi possível iniciar o servidor");
			e.printStackTrace();
		}
	}


	/**
	 * Abre um socket de conexão em uma porta especificada.
	 * 
	 * @throws IOException
	 */
	private void iniciar() throws IOException {
		servidor = new ServerSocket(PORT);
		System.out.println("Servidor iniciado na porta: " + PORT);
		escutarConexoes(); 
	}

	
	/**
	 * O socket de conexão fica escutando por conexões de 
	 * usuários.
	 * 
	 * @throws IOException
	 */
	private void escutarConexoes() throws IOException {
		while (true) {
			new GerenciadorCliente(servidor.accept());
		}
	}	
	
}