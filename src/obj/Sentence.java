package obj;

public class Sentence implements Comparable<Sentence> {
	private String text, quest;
	public Sentence(String text, String quest)  {
		this.text = text;
		this.quest = quest;
	}
	public Sentence() {
		this("", "");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getQuest() {
		return quest;
	}

	public void setQuest(String quest) {
		this.quest = quest;
	}

	@Override
	public int compareTo(Sentence o) {
		int cmp = text.compareTo(o.text);
		if(cmp == 0) return quest.compareTo(o.quest);
		return cmp;
	}
}
