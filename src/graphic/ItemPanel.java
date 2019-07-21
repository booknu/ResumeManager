package graphic;

import animation.PressablePanel;

import javax.swing.*;
import java.awt.*;

public class ItemPanel extends JPanel {
	private JButton removeBtn;
	private PressablePanel contentLabel;

	public ItemPanel() {
		this.setLayout(new BorderLayout());
		removeBtn = new JButton("삭제");
		contentLabel = new PressablePanel();
		this.add(contentLabel, BorderLayout.CENTER);
		this.add(removeBtn, BorderLayout.EAST);
	}

	public PressablePanel getContent() {
		return contentLabel;
	}

	public JButton getRemoveBtn() {
		return removeBtn;
	}
}
