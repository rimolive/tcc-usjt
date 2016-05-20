package br.usjt.tcc.jogodofutisco;

import br.usjt.tcc.utils.Entity;
import br.usjt.tcc.utils.ResourceFactory;

public class DiceEntity extends Entity implements Runnable {

	/**
	 * 
	 * @param ref
	 * @param x
	 * @param y
	 */
	public DiceEntity(String ref, int x, int y) {
		super(ref, x, y);
	}

	/**
	 * 
	 * @param run
	 * @return
	 */
	public int rollDice() {
		int value = (int)(Math.random() * 6);
		if(value == 0) {
			value = 1;
		}
		
		StringBuffer spritePath = new StringBuffer();
		spritePath.append( "sprites/jogodofutisco/" );
		spritePath.append( value );
		spritePath.append( ".gif" );
		
		this.sprite = ResourceFactory.get().getSprite(spritePath.toString());
		return value;
	}

	public void run() {
		int value = (int)(Math.random() * 6);
		if(value < 0) {
			
		}
	}
}
