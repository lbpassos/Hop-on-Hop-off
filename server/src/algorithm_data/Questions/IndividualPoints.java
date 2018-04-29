package algorithm_data.Questions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import authentication.User;

class Answers{
	private int numOfQuestions;
	private int numOfCorrectAnswers;
	
	public Answers(int nq, int ncq) {
		numOfQuestions = nq;
		numOfCorrectAnswers = ncq;
	}
	
	public int get_numOfQuestions() {
		return numOfQuestions;
	}
	public int get_numOfCorrectAnswers() {
		return numOfCorrectAnswers;
	}
}


public class IndividualPoints{
	private User user;
	//private ArrayList<Monument> monumentsVisited;
	private Map<Monument, Answers> trip;
	
	public IndividualPoints(User u) {
		user = u;
		//monumentsVisited = new ArrayList<Monument>();
		trip = new HashMap<Monument, Answers>();
	}
	
	public void addPoints_byMonument(Monument m, int nq, int ncq) {
		trip.put(m, new Answers(nq, ncq));
	}
	
	public int getNumberOfQuestionsAnswered(Monument m) {
		if(trip.get(m)==null) {
			return 0;
		}
		return trip.get(m).get_numOfQuestions();
	}
	
	public int getNumberOfCorrectQuestionsAnswered(Monument m) {
		if(trip.get(m)==null) {
			return 0;
		}
		return trip.get(m).get_numOfCorrectAnswers();
	}
	
	public int getTotalPoints() {
		int total=0;
		
		Iterator<Map.Entry<Monument, Answers>> it = trip.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry<Monument, Answers> pair = it.next();
		    total += pair.getValue().get_numOfCorrectAnswers();
		}
		return total;
	}
	
	public User getUser() {
		return user;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this==other) {
			return true;
		}
		if(other==null) {
			return false;
		}
		IndividualPoints otherUser = (IndividualPoints)other;
					
		if( this.user.equals(otherUser.user) ) {
			return true;
		}
		return false;
	}

}
