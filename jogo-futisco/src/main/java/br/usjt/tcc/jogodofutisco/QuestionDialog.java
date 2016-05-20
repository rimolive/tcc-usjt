package br.usjt.tcc.jogodofutisco;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Enumeration;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import br.usjt.tcc.utils.xml.Answer;
import br.usjt.tcc.utils.xml.Question;

public class QuestionDialog extends JDialog {
	/** */
	private static final long serialVersionUID = 6611834677715249280L;
	/** */
	private JPanel jContentPane = null;
	/** */
	private JPanel pControls = null;
	/** */
	private JButton bResponder = null;
	/** */
	private JTextArea taQuestion = null;
	/** */
	private ButtonGroup buttonGroup = null;

	/**
	 * This is the default constructor
	 */
	public QuestionDialog(Question question, Jogo jogo) {
		super();
		initialize(question);
	}

	/**
	 * This method initializes this
	 * 
	 * @param question 
	 * @return void
	 */
	private void initialize(Question question) {
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds((screenSize.width-600)/2, (screenSize.height-400)/2, 600, 400);
		this.setUndecorated(true);
		this.setModal(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setContentPane(getJContentPane(question));
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @param question 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane(Question question) {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(0,1));
			jContentPane.add(getTaQuestion(question.getDescription()));
			buttonGroup = new ButtonGroup();
			for(Answer answer: question.getAnswers()) {
				JRadioButton button = new JRadioButton();
				button.setText(answer.getValue());
				buttonGroup.add(button);
				jContentPane.add(button);
			}
			jContentPane.add(getPControls());
		}
		return jContentPane;
	}

	/**
	 * This method initializes pControls	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPControls() {
		if (pControls == null) {
			pControls = new JPanel();
			pControls.setLayout(new FlowLayout());
			pControls.add(getBResponder());
		}
		return pControls;
	}

	/**
	 * This method initializes bResponder	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBResponder() {
		if (bResponder == null) {
			bResponder = new JButton();
			bResponder.setText("Responder");
			bResponder.setPreferredSize(new java.awt.Dimension(96,20));
			bResponder.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Enumeration enumeration = buttonGroup.getElements();
					while(enumeration.hasMoreElements()) {
						JButton button = (JButton) enumeration.nextElement();
						if(button.isSelected()) {
						}
					}
				}
			});
		}
		return bResponder;
	}

	/**
	 * This method initializes taQuestion	
	 * @param string 
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTaQuestion(String question) {
		if (taQuestion == null) {
			taQuestion = new JTextArea();
			taQuestion.setRows(3);
			taQuestion.setLineWrap(true);
			taQuestion.setWrapStyleWord(true);
			taQuestion.setEditable(false);
			taQuestion.setBackground(new java.awt.Color(238,238,238));
			taQuestion.setText(question);
		}
		return taQuestion;
	}
}