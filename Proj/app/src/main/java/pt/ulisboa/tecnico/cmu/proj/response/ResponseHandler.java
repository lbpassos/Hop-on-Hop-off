package pt.ulisboa.tecnico.cmu.proj.response;

public interface ResponseHandler {
	public void handle(HelloResponse hr);

	public void handle(LoginResponse hr);
	public void handle(SignInResponse hr);
	public void handle(LogOutResponse hr);
	public void handle(ListLocationsResponse hr);
	public void handle(DownloadQuizResponse hr);
	public void handle(UploadQuizResponse hr);
	public void handle(GetRankingResponse hr);
}
