package br.usjt.tcc.utils.input;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Um sistema de manipulacao do teclado, utilizados para manipular "todas" as
 * teclas do teclado, desde que sejam mapeadas aqui. Por motivos de tempo, foram
 * mapeadas apenas as teclas realmente utilizadas nos jogos.
 *
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Keyboard {

	/** O estado das teclas no teclado */
	private static boolean[] keys = new boolean[1024];

	/**
	 * Inicializa o sistema de manipulacao do teclado
	 */
	public static void init() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new KeyHandler(), AWTEvent.KEY_EVENT_MASK);
	}

	/**
	 * Inicializa o sistema de manipulacao do teclado.
	 * 
	 * @param c
	 *            O componente que sera monitorado
	 */
	public static void init(Component c) {
		c.addKeyListener(new KeyHandler());
	}

	/**
	 * Checa se uma tecla especifica e pressionada.
	 * 
	 * @param key
	 *            O codigo da tecla para checar (definido na classe KeyEvent)
	 * @return True se a tecla foi pressionada
	 */
	public static boolean isPressed(int key) {
		return keys[key];
	}

	/**
	 * Seta o estado da tecla.
	 * 
	 * @param key
	 *            O codigo da tecla para setar
	 * @param pressed
	 *            O novo estado da tecla
	 */
	public static void setPressed(int key, boolean pressed) {
		keys[key] = pressed;
	}

	/**
	 * Uma classe interna para responder as telas presionadas em uma escala
	 * global.
	 * 
	 * @author Diego Bolzan da Silva
	 * @author Henrique Silva Conceicao
	 * @author Jayson Jun Silva Sumi
	 * @author Leandro Capinan Scheiner
	 * @author Ricardo Martinelli de Oliveira
	 */
	private static class KeyHandler extends KeyAdapter implements AWTEventListener {
		/**
		 * Notificacao de uma tecla pressionada.
		 * 
		 * @param e
		 *            Os detalhes do evento
		 */
		public void keyPressed(KeyEvent e) {
			if (e.isConsumed()) {
				return;
			}
			keys[e.getKeyCode()] = true;
		}

		/**
		 * Notificacao de uma tecla solta.
		 * 
		 * @param e
		 *            Os detalhes do evento
		 */
		public void keyReleased(KeyEvent e) {
			if (e.isConsumed()) {
				return;
			}

			KeyEvent nextPress = (KeyEvent) Toolkit.getDefaultToolkit().getSystemEventQueue()
					.peekEvent(KeyEvent.KEY_PRESSED);

			if ((nextPress == null) || (nextPress.getWhen() != e.getWhen())) {
				keys[e.getKeyCode()] = false;
			}

		}

		/**
		 * Notificacao de que um evento ocorreu no sistema de eventos AWT.
		 * 
		 * @param e
		 *            Os detalhes do evento
		 */
		public void eventDispatched(AWTEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				keyPressed((KeyEvent) e);
			}
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				keyReleased((KeyEvent) e);
			}
		}
	}
}
