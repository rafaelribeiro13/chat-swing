package chat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Essa classe é responsável por abrir a conexão com
 * o banco de dados.
 * 
 * Para que a conexão se torne possível é necessário que se
 * tenha o banco de dados na máquina. Pra isso basta executar
 * os comandos contidos no arquivo <script_bd> no MySQL Workbench.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class ConnectionFactory {
	// jdbc:mysql://localhost:3306/<nomeBanco>?useSSL=false
	private static final String URL = "jdbc:mysql://localhost:3306/<nomeBanco>?useSSL=false"; 
	private static final String USUARIO = "root"; // inserir o usuário do SGDB, exemplo: root
	private static final String SENHA = ""; // inserir a senha do SGBD
	
	
	/**
	 * Retorna a conexão com o banco de dados.
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		Connection con = null;
		
		try {
			con = DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (SQLException e) {
			throw new SQLException("Erro ao conectar com abase de dados" + e.getMessage());
		}
		
		return con;
	}
	
	
	/**
	 * Fecha a conexão com o banco de dados.
	 * 
	 * @param con
	 */
	public static void fecharConexao(Connection con) {

        try {
            if (con != null) {
                con.close();
                System.out.println("Fechada a conexão com o banco de dados");
            }

        } catch (Exception e) {
            System.out.println("Não foi possível fechar a conexão com o banco de dados " + e.getMessage());
        }
    }
	
	
	/**
	 * Fecha todo recurso de manipulação do banco de dados.
	 * 
	 * @param con
	 * @param pst
	 */
	public static void fecharConexao(Connection con, PreparedStatement pst) {

        try {
            if (con != null) {
                fecharConexao(con);
            }
            
            if (pst != null) {
                pst.close();
                System.out.println("Statement fechado com sucesso");
            }

        } catch (Exception e) {
            System.out.println("Não foi possível fechar o statement " + e.getMessage());
        }
    }
	
	
	/**
	 * Fecha todo recurso de manipulação do banco de dados.
	 * 
	 * @param con
	 * @param pst
	 * @param rs
	 */
	public static void fecharConexao(Connection con, PreparedStatement pst, ResultSet rs) {

        try {
            if (con != null || pst != null) {
                fecharConexao(con, pst);
            }
            if (rs != null) {
                rs.close();
                System.out.println("ResultSet fechado com sucesso");
            }

        } catch (Exception e) {
            System.out.println("Não foi possível fechar o ResultSet " + e.getMessage());
        }
    }
	
}