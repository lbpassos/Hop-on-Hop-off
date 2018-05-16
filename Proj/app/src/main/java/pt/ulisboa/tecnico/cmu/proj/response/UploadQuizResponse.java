package pt.ulisboa.tecnico.cmu.proj.response;

public class UploadQuizResponse implements Response{
	private static final long serialVersionUID = 734457624276534185L;
	private String message;

	public UploadQuizResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}