package br.usjt.tcc.jogodofutisco;

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
import br.usjt.tcc.utils.java2d.input.Mouse;
import br.usjt.tcc.utils.sound.SoundCache;
import br.usjt.tcc.utils.xml.Question;
import br.usjt.tcc.utils.xml.QuestionLoader;

/**
 * Classe que representa o jogo do futisco. O objetivo desta classe e
 * centralizar todo o fluxo do jogo nesta classe e inicializar as
 * entidades do jogo.
 *  
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Jogo extends Canvas implements GameWindowCallback {
	/** Resolucao do jogo */
	public final Dimension resolution = new Dimension(1024, 768);
	/** Numero de versao calculado pelo Eclipse */
	private static final long serialVersionUID = 26727163333099623L;
	/** A janela que ser� utilizada para renderizar o jogo */
	private GameWindow window;
	/** O titulo da janela do jogo */
	private String windowTitle = "Jogo do Futisco";
	/** O tempo na qual o loop anterior foi executado */
	private transient long lastLoopTime = SystemTimer.getTime();
	/** O tempo decorrido desde que houve a ultima medicao do FPS */
	private long lastFpsTime = 0;
	/** A taxa de FPS calculada */
	private int fps;
	/** */
	private Vector<PlayerEntity> players = new Vector<PlayerEntity>();
	/** */
	public SpaceCoord  coord = new SpaceCoord("resources/tab1.klc");
	/** */
	private BoardEntity board;
	/** */
	private DiceEntity dice;
	/** */
	private int turn = 0;
	/** */
	private QuestionLoader loader = new QuestionLoader();
	/** */
	private int lastspace = 0;
	/** */
	private int diceValue = 1;
	/** Engine sonora para os cliques do mouse */
	public SoundCache sound = new SoundCache();
	
	/**
	 * Contrutor da classe que seta o tipo de renderizacao a ser utilizado.
	 * 
	 * @see br.usjt.tcc.utils.java2d.Java2DGameWindow
	 * @see br.usjt.tcc.utils.lwjgl.LWJGLGameWindow
	 * 
	 * @param renderingType O tipo de renderizacao
	 */
	public Jogo(int renderingType) {
		// Cria a janela baseada no tipo de renderizacao
		ResourceFactory.get().setRenderingType(renderingType);
		window = ResourceFactory.get().getGameWindow();
		// Seta os atributos basicos da janela do jogo		
		window.setResolution(resolution.width, resolution.height);
		window.setGameWindowCallback(this);
		window.setTitle(windowTitle);
		// Inicia a renderizacao do jogo
		window.startRendering();
	}
	
	/**
	 * Inicia um novo jogo. Este metodo deve limpar as colecoes de entidades para
	 * criar novas colecoes.
	 */
	private void startGame() {
		// Limpa a colecao de entidades e inicializa uma nova colecao.
		players.clear();
		// Inicializa as entidades do jogo
		initEntities();
	}
	
	/**
	 * 
	 *
	 */
	private void initEntities() {
		board = new BoardEntity(this,"sprites/jogodofutisco/mundo1.jpg",0,0);
		dice = new DiceEntity("sprites/jogodofutisco/1.gif",865,620);
		for(int i = 0; i < 4; i++) {
			PlayerEntity playerEntity = new PlayerEntity( this, i );
			players.add( playerEntity );
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
	 * Notificacao de que o frame sera renderizado. Este metodo e responsavel por
	 * atualizar o frame e a logica do jogo.
	 */
	public void frameRendering() {
		// Dispara um Sleep no jogo para que a thread possa reagir em
		// tempo certo aos comandos do jogador.
		SystemTimer.sleep(lastLoopTime + 10 - SystemTimer.getTime());

		// Calcula quanto tempo se passou desde a ultima atualizacao,
		// este bloco serve para quantificar a movimentacao das entidades
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
		
		// Desenha o tabuleiro e o dado
		board.draw();
		dice.draw();
		
		// Passa pela colecao de pecas para redesenha-las.
		for ( PlayerEntity playerEntity : players ) {
			playerEntity.draw(coord,playerEntity.getSpace(),playerEntity.getPlayer());
		}
		
		// Se a tecla ESC foi pressionada, fecha o jogo
		if (window.isKeyPressed(KeyEvent.VK_ESCAPE)) {
			System.exit(0);
		}
		
		// Se o botao do mouse for clicado, Tratar evento
		if (window.isMousePressed(MouseEvent.BUTTON1)) {
			Mouse.setPressed(MouseEvent.BUTTON1, false);

			if (Mouse.getMx() >= 865 &&
					Mouse.getMy() >= 620 &&
					Mouse.getMx() <= 971 &&
					Mouse.getMy() <= 726) {
				sound.playSound("sound/winAquariumMenuPopUp.wav");
				
				this.diceValue = dice.rollDice();
				PlayerEntity player = players.get(turn);
				this.lastspace = player.getSpace() + this.diceValue;
				int spaces = player.getSpace();
				
				if(this.lastspace > coord.getTotalNumberOfSpaces()) {
					this.lastspace = coord.getTotalNumberOfSpaces();
				}
				
				while(player.getSpace() < this.lastspace) {
					player.setSpace(++spaces);
					if(coord.getType(spaces).equalsIgnoreCase("question")) {
						this.showQuestion(loader.getQuestion("Conceitos Gerais",
							loader.getRandomQuestion(loader.getSizeListQuestion("Conceitos Gerais"))), 
								this);
					}	
				}
				this.turn++;
				if(this.turn > 3) {
					this.turn = 0;
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
	
	public void showQuestion(Question question,Jogo jogo) {
		new QuestionDialog(question,jogo);
	}

	/**
	 * O ponto central do jogo. Este metodo simplesmente cria uma instancia da
	 * classe na qual ira iniciar a tela e o loop principal do jogo.
	 * 
	 * @param args Os argumentos que sao passados via CLI. OBS: Isso nao e utilizado.
	 */
	public static void main(String argv[]) {
		int result = JOptionPane.showOptionDialog(null,"Java2D ou OpenGL?","Java2D ou OpenGL?",
				JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[] {"Java2D","LWJGL"},null);
		
		if (result == 0) {
			new Jogo(ResourceFactory.JAVA2D);
		} else if (result == 1) {
			new Jogo(ResourceFactory.OPENGL_LWJGL);
		}
	}
}
