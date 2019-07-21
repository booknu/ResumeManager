package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import obj.Resume;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class ResumeManager {
	private ArrayList<Resume> resumes;
	public ResumeManager() {
		super();
		resumes = new ArrayList<>();
	}
	public synchronized void writeJson(File json) {
		Gson gson = new GsonBuilder().create();
		if(!json.getParentFile().exists() || !json.getParentFile().isDirectory()) json.getParentFile().mkdir();
		try (Writer writer = new FileWriter(json)) {
			gson.toJson(resumes, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readJson(File json) {
		if(json.exists()) {
			Gson gson = new Gson();
			try {
				resumes = gson.fromJson(new FileReader(json), new TypeToken<ArrayList<Resume>>(){}.getType());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Resume> getResumes() {
		return resumes;
	}

	public void setResumes(ArrayList<Resume> resumes) {
		this.resumes = resumes;
	}
}
