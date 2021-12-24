package chat.model;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Essa classe é responsável por intermediar a comunicação
 * entre cliente/servidor.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante
 */
public class GerenciadorCliente implements Runnable {

	private Socket socketCliente;
	private Scanner leitorDoCliente;
	private PrintStream escritorParaCliente;
	private String nomeCliente;
	private static Map<String, GerenciadorCliente> clientes = new HashMap<>();
	
	public GerenciadorCliente(Socket socketCliente) {
		this.socketCliente = socketCliente;
		new Thread(this).start();
	}

	
	/**
	 * Thread de comunicação cliente/servidor para cada usuário 
	 * conectado ao chat.
	 */
	@Override
	public void run() {

		try {
			this.leitorDoCliente = new Scanner(socketCliente.getInputStream(), StandardCharsets.UTF_8);
			this.escritorParaCliente = new PrintStream(socketCliente.getOutputStream(), true, StandardCharsets.UTF_8);
			
			efetuarLogin();
			
			while (leitorDoCliente.hasNextLine()) {
				String mensagemCliente = leitorDoCliente.nextLine().trim();
				
				if (mensagemCliente.equals(Comando.SAIR)) {
					break;
				} else if (mensagemCliente.equals(Comando.LISTAR_USUARIOS)) {
					listarUsuarios(this);
				} else if (mensagemCliente.startsWith(Comando.MENSAGEM)) {
					enviarParaOutroCliente(mensagemCliente);
				} else {
					enviarATodos(this, mensagemCliente);
				}
			}
		} catch (IOException e) {
			System.out.println("Não foi possível ler a mensagem do cliente: " + e.getMessage());
			e.printStackTrace();
		} finally {
			fechar();
		}
		
	}
	
	
	/**
	 * Inicia a troca de mensagem inicial entre cliente/servidor
	 * para cada usuário ativo no chat.
	 */
	private void efetuarLogin() {
		escritorParaCliente.println(Comando.LOGIN);
		this.nomeCliente = leitorDoCliente.nextLine().trim();
		escritorParaCliente.println(Comando.LOGIN_ACEITO);
		escritorParaCliente.println("Olá " + this.nomeCliente);
		clientes.put(this.nomeCliente, this);
		System.out.printf("Cliente %s/%s conectou\n", socketCliente.getRemoteSocketAddress(), this.nomeCliente);
		
		atualizarUsuarios();
	}
	
	
	/**
	 * Lista todos os usuários ativos para cada usuário conectado 
	 * ao chat.
	 * 
	 * @param gc
	 */
	private void listarUsuarios(GerenciadorCliente gc) {
		StringBuffer str = new StringBuffer();
		for (String cli : clientes.keySet()) {
			if (gc.getNomeCliente().equals(cli)) 
				continue;
			
			str.append(cli);
			str.append(",");
		}
		
		if (str.length() > 0)
			str.deleteCharAt(str.length()-1); // remove a última vírgula
		
		gc.getEscritorParaCliente().println(Comando.LISTAR_USUARIOS);
		gc.getEscritorParaCliente().println(str.toString());
	}
	
	
	/**
	 * Atualiza a lista de usuários a todos os usuários
	 * ativos no chat.
	 */
	private void atualizarUsuarios() {
		for (String cliente : clientes.keySet()) {
			listarUsuarios(clientes.get(cliente));
		}
	}
	
	
	/**
	 * Envia a mensagem do remetente a todos os usuários 
	 * conectados.
	 * 
	 * @param gcRemetente
	 * @param mensagem
	 */
	private void enviarATodos(GerenciadorCliente gcRemetente, String mensagem) {
		String remetente = gcRemetente.getNomeCliente();
		for (String clienteDestinatario : clientes.keySet()) {
			if (!remetente.equals(clienteDestinatario)) {
				GerenciadorCliente gcDestinatario = clientes.get(clienteDestinatario);
				gcDestinatario.getEscritorParaCliente().println(remetente + " disse: " + mensagem);
			}
		}
	}

	
	/**
	 * Envia a mensagem do remetente a um destinatário específico se 
	 * o mesmo está conectado.
	 * 
	 * @param comandoMSG
	 */
	private void enviarParaOutroCliente(String comandoMSG) {
		String nomeDestinatario = comandoMSG.substring(5, comandoMSG.length());
		GerenciadorCliente gcDestinatario = clientes.get(nomeDestinatario);
		
		if (gcDestinatario != null) {
			String mensagemRemetente = leitorDoCliente.nextLine().trim();
			gcDestinatario.getEscritorParaCliente().println(this.nomeCliente + " disse a você: " + mensagemRemetente);
		} else {
			escritorParaCliente.println(Comando.USUARIO_INDISPONIVEL);
		}
	} 
	
	
	/**
	 * Fecha todo recurso de leitura/escrita quando os mesmos 
	 * já não estão sendo mais utilizados a fim de preservar
	 * a memória do sistema operacional.  
	 */
	private void fechar() {
		try {
			System.out.printf("Cliente %s/%s desconectou\n", socketCliente.getRemoteSocketAddress(), this.nomeCliente);
			clientes.remove(this.nomeCliente, this);
			atualizarUsuarios();
			leitorDoCliente.close();
			escritorParaCliente.close();
			socketCliente.close();
		} catch (IOException e) {
			System.out.println("Não foi possível fechar o socketCliente do servidor: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public String getNomeCliente() {
		return nomeCliente;
	}
	
	
	public PrintStream getEscritorParaCliente() {
		return escritorParaCliente;
	}
	
	
}