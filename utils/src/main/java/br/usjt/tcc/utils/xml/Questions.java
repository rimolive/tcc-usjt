package br.usjt.tcc.utils.xml;

import java.util.Vector;

/**
 * Classe utilitaria para mapeamento da Base de Conhecimento dos jogos. Essa
 * classe e a raiz da base de conhecimento.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Questions {
	
	/** Vetor de armazenamento dos themes */
	private Vector<Theme> themes = new Vector<Theme>();

	public Vector<Theme> getThemes() {
		return themes;
	}

	public void setThemes(Vector<Theme> themes) {
		this.themes = themes;
	}

	/**
	 * Adiciona um objeto Theme ao Vetor
	 * 
	 * @param theme
	 *            O objeto Theme
	 */
	public void addTheme(Theme theme) {
		themes.add(theme);
	}
}
