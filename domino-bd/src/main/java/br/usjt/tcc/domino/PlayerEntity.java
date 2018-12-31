package br.usjt.tcc.domino;

import java.util.Vector;

import br.usjt.tcc.domino.Domino.canMove;
import br.usjt.tcc.utils.Entity;

/**
 * Entidade que representa o jogador. Nela encontra-se a logica de IA do
 * jogador, mas somente se o jogador for controlado pelo computador
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class PlayerEntity extends Entity {

	/** O jogo no qual existe o jogador */
	private Domino game;
	
	/** Vetor que contem as pecas na mao do jogador */
	private Vector<PieceEntity> pieces = new Vector<PieceEntity>(7);
	
	/** Indicador de que a entidade esta sendo controlada pelo jogador */
	private int player;

	/**
	 * Cria uma nova Entity para representar a peca do domino
	 * 
	 * @param game
	 *            O jogo na qual a peca sera criada
	 * @param ref
	 *            A referencia para o sprite que representa a Entity
	 * @param x
	 *            A posicao inicial em x da peca
	 * @param y
	 *            A posicao inicial em y da peca
	 */
	private PlayerEntity(Domino game, String ref, int x, int y) {
		super(ref, x, y);

		this.game = game;
	}

	/**
	 * Cria uma nova Entity para representar a peca do domino
	 * 
	 * @param game
	 *            O jogo na qual a peca sera criada
	 * @param order
	 *            A ordem de visualizacao na tela
	 */
	public PlayerEntity(Domino game, int order) {
		super("sprites/domino/domino_blank.GIF", 0, 0);
		this.player = order;
		switch (order) {
		case 0:
			this.x = game.resolution.width / 2;
			this.y = game.resolution.height - 100;
			break;
		case 1:
			this.x = game.resolution.width - 100;
			this.y = game.resolution.height / 2;
			break;
		case 2:
			this.x = game.resolution.width / 2;
			this.y = 100;
			break;
		case 3:
			this.x = 100;
			this.y = game.resolution.height / 2;
			break;
		default:
			break;
		}

		this.game = game;
	}

	/**
	 * Adiciona as pecas para o jogador utilizar
	 */
	public void addPiece(PieceEntity piece) {
		pieces.add(piece);
	}

	/**
	 * Implementa a logica do jogador. Este metodo e a implementacao fisica da
	 * logica de IA do jogador.
	 */
	public void doLogic() {
		PieceEntity playerPiece = null;
		canMove direction = canMove.DO_NOTHING;
		if (this.player != 0) {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			return;
		}
		// Percorre a mao do jogador para verificar se ha alguma possibilidade
		// de jogada
		for (PieceEntity piece : this.pieces) {
			canMove dir = piece.canMove();
			if ((dir != canMove.DO_NOTHING) && (piece.getLeft() + piece.getRight()) > 0) {
				playerPiece = piece;
				direction = dir;
			}
		}
		if (playerPiece != null) {
			game.skip = 0;
			if (direction == canMove.CAN_BOTH) {
				if (Math.random() > 0.5)
					direction = canMove.CAN_LEFT;
				else
					direction = canMove.CAN_RIGHT;
			}
			switch (direction) {
			case CAN_LEFT:
				playerPiece.movePiece(true);
				((PlayerEntity) this).pieces.removeElement(playerPiece);
				break;
			case CAN_RIGHT:
				playerPiece.movePiece(false);
				((PlayerEntity) this).pieces.removeElement(playerPiece);
				break;
			}
			if (((PlayerEntity) this).pieces.size() == 0) {
				game.window.showMessage("Fim de Jogo. Venceu Jogador " + (this.player + 1));
				game.gameOver = true;
				return;
			}
		} else {
			game.skip++;
			game.window.showMessage("Jogador " + (this.player + 1) + " passa a jogada");
			if (game.skip >= 4) {
				game.window.showMessage("Fim de jogo. Empate");
				game.gameOver = true;
				game.gameInitiated = false;
				return;
			}
		}
		game.nextTurn = (this.player + 1) % 4;
	}

	/**
	 * Retorna as pecas do jogador.
	 * 
	 * @return Um vetor com todas as pecas do jogador
	 */
	public Vector<PieceEntity> getPieces() {
		return pieces;
	}

	public void collidedWith(Entity other) {
		// Do Nothing
	}
	
}
