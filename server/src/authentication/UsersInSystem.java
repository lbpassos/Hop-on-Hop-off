package authentication;

import java.util.ArrayList;

public class UsersInSystem {
	private ArrayList<User> users;
	
	public UsersInSystem() {
		users = new ArrayList<User>();
	}

	public void insert(User u) {
		users.add(u);
	}
	
	public int get(User u) {
		return users.indexOf(u);
	}
	
	public User getUser(int idx) {
		return users.get(idx);
	}
		
}
