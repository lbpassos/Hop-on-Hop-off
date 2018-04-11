package pt.ulisboa.tecnico.cmu.proj.response;

public class SignInResponse implements Response{
	
	private static final long serialVersionUID = 734457624276534180L;
	private String message;
	
	public SignInResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
	

}
