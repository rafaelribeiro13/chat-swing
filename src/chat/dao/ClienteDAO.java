package chat.dao;

import static chat.dao.ConnectionFactory.fecharConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import chat.model.Cliente;

/**
 * Essa classe é responsável por manipular todas as operações
 * básicas no banco de dados.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class ClienteDAO implements Persistencia<Cliente>{

	private static final String INSERT = "INSERT INTO Clientes (Nome, Senha) VALUES (?, ?)";
	private static final String FIND = "SELECT * FROM Clientes WHERE Nome=? AND Senha=?";
	private static final String UPDATE = "UPDATE Clientes SET Senha=? WHERE Nome=? AND Senha=?";
	private static final String DELETE = "DELETE FROM Clientes WHERE Nome=? AND Senha=?";
	private static final String FILTER = "SELECT Mensagem FROM Filtro";
	public static Set<String> palavrasFiltradas = new HashSet<>();
	
	/**
	 * Recebe um cliente/usuário a ser inserido no tabela Clientes 
	 * através do comando INSERT.
	 */
	@Override
	public void inserir(Cliente cliente) throws ExceptionDAO {
		if (cliente != null) {
			Connection con = null;
			PreparedStatement pst = null;
			
			try {
				con = ConnectionFactory.getConnection();
				pst = con.prepareStatement(INSERT);
				
				pst.setString(1, cliente.getNome());
				pst.setString(2, cliente.getSenha());
				pst.execute();
			} catch (SQLException e) {
				throw new ExceptionDAO("Erro ao cadastrar o cliente: " + e.getMessage());
			} finally {
				fecharConexao(con, pst);
			}
		} else {
			System.out.println("O cliente enviado por parâmetro está vazio");
		}
	}

	
	/**
	 * Realiza a consulta de clientes/usuários através do 
	 * nome/senha e retorna um Cliente se o mesmo existe 
	 * na tabela Clientes através do comando FIND.
	 */
	@Override
	public Cliente consultar(String nome, String senha) throws ExceptionDAO {
		Connection con = null;
		PreparedStatement pstClientes = null;
		PreparedStatement pstFiltro = null;
		ResultSet rstClientes = null;
		ResultSet rstFiltro = null;
		Cliente cliente = null;
		Set<String> set = new HashSet<>();
		
		try {
			con = ConnectionFactory.getConnection();
			pstClientes = con.prepareStatement(FIND);
			pstFiltro = con.prepareStatement(FILTER);
			
			pstClientes.setString(1, nome);
			pstClientes.setString(2, senha);
			rstClientes = pstClientes.executeQuery();
			
			rstFiltro = pstFiltro.executeQuery();
			
			if (rstClientes != null) {
				while (rstClientes.next()) {
					String nomeCliente = rstClientes.getString("Nome");
					String senhaCliente = rstClientes.getString("Senha");
					
					if (nomeCliente != null && !nomeCliente.isEmpty() && senhaCliente != null && !senhaCliente.isEmpty()) {
						cliente = new Cliente(nomeCliente, senhaCliente);
					}
				}
			}
			
			if (rstFiltro != null) {
				while (rstFiltro.next()) {
					set.add(rstFiltro.getString("Mensagem").toLowerCase());
				}
			}
		} catch (SQLException e) {
			throw new ExceptionDAO("Erro ao consultar o cliente: " + e.getMessage());
		} finally {
			fecharConexao(con, pstClientes, rstClientes);
			try {
				if (pstFiltro != null)
					pstFiltro.close();
			} catch (SQLException e) {
				System.out.println("Não foi possível fechar o Statemente de filtro");
			}
			try {
				if (rstFiltro != null)
					rstFiltro.close();
			} catch (SQLException e) {
				System.out.println("Não foi possível fechar o ResultSet de filtro");
			}
		}
		
		ClienteDAO.palavrasFiltradas = set;
		return cliente;
	}


	/**
	 * Realiza a troca de senha de um cliente/usuário específicado 
	 * se o mesmo existe na tabela Clientes através do comando 
	 * UPDATE.
	 */
	@Override
	public void alterar(Cliente cliente, String novaSenha) throws ExceptionDAO {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = ConnectionFactory.getConnection();
			pst = con.prepareStatement(UPDATE);
			
			pst.setString(1, novaSenha);
			pst.setString(2, cliente.getNome());
			pst.setString(3, cliente.getSenha());
			pst.execute();
		} catch (SQLException e) {
			throw new ExceptionDAO("Erro ao alterar o cliente: " + e);
		} finally {
			fecharConexao(con, pst);
		}
	}
	
	
	/**
	 * Realiza a exclusão de um cliente/usuário a partir do nome/senha
	 * se o mesmo exite na tabela Clientes através do comando DELETE. 
	 */
	@Override
	public void excluir(String nome, String senha) throws ExceptionDAO {
		Connection con = null;
		PreparedStatement pst = null;
		
		try {
			con = ConnectionFactory.getConnection();
			pst = con.prepareStatement(DELETE);
			
			pst.setString(1, nome);
			pst.setString(2, senha);
			pst.execute();
		} catch (SQLException e) {
			throw new ExceptionDAO("Erro ao excluir o cliente:" + e);
		}
	}
	
}