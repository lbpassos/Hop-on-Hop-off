package algorithm_data.Questions;

import java.util.ArrayList;

public class Question {
	private String question;
	private ArrayList<Choice> answers;
	
	public Question(String q) {
		question = q;
		answers = new ArrayList<Choice>();
	}
	
	public void insertAnswer(Choice a) {
		answers.add(a);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public int getNumOfChoices() {
		return answers.size();
	}
	
	public String getChoice(int idx) {
		return answers.get(idx).getChoice();
	}
	
	public int getNumCorrectAnswers(boolean[] ans) {
		int val = 0;
		for(int i=0; i<answers.size(); ++i) {
			if( answers.get(i).getStatus()==true && ans[i]==true ) {
				++val;
			}
		}
		return val;
	}
}
