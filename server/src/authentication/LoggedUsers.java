package authentication;

import java.util.HashMap;
import java.util.Map;

public class LoggedUsers {
	private Map<User,String> logsIn;
	
	public LoggedUsers() {
		logsIn = new HashMap<User,String>();
	}
	
	public boolean insertLogIn(User u, String session_id) {
		if(logsIn.containsKey(u)==true) {
			return false;
		}
		logsIn.put(u, session_id);
		return true;
	}
	
	public void removeFromLog(User u) {
		logsIn.remove(u);
	}
	
	public boolean userAlreadyLogin(String user, String code) {
		User tmp = new User(user, code);
		
		if( logsIn.containsKey(tmp)==true ) {
			return true;
		}
		return false;
	}
	
	public boolean userSessionIDcompare(User user, String sid) {
		if( logsIn.containsKey(user)==true ) {
			if( logsIn.get(user).equals(sid) ) {
				return true;
			}
		}
		return false;
	}

}
