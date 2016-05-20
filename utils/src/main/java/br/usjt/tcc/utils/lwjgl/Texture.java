package br.usjt.tcc.utils.lwjgl;

import org.lwjgl.opengl.GL11;

/**
 * A textura a ser utilizada com JOGL. Este objeto e responsavel por manter a
 * trilha de uma dada textura OpenGL e por calcular as coordenadas do mapeamento
 * da texturizacao da imagem inteira.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Texture {
	
	/** O tipo de "alvo" GL */
	private int target;
	
	/** O ID da textura GL */
	private int textureID;
	
	/** A altura da imagem */
	private int height;
	
	/** A largura da imagem */
	private int width;
	
	/** A largura da textura */
	private int texWidth;
	
	/** A altura da textura */
	private int texHeight;
	
	/** O fator largura da imagem para a textura */
	private float widthRatio;
	
	/** O fator altura da imagem para a textura */
	private float heightRatio;

	/**
	 * Cria uma nova textura
	 *
	 * @param target
	 *            O "alvo" GL
	 * @param textureID
	 *            O ID da textura GL
	 */
	public Texture(int target, int textureID) {
		this.target = target;
		this.textureID = textureID;
	}

	/**
	 * Faz um bind do contexto GL especificado para a textura
	 */
	public void bind() {
		GL11.glBindTexture(target, textureID);
	}

	/**
	 * Seta a altura da imagem
	 * 
	 * @param height
	 *            A altura da imagem
	 * @uml.property name="height"
	 */
	public void setHeight(int height) {
		this.height = height;
		setHeight();
	}

	/**
	 * Seta a largura da imagem
	 * 
	 * @param width
	 *            A largura da imagem
	 * @uml.property name="width"
	 */
	public void setWidth(int width) {
		this.width = width;
		setWidth();
	}

	/**
	 * Retorna a altura da imagem original
	 *
	 * @return A altura da imagem original
	 */
	public int getImageHeight() {
		return height;
	}

	/**
	 * Retorna a largura da imagem original
	 *
	 * @return A largura da imagem original
	 */
	public int getImageWidth() {
		return width;
	}

	/**
	 * Retorna a altura da textura fisica
	 * 
	 * @return A altura textura fisica
	 * @uml.property name="height"
	 */
	public float getHeight() {
		return heightRatio;
	}

	/**
	 * Retorna a largura da textura fisica
	 * 
	 * @return A largura da textura fisica
	 * @uml.property name="width"
	 */
	public float getWidth() {
		return widthRatio;
	}

	/**
	 * Seta a altura da textura
	 *
	 * @param texHeight
	 *            A altura da textura
	 */
	public void setTextureHeight(int texHeight) {
		this.texHeight = texHeight;
		setHeight();
	}

	/**
	 * Seta a largura da textura
	 *
	 * @param texWidth
	 *            A largura da textura
	 */
	public void setTextureWidth(int texWidth) {
		this.texWidth = texWidth;
		setWidth();
	}

	/**
	 * Seta a altura da textura. Isto ira atualizar o fator tambem.
	 */
	private void setHeight() {
		if (texHeight != 0) {
			heightRatio = ((float) height) / texHeight;
		}
	}

	/**
	 * Seta a largura da textura. Isto ira atualizar o fator tambem.
	 */
	private void setWidth() {
		if (texWidth != 0) {
			widthRatio = ((float) width) / texWidth;
		}
	}
}
