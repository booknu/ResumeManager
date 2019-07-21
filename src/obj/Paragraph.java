package obj;

import java.util.Set;
import java.util.TreeSet;

public class Paragraph {
	private String text;
	private Set<Sentence> sentences;
	public Paragraph(String text) {
		super();
		this.text = text;
		this.sentences = new TreeSet<>();
	}
	public Paragraph() {
		this("");
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public Set<Sentence> getSentences() {
		return sentences;
	}

	public void setSentences(Set<Sentence> sentences) {
		this.sentences = sentences;
	}
}
