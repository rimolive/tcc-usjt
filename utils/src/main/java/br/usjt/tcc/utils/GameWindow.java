package br.usjt.tcc.utils;

import java.awt.Color;

/**
 * A janela na qual o jogo sera mostrado. Esta interface expoe apenas o
 * suficiente para que a logica do jogo possa interagir, enquanto ainda e
 * mantida a abstracao longe de qualquer implementacao fisica de janelamento,
 * ex. Java2D, OpenGL.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public interface GameWindow {

	/**
	 * Seta o titulo da janela do jogo
	 * 
	 * @param title
	 *            O novo titulo da janela do jogo
	 */
	public void setTitle(String title);

	/**
	 * Seta a resolucao da tela
	 * 
	 * @param x
	 *            A nova resolucao em x da tela
	 * @param y
	 *            A nova resolucao em y da tela
	 */
	public void setResolution(int x, int y);

	/**
	 * Inicia a janela do jogo, renderizando a tela
	 */
	public void startRendering();

	/**
	 * Seta o callback que deve ser notificado dos eventos da janela.
	 * 
	 * @param callback
	 *            O callbak que deve ser notificado dos eventos da janela
	 */
	public void setGameWindowCallback(GameWindowCallback callback);

	/**
	 * Checa se uma tecla foi pressionada
	 * 
	 * @param keyCode
	 *            O codigo da tecla associada com a chave para checar
	 * @return True se a tecla foi pressionada
	 */
	public boolean isKeyPressed(int keyCode);

	/**
	 * Seta a cor de fundo da tela
	 * 
	 * @param c
	 */
	public void setBackgroundColor(Color c);

	/**
	 * Retorna a dor de fundo da tela
	 * 
	 * @return
	 */
	public Color getBackgroundColor();

	/**
	 * Verifica se o botao do mouse foi pressinado
	 * 
	 * @param button
	 * @return
	 */
	public boolean isMousePressed(int button);

	/**
	 * Seta a imagem de fundo da tela
	 * 
	 * @param sprite
	 */
	public void setBackgroundImage(String sprite);

	/**
	 * 
	 * @param message
	 */
	public void showMessage(String message);
}