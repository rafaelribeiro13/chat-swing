package chat.model;

import chat.dao.ClienteDAO;
import chat.dao.ExceptionDAO;

/**
 * Essa classe é responsável por representar um cliente da
 * aplicação mantendo os dados requeridos e fazendo a comunicação
 * direta com a camada do banco de dados. 
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante
 */
public class Cliente {

	private String nome;
	private String senha;
	
	public Cliente(String nome, String senha) throws IllegalArgumentException {
		this.setNome(nome);
		this.setSenha(senha);
	}

	
	/**
	 * Solicita ao DAO o cadastro de cliente/usuário.
	 * 
	 * @param cliente
	 * @throws ExceptionDAO
	 */
	public void cadastrar(Cliente cliente) throws ExceptionDAO {
		new ClienteDAO().inserir(cliente);
	}
	
	
	/**
	 * Solicita ao DAO a consulta de cliente/usuário.
	 * 
	 * @param nome
	 * @param senha
	 * @return
	 * @throws ExceptionDAO
	 */
	public static Cliente consultar(String nome, String senha) throws ExceptionDAO {
		return new ClienteDAO().consultar(nome, senha);
	}
	
	
	/**
	 * Solicita ao DAO a alteração de cliente/usuário.
	 * 
	 * @param cliente
	 * @param novaSenha
	 * @throws ExceptionDAO
	 */
	public void alterar(Cliente cliente, String novaSenha) throws ExceptionDAO {
		new ClienteDAO().alterar(cliente, novaSenha);
	}
	
	
	/**
	 * Solicita ao DAO a exclusão de cliente/usuário.
	 * 
	 * @param nome
	 * @param senha
	 * @throws ExceptionDAO
	 */
	public void excluir(String nome, String senha) throws ExceptionDAO {
		new ClienteDAO().excluir(nome, senha);
	}
	

	public String getNome() {
		return nome;
	}

	
	public void setNome(String nome) {
		if (nome == null || nome.isEmpty())
			throw new IllegalArgumentException("Informe seu usuário!");
		
		if (nome.length() < 3 || !nome.matches("[a-z A-Z]+"))
			throw new IllegalArgumentException("Informe seu usuário somente com letras e no mínimo 3 caracteres");
		
		this.nome = nome;
	}
	
	
	public String getSenha() {
		return senha;
	}

	
	public void setSenha(String senha) {
		if (senha == null || senha.isEmpty())
			throw new IllegalArgumentException("Informe uma senha");
		
		if (senha.length() < 3)
			throw new IllegalArgumentException("Informe uma senha válida, mínimo 3 caracteres!");
		
		this.senha = senha;
	}
	
	
	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", senha=" + senha + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		return true;
	}
	
	
}