package animation;

import java.awt.*;

public class FaderAction implements Animator.AnimationAction {
	private Component comp;
	private float[] toRGBA;
	private float[] fromRGBA;
	private float[] rgba = new float[4];
	public FaderAction(Component comp, Color from, Color to) {
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