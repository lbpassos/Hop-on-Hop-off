package pt.ulisboa.tecnico.cmu.proj.command;

import pt.ulisboa.tecnico.cmu.proj.response.Response;

public interface CommandHandler {
	public Response handle(HelloCommand hc);
	public Response handle(AnotherHello hc);
	
	public Response handle(LoginCommand hc);
	public Response handle(SignInCommand hc);
	public Response handle(ListLocationsCommand hc);
	public Response handle(LogOutCommand hc);
	public Response handle(CheckRankingCommand hc);
	public Response handle(DownloadQuizCommand hc);
}
