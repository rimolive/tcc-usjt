package br.usjt.tcc.utils.lwjgl;

import java.awt.Color;
import java.awt.event.KeyEvent;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

// import org.lwjgl.input.Keyboard;
// import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import br.usjt.tcc.utils.GameWindow;
import br.usjt.tcc.utils.GameWindowCallback;
import br.usjt.tcc.utils.ResourceFactory;
import br.usjt.tcc.utils.Sprite;
import br.usjt.tcc.utils.xml.Question;

/**
 * Uma implementacao da interface GameWindow utilizando a tecnologia OpenGL
 * (LWJGL) para a renderizacao do cenario. Essa classe tambem e responsavel por
 * monitorar o teclado utilizando AWT.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class LWJGLGameWindow implements GameWindow {

	/** O callback na qual sera notificado dos eventos causados pela janela */
	private GameWindowCallback callback;

	/**
	 * True se o jogo esta no estado "executando", ou seja, se o loop principal
	 * foi iniciado
	 */
	private boolean gameRunning = false;

	/** A largura da tela */
	private int width;

	/** A altura da tela */
	private int height;

	/** The loader responsible for converting images into OpenGL textures */
	private TextureLoader textureLoader= new TextureLoader();

	/**
	 * Title of window, we get it before our window is ready, so store it still
	 * needed
	 */
	@SuppressWarnings("unused")
	private String title;

	@SuppressWarnings("unused")
	private float red;

	@SuppressWarnings("unused")
	private float green;

	@SuppressWarnings("unused")
	private float blue;

	@SuppressWarnings("unused")
	private float alpha = 255.0f;

	/** A sprite que podera ser renderizada no fundo da tela */
	private Sprite background;

	/**
	 * O caminho da sprite que podera ser renderizada no fundo da tela (Caso nao
	 * possa armazenar a sprite)
	 */
	private String back;

	private long window;

	/**
	 * Retorna o acesso ao texture loader que converte imagens em texturas
	 * OpenGL.
	 * 
	 * @return O texture loader
	 */
	TextureLoader getTextureLoader() {
		return textureLoader;
	}

	/**
	 * Seta o titulo da janela de jogo.
	 * 
	 * @param title
	 *            O novo titulo da janela
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Seta a resolucao da janela do jogo.
	 *
	 * @param x
	 *            A largura da janela do jogo
	 * @param y
	 *            A altura da janela do jogo
	 */
	public void setResolution(int x, int y) {
		width = x;
		height = y;
	}

	/**
	 * Inicia o processo de renderizacao. Este metodo ira causar o "redraw" da
	 * tela o mais rapido possivel.
	 */
	public void startRendering() {
		window = glfwCreateWindow(this.width, this.height, this.title, 1, 0);

		// habilita texturas desde que sejam utilizados pelas sprites
		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// desabilita o teste de profundidade do OpenGL
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GL11.glOrtho(0, this.width, this.height, 0, -1, 1);

		if (callback != null) {
			callback.initialise();
		}
		glfwShowWindow(window);

		gameLoop();
	}

	/**
	 * Registra um callback que sera notificado pelos eventos da janela do jogo.
	 *
	 * @param callback
	 *            O callback que sera notificado pelos eventos da janela do
	 *            jogo.
	 */
	public void setGameWindowCallback(GameWindowCallback callback) {
		this.callback = callback;
	}

	/**
	 * Checa se uma tecla especifica foi pressionada.
	 *
	 * @param keyCode
	 *            O codigo associado com a tecla pra checar
	 * @return True se a tecla foi pressionada
	 */
	public boolean isKeyPressed(int keyCode) {
		// O layout de teclas padrao nao foi utilizado,
		// portanto, devemos relaciona-las com o padrao
		// especifico
		// switch (keyCode) {
		// case KeyEvent.VK_SPACE:
		// 	keyCode = Keyboard.KEY_SPACE;
		// 	break;
		// case KeyEvent.VK_LEFT:
		// 	keyCode = Keyboard.KEY_LEFT;
		// 	break;
		// case KeyEvent.VK_RIGHT:
		// 	keyCode = Keyboard.KEY_RIGHT;
		// 	break;
		// }

		// return Keyboard.isKeyDown(keyCode);
		return false;
	}

	/**
	 * Executa o loop principal do jogo. Este metodo mantem a renderizacao do
	 * cenario e solicita para que o callback atualize a tela.
	 */
	private void gameLoop() {
		while (gameRunning) {
			gameRunning = true;
			// Limpa a tela
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glLoadIdentity();

			if (this.background != null && back != null) {
				background.draw(0, 0);
			} else if (back != null && background == null) {
				background = ResourceFactory.get().getSprite(back);
				background.draw(0, 0);
			}

			// Solicita que o subsistema renderize
			if (callback != null) {
				callback.frameRendering();
			}

			// Atualiza o conteudo da janela
			glfwPollEvents();
			glfwSwapBuffers(window);
			
			if (glfwWindowShouldClose(window)) {
			//  || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				gameRunning = false;
				glfwDestroyWindow(window);
			}
		}
	}

	/**
	 * Seta a cor de fundo da janela
	 * 
	 * @param c
	 *            A cor de fundo da janela
	 */
	public void setBackgroundColor(Color c) {
		this.red = c.getRed();
		this.green = c.getGreen();
		this.blue = c.getBlue();
		this.alpha = c.getAlpha();
	}

	/**
	 * Retorna a cor de fundo da janela
	 * 
	 * @return A cor de fundo da janela
	 */
	public Color getBackgroundColor() {
		return null;
	}

	/**
	 * 
	 */
	public boolean isMousePressed(int button) {
		// String strButton = "BUTTON" + button;
		// if (button == Mouse.getButtonIndex(strButton)) {
		// 	return true;
		// }
		return false;
	}

	/**
	 * Mostra a mensagem na tela
	 */
	public void showMessage(String message) {
		// TODO Auto-generated method stub

	}

	/**
	 * Seta a imagem de fundo da janela
	 * 
	 * @param sprite
	 *            O caminho da imagem de fundo da janela
	 */
	public void setBackgroundImage(String sprite) {
		this.background = ResourceFactory.get().getSprite(sprite);
	}

	public void showQuestion(Question question) {
		// TODO Auto-generated method stub
	}
}