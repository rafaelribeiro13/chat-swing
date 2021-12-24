package chat.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import chat.model.Comando;
import chat.model.FiltroMensagens;
import chat.model.Servidor;
import util.CustomTextArea;

/**
 * Essa classe representa o chat onde diferentes clientes/usuários
 * podem se comunicar.
 * 
 * @author João Victor
 * @author Rafael Ribeiro
 * @author Vinicius Cavalcante 
 */
public class TelaChat {

	private JFrame frame;
	private final String SERVER_ADDRESS = "192.168.0.67"; //  endereço ip ao qual o cliente irá se conectar ao servidor
	private JTextArea taConversa;	 
	private JScrollPane spRolagemConversa;
	private CustomTextArea taCampoTexto;
	private JScrollPane spRolagemTexto;
	private JButton btEnviar;
	private Cursor cursor = new Cursor(12);
	private JButton btSair;
	private JList<String> listaOnline;
	private JLabel lbonline;
	private Socket socketCliente;
	private Scanner leitorDoServidor;
	private PrintStream escritorParaServidor;
	private String nomeCliente;
	private TelaLogin telaLogin;

	
	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public TelaChat(String nomeCliente, TelaLogin telaLogin) throws UnknownHostException, IOException {
		this.nomeCliente = nomeCliente;
		this.telaLogin = telaLogin;
		initialize();
		iniciar();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Chat");
		frame.setBounds(100, 100, 890, 630);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(250, 255, 255));
		
