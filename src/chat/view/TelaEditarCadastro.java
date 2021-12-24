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
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import chat.controller.ClienteController;
import util.CustomPasswordField;

/**
 * Tela onde é possível o cliente/usuário trocar a senha de 
 * cadastro.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class TelaEditarCadastro {
	
	
	private JFrame frame;
	private JPanel pnAlterarCadastro;
	private JLabel lbAlterar;
	private JPasswordField tfSenhaAtual;
	private JPasswordField tfNovaSenha;
	private JButton btConfirmar;
	private Cursor cursor = new Cursor(12);
	private TelaLogin telaLogin;
	private String novaSenhaCliente;
	private String nomeCliente;
	private JPasswordField tfConfirmarNovaSenha;
	private JButton btCancelar;
	private String senhaAtualDigitada;
	private String novaSenhaDigitada;
	private String senhaConfirmacaoDigitada;

	/**
	 * Create the application.
	 */
	public TelaEditarCadastro() {
		initialize();
	}

	
	/**
	 * @wbp.parser.constructor
	 * @param telaLogin
	 */
	public TelaEditarCadastro(TelaLogin telaLogin, String nomeCliente) {
		this.telaLogin = telaLogin;
		this.nomeCliente = nomeCliente;
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Editar Cadastro");
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
		
		pnAlterarCadastro = new JPanel();
		pnAlterarCadastro.setBackground(new Color(250, 255, 255));
		pnAlterarCadastro.setBounds(52, 24, 513, 369);
		pnAlterarCadastro.setBorder(new LineBorder(Color.BLACK, 1, true));
		pnAlterarCadastro.setLayout(null);
		frame.getContentPane().add(pnAlterarCadastro);
		
		lbAlterar = new JLabel("Alterar");
		lbAlterar.setBounds(26, 11, 54, 19);
		lbAlterar.setFont(new Font("Arial", Font.BOLD, 16));
		pnAlterarCadastro.add(lbAlterar);
		
		tfSenhaAtual = new CustomPasswordField("Senha Atual");
		tfSenhaAtual.setFont(new Font("Arial", Font.PLAIN, 14));
		tfSenhaAtual.setColumns(10);
		tfSenhaAtual.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		tfSenhaAtual.setBounds(43, 80, 411, 32);
		pnAlterarCadastro.add(tfSenhaAtual);
		
		tfNovaSenha = new CustomPasswordField("Nova Senha");
		tfNovaSenha.setFont(new Font("Arial", Font.PLAIN, 14));
		tfNovaSenha.setColumns(10);
		tfNovaSenha.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		tfNovaSenha.setBounds(43, 132, 411, 32);
		pnAlterarCadastro.add(tfNovaSenha);
		
		btConfirmar = new JButton("Confirmar");
		btConfirmar.setFont(new Font("Arial", Font.BOLD, 14));
		btConfirmar.setBounds(43, 237, 411, 32);
		btConfirmar.setBackground(new Color(0, 175, 156));
		btConfirmar.setForeground(Color.WHITE);
		btConfirmar.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btConfirmar.setCursor(cursor );
		btConfirmar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		pnAlterarCadastro.add(btConfirmar);
		
		tfConfirmarNovaSenha = new CustomPasswordField("Confirmar nova senha");
		tfConfirmarNovaSenha.setFont(new Font("Arial", Font.PLAIN, 14));
		tfConfirmarNovaSenha.setColumns(10);
		tfConfirmarNovaSenha.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		tfConfirmarNovaSenha.setBounds(43, 184, 411, 32);
		pnAlterarCadastro.add(tfConfirmarNovaSenha);
		
		btCancelar = new JButton("Cancelar");
		btCancelar.setForeground(Color.WHITE);
		btCancelar.setFont(new Font("Arial", Font.BOLD, 14));
		btCancelar.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btCancelar.setBackground(new Color(0, 175, 156));
		btCancelar.setBounds(43, 280, 411, 32);
		btCancelar.setCursor(cursor);
		btCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		pnAlterarCadastro.add(btCancelar);
	}
	
	
	/**
	 * Solicita ao controlador de Cliente a alteração do cadastro.
	 */
	private void editar() {
		if (preencheuCampos()) {
				if (ehValidaNovaSenha()) {
					if (confirmouSenha()) {
						ClienteController clienteController = new ClienteController();
						boolean sucesso = clienteController.alterarCliente(this.nomeCliente, this.senhaAtualDigitada, this.novaSenhaCliente);
						
						if (sucesso) {
							JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!");
							limparCampos();
						} else {
							JOptionPane.showMessageDialog(null, "Senha atual incorreta!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Senha confirmação incorreta!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Informe uma senha válida, mínimo 3 caracteres!");
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
		senhaAtualDigitada = new String(tfSenhaAtual.getPassword()).trim();
		novaSenhaDigitada = new String(tfNovaSenha.getPassword()).trim();
		senhaConfirmacaoDigitada = new String(tfConfirmarNovaSenha.getPassword()).trim();
		
		boolean preencheuSenhaAtual = (senhaAtualDigitada != null && senhaAtualDigitada.length() > 0) ? true : false;
		boolean preencheuNovaSenha = (novaSenhaDigitada != null && novaSenhaDigitada.length() > 0) ? true : false;
		boolean preencheuSenhaConfirmacao = (senhaConfirmacaoDigitada != null && senhaConfirmacaoDigitada.length() > 0) ? true : false;
		
		return (preencheuSenhaAtual && preencheuNovaSenha && preencheuSenhaConfirmacao);
		
	}
	
	
	/**
	 * Retorna um booleano indicando se a nova senha possui
	 * uma igualdade com a senha de confirmação.
	 * 
	 * @return
	 */
	private boolean confirmouSenha() {
		boolean confirmou = (novaSenhaDigitada.equals(senhaConfirmacaoDigitada));
		this.novaSenhaCliente = novaSenhaDigitada;
		
		return confirmou;
	}
	
	
	/**
	 * Retorna um booleano indicando se a nova senha digitada pelo
	 * cliente/usuário é válida.
	 * 
	 * @return
	 */
	private boolean ehValidaNovaSenha() {
		if (novaSenhaDigitada.length() < 3) return false;
		return true;
	}
	
	
	/**
	 * Limpa os campos de texto após a confirmação de alteração.
	 */
	private void limparCampos() {
		tfSenhaAtual.requestFocus();
		tfSenhaAtual.setText("");
		tfNovaSenha.setText("");
		tfConfirmarNovaSenha.setText("");
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