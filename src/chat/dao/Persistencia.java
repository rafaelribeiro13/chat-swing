package chat.dao;

/**
 * Define as classes DAO's operações básicas para a manipulação
 * do banco de dados.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 * @param <T>
 */
public interface Persistencia<T> {

	void inserir(T object) throws ExceptionDAO;
	T consultar(String nome, String senha) throws ExceptionDAO;
	void excluir(String nome, String senha) throws ExceptionDAO;
	void alterar(T object, String novaSenha) throws ExceptionDAO;

}