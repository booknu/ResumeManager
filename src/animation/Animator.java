package animation;

import javax.swing.*;

public class Animator {
	public static interface AnimationAction {
		/**
		 * 현재 프레임에 대한 액션을 수행한다.
		 * 프레임은 1-idx로 제공된다.
		 *
		 * @param curFrameIdx 1-idx로 된 프레임
		 * @param totalFrame 총 프레임 수
		 */
		public void action(int curFrameIdx, int totalFrame);
	}
	private static final long CHECKER_THREAD_SLEEP_MILLIS = 0;
	private static final int CHECKER_THREAD_SLEEP_NANOS = 1;
	protected int animDurationMillis;
	// s, e: 마치 queue와 같이 사용, Timer 자체에서 갱신해버리면 Timer 대기시간 + 그래픽 갱신시간 두 개가 합쳐져 하나의 Interval이 되어 버림.
	protected int totalFrame;
	protected Timer timer;
	protected AnimationAction action;
	protected Thread checker;
	protected int s;
	protected volatile int e;
	public Animator(int animDurationMillis, int totalFrame, AnimationAction action) {
		this.animDurationMillis = animDurationMillis;
		this.totalFrame = totalFrame;
		this.timer = new Timer(animDurationMillis / totalFrame, e -> updateGraphic());
		this.action = action;
		s = 1; e = 0;
	}

	public Animator(AnimationAction action) {
		this(100, 24, action);
	}

	protected void updateGraphic() {
		if(++e >= totalFrame) {
			timer.stop();
			return;
		}
	}
	public void start() {
		e = 0;
		timer.restart();
		checker = new Thread(()->{
			while(s <= totalFrame) {
				while(s < e) s++; // 현재 프레임까지 이동해 주자! (중간 프레임은 이미 지나갔기 때문에 필요 없다)
				action.action(s, totalFrame); // 현재 프레임에 대해 표현
				try {
					Thread.sleep(CHECKER_THREAD_SLEEP_MILLIS, CHECKER_THREAD_SLEEP_NANOS);
				} catch (InterruptedException e1) {
					return;
				}
			}
		});
		checker.start();
	}
	public void stop() {
		if(checker != null) checker.interrupt();
		timer.stop();
	}
}
