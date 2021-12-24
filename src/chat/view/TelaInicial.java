package chat.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import chat.controller.ClienteController;
import util.CustomPasswordField;
import util.CustomTextField;

/**
 * Tela Inicial onde o cliente/usuário pode realizar o 
 * cadastro/logar na aplicação.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class TelaInicial {

	private JFrame frame;
	private JLabel lbTitulo;
	private JTextField tfUsuario;
	private JPasswordField tfSenha;
	private JButton btEntrar;
	private Cursor cursor = new Cursor(12);
	private JButton btCadastrar;
	private JButton btSair;
	private String nomeCliente;
	private String senhaCliente;
	private int resposta = 1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial window = new TelaInicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaInicial() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Início");
		frame.setBounds(100, 100, 600, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(250, 255, 255));
		
		lbTitulo = new JLabel("GenChat");
		lbTitulo.setBounds(216, 100, 160, 47);
		lbTitulo.setFont(new Font("Arial", Font.PLAIN, 36));
		lbTitulo.setForeground(new Color(0, 175, 156));
		frame.getContentPane().add(lbTitulo);
		
		tfUsuario = new CustomTextField("Usuário");
		tfUsuario.setBounds(161, 180, 256, 32);
		tfUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
		tfUsuario.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		frame.getContentPane().add(tfUsuario);
		
		tfSenha = new CustomPasswordField("Senha");
		tfSenha.setBounds(161, 230, 256, 32);
		tfSenha.setFont(new Font("Arial", Font.PLAIN, 14));
		tfSenha.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		frame.getContentPane().add(tfSenha);
		
		btEntrar = new JButton("Entrar");
		btEntrar.setBounds(295, 273, 89, 32);
		btEntrar.setFont(new Font("Arial", Font.BOLD, 14));
		btEntrar.setForeground(Color.WHITE);
		btEntrar.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btEntrar.setBackground(new Color(0, 175, 156));
		btEntrar.setCursor(cursor);
		btEntrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		frame.getContentPane().add(btEntrar);
		
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.setForeground(Color.WHITE);
		btCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
		btCadastrar.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btCadastrar.setBackground(new Color(0, 175, 156));
		btCadastrar.setBounds(195, 273, 89, 32);
		btCadastrar.setCursor(cursor);
		btCadastrar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrar();
			}
		});
		frame.getContentPane().add(btCadastrar);
		
		btSair = new JButton("Sair");
		btSair.setBounds(474, 409, 89, 32);
		btSair.setFont(new Font("Arial", Font.BOLD, 14));
		btSair.setBackground(new Color(8, 83, 115));
		btSair.setForeground(Color.WHITE);
		btSair.setBorder(new LineBorder(new Color(8, 83, 115), 1, true));
		btSair.setCursor(cursor);
		btSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		frame.getContentPane().add(btSair);
		
	}
	
	
	/**
	 * Solicita ao controlador de Cliente a realização do login.
	 */
	private void logar() {
		if (preencheuCampos()) {
			ClienteController clienteController = new ClienteController();
			boolean existe = clienteController.consultarCliente(nomeCliente, senhaCliente); 
			
			if (existe) {
				frame.setVisible(false);
				new TelaLogin(getClasse(), nomeCliente).exibir();
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.");
		}
	}
	
	
	/**
	 * Solicita ao controlador de Cliente a realização do cadastro.
	 */
	private void cadastrar() {
		if (preencheuCampos()) {
			try {
				ClienteController clienteController = new ClienteController();
				boolean sucesso = clienteController.cadastrarCliente(nomeCliente, senhaCliente);
				
				if (sucesso) {
					JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
					resposta  = JOptionPane.showConfirmDialog(null	, "Deseja entrar?"); // yes=0, no=1, cancel=2
				} else {
					JOptionPane.showMessageDialog(null, "Usuário já existente.");
				}
				
				if (resposta == 0) {
					logar();
				}
				
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.");
		}
	}
	
	
	/**
	 * Retorna um booleano indicando se os campos foram preenchidos
	 * corretamente.
	 * 
	 * @return
	 */
	private boolean preencheuCampos() {
		nomeCliente = tfUsuario.getText().trim();
		senhaCliente = new String(tfSenha.getPassword()).trim();
		
		boolean nomePreenchido = (nomeCliente != null && nomeCliente.length() > 0) ? true : false;
		boolean senhaPreenchida = (senhaCliente != null && senhaCliente.length() > 0) ? true : false;
		
		return (nomePreenchido && senhaPreenchida);
	}
	
	
	/**
	 * Limpa os campos de texto após um login ou cadastro.
	 */
	private void limparCampos() {
		tfUsuario.requestFocus();
		tfUsuario.setText("");
		tfSenha.setText("");
	}
	
	
	public void exibir() {
		this.frame.setVisible(true);
	}
	
	
	public void fechar() {
		this.frame.dispose();
	}
	
	
	public TelaInicial getClasse() {
		return this;
	}
	
}