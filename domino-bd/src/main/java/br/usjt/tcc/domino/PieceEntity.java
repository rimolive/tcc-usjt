package br.usjt.tcc.domino;

import br.usjt.tcc.domino.Domino.Direction;
import br.usjt.tcc.domino.Domino.canMove;
import br.usjt.tcc.utils.Entity;
import br.usjt.tcc.utils.ResourceFactory;

/**
 * A entidade que representa a peca do domino. Nela esta contida todas as
 * caracteristicas das pecas de domino.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class PieceEntity extends Entity {

	/** O jogo no qual existe a peca */
	private Domino game;

	/** Numero marcado no lado esquerdo da peca */
	private int left;

	/** Numero marcado no lado direito da peca */
	private int right;

	/** Indicador para qual jogador a peca pertence */
	private int player = -1;

	/** Posicao da peca: Horizontal */
	public static final double HORIZONTAL = 90.00;

	/** Posicao da peca: Vertical */
	public static final double VERTICAL = 0.00;

	/** Valores possiveis para as combinacoes de pecas */
	public static enum Values {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX
	};

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
	@SuppressWarnings("unused")
	private PieceEntity(Domino game, String ref, int x, int y) {
		super(ref, x, y);
		this.game = game;
	}

	/**
	 * Cria uma entidade personalizada de acordo com o tipo da peca a ser criada
	 * 
	 * @param game
	 *            O jogo na qual pertence a peca
	 * @param left
	 *            O numero correspondente ao lado esquerdo da peca
	 * @param right
	 *            O numero correspondente ao lado direito da peca
	 * @param x
	 *            A posicao inicial em x da peca
	 * @param y
	 *            A poscicao inicial em y da peca
	 */
	public PieceEntity(Domino game, int left, int right, int x, int y) {
		super("sprites/domino/domino_blank.GIF", x, y);
		this.left = left;
		this.right = right;

		StringBuffer buffer = new StringBuffer();
		buffer.append("sprites/domino/l");
		buffer.append(left);
		buffer.append('r');
		buffer.append(right);
		buffer.append(".GIF");
		this.sprite = ResourceFactory.get().getSprite(buffer.toString());

		this.game = game;
	}

	/**
	 * Metodo para indicar de uma peca e double ou nao.
	 * 
	 * @return True se a peca for double
	 */
	public boolean isDoubleSided() {
		return left == right;
	}

	/**
	 * Retorna o valor da peca no lado esquerdo.
	 * 
	 * @return o valor da peca no lado esquerdo
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * Retorna o valor da peca no lado direito.
	 * 
	 * @return o valor da peca no lado direito
	 */
	public int getRight() {
		return right;
	}

	/**
	 * Seta os atributos basicos para o jogo.
	 * 
	 * @param order
	 *            A ordem do jogador na tela
	 * @param quantity
	 *            A quantidade de pecas do jogador
	 */
	public void setAttributes(int order, int quantity) {
		this.player = order;
		switch (order) {
		case 0:
			this.x = ((game.resolution.getWidth() / 2) - 150.00) + quantity * 60.00;
			this.y = game.resolution.getHeight() - 100;
			this.theta = Math.toRadians(PieceEntity.HORIZONTAL);
			break;
		case 1:
			this.x = game.resolution.getWidth() - 100;
			this.y = 100.00 + quantity * 60.00;
			this.theta = Math.toRadians(PieceEntity.VERTICAL);
			this.setVisible(false);
			break;
		case 2:
			this.x = ((game.resolution.getWidth() / 2) - 150.00) + quantity * 60.00;
			this.y = 5.00;
			this.theta = Math.toRadians(PieceEntity.HORIZONTAL);
			this.setVisible(false);
			break;
		case 3:
			this.x = 5.00;
			this.y = 100.00 + quantity * 60.00;
			this.theta = Math.toRadians(PieceEntity.VERTICAL);
			this.setVisible(false);
			break;
		default:
			break;
		}
	}

	/**
	 * Indica se a peca pertence a algum jogador ou nao.
	 */
	public boolean isPlayerPiece() {
		if (this.player != -1) {
			return true;
		}
		return false;
	}

	/**
	 * Seta a posicao da peca na mesa (O angulo em degrees)
	 * 
	 * @param p
	 *            O valor do angulo em degrees
	 */
	public void setPosition(double p) {
		this.theta = Math.toRadians(p);
	}

	/**
	 * Seta a posicao em x da peca.
	 * 
	 * @param x
	 *            A posicao x da peca
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Seta a posicao em y da peca.
	 * 
	 * @param y
	 *            A posicao y da peca
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Seta a visibilidade da peca.
	 * 
	 * @param visible
	 *            True se a peca deve ficar visivel
	 */
	public void setVisible(boolean visible) {
		if (visible) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("sprites/domino/l");
			buffer.append(this.left);
			buffer.append('r');
			buffer.append(this.right);
			buffer.append(".GIF");
			this.sprite = ResourceFactory.get().getSprite(buffer.toString());
		} else {
			this.sprite = ResourceFactory.get().getSprite("sprites/domino/domino_blank.GIF");
		}
	}

	/**
	 * Metodo que troca os valores da peca de lugar.
	 */
	public void swap() {
		int aux = -1;
		aux = this.left;
		this.left = this.right;
		this.right = aux;
		this.setVisible(true);
	}

	/**
	 * Metodo que analisa as possibilidades de jogada da peca.
	 * 
	 * @param piece
	 *            A peca a ser analisada
	 * @return A conclusao da jogada
	 */
	public canMove canMove() {
		boolean left = false;
		boolean right = false;
		// Caso o jogo esteja iniciando agora, o jogador deve jogar uma peca
		// double
		// 6.
		if (!game.gameInitiated && this.isDoubleSided() && this.left == 6) {
			return canMove.CAN_BOTH;
		}
		// Compara os valores da peca com as extremidades da mesa
		left = game.extremeLeft == this.left || game.extremeLeft == this.right;
		right = game.extremeRight == this.left || game.extremeRight == this.right;
		// Caso a peca sirva para ambos os lados
		if (left && right) {
			return canMove.CAN_BOTH;
		}
		// Caso a peca sirva para a extremidade esquerda da mesa
		if (left) {
			return canMove.CAN_LEFT;
		}
		// Caso a peca sirva para a extremidade direita da mesa
		if (right) {
			return canMove.CAN_RIGHT;
		}
		// Caso a peca nao tem possibilidades de jogada
		return canMove.DO_NOTHING;
	}

	/**
	 * Move a peca do jogador para o centro da mesa. Este metodo posiciona a
	 * peca imediatamente na extremidade que houver disponivel e verifica a
	 * disposicao das pecas na mesa para poder mudar a direcao da mesa.
	 * 
	 * @param piece
	 *            A peca a ser colocada na mesa
	 * @param left
	 *            True para indicar que a peca sera colocada no lado esquerdo da
	 *            mesa
	 */
	public void movePiece(boolean left) {
		this.game.sound.playSound("sound/winAquariumMenuPopUp.wav");
		this.setVisible(true);
		if (!game.gameInitiated && this.isDoubleSided()) {
			game.extremeLeft = game.extremeRight = this.left;
			this.setPosition(PieceEntity.HORIZONTAL);
			this.setX(game.resolution.width / 2);
			this.setY((game.resolution.height / 2) - (this.sprite.getWidth() / 2));
			game.LEFT.width = this.getX() - this.sprite.getHeight();
			game.LEFT.height = this.getY() + ((this.sprite.getWidth() / 2) - (this.sprite.getHeight() / 2));
			game.DIR_LEFT = Direction.RIGHT_TO_LEFT;
			game.RIGHT.width = this.getX();
			game.RIGHT.height = this.getY() + ((this.sprite.getWidth() / 2) - (this.sprite.getHeight() / 2));
			game.DIR_RIGHT = Direction.LEFT_TO_RIGHT;
			game.gameInitiated = true;
		} else {
			int x;
			int y;
			Direction dir;
			int value;
			boolean canTurn;
			if (left) {
				x = game.LEFT.width;
				y = game.LEFT.height;
				dir = game.DIR_LEFT;
				value = game.extremeLeft;
				canTurn = game.turnLeft;
			} else {
				x = game.RIGHT.width;
				y = game.RIGHT.height;
				dir = game.DIR_RIGHT;
				value = game.extremeRight;
				canTurn = game.turnRight;
			}
			switch (dir) {
			case LEFT_TO_RIGHT:
				if (this.isDoubleSided()) {
					if (canTurn) {
						this.setPosition(PieceEntity.VERTICAL);
						this.setX(x);
						this.setY(y);
						x = this.getX() + this.sprite.getWidth();
						y = this.getY();
					} else {
						this.setPosition(PieceEntity.HORIZONTAL);
						this.setX(x + this.sprite.getHeight());
						this.setY(y - (this.sprite.getHeight() / 2));
						x = this.getX();
						y = this.getY() + (this.sprite.getHeight() / 2);
						if ((x + this.sprite.getWidth()) > game.MAX.width) {
							dir = Direction.UP_TO_DOWN;
							x = this.getX();
							y = this.getY() + this.sprite.getWidth();
						}
					}
				} else {
					if (this.getLeft() != value) {
						value = this.getLeft();
						this.swap();
					} else {
						value = this.getRight();
					}
					this.setPosition(PieceEntity.VERTICAL);
					this.setX(x);
					this.setY(y);
					x = this.getX() + (this.sprite.getWidth());
					if ((x + this.sprite.getWidth()) > game.MAX.width) {
						dir = Direction.UP_TO_DOWN;
						x = this.getX() + this.sprite.getWidth();
						y = this.getY() + (this.sprite.getHeight());
					}
				}
				break;
			case UP_TO_DOWN:
				if (this.isDoubleSided()) {
					if (canTurn) {
						this.setPosition(PieceEntity.HORIZONTAL);
						this.setX(x);
						this.setY(y);
						x = this.getX();
						y = this.getY() + (this.sprite.getWidth());
					} else {
						this.setPosition(PieceEntity.VERTICAL);
						this.setX(x - (this.sprite.getWidth() / 2) - (this.sprite.getHeight() / 2));
						this.setY(y);
						x = this.getX() + (this.sprite.getHeight() / 2);
						y = this.getY() + (this.sprite.getWidth() / 2) + (this.sprite.getHeight() / 2);
						if ((y + this.sprite.getHeight()) > game.MAX.height) {
							dir = Direction.RIGHT_TO_LEFT;
							x = this.getX();
							y = this.getY();
						}
					}
				} else {
					if (this.getLeft() != value) {
						value = this.getLeft();
						this.swap();
					} else {
						value = this.getRight();
					}
					this.setPosition(PieceEntity.HORIZONTAL);
					this.setX(x);
					this.setY(y);
					x = this.getX();
					y = this.getY() + (this.sprite.getWidth());
					if ((y + this.sprite.getHeight()) > game.MAX.height) {
						dir = Direction.RIGHT_TO_LEFT;
						x = this.getX();
						y = this.getY() + (this.sprite.getWidth());
					}
				}
				break;
			case DOWN_TO_UP:
				if (this.isDoubleSided()) {
					if (canTurn) {
						this.setPosition(PieceEntity.HORIZONTAL);
						this.setX(x);
						this.setY(y - this.sprite.getWidth());
						x = this.getX();
						y = this.getY();
					} else {
						this.setPosition(PieceEntity.VERTICAL);
						this.setX(x - (this.sprite.getWidth() / 2) - (this.sprite.getHeight() / 2));
						this.setY(y - this.sprite.getHeight());
						x = this.getX() + (this.sprite.getWidth() / 2) + (this.sprite.getHeight() / 2);
						y = this.getY();
						if (y < game.MIN.height) {
							dir = Direction.LEFT_TO_RIGHT;
							x = this.getX() + this.sprite.getWidth();
							y = this.getY();
						}
					}
				} else {
					if (this.getLeft() == value) {
						value = this.getRight();
						this.swap();
					} else {
						value = this.getLeft();
					}
					this.setPosition(PieceEntity.HORIZONTAL);
					this.setX(x);
					this.setY(y - this.sprite.getWidth());
					x = this.getX();
					y = this.getY();
					if (y < game.MIN.height) {
						dir = Direction.LEFT_TO_RIGHT;
						x = this.getX();
						y = this.getY();
					}
				}
				break;
			case RIGHT_TO_LEFT:
				if (this.isDoubleSided()) {
					if (canTurn) {
						this.setPosition(PieceEntity.VERTICAL);
						this.setX(x - this.sprite.getWidth());
						this.setY(y);
						x = this.getX();
						y = this.getY();
					} else {
						this.setPosition(PieceEntity.HORIZONTAL);
						this.setX(x);
						this.setY(y - (this.sprite.getHeight() / 2));
						x = this.getX() - this.sprite.getHeight();
						y = this.getY() + (this.sprite.getHeight() / 2);
						if ((x - this.sprite.getHeight()) < game.MIN.width) {
							dir = Direction.DOWN_TO_UP;
							x = this.getX();
							y = this.getY();
						}
					}
				} else {
					if (this.getLeft() == value) {
						value = this.getRight();
						this.swap();
					} else {
						value = this.getLeft();
					}
					this.setPosition(PieceEntity.VERTICAL);
					this.setX(x - this.sprite.getWidth());
					this.setY(y);
					x = this.getX();
					y = this.getY();
					if ((x - this.sprite.getHeight()) < game.MIN.width) {
						dir = Direction.DOWN_TO_UP;
						x = this.getX() + 2;
						y = this.getY() + this.sprite.getHeight();
					}
				}
				break;
			}
			if (left) {
				game.turnLeft = dir != game.DIR_LEFT;
				game.LEFT.width = x;
				game.LEFT.height = y;
				game.DIR_LEFT = dir;
				game.extremeLeft = value;
			} else {
				game.turnRight = dir != game.DIR_RIGHT;
				game.RIGHT.width = x;
				game.RIGHT.height = y;
				game.DIR_RIGHT = dir;
				game.extremeRight = value;
			}
		}
	}
}