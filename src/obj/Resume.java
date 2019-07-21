package obj;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Resume {
	private ArrayList<Paragraph> paragraphs;
	public Resume() {
		super();
		paragraphs = new ArrayList<>();
	}

	public ArrayList<Paragraph> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(ArrayList<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}
}
