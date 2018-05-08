package pt.ulisboa.tecnico.cmu.proj.command;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmu.proj.response.Response;

public interface Command extends Serializable {
	Response handle(CommandHandler ch);
}
