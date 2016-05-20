package br.usjt.tcc.utils.xml;

import java.util.Vector;

/**
 * Classe utilitaria para mapeamento da Base de Conhecimento dos jogos. Essa
 * classe representa a pergunta ao tema da Base de conhecimento.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class Question {
	
	/** ID da pergunta */
	private String id;
	
	/** Descricao da pergunta */
	private String description;
	
	/** Nivel de dificuldade (easy/medium/hard) */
	private String level;
	
	/** Vetor que armazena as alternativas de respostas */
	private Vector<Answer> answers = new Vector<Answer>();
	
	/** Vetor que armazena as dicas das perguntas */
	private Vector<Tip> tips = new Vector<Tip>();

	/**
	 * Retorna o ID da pergunta
	 * 
	 * @return O ID da pergunta
	 */
	public String getId() {
		return id;
	}

	/**
	 * Seta o ID da pergunta
	 * 
	 * @param id
	 *            o novo ID da pergunta
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retorna a descricao da pergunta
	 * 
	 * @return A string contendo a descricao da pergunta
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Seta a descricao da pergunta
	 * 
	 * @param description
	 *            A descricao da pergunta
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Retorna o nivel de dificuldade da pergunta
	 * 
	 * @return A string contendo o nivel de dificuldade da pergunta
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Seta o nivel de dificuldade da pergunta
	 * 
	 * @param level
	 *            o nivel de dificuldade da pergunta
	 * @uml.property name="level"
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Adiciona uma alternativa ao vetor de alternativas
	 * 
	 * @param answer
	 *            A alternativa
	 */
	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	/**
	 * Adiciona uma dica ao vetor de dicas
	 * 
	 * @param tip
	 *            A dica
	 */
	public void addTip(Tip tip) {
		tips.add(tip);
	}

	public Vector<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Vector<Answer> answers) {
		this.answers = answers;
	}

	public Vector<Tip> getTips() {
		return tips;
	}

	public void setTips(Vector<Tip> tips) {
		this.tips = tips;
	}
}
