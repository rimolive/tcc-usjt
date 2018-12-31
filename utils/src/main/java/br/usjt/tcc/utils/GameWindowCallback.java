package br.usjt.tcc.utils;

/**
 * Uma interface que descreve qualquer classe que deseja ser notificada como a
 * janela do jogo renderiza.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public interface GameWindowCallback {
	/**
	 * Notificacao que o jogo deve inicializar quaisquer recursos necessarios
	 * para utilizacao. Essa operacao inclui carregar os sprites.
	 */
	public void initialise();

	/**
	 * Notificacao que a tela esta sendo renderizada. A implementacao deve
	 * renderizar o cenario e atualizar a logica do jogo.
	 */
	public void frameRendering();

	/**
	 * Notificacao que a janela do jogo foi fechada.
	 */
	public void windowClosed();
}