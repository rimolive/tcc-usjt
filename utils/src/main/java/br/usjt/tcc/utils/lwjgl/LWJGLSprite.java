package br.usjt.tcc.utils.lwjgl;

import br.usjt.tcc.utils.Sprite;
import java.io.IOException;
import org.lwjgl.opengl.GL11;

/**
 * Implementacao de um sprite que utiliza OpenGL e texturas para renderizar uma
 * certa imagem na tela.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class LWJGLSprite implements Sprite {
	
	/** A textura que armazena a imagem da sprite */
	private Texture texture;
	
	/** A largura em pixels da sprite */
	private int width;
	
	/** A altura em pixels da sprite */
	private int height;

	/**
	 * Cria uma nova sprite de uma imagem especifica.
	 * 
	 * @param window
	 *            A janela na qual a sprite sera mostrada
	 * @param ref
	 *            A referencia para a imagem na qual a sprite sera baseada
	 */
	public LWJGLSprite(LWJGLGameWindow window, String ref) {
		try {
			texture = window.getTextureLoader().getTexture(ref);

			width = texture.getImageWidth();
			height = texture.getImageHeight();
		} catch (IOException e) {
			// Se nao encontrar a imagem da sprite, faz um dump
			// do erro no console.
			System.err.println("Nao foi possival carregar textura: " + ref);
			System.exit(0);
		}
	}

	/**
	 * Retrona a largura da sprite em pixels
	 * 
	 * @return A largura da sprite em pixels
	 */
	public int getWidth() {
		return texture.getImageWidth();
	}

	/**
	 * Retorna a altura da sprite em pixels
	 * 
	 * @return A altura da sprite em pixels
	 */
	public int getHeight() {
		return texture.getImageHeight();
	}

	/**
	 * Desenha a sprite em uma determinada posicao
	 * 
	 * @param x
	 *            A posicao em x na qual sera desenhada a sprite
	 * @param y
	 *            A posicao em y na qual sera desenhada a sprite
	 */
	public void draw(int x, int y) {
		// Armazena a matriz modelo atual
		GL11.glPushMatrix();

		// Faz um bind para a textura apropriada para a sprite
		texture.bind();

		// Traduz a posicao correta a se prepara para desenhar
		GL11.glTranslatef(x, y, 0);
		GL11.glColor3f(1, 1, 1);

		// Desenha um quadrado texturizado de acordo com a sprite
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(0, texture.getHeight());
			GL11.glVertex2f(0, height);
			GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
			GL11.glVertex2f(width, height);
			GL11.glTexCoord2f(texture.getWidth(), 0);
			GL11.glVertex2f(width, 0);
		}
		GL11.glEnd();

		// Recupera a matriz modelo para evitar confus�o
		GL11.glPopMatrix();
	}

	/**
	 *
	 * @param x
	 *            A posicao em x na qual sera desenhada a sprite
	 * @param y
	 *            A posicao em y na qual sera desenhada a sprite
	 * @param theta
	 *            O angulo de inclinacao em radianos
	 */
	public void draw(int x, int y, double theta) {
		// Armazena a matriz modelo atual
		GL11.glPushMatrix();

		// Faz um bind para a textura apropriada para a sprite
		texture.bind();

		// Traduz a posicao correta a se prepara para desenhar
		GL11.glTranslatef(x, y, 0);
		GL11.glColor3f(1, 1, 1);

		float rotation = (float) (theta / 50.f);
		float newX = (float) Math.sin(rotation);
		float newY = (float) -Math.cos(rotation);

		// Desenha um quadrado texturizado de acordo com a sprite
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2d(0, 0);
			GL11.glVertex2d(0, 0);
			GL11.glTexCoord2d(0, newY);
			GL11.glVertex2d(0, newY);
			GL11.glTexCoord2d(newX, newY);
			GL11.glVertex2d(newX, newY);
			GL11.glTexCoord2d(newX, 0);
			GL11.glVertex2d(newX, 0);
		}
		GL11.glEnd();

		// Recupera a matriz modelo para evitar confusao
		GL11.glPopMatrix();
	}

}