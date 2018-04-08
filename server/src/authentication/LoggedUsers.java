package authentication;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
	private Map<User,Integer> logsIn;
	
	public LoggedUsers() {
		logsIn = new HashMap<User,Integer>();
	}
	
	public boolean insertLogIn(User u, int sid) {
		if(logsIn.containsKey(u)==true) {
			return false;
		}
		logsIn.put(u, sid);
		return true;
	}
	
	public void removeFromLog(User u) {
		logsIn.remove(u);
	}

}
