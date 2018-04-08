package algorithm_data.Questions;

public class Choice {
	private String quest;
	private boolean value; //true correct
	                    //false incorrect
	public Choice(String q, boolean v) {
		quest = q;
		value = v;
	}
	
	public String getChoice() {
		return quest;
	}
	
	public boolean getStatus() {
		return value;
	}
	
}
