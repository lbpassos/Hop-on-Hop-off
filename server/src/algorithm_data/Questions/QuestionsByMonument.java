package algorithm_data.Questions;

import java.util.ArrayList;

public class QuestionsByMonument {
	private Monument m;
	private ArrayList<Question> questions;
	
	public QuestionsByMonument(Monument m) {
		this.m = m;
		questions = new ArrayList<Question>();
	}

	public void addQuestion(Question q) {
		questions.add(q);
	}
	
	public Monument getMonument() {
		return m;
	}
	
	public int getSize() {
		return questions.size();
	}
	
	public Question getQuestion(int idx) {
		return questions.get(idx);
	}
}
