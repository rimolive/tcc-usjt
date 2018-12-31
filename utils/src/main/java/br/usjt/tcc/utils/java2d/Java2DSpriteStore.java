package br.usjt.tcc.utils.java2d;

import br.usjt.tcc.utils.Sprite;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Um gerenciador de recursos para os sprites do jogo.
 * <p>
 * [singleton]
 * <p>
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Java2DSpriteStore {

	/** A instancia unica da classe */
	private static Java2DSpriteStore single = new Java2DSpriteStore();

	/** O mapa de sprites em cache */
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();

	/**
	 * O construtor padrao se tornou privado para evitar que uma nova instancia
	 * da classe seja construida externamente. Isso foi feito para forcar o
	 * padrao singleton da classe.
	 */
	private Java2DSpriteStore() {
	}

	/**
	 * Retorna a instancia unica da classe.
	 * 
	 * @return A instancia unica da classe
	 */
	public static Java2DSpriteStore get() {
		return single;
	}

	/**
	 * Retorna a sprite do cache
	 * 
	 * @param window
	 *            A janela na qual o sprita sera desenhado
	 * @param ref
	 *            A referencia da imagem a ser utilizada pela sprite
	 * @return A instancia da sprite contendo uma imagem
	 */
	public Sprite getSprite(Java2DGameWindow window, String ref) {
		// Se ja existe a sprite dentro do cache,
		// retorna a sprite ja existente
		if (sprites.get(ref) != null) {
			return (Sprite) sprites.get(ref);
		}

		// Caso contrario, faca a criacao da sprite e armazena-a no cache
		BufferedImage sourceImage = null;

		try {
			// O metodo Classloader.getResource() assegura de que estamos
			// capturando o sprite do lugar certo.
			URL url = this.getClass().getClassLoader().getResource(ref);

			if (url == null) {
				fail("Nao foi possivel encontrar sprite: " + ref);
			}

			// Utiliza a classe ImageIO para ler a imagem
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			fail("Falha ao carregar: " + ref);
		}

		// Cria a imagem grafica do tamanho certo para armezenar a sprite
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		Image image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(), Transparency.BITMASK);

		// Desenha a imagem
		image.getGraphics().drawImage(sourceImage, 0, 0, null);

		// Cria a sprite, adiciona no cache e retorna-o
		Sprite sprite = new Java2DSprite(window, image);
		sprites.put(ref, sprite);

		return sprite;
	}

	/**
	 * Metodo utitario para manipular recursos com falha ao carregar
	 * 
	 * @param message
	 *            A mensagem a ser apresentada em caso de falha
	 */
	private void fail(String message) {
		// Se o recurso nao esta disponivel, o jogo fara um dump
		// da mensagem e saira do jogo.
		System.err.println(message);
		System.exit(0);
	}
}