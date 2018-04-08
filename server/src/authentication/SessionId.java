package authentication;

public class SessionId {
	private static long session_id;
	
	public SessionId() {
		session_id = 0;
	}
	
	public long getAnotherSession() {
		return ++session_id;
	}

}
