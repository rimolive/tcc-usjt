package br.usjt.tcc.utils.xml;

import java.util.Vector;

/**
 * Classe utilitaria para mapeamento da Base de Conhecimento dos jogos. Essa
 * classe representa o tema relacionado a Base de Conhecimento.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Theme {
	
	/** O nome do tema a ser mapeado */
	private String name;
	
	/** Vetor de perguntas relacionadas ao tema */
	private Vector<Question> questions = new Vector<Question>();

	/**
	 * Retorna o nome do tema.
	 * 
	 * @return A string com o nome do tema
	 */
	public String getName() {
		return name;
	}

	/**
	 * Seta o nome do tema.
	 * 
	 * @param name
	 *            O nome do tema
	 */
	public void setName(String name) {
		this.name = name;
	}

	public Vector<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Vector<Question> questions) {
		this.questions = questions;
	}

	/**
	 * Adiciona uma pergunta ao tema relacionado
	 * 
	 * @param question
	 *            A pergunta relacionada ao tema
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}

}
