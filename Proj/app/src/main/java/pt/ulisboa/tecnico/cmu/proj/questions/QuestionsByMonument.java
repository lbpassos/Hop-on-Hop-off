package pt.ulisboa.tecnico.cmu.proj.questions;

import java.util.ArrayList;

public class QuestionsByMonument {
	private String id;
	private ArrayList<Question> questions;
	
	public QuestionsByMonument(String m) {
		this.id = m;

		questions = new ArrayList<Question>();
	}


	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	public String getMonument() {
		return id;
	}
	
	public int getSize() {
		return questions.size();
	}
	
	public Question getQuestion(int idx) {
		return questions.get(idx);
	}
}
