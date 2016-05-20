package br.usjt.tcc.utils.java2d;

import br.usjt.tcc.utils.Sprite;
import java.awt.Image;

/**
 * A sprite a ser desenhada na tela. Lembrando que um sprite nao possui
 * informacoes de estado, apenas sua imagem e sua dimensao. Isso nos permite
 * utilizar uma unica sprite para representar multiplas entidades na tela, sem
 * ter que armazenar diversas copias da imagem.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Java2DSprite implements Sprite {

	/** A imagem a ser desenhada pela sprite */
	private Image image;

	/** A janela na qual a sprite sera desenhada */
	private Java2DGameWindow window;

	/**
	 * Cria uma nova sprite baseado na imagem.
	 * 
	 * @param window
	 *            A janela do jogo na qual o sprite sera desenhado
	 * @param image
	 *            A imagem a ser carregada pela sprite
	 */
	public Java2DSprite(Java2DGameWindow window, Image image) {
		this.image = image;
		this.window = window;
	}

	/**
	 * Retorna a largura do sprite desenhado
	 * 
	 * @return A largura em pixels da sprite
	 */
	public int getWidth() {
		return image.getWidth(null);
	}

	/**
	 * Retorna a altura do sprite desenhado
	 * 
	 * @return A altura em pixels da sprite
	 */
	public int getHeight() {
		return image.getHeight(null);
	}

	/**
	 * Desenha a sprite dentro do graphics context fornecido.
	 * 
	 * @param x
	 *            A posicao em x na qual sera desenhado a imagem
	 * @param y
	 *            A posicao em y na qual sera desenhado a imagem
	 */
	public void draw(int x, int y) {
		window.getDrawGraphics().drawImage(image, x, y, null);
	}

	/**
	 * Desenha a sprite dentro do graphics context fornecido. Este metodo
	 * renderiza a imagem rotacionada de acordo com o angulo fornecido em
	 * radianos.
	 * 
	 * @param x
	 *            A posicao em x na qual sera desenhado a imagem
	 * @param y
	 *            A posicao em y na qual sera desenhado a imagem
	 * @param theta
	 *            O angulo a ser rotacionado a imagem expresso em radianos
	 */
	public void draw(int x, int y, double theta) {
		window.getDrawGraphics().rotate(theta, x, y);
		window.getDrawGraphics().drawImage(image, x, y, null);
		window.getDrawGraphics().rotate(-theta, x, y);
	}
}