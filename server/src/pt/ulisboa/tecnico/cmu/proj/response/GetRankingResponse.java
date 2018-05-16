package pt.ulisboa.tecnico.cmu.proj.response;

public class GetRankingResponse implements Response{
	private static final long serialVersionUID = 734457624276534198L;
	private String message;

	public GetRankingResponse(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}