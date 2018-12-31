package br.usjt.tcc.domino;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JOptionPane;

import br.usjt.tcc.utils.GameWindow;
import br.usjt.tcc.utils.GameWindowCallback;
import br.usjt.tcc.utils.ResourceFactory;
import br.usjt.tcc.utils.SystemTimer;
import br.usjt.tcc.utils.java2d.input.Keyboard;
import br.usjt.tcc.utils.java2d.input.Mouse;
import br.usjt.tcc.utils.sound.SoundCache;

/**
 * Classe que representa toda a implementacao do jogo Domino. O objetivo desta
 * classe e centralizar todo o fluxo do jogo nesta classe e inicializar as
 * entidades do jogo.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Domino extends Canvas implements GameWindowCallback {

	/** Resolucao do jogo */
	public final Dimension resolution = new Dimension(1024, 768);

	/** Numero de versao calculado pelo Eclipse */
	private static final long serialVersionUID = 3791270690500076519L;

	/** A janela que sera utilizada para renderizar o jogo */
	public transient final GameWindow window;

	/** O titulo da janela do jogo */
	private transient final String windowTitle = "Domino";

	/** O tempo na qual o loop anterior foi executado */
	private transient long lastLoopTime = SystemTimer.getTime();

	/**
	 * Um sinalizador de que o jogo espera por uma botao do mouse a ser
	 * pressionado
	 */
	@SuppressWarnings("unused")
	private boolean waitingForMousePress = true;

	/** O tempo decorrido desde que houve a ultima medicao do FPS */
	private long lastFpsTime = 0;

	/** A taxa de FPS calculada */
	private int fps;

	/** Indicador para que o jogo comece */
	public boolean gameInitiated = false;

	/** Indicador para que o jogo termine */
	public boolean gameOver = false;

	/**
	 * Vetor com as entidades dos jogadores. O tamanho foi fixado para que nao
	 * haja violacoes
	 */
	private Vector<PlayerEntity> players = new Vector<PlayerEntity>(4);

	/**
	 * Vetor com as entidades das pecas. O tamanho foi fixado para que nao haja
	 * violacoes
	 */
	private Vector<PieceEntity> gamePieces = new Vector<PieceEntity>(28);

	/**
	 * Enum contendo as direcoes que a mesa pode seguir ao longo das jogadas
	 */
	public enum Direction {
		RIGHT_TO_LEFT, DOWN_TO_UP, LEFT_TO_RIGHT, UP_TO_DOWN
	};

	/** Enum contendo as possibilidades de jogadas dos jogadores */
	public enum canMove {
		DO_NOTHING, CAN_LEFT, CAN_RIGHT, CAN_BOTH
	};

	/** Coordenadas da mesa no lado esquerdo */
	public Dimension LEFT = new Dimension();

	/** Coordenadas da mesa no lado direito */
	public Dimension RIGHT = new Dimension();

	/** Indica se o lado esquerdo da mesa precisa virar a jogada */
	public boolean turnLeft = false;

	/** Indica se o lado direito da mesa precisa virar a jogada */
	public boolean turnRight = false;

	/** Ponto maximo onde a peca pode ser colocada na mesa */
	public final Dimension MAX = new Dimension((this.resolution.width - 150), (this.resolution.height - 150));

	/** Ponto minimo onde a peca pode ser colocada na mesa */
	public final Dimension MIN = new Dimension(200, 150);

	/** A direcao a ser tomada pelo lado esquerdo da mesa */
	public Direction DIR_LEFT;

	/** A direcao a ser tomada pelo lado direito da mesa */
	public Direction DIR_RIGHT;

	/** Valor atual da extremidade Esquerda do jogo */
	public int extremeLeft = -1;

	/** Valor atual da extremidade Direita do jogo */
	public int extremeRight = -1;

	/** Numero de vezes que os jogadores podem passar a jogada (Max. 4 vezes) */
	public int skip;

	/** Indicador de quem sera a proxima jogada */
	public int nextTurn = -1;

	/** Engine sonora para os cliques do mouse */
	public SoundCache sound = new SoundCache();

	/**
	 * Contrutor da classe que seta o tipo de renderizacao a ser utilizado.
	 * 
	 * @see br.usjt.tcc.utils.java2d.Java2DGameWindow
	 * @see br.usjt.tcc.utils.lwjgl.LWJGLGameWindow
	 * 
	 * @param renderingType
	 *            O tipo de renderizacao
	 */
	public Domino(int renderingType) {
		super();
		// Cria a janela baseada no tipo de renderizacao
		ResourceFactory.get().setRenderingType(renderingType);
		window = ResourceFactory.get().getGameWindow();
		// Seta os atributos basicos da janela do jogo
		window.setResolution(resolution.width, resolution.height);
		window.setGameWindowCallback(this);
		window.setTitle(windowTitle);
		// window.setBackgroundColor(new Color(4096582)); // Cor de fundo: Verde
		window.setBackgroundImage("sprites/domino/background.jpg");
		// Inicia a renderizacao do jogo
		window.startRendering();
	}

	/**
	 * Inicia um novo jogo. Este metodo deve limpar as colecoes de entidades
	 * para criar novas colecoes.
	 */
	private void startGame() {
		// Limpa a colecao de entidades e inicializa uma nova colecao.
		gamePieces.clear();
		players.clear();
		// Inicializa as entidades do jogo
		initEntities();
	}

	/**
	 * Prepara o cenario para o estado inicial do jogo. As entidades do tipo
	 * peca serao adicionadas na lista de pecas a serem renderizadas, enquanto
	 * as entidades do tipo jogador serao adicionadas na lista de jogadores para
	 * executar a logica do jogo baseado em turnos.
	 */
	private void initEntities() {
		// Inicia as pecas que pode aparecer no jogo. O objetivo o incializar
		// TODAS as pecas com as combinacoes possiveis sem que haja repeticao.
		for (int i = 0; i < 7; i++) {
			for (int j = i; j < 7; j++) {
				gamePieces.addElement(new PieceEntity(this, i, j, 0, 0));
			}
		}
		// Cria os jogadores e atribui a cada um uma pe�a previamente
		// inicializada
		for (int i = 0; i < 4; i++) {
			players.addElement(new PlayerEntity(this, i));
			for (int j = 0; j < 7; j++) {
				PlayerEntity playerEntity = (PlayerEntity) players.elementAt(i);
				int p = (int) (28.0 * Math.random());
				PieceEntity pieceEntity = (PieceEntity) gamePieces.elementAt(p);
				if (!pieceEntity.isPlayerPiece()) { // Pe�a ainda n�o utilizada,
					// adicione a colecao de pecas do jogador
					pieceEntity.setAttributes(i, playerEntity.getPieces().size());
					playerEntity.addPiece(pieceEntity);
				} else { // Caso a peca escolhida aleatoriamente ja tenha sido
							// usada,
					// repita o loop
					j--;
				}
			}
		}
	}

	/**
	 * Inicializa os elementos do jogo.
	 */
	public void initialise() {
		// Inicia o estado inicial do jogo
		startGame();
	}

	/**
	 * Notificacao de que o frame sera renderizado. Este metodo e responsavel
	 * por atualizar o frame(Pecas) e a logica do jogo(Jogadores).
	 */
	public void frameRendering() {
		// Dispara um Sleep no jogo para que a thread possa reagir em
		// tempo certo aos comandos do jogador.
		SystemTimer.sleep(lastLoopTime + 10 - SystemTimer.getTime());

		// Calcula quanto tempo se passou desde a ultima atualizacao,
		// este bloco serve para quantizar a movimentacao das entidades
		// na ultima iteracao do loop.
		long delta = SystemTimer.getTime() - lastLoopTime;
		lastLoopTime = SystemTimer.getTime();
		lastFpsTime += delta;
		fps++;

		// Atualiza o contador de FPS se passou 1 segundo.
		if (lastFpsTime >= 1000) {
			window.setTitle(windowTitle + " (FPS: " + fps + ")");
			lastFpsTime = 0;
			fps = 0;
		}

		// Passa pela colecao de pecas para redesenha-las.
		for (PieceEntity piece : gamePieces) {
			piece.draw();
		}

		// Se algum evento do jogo indicar que a logica do jogo
		// deve ser atualizada, percorre a colecao de entidades
		// jogador para realizar a logica de cada entidade
		// individualmente.
		PlayerEntity player = null;
		switch (nextTurn) {
		case 0:
			break;
		case 1:
			player = this.players.elementAt(1);
			break;
		case 2:
			player = this.players.elementAt(2);
			break;
		case 3:
			player = this.players.elementAt(3);
			break;
		}
		if (player != null) {
			player.doLogic();
		}

		// Se o jogo nao foi iniciado, inicie agora
		if (!gameInitiated) {
			this.doInitialLogic();
		}

		if (gameOver) {
			nextTurn = -1;
		}

		// Se a tecla ESC foi pressionada, fecha o jogo
		if (window.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}

		if (window.isKeyPressed(KeyEvent.VK_F2)) {
			Keyboard.setPressed(KeyEvent.VK_F2, false);
			final int result = JOptionPane.showOptionDialog(null, "Voce deseja iniciar um novo jogo?",
					"Iniciar Novo Jogo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (result == 0) {
				this.nextTurn = -1;
				this.gameInitiated = false;
				this.gameOver = false;
				this.turnLeft = false;
				this.turnRight = false;
				this.extremeLeft = -1;
				this.extremeRight = -1;
				this.LEFT = new Dimension();
				this.RIGHT = new Dimension();
				window.startRendering();
			}
		}

		// Se o botao do mouse for clicado, Tratar evento
		if (window.isMousePressed(MouseEvent.BUTTON1)) {
			Mouse.setPressed(MouseEvent.BUTTON1, false);
			if (this.playerTurn() && !gameOver) {
				if (this.players.get(0).getPieces().size() == 0) {
					window.showMessage("Fim de jogo. Venceu jogador 1");
					gameInitiated = false;
					gameOver = true;
				} else {
					nextTurn = 1;
				}
			}
		}

		// Se o botao do mouse for clicado, Tratar evento
		if (window.isMousePressed(MouseEvent.BUTTON3) && !gameOver) {
			Mouse.setPressed(MouseEvent.BUTTON3, false);
			window.showMessage("Jogador 1 passa a jogada");
			this.skip++;
			if (this.skip >= 4) {
				this.window.showMessage("Fim de jogo. Empate");
				this.gameOver = true;
				this.gameInitiated = false;
			}
			nextTurn = 1;
		}
	}

	/**
	 * Executa a logica do jogador
	 * 
	 * @return True se o jogador fez a jogada
	 */
	private boolean playerTurn() {
		PieceEntity playerPiece = null;
		for (PieceEntity piece : players.get(0).getPieces()) {
			if ((Mouse.getMx() >= (piece.getX() - 49)) && (Mouse.getMx() <= piece.getX())) {
				if ((Mouse.getMy() >= piece.getY()) && (Mouse.getMy() <= (piece.getY() + 96))) {
					playerPiece = piece;
				}
			}
		}
		if (playerPiece != null) {
			if (!this.gameInitiated && playerPiece.isDoubleSided()) {
				playerPiece.movePiece(true);
			} else {
				canMove dir = playerPiece.canMove();
				if ((dir != canMove.DO_NOTHING) && (playerPiece.getLeft() + playerPiece.getRight()) >= 0) {
					this.skip = 0;
					if (dir == canMove.CAN_BOTH) {
						String[] options = (this.extremeLeft != this.extremeRight)
								? new String[] { String.valueOf(this.extremeLeft), String.valueOf(this.extremeRight) }
								: new String[] { String.valueOf(this.extremeLeft) + " esquerdo",
										String.valueOf(this.extremeRight) + " direito" };
						int result = JOptionPane.showOptionDialog(null, "Escolha em qual lado deseja jogar",
								"Lados iguais", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
								null);
						if (result == 0) {
							dir = canMove.CAN_LEFT;
						} else {
							dir = canMove.CAN_RIGHT;
						}
					}
					switch (dir) {
					case CAN_LEFT:
						playerPiece.movePiece(true);
						break;
					case CAN_RIGHT:
						playerPiece.movePiece(false);
						break;
					}
				}
			}
			players.get(0).getPieces().remove(playerPiece);
			return true;
		}
		return false;
	}

	/**
	 * Realiza a logica inicial do jogo. Este metodo varre todas as pecas dos
	 * jogadores e procura pela peca double 6.
	 */
	private void doInitialLogic() {
		// Percorre as pecas para saber quem possui a maior pe�a double.
		for (PlayerEntity player : this.players) {
			for (PieceEntity piece : player.getPieces()) {
				if ((piece.getLeft() == 6) && (piece.getRight() == 6)) {
					player.doLogic();
					return;
				}
			}
		}
	}

	/**
	 * Notificacao de que o jogo sera fechado.
	 */
	public void windowClosed() {
		System.exit(0);
	}

	/**
	 * O ponto central do jogo. Este metodo simplesmente cria uma instancia da
	 * classe na qual ira iniciar a tela e o loop principal do jogo.
	 * 
	 * @param args
	 *            Os argumentos que sao passados via CLI. OBS: Isso nao e
	 *            utilizado.
	 */
	public static void main(String args[]) {
		final int result = JOptionPane.showOptionDialog(null, "Java2D ou OpenGL?", "Java2D ou OpenGL?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] { "Java2D", "LWJGL" },
				null);
		if (result == 0) {
			new Domino(ResourceFactory.JAVA2D);
		} else if (result == 1) {
			new Domino(ResourceFactory.OPENGL_LWJGL);
		}
	}
}