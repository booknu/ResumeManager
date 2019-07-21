package graphic;

import obj.Paragraph;
import obj.Resume;
import obj.Sentence;
import obj.Shared;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

public class MainFrame {
	private JFrame frame;
	private static MainFrame mainFrame;
	private ActionListener keyFunctions[] = new ActionListener[256];

	public MainFrame(File json) {
		Shared.mainPanel = new MainPanel();
		Shared.mainPanel.createUIComponents();
		Shared.resumeListPanel = new ResumeListPanel(json);
		Shared.paragraphListPanel = new ParagraphListPanel(null);
		Shared.sentenceListPanel = new SentenceListPanel(null);
		Shared.mainPanel.getListPanel(0).add(Shared.resumeListPanel, BorderLayout.CENTER);
		Shared.mainPanel.getListPanel(1).add(Shared.paragraphListPanel, BorderLayout.CENTER);
		Shared.mainPanel.getListPanel(2).add(Shared.sentenceListPanel, BorderLayout.CENTER);
		// frame 설정
		frame = new JFrame("노가다");
		frame.setContentPane(Shared.mainPanel.get());
		frame.setSize(1600, 800);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			boolean keyPressed[] = new boolean[256];
			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(frame,
						"프로그램 종료?", "프로그램 종료",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					Shared.resumeManager.writeJson(Shared.jsonFile);
					System.exit(0);
				}
			}
		});
		// 화면 가운데로
		Dimension frameSize = frame.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		// 전역 키 설정
		KeyboardFocusManager keyManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		keyMapping();
		keyManager.addKeyEventDispatcher(e -> {
			// 개수 많아지면 배열로 해결해야 함.
			if(e.getID() == KeyEvent.KEY_PRESSED && e.isControlDown()) {
				int code = e.getKeyCode();
				if(keyFunctions[code] != null) {
					keyFunctions[code].actionPerformed(null);
				}
			}
			return false;
		});
	}
	public void keyMapping() {
		keyFunctions[KeyEvent.VK_Q] = e -> {
			Resume resume = new Resume();
			Shared.resumeManager.getResumes().add(resume);
			Shared.resumeListPanel.addResume(resume);
		};
		keyFunctions[KeyEvent.VK_W] = e -> {
			if(Shared.paragraphListPanel.isValidate()) {
				Paragraph paragraph = new Paragraph();
				Shared.paragraphListPanel.getResume().getParagraphs().add(paragraph);
				Shared.paragraphListPanel.addParagraph(paragraph);
			}
		};
		keyFunctions[KeyEvent.VK_E] = e -> {
			if(Shared.sentenceListPanel.isValidate()) {
				Sentence sentence = new Sentence();
				Shared.sentenceListPanel.getParagraph().getSentences().add(sentence);
				Shared.sentenceListPanel.addSentence(sentence);
			}
		};
		keyFunctions[KeyEvent.VK_Z] = e -> {

		};
		keyFunctions[KeyEvent.VK_X] = e -> {

		};
		keyFunctions[KeyEvent.VK_C] = e -> {

		};
	}
	public JFrame getFrame() { return frame; }
	public ResumeListPanel getResumeListPanel() { return Shared.resumeListPanel; }
	public static void setMainFrame(MainFrame mainFrame) { MainFrame.mainFrame = mainFrame; }
	public static MainFrame getMainFrame() { return mainFrame; }
}
