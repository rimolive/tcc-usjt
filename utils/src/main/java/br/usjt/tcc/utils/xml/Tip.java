package br.usjt.tcc.utils.xml;

/**
 * Classe utilitaria para mapeamento da Base de Conhecimento dos jogos. Essa
 * classe representa a dica para a resposta da pergunta relacionada.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Tip {
	
	/** ID da dica */
	private String id;
	
	/** Descricao da dica */
	private String description;

	/**
	 * Retorna o ID da dica
	 * 
	 * @return O ID da dica
	 */
	public String getId() {
		return id;
	}

	/**
	 * Seta o ID da dica
	 * 
	 * @param id
	 *            O novo ID da Dica
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao da dica
	 * 
	 * @return A descricao da dica
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Seta a descricao da dica
	 * 
	 * @param description
	 *            A descricao da dica
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
