package br.usjt.tcc.utils;

//import com.dnsalias.java.timer.AdvancedTimer;

/**
 * Uma classe wrapper que fornece metodos de temporizacao. Esta classe
 * nos permite utilizar um ponto central onde podemos criar nossa
 * implementacao de tempo. A implementacao foi feita em cima do framework
 * GAGE timer. (@see http://java.dnsalias.com)
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class SystemTimer {
	/** O link com a biblioteca do GAGE timer */
//	private static AdvancedTimer timer = new AdvancedTimer();
	/** O numero de "pulsos de tempo" por segundo */
	private static long timerTicksPerSecond;
	
	/** Inicializacao do temporizador */
	static {
//		timer.start();
//		timerTicksPerSecond = AdvancedTimer.getTicksPerSecond();
	}
	
	/**
	 * Retorna o tempo em milisegundos
	 * 
	 * @return Tempo em milisegundos
	 */
	public static long getTime() {
		// Capturamos os "pulsos de tempo" do temporizador,
		// multiplicamos por 1000 para termos o resultado em
		// milisegundos, entao dividimos pelo numero de pulsos
		// em um segundo e recebemos o tempo em milisegundos
//		return (timer.getClockTicks() * 1000) / timerTicksPerSecond;
		return 0l;
	}
	
	/**
	 * Paraliza por um tempo (em milisegundos).
	 * 
	 * @param duration O tempo em milisegundos
	 */
	public static void sleep(long duration) {
//		timer.sleep((duration * timerTicksPerSecond) / 1000);
	}
}