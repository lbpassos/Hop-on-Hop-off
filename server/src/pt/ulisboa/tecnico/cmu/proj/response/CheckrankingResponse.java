package pt.ulisboa.tecnico.cmu.proj.response;

public class CheckrankingResponse implements Response{
	private static final long serialVersionUID = 734457624276534183L;
	private String message;
	
	public CheckrankingResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
