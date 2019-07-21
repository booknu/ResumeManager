package graphic;

import animation.PressablePanel;
import manager.ResumeManager;
import obj.Resume;
import obj.Shared;

import javax.swing.*;
import java.io.File;

public class ResumeListPanel extends ListPanel {
	private static final int SHOW_LIMIT = 5;
	private static final int WORD_LIMIT = 15;
	private PressablePanel selectedPanel;
	public ResumeListPanel(File json) {
		super();
		// manager 읽고
		Shared.resumeManager = new ResumeManager();
		Shared.resumeManager.readJson(json);
		// 보여주자
		refresh();
	}

	public void addResume(Resume resume) {
		ItemPanel item = addItemPanel(resume);
		item.getContent().setClicked();
		Shared.mainPanel.scrollDown(0);
	}

	public void changeSelected(PressablePanel sel, Resume resume) {
		if(selectedPanel != null) selectedPanel.free();
		selectedPanel = sel;
		Shared.paragraphListPanel.changeResume(resume);
	}

	public void refresh() {
		removeAllPanel();
		for(int i = 0; i < Shared.resumeManager.getResumes().size(); ++i) {
			Resume resume = Shared.resumeManager.getResumes().get(i);
			addItemPanel(resume);
		}
	}

	private ItemPanel addItemPanel(Resume resume) {
		ItemPanel item = new ItemPanel();
		item.getContent().setTextLabel(getContentText(resume));
		// 선택 시 리스너
		item.getContent().addSelectedListener(e -> {
			changeSelected(item.getContent(), resume);
		});
		// Free 시 리스너
		item.getContent().addFreeListener(e -> {
			// Json 저장해줘야 함.
			Shared.resumeManager.writeJson(Shared.jsonFile);
		});
		// 삭제 버튼
		item.getRemoveBtn().addActionListener(e -> {
			// 삭제 잘 되나?
			if(JOptionPane.showConfirmDialog(MainFrame.getMainFrame().getFrame(), "자기소개서 삭제?", "자기소개서 데이터 삭제", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				Shared.resumeManager.getResumes().remove(resume);
				Shared.resumeManager.writeJson(Shared.jsonFile);
				if(selectedPanel == item.getContent()) {
					selectedPanel = null;
					Shared.paragraphListPanel.invalidatePanel();
				}
				super.removePanel(item);
				MainFrame.getMainFrame().getFrame().repaint();
			}
		});
		addPanel(item);
		return item;
	}

	public PressablePanel getSelectedPanel() { return selectedPanel; }

	public String getContentText(Resume resume) {
		if(resume.getParagraphs().size() == 0) return "";
		String text = resume.getParagraphs().get(0).getText();
		if(text.length() > WORD_LIMIT) return text.substring(0, WORD_LIMIT) + "...";
		return text;
	}
}
