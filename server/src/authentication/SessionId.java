package authentication;

import java.util.UUID;

public class SessionId {
	//private static long session_id = 0;
	
	public static String getAnotherSession() {
		return String.valueOf( UUID.randomUUID() );
	}

}
