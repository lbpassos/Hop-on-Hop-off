package authentication;

import java.util.HashMap;
import java.util.Map;

public class Authentication {
	private Map<String, Integer> users;
	
	public Authentication() {
		users = new HashMap<String, Integer>();
	}
	
	public boolean insertUser(String name, int code) {
		if( users.containsKey(name)==true || users.containsValue(code)==true) {
			return false;
		}
		users.put(name, code);
		return true;
	}

	public boolean checkUser(String name) {
		return users.containsKey(name);
	}
	
}
