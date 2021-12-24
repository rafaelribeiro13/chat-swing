package chat.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * Tela onde é exibido opções internas para cada cliente/usuário.
 * 	
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class TelaLogin {

	private JFrame frame;
	private JPanel pnLogin;
	private JButton btEntrarChat;
	private Cursor cursor = new Cursor(12);
	private JButton btAlterarSenha;
	private JButton btExcluirCadastro;
	private JButton btSair;
	private JLabel lbTitulo;
	private TelaInicial telaInicial;
	private String nomeCliente;
	

	/**
	 * Create the application.
	 */
	public TelaLogin() {
		initialize();
	}

	/**
	 * @wbp.parser.constructor
	 * @param telaInicial
	 * @param nomeCliente
	 */
	public TelaLogin(TelaInicial telaInicial, String nomeCliente) {
		this.telaInicial = telaInicial;
		this.nomeCliente = nomeCliente;
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setBounds(100, 100, 632, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		
		pnLogin = new JPanel();
		pnLogin.setBackground(new Color(250, 255, 255));
		pnLogin.setBounds(52, 24, 513, 360);
		pnLogin.setBorder(new LineBorder(Color.BLACK, 1, true));
		pnLogin.setLayout(null);
		frame.getContentPane().add(pnLogin);
		
		btEntrarChat = new JButton("Chat");
		btEntrarChat.setBounds(195, 103, 110, 32);
		btEntrarChat.setFont(new Font("Arial", Font.BOLD, 14));
		btEntrarChat.setForeground(Color.WHITE);
		btEntrarChat.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btEntrarChat.setBackground(new Color(0, 175, 156));
		btEntrarChat.setCursor(cursor);
		btEntrarChat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				try {
					new TelaChat(nomeCliente, getClasse()).exibir();
				} catch (UnknownHostException e1) {
					System.out.println("Endereço inválido para o servidor: " + e1.getMessage());
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Não foi possível estabelecer uma conexão com o servidor:" + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
		pnLogin.add(btEntrarChat);
		
		btAlterarSenha = new JButton("Alterar Senha");
		btAlterarSenha.setFont(new Font("Arial", Font.BOLD, 14));
		btAlterarSenha.setBounds(195, 150, 110, 32);
		btAlterarSenha.setForeground(Color.WHITE);
		btAlterarSenha.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btAlterarSenha.setBackground(new Color(0, 175, 156));
		btAlterarSenha.setCursor(cursor);
		btAlterarSenha.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new TelaEditarCadastro(getClasse(), nomeCliente).exibir();
			}
		});
		pnLogin.add(btAlterarSenha);
		
		btExcluirCadastro = new JButton("Excluir Cadastro");
		btExcluirCadastro.setBounds(187, 200, 126, 32);
		btExcluirCadastro.setFont(new Font("Arial", Font.BOLD, 14));
		btExcluirCadastro.setForeground(Color.WHITE);
		btExcluirCadastro.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btExcluirCadastro.setBackground(new Color(0, 175, 156));
		btExcluirCadastro.setCursor(cursor);
		btExcluirCadastro.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new TelaExcluirCadastro(getClasse(), nomeCliente).exibir();
			}
		});
		pnLogin.add(btExcluirCadastro);
		
		btSair = new JButton("Sair");
		btSair.setForeground(Color.WHITE);
		btSair.setFont(new Font("Arial", Font.BOLD, 14));
		btSair.setBorder(new LineBorder(new Color(8, 83, 115), 1, true));
		btSair.setBackground(new Color(8, 83, 115));
		btSair.setBounds(402, 306, 80, 32);
		btSair.setCursor(cursor);
		btSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnLogin.add(btSair);
		
		lbTitulo = new JLabel("GenChat");
		lbTitulo.setBounds(23, 25, 101, 20);
		lbTitulo.setFont(new Font("Arial", Font.BOLD, 22));
		lbTitulo.setForeground(new Color(0, 175, 156));
		pnLogin.add(lbTitulo);
	}

	
	public void exibir() {
		this.frame.setVisible(true);
	}
	
	
	/**
	 * Fecha a tela e volta para a anterior.
	 */
	private void sair() {
		this.frame.dispose();
		this.telaInicial.exibir();
	}
	
	
	public void fechar() {
		this.frame.dispose();
	}
	
	
	public TelaLogin getClasse() {
		return this;
	}
	
}