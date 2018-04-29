package algorithm_data.Questions;

import authentication.User;

public class Ranking {
	private String username;
	private int points;
	
	public Ranking(String u, int p) {
		username = u;
		points = p;
	}

	public String getUser() {
		return username;
	}
	public int getPoints() {
		return points;
	}
}
