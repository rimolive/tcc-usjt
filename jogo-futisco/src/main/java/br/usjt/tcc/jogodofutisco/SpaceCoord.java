package br.usjt.tcc.jogodofutisco;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que mapeia as casas do tabuleiro de acordo com um arquivo .klc.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class SpaceCoord {
	/** Lista de Casas do tabuleiro */
	private List<Dimension> spaces;

	/** Lista contendo o tipo das casas */
	private List<String> types;
	
	private int value;

	/** Leitor do arquivo .klc */
	private BufferedReader reader;

	/**
	 * Construtor padrao.
	 * 
	 * @param filetb
	 */
	public SpaceCoord(String filetb) {
		String fLine = "";
		URL url = this.getClass().getClassLoader().getResource(filetb);
		try {
			reader = new BufferedReader(new FileReader(url.getFile()));
			fLine = reader.readLine();
		} catch (FileNotFoundException fl) {
			fl.printStackTrace();
		} catch (IOException io) {
			io.printStackTrace();
		}

		this.value = Integer.parseInt(fLine.substring(7));

		spaces = new ArrayList<Dimension>(value);
		types = new ArrayList<String>(value);
		int x = 0, y = 0, index1 = 0, index2 = 0;
		for (int i = 0; i < value; i++) {
			try {
				fLine = reader.readLine();
				index1 = new String("sp" + (i + 1) + "=(").length();
				index2 = fLine.indexOf(",");
				x = Integer.parseInt(fLine.substring(index1, index2));
				index1 = index2 + 1;
				index2 = fLine.indexOf(")", index1);
				y = Integer.parseInt(fLine.substring(index1, index2));
				spaces.add(new Dimension(x, y));
				index1 = fLine.indexOf("tp=") + 3;
				types.add(fLine.substring(index1));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Retorna a coordenada de acordo com o numero da casa e da peca.
	 * 
	 * @param space
	 *            O numero da casa
	 * @param peca
	 *            O numero da peca
	 * @return A coordenada a ser desenhada a peca
	 */
	public Dimension getCoord(int space, int peca) {
		switch (peca) {
		case 0:
			return spaces.get(space);
		case 1:
			return new Dimension(spaces.get(space).width + 44, spaces
					.get(space).height);
		case 2:
			return new Dimension(spaces.get(space).width,
					spaces.get(space).height + 44);
		case 3:
			return new Dimension(spaces.get(space).width + 44, spaces
					.get(space).height + 44);
		}
		return spaces.get(space);
	}

	/**
	 * Retorna o tipo da casa de acordo com o numero da casa
	 * 
	 * @param i
	 * @return
	 */
	public String getType(int space) {
		return types.get(space);
	}

	public int getTotalNumberOfSpaces() {
		return this.value;
	}
}
