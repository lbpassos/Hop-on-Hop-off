package authentication;

import algorithm_data.Questions.QuestionsByMonument;

//In working

public class User {
	private int numberOfCorrectAnswers;
	private int totalNumberOfAnswers;
	//private int count;
	private QuestionsByMonument current;
	private String name;
	private int code;
	
	public User(String name, int code) {
		numberOfCorrectAnswers = 0;
		totalNumberOfAnswers = 0;
		
		current = null;
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setQuestion(QuestionsByMonument q) {
		current = q;
	}
	
	public QuestionsByMonument getQuestions() {
		return current;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this==other) {
			return true;
		}
		if(other==null) {
			return false;
		}
		User otherUser = (User)other;
		if( this.getName().equals(otherUser.getName()) && this.getCode()==otherUser.getCode() ) {
			return true;
		}
		return false;
	}
	
}
