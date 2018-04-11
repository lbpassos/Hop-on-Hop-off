package pt.ulisboa.tecnico.cmu.proj.response;

public class LoginResponse implements Response{
	
	private static final long serialVersionUID = 734457624276534179L;
	private String message;
	
	public LoginResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
