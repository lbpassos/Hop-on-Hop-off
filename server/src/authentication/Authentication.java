package authentication;

import java.util.HashMap;
import java.util.Map;

public class Authentication {
	private Map<String, String> users;
	
	public Authentication() {
		users = new HashMap<String, String>();
	}
	
	public boolean insertUser(String name, String code) {
		if( users.containsKey(name)==true || users.containsValue(code)==true) {
			return false;
		}
		users.put(name, code);
		return true;
	}

	public boolean checkUserName(String name) {
		return users.containsKey(name);
	}
	
	public boolean checkCode(String code) {
		return users.containsValue(code);
	}
}
