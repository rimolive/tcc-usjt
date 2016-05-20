package br.usjt.tcc.utils;

/**
 * Um sprite e uma imagem a ser mostrada na tela. Lembrando que um sprite nao
 * possui informacaes de estado, apenas sua imagem e sua dimensao. Isso nos
 * permite utilizar uma unica sprite para representar multiplas entidades na
 * tela, sem ter que armazenar diversas copias da imagem.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public interface Sprite {

	/**
	 * Retorna a largura do sprite
	 * 
	 * @return A largura em pixels da sprite
	 */
	public int getWidth();

	/**
	 * Retorna a altura do sprite
	 * 
	 * @return A altura em pixels da sprite
	 */
	public int getHeight();

	/**
	 * Desenha a sprite dentro do graphics context fornecido
	 * 
	 * @param x
	 *            A posicao em x na qual sera desenhado o sprite
	 * @param y
	 *            A posicao em y na qual sera desenhado o sprite
	 */
	public void draw(int x, int y);

	/**
	 * Desenha a sprite dentro do graphics context fornecido. A sprite pode ser
	 * rotacionada informando o valor de theta maior que zero.
	 * 
	 * @param x
	 *            A posicao em x na qual sera desenhado o sprite
	 * @param y
	 *            A posicao em y na qual sera desenhado o sprite
	 * @param theta
	 *            Inclinacao da Sprite em radians
	 */
	public void draw(int x, int y, double theta);
}