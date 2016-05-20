package br.usjt.tcc.jogodofutisco;

import br.usjt.tcc.utils.Entity;
import br.usjt.tcc.utils.ResourceFactory;

/**
 * Entidade que representa o jogador. Nela encontra-se a lógica de IA do
 * jogador, mas somente se o jogador for controlado pelo computador
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceição
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class PlayerEntity extends Entity {
	/** O jogo no qual existe o jogador */
	@SuppressWarnings("unused")
	private Jogo game;
	/** Indicador de que a entidade está sendo controlada pelo jogador */
	@SuppressWarnings("unused")
	private int player;
	/** */
	private int space = 0;

	/**
	 * Cria uma nova Entity para representar o jogador.
	 * 
	 * @param game O jogo na qual a peça será criada
	 * @param ref A referência para o sprite que representa a Entidade
	 * @param x A posição inicial em x da peça
	 * @param y A posição inicial em y da peça
	 */
	@SuppressWarnings("unused")
	private PlayerEntity(Jogo game, String ref, int x, int y) {
		super(ref, x, y);

		this.game = game;
	}

	/**
	 * Cria uma nova Entidade para representar o jogador.
	 * 
	 * @param game O jogo na qual a peça será criada
	 * @param order A ordem de visualização na tela
	 */
	public PlayerEntity(Jogo game, int order) {
		super("sprites/domino/domino_blank.GIF", 0, 0);
		final String BASE_PATH = "sprites/jogodofutisco/";
		this.player = order;

		switch (order) {
		case 0:
			this.sprite = ResourceFactory.get().getSprite(BASE_PATH + "p0.jpg");
			break;
		case 1:
			this.sprite = ResourceFactory.get().getSprite(BASE_PATH + "p1.jpg");
			break;
		case 2:
			this.sprite = ResourceFactory.get().getSprite(BASE_PATH + "p2.jpg");
			break;
		case 3:
			this.sprite = ResourceFactory.get().getSprite(BASE_PATH + "p3.jpg");
			break;
		default:
			break;
		}

		this.game = game;
	}

	/**
	 * Implementa a lógica do jogador. Este método é a implementação física da
	 * lógica de IA do jogador.
	 */
	public void doLogic() {
		// TODO Implementar a lógica do jogo
	}

	/**
	 * Método que redesenha o jogador no tabuleiro de acordo com o número da
	 * casa e com o número do jogador. 
	 *  
	 * @param coord
	 * @param space
	 * @param player
	 */
	public void draw(SpaceCoord coord, int space, int player) {
		this.x = coord.getCoord(space, player).width;
		this.y = coord.getCoord(space, player).height;
		this.draw();
	}

	/**
	 * 
	 * @return
	 */
	public int getPlayer() {
		return player;
	}

	/**
	 * 
	 * @return
	 */
	public int getSpace() {
		return space;
	}

	/**
	 * 
	 * @param space
	 */
	public void setSpace(int space) {
		this.space = space;
	}
}
