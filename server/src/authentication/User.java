package authentication;

import algorithm_data.Questions.IndividualPoints;
import algorithm_data.Questions.Monument;
import algorithm_data.Questions.QuestionsByMonument;

//In working

public class User {
	
	private QuestionsByMonument current;
	private IndividualPoints points;
	private String name;
	private String code;
	
	public User(String name, String code) {
				
		current = null;
		points = new IndividualPoints();
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setQuestion(QuestionsByMonument q) {
		current = q;
	}
	
	public QuestionsByMonument getQuestions() {
		return current;
	}
	
	public IndividualPoints getPoints() {
		return points;
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
					
		if( this.getName().equals(otherUser.getName()) && this.getCode().equals(otherUser.getCode()) ) {
			return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		return name.hashCode()+code.hashCode();
	}
	
}
