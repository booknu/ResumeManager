package main;

import com.google.gson.Gson;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import de.javasoft.synthetica.dark.SyntheticaDarkLookAndFeel;
import de.javasoft.synthetica.plain.SyntheticaPlainLookAndFeel;
import graphic.MainFrame;
import manager.ResumeManager;
import obj.Paragraph;
import obj.Resume;
import obj.Sentence;
import obj.Shared;

import javax.swing.*;
import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.text.ParseException;

public class Test {
	// Theme : http://www.javasoft.de/synthetica/faq/
	// booknu, a64405
	public static void main(String args[]) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, ParseException, NoSuchFieldException {
		System.setProperty("file.encoding","UTF-8");
		Field charset = Charset.class.getDeclaredField("defaultCharset");
		charset.setAccessible(true);
		charset.set(null,null);

		UIManager.setLookAndFeel(new SyntheticaDarkLookAndFeel());
		JFrame.setDefaultLookAndFeelDecorated(true);
		SyntheticaPlainLookAndFeel.setFont("fonts/NanumSquareRoundB.ttf", 12);

		MainFrame frame = new MainFrame(Shared.jsonFile);
		MainFrame.setMainFrame(frame);
//		writeJsonTest();
	}

	public static void writeJsonTest() {
		ResumeManager man = new ResumeManager();
		Resume res = new Resume();
		Paragraph[] par = {
				new Paragraph("테스트 문단 1"),
				new Paragraph("테스트 문단 2"),
				new Paragraph("테스트 문단 3")
		};
		par[0].getSentences().add(new Sentence("1", "2"));
		par[0].getSentences().add(new Sentence("a", "b"));
		par[1].getSentences().add(new Sentence("히히히", "히히히"));
		for(int i = 0; i < par.length; ++i) {
			res.getParagraphs().add(par[i]);
		}
		for(int i = 0; i < 100; ++i) man.getResumes().add(res);
		man.writeJson(new File("json/data.json"));
	}
	public static void readJsonTest() {
		ResumeManager man = new ResumeManager();
		man.readJson(new File("json/data.json"));
		Gson gson = new Gson();
		System.out.println(gson.toJson(man.getResumes()));
	}
}
