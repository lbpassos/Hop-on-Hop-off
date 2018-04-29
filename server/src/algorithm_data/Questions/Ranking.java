package algorithm_data.Questions;

import authentication.User;

public class Ranking {
	private User user;
	private int points;
	
	public Ranking(User u, int p) {
		user = u;
		points = p;
	}

	public String getUser() {
		return user.getName();
	}
	public int getPoints() {
		return points;
	}
}
