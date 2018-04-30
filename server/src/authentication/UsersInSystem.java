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
	
	public User checkExistance(String name) {
		for(int i=0; i<users.size(); ++i) {
			if( users.get(i).getName().equals(name) ) {
				return users.get(i);
			}
		}
		return null;
	}
		
}
