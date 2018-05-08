package pt.ulisboa.tecnico.cmu.proj.command;

import pt.ulisboa.tecnico.cmu.proj.response.Response;

/**
 * Created by ist426300 on 11-04-2018.
 */

public class SignInCommand implements Command {
    private static final long serialVersionUID = -8807331723807741901L;
    private String message;

    public SignInCommand(String message) {
        this.message = message;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String getMessage() {
        return this.message;
    }
}
