package chat.model;

/**
 * Essa classe é responsáel por representar a comunição
 * interna entre cliente/servidor através de comandos.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante
 */
public class Comando {

	public static final String SAIR = "::sair";
	public static final String LOGIN = "::login";
	public static final String LOGIN_ACEITO = "::login-aceito";
	public static final String LOGIN_NEGADO = "::login-negado";
	public static final String LOGIN_EXISTENTE = "::login-existente";
	public static final String LISTAR_USUARIOS = "::listar-usuarios";
	public static final String MENSAGEM = "::msg";
	public static final String USUARIO_INDISPONIVEL = "::usuario-indisponivel";
	
}