package graphic;

import animation.PressablePanel;
import obj.Paragraph;
import obj.Resume;
import obj.Sentence;
import obj.Shared;

public class SentenceListPanel extends ListPanel {
	private static final int SHOW_LIMIT = 5;
	private static final int WORD_LIMIT = 30;
	private PressablePanel selectedPanel;
	private Paragraph paragraph;
	private Sentence selSentence;
	public SentenceListPanel(Paragraph paragraph) {
		super();
		this.paragraph = paragraph;
		// 외부 문맥 저장, 문장 저장 처리용
		// 문단 저장 시
		Shared.mainPanel.addSaveParagraphBtnListener(e -> {
			saveParagraph();
			Shared.resumeManager.writeJson(Shared.jsonFile);
		});
		// 문장 저장 시
		Shared.mainPanel.addSaveSentenceBtnListener(e -> {
			saveSentence();
			Shared.resumeManager.writeJson(Shared.jsonFile);
		});
		// 보여주자
		if(paragraph != null) refresh();
	}

	/**
	 * 문단 저장 프로세스
	 */
	public void saveParagraph() {
		if(this.paragraph == null) return;
		// 데이터 수정하고 Json 저장
		this.paragraph.setText(Shared.mainPanel.getParagraphText().getText());
		Shared.refreshResumeText();
		Shared.refreshParagraphText();
	}

	/**
	 * 문장 저장 프로세스
	 */
	public void saveSentence() {
		if(selSentence == null) return;
		// Sentence 추가하고 Json 저장
		selSentence.setText(Shared.mainPanel.getTextField().getText());
		selSentence.setQuest(Shared.mainPanel.getQuestField().getText());
		Shared.refreshSentenceText();
	}


	/**
	 * Sentence 추가 액션
	 * @param sentence
	 */
	public void addSentence(Sentence sentence) {
		ItemPanel item = addItemPanel(sentence);
		item.getContent().setClicked();
		Shared.mainPanel.scrollDown(2);
	}

	/**
	 * 활성화 되어있는지?
	 * @return
	 */
	public boolean isValidate() { return paragraph != null; }

	/**
	 * 비활성화
	 */
	public void invalidatePanel() {
		// 현재 작성중인거 저장.
		saveParagraph();
		saveSentence();
		if(this.paragraph != null || this.selSentence != null) Shared.resumeManager.writeJson(Shared.jsonFile);
		paragraph = null;
		releaseSelection();
		removeAllPanel();
	}

	/**
	 * 현재 문단은 유지한채 선택 정보를 초기화시킨다.
	 */
	private void releaseSelection() {
		// null로 바꿔주고
		selectedPanel = null;
		selSentence = null;
		// 외부 TextField들 비활성화
		Shared.mainPanel.getParagraphText().setText("");
		Shared.mainPanel.getQuestField().setText("");
		Shared.mainPanel.getTextField().setText("");
	}

	/**
	 * 선택된 문단이 바뀌었다.
	 * @param paragraph
	 */
	public void changeParagraph(Paragraph paragraph) {
		// 현재 작성중인거 저장하고
		saveParagraph();
		saveSentence();
		if(this.paragraph != null || this.selSentence != null) Shared.resumeManager.writeJson(Shared.jsonFile);
		// Selection 풀고
		releaseSelection();
		// 바꾸고
		this.paragraph = paragraph;
		// 텍스트 채우기
		refresh();
		Shared.mainPanel.getParagraphText().requestFocus();
	}

	/**
	 * 선택된 문장이 바뀌었다.
	 * @param sentence
	 */
	public void changeSentence(Sentence sentence) {
		saveSentence();
		if(selSentence != null) Shared.resumeManager.writeJson(Shared.jsonFile);
		selSentence = sentence;
		Shared.mainPanel.getTextField().setText(selSentence.getText());
		Shared.mainPanel.getQuestField().setText(selSentence.getQuest());
		Shared.mainPanel.getTextField().requestFocus();
	}

	public void refresh() {
		// 외부 TextField들 초기화
		if(paragraph != null) {
			Shared.mainPanel.getParagraphText().setText(paragraph.getText());
		}
		Shared.mainPanel.getQuestField().setText("");
		Shared.mainPanel.getTextField().setText("");
		// 모든 Sentence들 지우기
		removeAllPanel();
		selectedPanel = null;
		for(Sentence sentence : paragraph.getSentences()) {
			addItemPanel(sentence);
		}
	}

	public ItemPanel addItemPanel(Sentence sentence) {
		ItemPanel item = new ItemPanel();
		item.getContent().setTextLabel(getContentText(sentence));
		// 선택 시 리스너
		item.getContent().addSelectedListener(e -> {
			changeSentence(sentence);
			// 일단 현재 선택된거 free 하고
			if(selectedPanel != null) selectedPanel.free();
			selectedPanel = item.getContent();
		});
		// Free 시 리스너
		item.getContent().addFreeListener(e -> {

		});
		// 문장 삭제 버튼
		item.getRemoveBtn().addActionListener(e -> {
			// 삭제 잘 되나?
			paragraph.getSentences().remove(sentence);
			Shared.resumeManager.writeJson(Shared.jsonFile);
			super.removePanel(item);
			MainFrame.getMainFrame().getFrame().repaint();
		});
		addPanel(item);
		return item;
	}

	public Paragraph getParagraph() { return paragraph; }

	public PressablePanel getSelectedPanel() { return selectedPanel; }

	public Sentence getSelSentence() { return selSentence; }

	private String getString(String txt) {
		if(txt.length() > WORD_LIMIT) return txt.substring(0, WORD_LIMIT) + "...";
		return txt;
	}

	public String getContentText(Sentence sentence) {
		return getString(sentence.getText()) + " --> " + getString(sentence.getQuest());
	}
}
