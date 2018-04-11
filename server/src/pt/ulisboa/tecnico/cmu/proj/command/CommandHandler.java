package pt.ulisboa.tecnico.cmu.proj.command;

import pt.ulisboa.tecnico.cmu.proj.response.Response;

public interface CommandHandler {
	public Response handle(HelloCommand hc);
	public Response handle(AnotherHello hc);
	
	public Response handle(LoginCommand hc);
	public Response handle(SignInCommand hc);
}