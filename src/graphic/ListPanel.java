package graphic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 30;
	private static final Insets INSETS = new Insets(0, 0, 0, 0);
	private JPanel fillerPanel;
	private int ycounts;
	private ArrayList<JPanel> panels;

	public ListPanel(List<JPanel> panels) { this(panels, HEIGHT, INSETS); }
	public ListPanel(List<JPanel> panels, int height)
	{
		this(panels, height, INSETS);
	}
	public ListPanel(List<JPanel> panels, int height, Insets insets)
	{
		this();
		for (JPanel panel : panels)
			addPanel(panel, height, insets);
	}

	public ListPanel()
	{
		super();
		ycounts = 0;
		this.fillerPanel = new JPanel();
		this.fillerPanel.setMinimumSize(new Dimension(0, 0));
		this.panels = new ArrayList<JPanel>();
		setLayout(new GridBagLayout());
	}

	public void addPanel(JPanel p) { addPanel(p, HEIGHT, INSETS); }
	public void addPanel(JPanel p, int height){ addPanel(p, height, INSETS); }
	public void addPanel(JPanel p, int height, Insets insets)
	{
		remove(fillerPanel);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = ycounts++; // 이게 상대적인 위치
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.PAGE_START;
		gbc.ipady = height;
		gbc.insets = insets;
		gbc.weightx = 1.0;
		panels.add(p);
		add(p, gbc);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = ycounts++;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weighty = 1.0;
		add(fillerPanel, gbc);
		revalidate();
		invalidate();
		repaint();
	}

	public void removePanel(JPanel p)
	{
		removePanel(panels.indexOf(p));
	}


	public void removePanel(int idx) { removePanel(idx, HEIGHT, INSETS); }
	public void removePanel(int idx, int height) { removePanel(idx, height, INSETS); }
	public void removePanel(int idx, int height, Insets insets)
	{
		panels.remove(idx);
		remove(idx);

		revalidate();
		invalidate();
		repaint();
	}

	public void removeAllPanel() {
		while(panels.size() > 0) {
			removePanel(panels.size()-1);
		}
	}

	public ArrayList<JPanel> getPanels()
	{
		return this.panels;
	}
	public static void main(String args[]) {
		JFrame f = new JFrame("버그 테스트");
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 600);
		ListPanel lis = new ListPanel();
		f.add(lis);
		for(int i = 0; i < 10; ++i) {
			JPanel p = new JPanel();
			JLabel lab = new JLabel("label number " + (i+1));
			p.add(lab);
			lis.addPanel(p, 30);
		}
		Scanner sc = new Scanner(System.in);
		while(sc.hasNextLine()) {
			System.out.print("> ");
			String cmd = sc.nextLine();
			String[] sp = cmd.split(" ");
			if(sp.length != 2) {
				System.out.println("명령어는 두 단어");
				continue;
			}
			try {
				int idx = Integer.parseInt(sp[1]);
				if(sp[0].equals("c")) {
					System.out.println(idx + " 패널 생성");
					JPanel p = new JPanel();
					JLabel lab = new JLabel("new label number " + idx);
					p.add(lab);
					lis.addPanel(p, 30);
				} else if(sp[0].equals("r")) {
					if(!(0 <= idx && idx < lis.panels.size())) {
						System.out.println(lis.panels.size() + " 미만의 숫자 입력");
						continue;
					}
					System.out.println(idx + " 패널 삭제");
					lis.removePanel(idx);
				} else {
					System.out.println("알 수 없는 명령어");
					continue;
				}
			} catch (Exception e) {
				System.out.println("두 번째 인자는 숫자여야 함.");
				continue;
			}
		}
	}
}