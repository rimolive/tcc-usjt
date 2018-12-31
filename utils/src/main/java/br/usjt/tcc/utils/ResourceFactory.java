package br.usjt.tcc.utils;

import br.usjt.tcc.utils.java2d.Java2DGameWindow;
import br.usjt.tcc.utils.java2d.Java2DSpriteStore;
import br.usjt.tcc.utils.lwjgl.LWJGLGameWindow;
import br.usjt.tcc.utils.lwjgl.LWJGLSprite;

/**
 * Um ponto central para criacao de recursos para utilizacao no jogo. O retorno
 * dos recursos podem ser implementados de varios contextos de renderizacao
 * diferentes mas tambem ira trabalhar com a interface GameWindow fornecida pela
 * classe. Dessa forma, o sprite retornado como um recurso ira ser desenhada
 * facilmente na interface GameWindow fornecida por esta factory.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class ResourceFactory {
	/** A instancia unica da classe <singleton> */
	private static final ResourceFactory single = new ResourceFactory();
	/**
	 * Um indicador de que o jogo deve utilizar Java2D para renderizar o jogo
	 */
	public static final int JAVA2D = 1;
	/**
	 * Um indicador de que o jogo deve utilizar OpenGL (LWJGL) para renderizar o
	 * jogo
	 */
	public static final int OPENGL_LWJGL = 3;
	/** O tipo de renderizacao que sera atualmente utilizado */
	private int renderingType = JAVA2D;
	/** A janela que o jogo utilizara para renderizar */
	private GameWindow window;

	/**
	 * O construtor padrao se tornou privado para evitar que uma nova instsncia
	 * da classe seja construida externamente. Isso foi feito para forcar o
	 * padrso singleton da classe.
	 */
	private ResourceFactory() {
	}

	/**
	 * Retorna a instancia unica da classe
	 * 
	 * @return A instancia unica da classe
	 */
	public static ResourceFactory get() {
		return single;
	}

	/**
	 * Seta o metodo de renderizacao que deve ser utilizado. ATENCAO: Este
	 * metodo so pode ser utilizado antes que o primeiro recurso seja alocado.
	 * 
	 * @param renderingType
	 *            O tipo de renderizacao a ser utilizado
	 */
	public void setRenderingType(int renderingType) {
		// Se o tipo de renderizacao e desconhecido, avisa o programa
		if ((renderingType != JAVA2D) && (renderingType != OPENGL_LWJGL)) {
			// Lanca uma RuntimeException para avisar o usuario.
			throw new RuntimeException("Tipo de renderizacao especificado desconhecido: " + renderingType);
		}

		// Se a janela do jogo ja foi criada, entao ja existe recursos alocados
		// no tipo de renderizacao atual, entao e lancada uma RuntimeException.
		if (window != null) {
			throw new RuntimeException("Tentativa de mudar o metodo de renderizacao em tempo de execucao");
		}

		this.renderingType = renderingType;
	}

	/**
	 * Retorna a janela do jogo que deve ser utilizada para renderizar o jogo
	 * 
	 * @return A janela do jogo na qual o jogo deve ser renderizado
	 */
	public GameWindow getGameWindow() {
		// Se a janela ainda n�o foi criada, cria uma janela agora
		if (window == null) {
			switch (renderingType) {
			case JAVA2D:
				window = new Java2DGameWindow();
				break;
			case OPENGL_LWJGL:
				window = new LWJGLGameWindow();
				break;
			}
		}

		return window;
	}

	/**
	 * Cria ou retorna um sprite que mostra a imagem que e apontada na classpath
	 * como "ref"
	 * 
	 * @param ref
	 *            A referencia da imagem a ser carregada
	 * @return O sprite que pode ser desenhado dentro graphics context atual.
	 */
	public Sprite getSprite(String ref) {
		if (window == null) {
			throw new RuntimeException("Tentativa de retornar um sprite antes que a janela do jogo seja criada");
		}

		switch (renderingType) {
		case JAVA2D: {
			return Java2DSpriteStore.get().getSprite((Java2DGameWindow) window, ref);
		}
		case OPENGL_LWJGL: {
			return new LWJGLSprite((LWJGLGameWindow) window, ref);
		}
		}

		throw new RuntimeException("Tipo de renderizacao desconhecido: " + renderingType);
	}
}