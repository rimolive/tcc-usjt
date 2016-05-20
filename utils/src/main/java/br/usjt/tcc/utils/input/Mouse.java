package br.usjt.tcc.utils.input;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Sistema de manipulacao dos eventos do mouse.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Mouse {

	/** O estado dos botoes do mouse */
	private static boolean[] buttons = new boolean[64];

	/** A posicao inicial em x do evento acionado */
	private static int mx = -1;

	/** A posicao inicial em y do evento acionado */
	private static int my = -1;

	/**
	 * Inicializa o sistema de manipulacao do mouse.
	 */
	public static void init() {
		Toolkit.getDefaultToolkit().addAWTEventListener(new MouseHandler(), AWTEvent.MOUSE_EVENT_MASK);
	}

	/**
	 * Inicializa o sistema de manipulacao do mouse.
	 * 
	 * @param c
	 *            O componente que sera monitorado
	 */
	public static void init(Component c) {
		c.addMouseListener(new MouseHandler());
	}

	/**
	 * Checa se uma tecla especifica e pressionada.
	 * 
	 * @param button
	 *            O codigo da tecla para checar (definido na classe MouseEvent)
	 * @return True se a tecla foi pressionada
	 */
	public static boolean isPressed(int button) {
		return buttons[button];
	}

	/**
	 * Seta o estado do botao.
	 * 
	 * @param button
	 *            O codigo do botao para setar
	 * @param pressed
	 *            O novo estado da tecla
	 */
	public static void setPressed(int button, boolean pressed) {
		buttons[button] = pressed;
	}

	/**
	 * Retorna a posicao do mouse no eixo x no momento do evento acionado.
	 * 
	 * @return A posicao em x do mouse
	 */
	public static int getMx() {
		return mx;
	}

	/**
	 * Retorna a posicao do mouse no eixo x no momento do evento acionado.
	 * 
	 * @return A posicao em x do mouse
	 */
	public static int getMy() {
		return my;
	}

	/**
	 * Uma classe interna para responder aos botoes presionadas em uma escala
	 * global.
	 * 
	 * @author Diego Bolzan da Silva
	 * @author Henrique Silva Conceicao
	 * @author Jayson Jun Silva Sumi
	 * @author Leandro Capinan Scheiner
	 * @author Ricardo Martinelli de Oliveira
	 */
	private static class MouseHandler extends MouseAdapter implements AWTEventListener {

		/**
		 * Notificacao de um botao pressionado.
		 * 
		 * @param e
		 *            Os detalhes do evento
		 */
		public void mousePressed(MouseEvent e) {
			if (e.isConsumed()) {
				return;
			}
			// Seta no mapa de botoes que o botao foi acionado
			buttons[e.getButton()] = true;
			// Registra as coordenadas no momento do evento acionado
			mx = e.getX();
			my = e.getY();
		}

		/**
		 * Notificacao de um botao solto.
		 * 
		 * @param e
		 *            Os detalhes do evento
		 */
		public void mouseReleased(MouseEvent e) {
			if (e.isConsumed()) {
				return;
			}
			MouseEvent nextPress = (MouseEvent) Toolkit.getDefaultToolkit().getSystemEventQueue()
					.peekEvent(MouseEvent.MOUSE_PRESSED);
			if ((nextPress == null) || (nextPress.getWhen() != e.getWhen())) {
				buttons[e.getButton()] = false;
			}
		}

		/**
		 * Notificacao de evento disparado.
		 * 
		 * @param e
		 *            O evento AWT disparado
		 */
		public void eventDispatched(AWTEvent e) {
			if (e.getID() == MouseEvent.MOUSE_PRESSED) {
				mousePressed((MouseEvent) e);
			}
			if (e.getID() == MouseEvent.MOUSE_RELEASED) {
				mouseReleased((MouseEvent) e);
			}
		}
	}
}
