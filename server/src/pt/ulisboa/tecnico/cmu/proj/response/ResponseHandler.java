package pt.ulisboa.tecnico.cmu.proj.response;

import pt.ulisboa.tecnico.cmu.proj.command.ListLocationsCommand;
import pt.ulisboa.tecnico.cmu.proj.command.LogOutCommand;

public interface ResponseHandler {
	public void handle(HelloResponse hr);
	
	public void handle(LoginResponse hr);
	public void handle(SignInResponse hr);
	public void handle(ListLocationsCommand hc);
	public void handle(LogOutCommand hc);
	public void handle(CheckRankingCommand hc);
}
