package chat.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import chat.controller.ClienteController;
import util.CustomPasswordField;
import util.CustomTextField;

/**
 * Tela onde é possível o cliente/usuário realizar a exclusão
 * de seu cadastro.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class TelaExcluirCadastro {

	private JFrame frame;
	private JPanel pnExcluirCadastro;
	private JLabel lbExcluir;
	private JTextField tfUsuario;
	private JPasswordField tfSenha;
	private JButton btExcluir;
	private Cursor cursor = new Cursor(12);
	private TelaLogin telaLogin;
	private String nomeCliente;
	private CustomPasswordField tfConfirmarSenha;
	private JButton btCancelar;
	private String nomeDigitado;
	private String senhaDigitada;
	private String senhaConfirmacaoDigitada;

	/**
	 * Create the application.
	 */
	public TelaExcluirCadastro() {
		initialize();
	}

	/**
	 * @wbp.parser.constructor
	 * @param telaLogin
	 */
	public TelaExcluirCadastro(TelaLogin telaLogin, String nomeCliente) {
		this.telaLogin = telaLogin;
		this.nomeCliente = nomeCliente;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Excluir Cadastro");
		frame.setBounds(100, 100, 632, 479);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				sair();
			}
		});
		
		pnExcluirCadastro = new JPanel();
		pnExcluirCadastro.setBackground(new Color(250, 255, 255));
		pnExcluirCadastro.setBounds(52, 24, 513, 369);
		pnExcluirCadastro.setBorder(new LineBorder(Color.BLACK, 1, true));
		pnExcluirCadastro.setLayout(null);
		frame.getContentPane().add(pnExcluirCadastro);
		
		lbExcluir = new JLabel("Excluir");
		lbExcluir.setBounds(26, 11, 54, 19);
		lbExcluir.setFont(new Font("Arial", Font.BOLD, 16));
		pnExcluirCadastro.add(lbExcluir);
		
		tfUsuario = new CustomTextField("Usuário");
		tfUsuario.setBounds(43, 80, 419, 32);
		tfUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
		tfUsuario.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		pnExcluirCadastro.add(tfUsuario);
		
		tfSenha = new CustomPasswordField("Senha");
		tfSenha.setBounds(43, 132, 419, 32);
		tfSenha.setFont(new Font("Arial", Font.PLAIN, 14));
		tfSenha.setColumns(10);
		tfSenha.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		pnExcluirCadastro.add(tfSenha);
		
		btExcluir = new JButton("Confirmar");
		btExcluir.setFont(new Font("Arial", Font.BOLD, 14));
		btExcluir.setBounds(43, 237, 419, 32);
		btExcluir.setBackground(new Color(0, 175, 156));
		btExcluir.setForeground(Color.WHITE);
		btExcluir.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btExcluir.setCursor(cursor );
		btExcluir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		pnExcluirCadastro.add(btExcluir);
		
		tfConfirmarSenha = new CustomPasswordField("Confirmar Senha");
		tfConfirmarSenha.setBounds(43, 184, 419, 32);
		tfConfirmarSenha.setFont(new Font("Arial", Font.PLAIN, 14));
		tfConfirmarSenha.setColumns(10);
		tfConfirmarSenha.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		pnExcluirCadastro.add(tfConfirmarSenha);
		
		btCancelar = new JButton("Cancelar");
		btCancelar.setForeground(Color.WHITE);
		btCancelar.setFont(new Font("Arial", Font.BOLD, 14));
		btCancelar.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btCancelar.setBackground(new Color(0, 175, 156));
		btCancelar.setBounds(43, 280, 419, 32);
		btCancelar.setCursor(cursor);
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnExcluirCadastro.add(btCancelar);
	}

	
	/**
	 * Solicita ao controlador de Cliente a exclusão de cadastro.
	 */
	private void excluir() {
		if (preencheuCampos()) {
			if (dadosCorretos()) {
				if (confirmouSenha()) {
					ClienteController clienteController = new ClienteController();
					boolean sucesso = clienteController.excluirCliente(this.nomeCliente, this.senhaDigitada);
					
					if (sucesso) {
						JOptionPane.showMessageDialog(null, "Cadastro excluído com sucesso!");
						frame.setVisible(false);
						telaLogin.fechar();
						new TelaInicial().exibir();
					} else {
						JOptionPane.showMessageDialog(null, "Senha atual incorreta!");
					}
				} else {	
					JOptionPane.showMessageDialog(null, "Senha confirmação incorreta!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Usuário incorreto!");
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
		nomeDigitado = tfUsuario.getText().trim();
		senhaDigitada = new String(tfSenha.getPassword()).trim();
		senhaConfirmacaoDigitada = new String(tfConfirmarSenha.getPassword()).trim();
		
		boolean preencheuNome = (nomeDigitado != null && nomeDigitado.length() > 0) ? true : false;
		boolean preencheuSenha = (senhaDigitada != null && senhaDigitada.length() > 0) ? true : false;
		boolean preencheuSenhaConfirmacao = (senhaConfirmacaoDigitada != null && senhaConfirmacaoDigitada.length() > 0) ? true : false;
		
		return (preencheuNome && preencheuSenha && preencheuSenhaConfirmacao);
	}
	
	
	/**
	 * Retorna um booleano indicando se os dados foram preenchidos
	 * corretamente.
	 *  
	 * @return	
	 */
	private boolean dadosCorretos() {
		return (this.nomeCliente.equals(nomeDigitado));
	}
	
	
	/**
	 * Retorna um booleano indicando se a nova senha possui
	 * uma igualdade com a senha de confirmação.
	 * 
	 * @return
	 */
	private boolean confirmouSenha() {
		boolean confirmou = (senhaDigitada.equals(senhaConfirmacaoDigitada));
		return confirmou;
	}
	
	
	/**
	 * Fecha a tela e volta para a anterior.
	 */
	private void sair() {
		this.frame.dispose();
		this.telaLogin.exibir();
	}
	
	
	public void exibir() {
		this.frame.setVisible(true);
	}
}
