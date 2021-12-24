package chat.dao;

/**
 * Classe de Exception customizada a fim de exibir uma mensagem
 * mais clara ao desenvolvedor.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class ExceptionDAO extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ExceptionDAO(String mensagem) {
		super(mensagem);
	}
	
}