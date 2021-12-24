package chat.controller;

import chat.dao.ExceptionDAO;
import chat.model.Cliente;

/**
 * Essa classe é responsável por receber os dados vindo dos
 * usuários através das interfaces gráficas e repassar para
 * a camada de domínio da aplicação.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class ClienteController {

	/**
	 * Solicita o cadastro de clientes/usuários a camada de domínio e 
	 * retorna um booleano indicando se obteve êxito.
	 * 
	 * @param nome
	 * @param senha
	 * @return
	 */
	public boolean cadastrarCliente(String nome, String senha) {
		
		if (nome != null && nome.length() > 0 && senha != null && senha.length() > 0) {
			boolean existe = consultarCliente(nome, senha);
			
			if (!existe) {
				try {
					Cliente cliente = new Cliente(nome, senha);
					cliente.cadastrar(cliente);
					return true;
				} catch (ExceptionDAO e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e2) {
					throw new IllegalArgumentException(e2.getMessage());
				}
			}
		}
		
		return false;
	}
	
	
	/**
	 * Solicita a consulta de clientes/usuários a camada de domínio e 
	 * retorna um booleano indicando se obteve êxito.
	 * 
	 * @param nome
	 * @param senha
	 * @return
	 */
	public boolean consultarCliente(String nome, String senha) {
		
		if (nome != null && nome.length() > 0 && senha != null && senha.length() > 0) {
			Cliente cliente = null;
			
			try {
				cliente = Cliente.consultar(nome, senha);
				if (cliente != null)
					return true; // cliente existe
			} catch (ExceptionDAO e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	
	/**
	 * Solicita a alteração no cadastro de clientes/usuários a camada de domínio
	 * e retorna um booleano indicando se obteve êxito.
	 * 
	 * @param nome
	 * @param senhaAtual
	 * @param novaSenha
	 * @return
	 */
	public boolean alterarCliente(String nome, String senhaAtual, String novaSenha) {

		if (nome != null && nome.length() > 0 && senhaAtual != null && senhaAtual.length() > 0 && novaSenha != null && novaSenha.length() > 0) {
			Cliente cliente = null;
			try {
				cliente = Cliente.consultar(nome, senhaAtual);
				
				if (cliente != null) {
					cliente.alterar(cliente, novaSenha);
					return true;
				} else {
					return false;
				}
			} catch (ExceptionDAO e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * Solicita a exclusão de clientes/usuários a camada de domínio e 
	 * retorna um booleano indicando se obteve êxito.
	 * 
	 * @param nome
	 * @param senha
	 * @return
	 */
	public boolean excluirCliente(String nome, String senha) {
		if (nome != null && nome.length() > 0 && senha != null && senha.length() > 0) {
			Cliente cliente = null;
			
			try {
				cliente = Cliente.consultar(nome, senha);
				
				if (cliente != null) {
					cliente.excluir(nome, senha);
					return true;
				}
			} catch (ExceptionDAO e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	
}