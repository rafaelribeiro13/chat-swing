package chat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import chat.dao.ClienteDAO;

/**
 * Essa classe é responsável pelo controle de filtragem
 * de mensagens indesejáveis.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante
 */
public class FiltroMensagens {

	private static Set<String> palavrasFiltroBD = ClienteDAO.palavrasFiltradas;
	
	/**
	 * Quebra toda frase enviada em palavras.
	 * 
	 * @param frase
	 * @return
	 */
	private static String[] separadorDePalavras(String frase){
        String[] palavras = frase.split(" ");
        return palavras;
    }
    
   
	/**
	 * Compara cada palavra contida na tabela Filtro com as 
	 * palavras enviadas por cada usuário.
	 * 
	 * @param palavras
	 * @return
	 */
	private static List<String> comparadorDePalavras(String[] palavras){
        List<String> palavrasFiltradas = new ArrayList<>();
        
        for(int i = 0; i<palavras.length; i++) {
            if (palavrasFiltroBD.contains(palavras[i].toLowerCase())){
                palavrasFiltradas.add("******");
            } else {
            	palavrasFiltradas.add(palavras[i]);
            }
        }    
        
        return palavrasFiltradas;
    }
    
   
	/**
	 * Remonta toda a frase já com os filtros aplicados.
	 * 
	 * @param palavrasfiltradas
	 * @return
	 */
	private static String montaFrase(List<String> palavrasfiltradas){
        String frasefiltrada = "";
        
         for (String palavra : palavrasfiltradas) {
            frasefiltrada += (palavra + " ");
         } 
        
         return frasefiltrada;
    }
    
    
	/**
	 * Retorna a frase filtrada.
	 * 
	 * @param mensagem
	 * @return
	 */
	private static String fraseFiltrada(String mensagem){
        String[] palavras = separadorDePalavras(mensagem);
        List<String> palavrasfiltro = comparadorDePalavras(palavras);
        return montaFrase(palavrasfiltro);
    }
	
	
	/**
	 * Recebe uma mensagem e retorna a mesma já filtrada.
	 * 
	 * @param mensagem
	 * @return
	 */
	public static String filtrar(String mensagem) {
		return fraseFiltrada(mensagem);
	}
	
	
}