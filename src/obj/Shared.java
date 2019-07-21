package obj;

import graphic.*;
import manager.ResumeManager;

import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shared {
	public static MainPanel mainPanel;
	public static ResumeManager resumeManager;
	public static ResumeListPanel resumeListPanel;
	public static ParagraphListPanel paragraphListPanel;
	public static SentenceListPanel sentenceListPanel;
	public static File jsonFile = new File("json/data.json");
	public static Lock managerLock = new ReentrantLock();
	public static void refreshResumeText() {
		if(resumeListPanel.getSelectedPanel() != null && paragraphListPanel.getResume() != null) {
			resumeListPanel.getSelectedPanel().setTextLabel(resumeListPanel.getContentText(paragraphListPanel.getResume()));
		}
	}
	public static void refreshParagraphText() {
		if(paragraphListPanel.getSelectedPanel() != null && sentenceListPanel.getParagraph() != null) {
			paragraphListPanel.getSelectedPanel().setTextLabel(paragraphListPanel.getContentText(sentenceListPanel.getParagraph()));
		}
	}
	public static void refreshSentenceText() {
		if(sentenceListPanel.getSelectedPanel() != null && sentenceListPanel.getSelSentence() != null) {
			sentenceListPanel.getSelectedPanel().setTextLabel(sentenceListPanel.getContentText(sentenceListPanel.getSelSentence()));
		}
	}
}
