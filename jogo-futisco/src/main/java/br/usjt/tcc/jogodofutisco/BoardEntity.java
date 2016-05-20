package br.usjt.tcc.jogodofutisco;

import br.usjt.tcc.utils.Entity;

/**
 * Classe que representa a entidade Tabuleiro, onde as pe�as seguem um
 * caminho de acordo com um arquivo que mapeia todas as casas do tabuleiro.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Concei��o
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class BoardEntity extends Entity {
	/** O jogo no qual existe a pe�a */
	@SuppressWarnings("unused")
	private Jogo game;

	/**
	 * Cria uma nova Entidade para representar o tabuleiro do jogo
	 * 
	 * @param game O jogo na qual o tabuleiro ser� criado
	 * @param ref A refer�ncia para o sprite que representa a Entity
	 * @param x A posi��o inicial em x do tabuleiro
	 * @param y A posi��o inicial em y do tabuleiro
	 */
	public BoardEntity(Jogo game, String ref, int x, int y) {
		super(ref, x, y);
		this.game = game;
	}

}
