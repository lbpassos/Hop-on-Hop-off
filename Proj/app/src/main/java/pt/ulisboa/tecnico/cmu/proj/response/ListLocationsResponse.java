package pt.ulisboa.tecnico.cmu.proj.response;

public class ListLocationsResponse implements Response{
	private static final long serialVersionUID = 734457624276534181L;
	private String message;
	
	public ListLocationsResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}

}
