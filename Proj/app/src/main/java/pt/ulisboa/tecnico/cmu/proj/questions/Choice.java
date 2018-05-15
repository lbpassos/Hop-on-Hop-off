package pt.ulisboa.tecnico.cmu.proj.questions;

public class Choice {
	private String quest;
	private boolean value; //true correct
	                    //false incorrect
	public Choice(String q) {
		quest = q;
		value = false;
	}
	
	public String getChoice() {
		return quest;
	}
	
	public boolean getStatus() {
		return value;
	}
	public void setStatus(boolean b) {
		value=b;
	}
	
}