		taConversa = new JTextArea();
		taConversa.setEditable(false);
		taConversa.setFont(new Font("Arial", Font.PLAIN, 14));
		taConversa.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 0, true), new EmptyBorder(0, 10, 0, 0)));
		
		spRolagemConversa = new JScrollPane();
		spRolagemConversa.setBounds(46, 58, 507, 311);
		spRolagemConversa.setBorder(new LineBorder(Color.BLACK, 1, true));
		spRolagemConversa.setViewportView(taConversa);
		frame.getContentPane().add(spRolagemConversa);
		
		taCampoTexto = new CustomTextArea("Digite sua mensagem...");
		taCampoTexto.setFont(new Font("Arial", Font.PLAIN, 14));
		taCampoTexto.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 0, true), new EmptyBorder(0, 10, 0, 0)));
		
		spRolagemTexto = new JScrollPane();
		spRolagemTexto.setBounds(46, 379, 507, 102);
		spRolagemTexto.setBorder(new LineBorder(Color.BLACK, 1, true));
		spRolagemTexto.setViewportView(taCampoTexto);
		frame.getContentPane().add(spRolagemTexto);
		
		btEnviar = new JButton("Enviar");
		btEnviar.setFont(new Font("Arial", Font.BOLD, 14));
		btEnviar.setBounds(46, 492, 507, 32);
		btEnviar.setBackground(new Color(0, 175, 156));
		btEnviar.setForeground(Color.WHITE);
		btEnviar.setBorder(new LineBorder(new Color(0, 175, 156), 1, true));
		btEnviar.setCursor(cursor );
		frame.getContentPane().add(btEnviar);
		
		btSair = new JButton("Sair");
		btSair.setFont(new Font("Arial", Font.BOLD, 14));
		btSair.setBounds(257, 535, 76, 32);
		btSair.setBackground(new Color(8, 83, 115));
		btSair.setForeground(Color.WHITE);
		btSair.setBorder(new LineBorder(new Color(8, 83, 115), 1, true));
		btSair.setCursor(cursor);
		btSair.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fechar();
			}
		});
		frame.getContentPane().add(btSair);
		
		listaOnline = new JList<>();
		listaOnline.setFont(new Font("Arial", Font.PLAIN, 14));
		listaOnline.setBounds(563, 57, 267, 424);
		listaOnline.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1, true), new EmptyBorder(0, 10, 0, 0)));
		frame.getContentPane().add(listaOnline);
		
		lbonline = new JLabel("Online");
		lbonline.setFont(new Font("Arial", Font.BOLD, 14));
		lbonline.setBounds(563, 32, 52, 14);
		frame.getContentPane().add(lbonline);
		
	}
	
	
	/**
	 * Inicia o processo de envio/recebimento de mensagens do 
	 * servidor para cada cliente/usuário conectado.
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void iniciar() throws UnknownHostException, IOException {
		socketCliente = new Socket(SERVER_ADDRESS, Servidor.PORT);
		new Thread(() -> receberMensagens()).start();
		escreverMensagens();
	}
	
	
	/**
	 * Recebimento de mensagens em uma thread a parte para
	 * que seja possível a comunicação em tempo real entre 
	 * os diferentes clientes/usuários. 
	 */
	private void receberMensagens() {
		try {
			leitorDoServidor = new Scanner(socketCliente.getInputStream(), StandardCharsets.UTF_8);
			while (leitorDoServidor.hasNextLine()) {
				String mensagemServidor = leitorDoServidor.nextLine().trim();
				
				if (mensagemServidor.equals(Comando.LOGIN)) {
					escritorParaServidor.println(this.nomeCliente);
				} else if (mensagemServidor.equals(Comando.LOGIN_ACEITO)) {
					taConversa.append("Servidor disse: " + leitorDoServidor.nextLine().trim() + System.lineSeparator());
					taCampoTexto.requestFocus();
				} else if (mensagemServidor.equals(Comando.LOGIN_NEGADO)) {
					JOptionPane.showMessageDialog(null, "LOGIN NEGADO");
				} else if (mensagemServidor.equals(Comando.LOGIN_EXISTENTE)) {
					JOptionPane.showMessageDialog(null, "Usuário já existente!");
				} else if (mensagemServidor.equals(Comando.LISTAR_USUARIOS)) {
					String[] usuariosAtivos = leitorDoServidor.nextLine().trim().split(",");
					preencherLista(usuariosAtivos);
				} else if (mensagemServidor.equals(Comando.USUARIO_INDISPONIVEL)) {
					JOptionPane.showMessageDialog(null, "A pessoa selecionada não está disponível!");
				} else {
					taConversa.append(FiltroMensagens.filtrar(mensagemServidor) + System.lineSeparator());
				}
			}
		} catch (IOException e) {
			System.out.println("Não foi possível ler a mensagem do servidor: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Envio de mensagens entre os diferentes clientes/usuários.
	 */
	private void escreverMensagens() {
		try {
			escritorParaServidor = new PrintStream(socketCliente.getOutputStream(), true, StandardCharsets.UTF_8);
		
			btEnviar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (mensagemFoiDigitada()) {
						String mensagem = taCampoTexto.getText().trim();
						
						if (usuarioFoiSelecionado()) {
							String destinatario = listaOnline.getSelectedValue().trim();
							escritorParaServidor.println(Comando.MENSAGEM + destinatario);
						}
						
						escritorParaServidor.println(mensagem);
						taConversa.append("Você: " + FiltroMensagens.filtrar(mensagem) + System.lineSeparator());
						limparCampoTexto();
					}
				}
			});
		} catch (IOException e) {
			System.out.println("Não foi possível enviar mensagem ao servidor: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Fecha todo recurso de leitura/escrita quando os mesmos 
	 * já não estão sendo mais utilizados a fim de preservar
	 * a memória do sistema operacional.  
	 */
	private void fechar() {
		escritorParaServidor.println(Comando.SAIR);
		try {
			System.out.println("Fechando recursos...");
			leitorDoServidor.close();
			escritorParaServidor.close();
			socketCliente.close();
			System.out.println("Recursos fechado.");
			frame.dispose();
			telaLogin.exibir();
		} catch (IOException e1) {
			System.out.println("Não foi possível fechar o socketCliente do cliente: " + e1.getMessage());
			e1.printStackTrace();
		}
	}
	
	
	/**
	 * Limpa o campo de texto após o envio da mensagem.
	 */
	private void limparCampoTexto() {
		taCampoTexto.setText("");
		taCampoTexto.requestFocus();
	}
	
	
	/**
	 * Retorna um booleano indicando se a mensagem foi
	 * digitada antes de enviá-la.
	 * 
	 * @return
	 */
	private boolean mensagemFoiDigitada() {
		return (!taCampoTexto.getText().isEmpty());
	}
	
	
	/**
	 * Retorna um booleano indicando se o usuário/cliente foi
	 * selecionado antes da mensagem ser enviada.
	 * 
	 * @return
	 */
	private boolean usuarioFoiSelecionado() {
		String destinatario = listaOnline.getSelectedValue();
		return (destinatario != null && !destinatario.isEmpty());
	}
	
	
	/**
	 * Preenche a lista de clientes/usuários ativos do chat.
	 * 
	 * @param usuariosAtivos
	 */
	private void preencherLista(String[] usuariosAtivos) {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		
		for (String cli : usuariosAtivos) {
			modelo.addElement(cli);
		}
		
		listaOnline.setModel(modelo);
	}

	
	public void exibir() {
		this.frame.setVisible(true);
	}
	
	
}