package br.usjt.tcc.utils.java2d;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.usjt.tcc.utils.GameWindow;
import br.usjt.tcc.utils.GameWindowCallback;
import br.usjt.tcc.utils.ResourceFactory;
import br.usjt.tcc.utils.Sprite;
import br.usjt.tcc.utils.java2d.input.Keyboard;
import br.usjt.tcc.utils.java2d.input.Mouse;

/**
 * Uma implementacao da classe {@code GameWindow}, na qual utiliza a tecnologia
 * Java2D para renderizacao do cenario. Essa classe tambem e responsavel por
 * moniorar os eventos do teclado e do mouse utilizando AWT.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Java2DGameWindow extends Canvas implements GameWindow {

	/** Numero de versao gerado pelo Eclipse */
	private static final long serialVersionUID = -8844169615078708589L;

	/** A estrategia que permite uilizar flipping de paginacao acelerada */
	private BufferStrategy strategy;

	/**
	 * True se o jogo esta no estado "executando", ou seja, se o loop principal
	 * foi iniciado
	 */
	private boolean gameRunning = true;

	/** A janela na qual o canvas sera mostrado */
	private JFrame frame;

	/** A largura da tela */
	private int width;

	/** A altura da tela */
	private int height;

	/** O callback na qual sera notificado dos eventos causados pela janela */
	private GameWindowCallback callback;

	/** O graphics context acelerado atual */
	private Graphics2D g;

	/** A cor de fundo da tela */
	private Color c;

	/** A sprite que podera ser usada para fundo de tela do jogo */
	private Sprite background;

	/**
	 * Cria uma nova janela para renderizar utilizando Java 2D. Atente ao
	 * detalhe de a janela e apenas criada, e nao mostrada.
	 */
	public Java2DGameWindow() {
		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setBounds(0, 0, this.width, this.height);
	}

	/**
	 * Seta o titulo que deve ser mostrado na janela
	 * 
	 * @param title
	 *            O titulo a ser mostrado na janela
	 */
	public void setTitle(String title) {
		frame.setTitle(title);
	}

	/**
	 * Seta a resolucao da janela do jogo. Esse metodo so tera o efeito desejado
	 * antes de mostrar a janela na tela.
	 * 
	 * @param x
	 *            A largura da tela
	 * @param y
	 *            A altura da tela
	 */
	public void setResolution(int x, int y) {
		width = x;
		height = y;
	}

	/**
	 * Inicia o processo de renderizacao. Esse metodo e responsavel por
	 * inicializar a janela, ajustar a resolucao e posiciona-la no centro da
	 * tela.
	 */
	public void startRendering() {
		// Armazena o conteudo da janela e seta a resolucao do jogo
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(this.width, this.height));
		panel.setLayout(null);

		Keyboard.init(this);
		Mouse.init(this);

		// Seta o tamanho do canvas e coloca no conteudo da janela
		setBounds(0, 0, this.width, this.height);
		panel.add(this);

		// Avisar o AWT para nao executar o "repaint" do canvas, ja
		// que estaremos delegando a tarefa para outras classes
		setIgnoreRepaint(true);

		// Faz a janela aparecer
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);

		// Adiciona um listener para responder aos eventos da janela (ex. janela
		// fechando). Se ocorrer o evento, responda de acordo com o evento
		// disparado
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (callback != null) {
					callback.windowClosed();
				} else {
					System.exit(0);
				}
			}
		});

		// Chama o foco para que os eventos da tecla venham para a janela
		requestFocus();

		// Cria a estrategia de buffering que permite que o AWT gerencie os
		// graficos
		createBufferStrategy(2);
		strategy = getBufferStrategy();

		// Se houver um callback registrado entao notifique-o que a incialiacao
		// esta em processo
		if (callback != null) {
			callback.initialise();
		}

		// Inicia o loop principal
		gameLoop();
	}

	/**
	 * Registra o callback que deve ser notificado dos eventos da janela do
	 * jogo.
	 * 
	 * @param callback
	 *            O callback a ser notificado dos eventos da tela
	 */
	public void setGameWindowCallback(GameWindowCallback callback) {
		this.callback = callback;
	}

	/**
	 * Checa se uma tecla especifica foi pressionada.
	 * 
	 * @param keyCode
	 *            O codigo associado com a tecla para checar
	 * @return True se a tecla foi pressionada
	 */
	public boolean isKeyPressed(int keyCode) {
		return Keyboard.isPressed(keyCode);
	}

	/**
	 * Retorna o graphics context utilizado. Este metodo possui o modificador de
	 * acesso padrao.
	 * 
	 * @return O graphics context atual da janela
	 */
	Graphics2D getDrawGraphics() {
		return g;
	}

	/**
	 * Executa o loop principal do jogo. Este metodo mantem a renderizacao do
	 * cenario e solicita que o callback atualize a tela.
	 */
	private void gameLoop() {
		while (gameRunning) {
			g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(c);
			g.fillRect(0, 0, this.width, this.height);
			if (this.background != null) {
				this.background.draw(0, 0);
			}

			if (callback != null) {
				callback.frameRendering();
			}

			// Completa o processo de renderiza��o limpando o graphics
			// context e executa um flipping no buffer
			g.dispose();
			strategy.show();
		}
	}

	/**
	 * Seta a cor de fundo da janela
	 * 
	 * @param c
	 *            A cor de fundo da janela
	 */
	public void setBackgroundColor(Color c) {
		this.c = c;
	}

	/**
	 * Retorna a cor de fundo da janela
	 * 
	 * @return A cor de fundo da janela
	 */
	public Color getBackgroundColor() {
		return c;
	}

	/**
	 * 
	 */
	public boolean isMousePressed(int button) {
		return Mouse.isPressed(button);
	}

	/**
	 * Mostra uma mensagem na tela usando AWT.
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setBackgroundImage(String sprite) {
		this.background = ResourceFactory.get().getSprite(sprite);
	}
}