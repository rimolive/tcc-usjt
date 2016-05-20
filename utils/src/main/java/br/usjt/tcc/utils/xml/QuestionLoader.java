package br.usjt.tcc.utils.xml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Vector;

import org.apache.commons.digester3.xmlrules.FromXmlRulesModule;

/**
 * Classe utilitaria para criacao das perguntas e respostas a serem utilizadas
 * pelos jogos. A base de dados e um arquivo XML que possui a seguinte estrutura
 * de dados:
 * 
 * <br>
 * questions <br>
 * &nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;--> theme <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;|-> name <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;--> question <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-> id <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-> description <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-> level <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-> answer <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|->
 * isCorrect <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|
 * <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
 * value <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--> tip <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|-> id <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;| <br>
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--> description
 * 
 * @author Diego Bolzan da Silva
 * @author Henrique Silva Conceicao
 * @author Jayson Jun Silva Sumi
 * @author Leandro Capinan Scheiner
 * @author Ricardo Martinelli de Oliveira
 */
public class QuestionLoader extends FromXmlRulesModule {
	/**
	 * Estrutura de dados especial que armazena toda a informacao dentro do xml
	 * de perguntas
	 */
	private Questions questions;

	/**
	 * Construtor Padrao.
	 */
	public QuestionLoader() {
		try {
			FileInputStream input = new FileInputStream(new File(this.getClass().getResource("questions.xml").getPath()));
			File rules = new File(this.getClass().getResource("questions-rules.xml").getPath());

			loadXMLRules(rules);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	@Override
	protected void loadRules() {
		//  TODO
	}


	/**
	 * Retorna um vetor de Temas da Base de Perguntas.
	 * 
	 * @return Um vetor de temas
	 */
	public Vector<Theme> getListThemes() {
		Vector<Theme> themes = new Vector<Theme>();
		for (Theme theme : questions.getThemes()) {
			themes.add(theme);
		}
		return themes;
	}

	/**
	 * Retorna a lista de perguntas de um determinado tema.
	 * 
	 * @param themeName
	 *            O nome do tema a ser pesquisado
	 * @return Um vetor de Perguntas
	 */
	public Vector<Question> getListQuestion(String themeName) {
		Theme theme = null;
		Vector<Theme> themes = this.getListThemes();
		for (Theme themeIter : themes) {
			if (themeIter.getName().equals(themeName)) {
				theme = themeIter;
				break;
			}
		}
		return theme.getQuestions();
	}

	/**
	 * Retorna o tamanho do vetor.
	 * 
	 * @param vector
	 * @return int Tamanho do vetor
	 */
	public int getSizeListQuestion(String themeName) {
		Vector<Question> vector = getListQuestion(themeName);
		return vector.size();
	}

	/**
	 * Retorna o numero da questao.
	 * 
	 * @param sizeQuestion
	 * @return int numero da questao
	 */

	public int getRandomQuestion(int sizeQuestion) {

		sizeQuestion = (int) (Math.random() * sizeQuestion);
		if (sizeQuestion == 0) {
			return 1;
		} else {
			return sizeQuestion;
		}
	}

	/**
	 * Retorna uma pergunta dado o nome do tema e o ID da pergunta.
	 * 
	 * @param themeName
	 *            O nome do tema a ser pesquisado
	 * @param idQuestion
	 *            O ID da pergunta a ser pesquisado
	 * @return Uma estrutura de dados do tipo Question
	 */
	public Question getQuestion(String themeName, int idQuestion) {
		Question question = null;
		Vector<Question> questions = getListQuestion(themeName);
		for (Question questionIter : questions) {
			if (questionIter.getId().equals(String.valueOf(idQuestion))) {
				question = questionIter;
				break;
			}
		}
		return question;
	}

}
