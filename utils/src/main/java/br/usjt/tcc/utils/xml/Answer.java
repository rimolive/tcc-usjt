package br.usjt.tcc.utils.xml;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

/**
 * Classe utilitaria para mapeamento da Base de Conhecimento dos jogos. Essa
 * classe representa a resposta da pergunta relacionada.
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
@ObjectCreate(pattern = "questions/theme/question/answer")
public class Answer {

	/** True se a resposta for a correta */

	private boolean correct;

	/** O conteudo da resposta */
	@BeanPropertySetter(pattern = "questions/theme/question/answer/value")
	private String value;

	/**
	 * Retorna o conteudo da resposta
	 * 
	 * @return A string com o conteudo da resposta
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Seta o conteudo da resposta
	 * 
	 * @param value
	 *            O conteudo da resposta
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Checa se a resposta e correta ou nao
	 * 
	 * @return True se a resposta for correta
	 */
	public boolean isCorrect() {
		return correct;
	}

	/**
	 * Seta o indicador de resposta correta.
	 * 
	 * @param correct
	 *            o indicador de resposta correta
	 */
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

}
