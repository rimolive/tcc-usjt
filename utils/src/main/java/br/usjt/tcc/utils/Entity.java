package br.usjt.tcc.utils;

import java.awt.Rectangle;

/**
 * Uma entidade representa qualquer elemento que aparece no jogo. A entidade e
 * responsavel por resolver as colisoes e movimentacoes baseado em um conjunto 
 * de propriedades definidas ou pela subclasse ou externamente. O posicionamento
 * da entidade esta expressa em double. Pode ate soar estranho visto que o pixel
 * e expresso em inteiros(integer). Entretanto, ao utilizar o valor expresso em
 * double podemos movimentar uma entidade meio pixel para qualquer direcao, por
 * exemplo, o que podemos tornar a movimentacao do jogo mais precisa.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public abstract class Entity {
	/** A posicao atual da entidade no eixo x */
	protected double x;
	/** A posicao atual da entidade no eixo y */
	protected double y;
	/** O {@ref Sprite} que representa a entidade */
	protected Sprite sprite;
	/** A velocidade atual da entidade horizontalmente (pixels/seg) */
	protected double dx;
	/** A velocidade atual da entidade verticalmente (pixels/seg) */
	protected double dy;
	/** A rotacao utilizada para desenhar a imagem */
	protected double theta = 0;
	/** O retangulo utilizado para resolucao de conflitos */
	private Rectangle me = new Rectangle();
	/**
	 * O retangulo utilizado por outras entidades para resolucao de conflitos
	 */
	private Rectangle him = new Rectangle();

	/**
	 * Constroi uma entidade baseada em um sprite e na posicao.
	 * 
	 * @param ref
	 *            A referencia da imagem a ser mostrada na entidade
	 * @param x
	 *            A posicao x inicial da entidade
	 * @param y
	 *            A posicao y inicial da entidade
	 */
	public Entity(String ref, int x, int y) {
		this.sprite = ResourceFactory.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}

	/**
	 * Requisicao que a entidade mova-se maseado em uma determinada quantidade
	 * de tempo decorrido.
	 * 
	 * @param delta
	 *            A quantidade de tempo decorrido em milisegundos
	 */
	public void move(long delta) {
		// update the location of the entity based on move speeds
		x += (delta * dx) / 1000;
		y += (delta * dy) / 1000;
	}

	/**
	 * Seta a velocidade horizontal da entidade
	 * 
	 * @param dx
	 *            A velocidade horizontal da entidade (pixels/seg)
	 */
	public void setHorizontalMovement(double dx) {
		this.dx = dx;
	}

	/**
	 * Seta a velocidade vertical da entidade
	 * 
	 * @param dy
	 *            A velocidade vertical da entidade (pixels/seg)
	 */
	public void setVerticalMovement(double dy) {
		this.dy = dy;
	}

	/**
	 * Retorna a velocidade horizontal da entidade
	 * 
	 * @return A velocidade horizontal da entidade (pixels/seg)
	 */
	public double getHorizontalMovement() {
		return dx;
	}

	/**
	 * Retorna a velocidade vertical da entidade
	 * 
	 * @return A velocidade vertical da entidade (pixels/seg)
	 */
	public double getVerticalMovement() {
		return dy;
	}

	/**
	 * Desenha a entidade dentro do graphics context fornecido
	 */
	public void draw() {
		sprite.draw((int) x, (int) y, (double) theta);
	}

	/**
	 * Realiza a logica associada na entidade. Este metodo sera chamado
	 * periodicamente baseado nos eventos do jogo.
	 */
	public abstract void doLogic();

	/**
	 * Retorna a posicao no eixo x da entidade
	 * 
	 * @return A posicao no eixo x da entidade
	 * @uml.property name="x"
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * Retorna a posicao no eixo y da entidade
	 * 
	 * @return A posicao no eixo y da entidade
	 * @uml.property name="y"
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * Checa se a entiddade colidiu-se com outra.
	 * 
	 * @param other
	 *            A outra entidade para checar a colisao
	 * @return True se a entidade colidiu-se com outra
	 */
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());

		return me.intersects(him);
	}

	/**
	 * Notificacao de que a entidade colidiu-se com outra.
	 * 
	 * @param other
	 *            A entidade na qual esta colidiu-se
	 */
	public abstract void collidedWith(Entity other);
	
}