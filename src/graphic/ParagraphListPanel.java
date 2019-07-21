package graphic;

import animation.PressablePanel;
import manager.ResumeManager;
import obj.Paragraph;
import obj.Resume;
import obj.Shared;

import javax.swing.*;
import java.io.File;

public class ParagraphListPanel extends ListPanel {
	private static final int SHOW_LIMIT = 5;
	private static final int WORD_LIMIT = 30;
	private PressablePanel selectedPanel;
	private Resume resume;
	public ParagraphListPanel(Resume resume) {
		super();
		this.resume = resume;
		// 보여주자
		if(resume != null) refresh();
	}

	public boolean isValidate() { return resume != null; }

	public void invalidatePanel() {
		resume = null;
		selectedPanel = null;
		removeAllPanel();
		Shared.sentenceListPanel.invalidatePanel();
	}

	public void changeResume(Resume resume) {
		selectedPanel = null;
		this.resume = resume;
		Shared.sentenceListPanel.invalidatePanel();
		refresh();
	}

	public void addParagraph(Paragraph paragraph) {
		ItemPanel item = addItemPanel(paragraph);
		item.getContent().setClicked();
		Shared.mainPanel.scrollDown(1);
	}

	public void refresh() {
		removeAllPanel();
		for(int i = 0; i < resume.getParagraphs().size(); ++i) {
			Paragraph para = resume.getParagraphs().get(i);
			addItemPanel(para);
		}
	}

	public ItemPanel addItemPanel(Paragraph paragraph) {
		ItemPanel item = new ItemPanel();
		item.getContent().setTextLabel(getContentText(paragraph));
		// 선택 시 리스너
		item.getContent().addSelectedListener(e -> {
			Shared.sentenceListPanel.changeParagraph(paragraph);
			// 일단 현재 선택된거 free 하고
			if(selectedPanel != null) selectedPanel.free();
			selectedPanel = item.getContent();
		});
		// Free 시 리스너
		item.getContent().addFreeListener(e -> {

		});
		// 삭제 버튼
		item.getRemoveBtn().addActionListener(e -> {
			// 삭제 잘 되나?
			if(JOptionPane.showConfirmDialog(MainFrame.getMainFrame().getFrame(), "문단 삭제?", "문단 삭제", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				resume.getParagraphs().remove(paragraph);
				Shared.resumeManager.writeJson(Shared.jsonFile);
				// resume list도 갱신해주고
				Shared.refreshResumeText();
				if(selectedPanel == item.getContent()) {
					selectedPanel = null;
					Shared.sentenceListPanel.invalidatePanel();
				}
				super.removePanel(item);
				MainFrame.getMainFrame().getFrame().repaint();
			}
		});
		addPanel(item);
		return item;
	}

	public Resume getResume() { return resume; }

	public PressablePanel getSelectedPanel() { return selectedPanel; }

	public String getContentText(Paragraph paragraph) {
		String text = paragraph.getText();
		if(text.length() > WORD_LIMIT) return text.substring(0, WORD_LIMIT) + "...";
		return text;
	}
}
