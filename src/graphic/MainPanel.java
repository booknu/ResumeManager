package graphic;

import obj.Paragraph;
import obj.Resume;
import obj.Sentence;
import obj.Shared;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainPanel {
	private JPanel mainPanel;
	private JButton resumeAddBtn;
	private JButton paragraphAddBtn;
	private JButton saveBtn;
	private JButton sentenceAddBtn;
	private JPanel firstPanel;
	private JPanel secondPanel;
	private JPanel thirdPanel;
	private JScrollPane paraScroll;
	private JScrollPane textScroll;
	private JScrollPane resumeScroll;
	private JScrollPane sentenceScroll;
	private JPanel twoPanel;
	private JTextArea paragraphText;
	private JPanel sentenceInputPanel;
	private JTextField textField;
	private JTextField questField;
	private JButton sentenceSaveBtn;

	public MainPanel() {
		// 자소서 추가
		resumeAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Resume resume = new Resume();
				Shared.resumeManager.getResumes().add(resume);
				Shared.resumeListPanel.addResume(resume);
			}
		});
		// 문단 추가
		paragraphAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Shared.paragraphListPanel.isValidate()) {
					Paragraph paragraph = new Paragraph();
					Shared.paragraphListPanel.getResume().getParagraphs().add(paragraph);
					Shared.paragraphListPanel.addParagraph(paragraph);
				}
			}
		});
		// 문장 추가
		sentenceAddBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(Shared.sentenceListPanel.isValidate()) {
					Sentence sentence = new Sentence();
					Shared.sentenceListPanel.getParagraph().getSentences().add(sentence);
					Shared.sentenceListPanel.addSentence(sentence);
				}
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					questField.requestFocus();
				}
			}
		});
		questField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					Shared.sentenceListPanel.saveSentence();
					Shared.resumeManager.writeJson(Shared.jsonFile);
				}
			}
		});
		paragraphText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
					Shared.sentenceListPanel.saveParagraph();
					Shared.resumeManager.writeJson(Shared.jsonFile);
				}
			}
		});
	}

	/**
	 * 0 : resumes
	 * 1 : paragraphs
	 * 2 : sentences
	 * @param idx
	 */
	public void scrollDown(int idx) {
		JScrollPane target = new JScrollPane[]{ resumeScroll, paraScroll, sentenceScroll }[idx];
		target.getVerticalScrollBar().setValue(target.getVerticalScrollBar().getMaximum());
	}

	public void createUIComponents() {
		for(JScrollPane pane : new JScrollPane[]{ paraScroll, textScroll, resumeScroll, sentenceScroll }) {
			pane.getVerticalScrollBar().setUnitIncrement(22);
		}
	}

	public JPanel getListPanel(int idx) {
		switch(idx) {
			case 0 : return firstPanel;
			case 1 : return secondPanel;
			case 2 : return thirdPanel;
		}
		throw new RuntimeException();
	}

	public JPanel get() { return mainPanel; }

	// MainPanel 외부에서 컴포넌트 접근하기 위한 메소드
	// 문맥 저장 클릭 시
	public void addSaveParagraphBtnListener(ActionListener listener) {
		saveBtn.addActionListener(listener);
	}

	// 문장 저장 클릭 시
	public void addSaveSentenceBtnListener(ActionListener listener) {
		sentenceSaveBtn.addActionListener(listener);
	}

	// 텍스트 필드들 getter
	public JTextArea getParagraphText() {
		return paragraphText;
	}

	public JTextField getTextField() {
		return textField;
	}

	public JTextField getQuestField() {
		return questField;
	}
}

