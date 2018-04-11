package authentication;

public class SessionId {
	private static long session_id = 0;
	
		
	public static long getAnotherSession() {
		return ++session_id;
	}

}
