/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Animated Hover, Click 기능이 구현된 버튼 같은 Panel
 *
 * @author lim55
 */
public class PressablePanel extends JPanel {
	private static class Fader implements Animator.AnimationAction {
		private Component comp;
		private float[] toRGBA;
		private float[] fromRGBA;
		private float[] rgba = new float[4];
		public Fader(Component comp, Color from, Color to) {
			this.comp = comp;
			fromRGBA = from.getComponents(null);
			toRGBA = to.getComponents(null);
		}

		@Override
		public void action(int curFrameIdx, int totalFrame) {
			if(curFrameIdx == totalFrame) {
				comp.setBackground(new Color(toRGBA[0], toRGBA[1], toRGBA[2], toRGBA[3]));
				return;
			}
			for(int i = rgba.length - 1; i >= 0; i--) {
				float from = fromRGBA[i], to = toRGBA[i];
				rgba[i] = from + (to - from) * curFrameIdx / totalFrame;
			}
			comp.setBackground(new Color(rgba[0], rgba[1], rgba[2], rgba[3]));
		}
	}

	private MouseAdapter animationAdapter;
	private Color backGroundColor, hoverColor, clickedColor;
	private volatile Animator curAnimation;
	private int aniFrames, aniDuration;
	private JLabel textLabel;
	public int selected = 0;
	private ActionListener selectedListener, freeListener;

	public PressablePanel(Color backgroundColor, Color hoverColor, Color clickedColor) {
		super();
		textLabel = new JLabel();
		textLabel.setForeground(Color.WHITE);
		this.add(textLabel);
		setBtnColor(backgroundColor, hoverColor, clickedColor);
		setAnimationFrame(8);
		setAnimationDuration(160);
	}

	public PressablePanel() {
		this(UIManager.getColor("Label.background"),
				blend(UIManager.getColor("Label.background"), Color.WHITE, 0.85),
				blend(UIManager.getColor("Label.background"), Color.WHITE, 0.75));
	}

	private synchronized void fade(Color to) {
		if(curAnimation != null) curAnimation.stop();
		Animator animation = new Animator(aniDuration, aniFrames, new Fader(this, getBackground(), to));
		curAnimation = animation;
		curAnimation.start();
	}

	public void setAnimationFrame(int frames) {
		this.aniFrames = frames;
	}
	public void setAnimationDuration(int duration) {
		this.aniDuration = duration;
	}

	public void setBtnColor(Color backgroundColor) {
		setBtnColor(backgroundColor, blend(backgroundColor, Color.WHITE, 0.5), backgroundColor.darker());
	}

	public void setBtnColor(Color backgroundColor, Color hoverColor, Color clickedColor) {
		super.setBackground(backgroundColor);
		this.backGroundColor = backgroundColor;
		this.hoverColor = hoverColor;
		this.clickedColor = clickedColor;
		if(animationAdapter != null) removeMouseListener(animationAdapter);
		addMouseListener(animationAdapter = new MouseAdapter() {
			private int pressed = 0;
			@Override
			public void mouseEntered(MouseEvent event) {
				if(selected == 0) {
					pressed = 0;
					fade(hoverColor);
				}
			}

			@Override
			public void mouseExited(MouseEvent event) {
				if(selected == 0) {
					pressed = 0;
					fade(backgroundColor);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(selected == 0) {
					pressed = 1;
					fade(clickedColor);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if(selected == 0) {
					if(pressed == 1) fade(hoverColor);
					pressed = 0;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				setClicked();
			}
		});
	}

	public void setClicked() {
		if(selected == 0) {
			selected = 1;
			fade(clickedColor);
			if(selectedListener != null) selectedListener.actionPerformed(null);
		}
	}

	public void setTextLabel(String text) {
		textLabel.setText(text);
	}

	public void addSelectedListener(ActionListener listener) {
		selectedListener = listener;
	}

	public void addFreeListener(ActionListener listener) {
		freeListener = listener;
	}

	public void free() {
		if(selected == 1) {
			selected = 0;
			fade(backGroundColor);
			if(freeListener != null) freeListener.actionPerformed(null);
		}
	}

	/**
	 * 두 색을 일정 비율로 섞어준다.
	 * @return
	 */
	public static Color blend(Color color1, Color color2, double ratio) {
		float r = (float) ratio;
		float ir = (float) 1.0 - r;

		float red = color1.getRed() * r + color2.getRed() * ir;
		float green = color1.getGreen() * r + color2.getGreen() * ir;
		float blue = color1.getBlue() * r + color2.getBlue() * ir;
		float alpha = color1.getAlpha() * r + color2.getAlpha() * ir;

		red = Math.min(255f, Math.max(0f, red));
		green = Math.min(255f, Math.max(0f, green));
		blue = Math.min(255f, Math.max(0f, blue));
		alpha = Math.min(255f, Math.max(0f, alpha));

		Color color = null;
		try {
			color = new Color((int) red, (int) green, (int) blue, (int) alpha);
		} catch (IllegalArgumentException exp) {
			exp.printStackTrace();
		}
		return color;
	}
}
