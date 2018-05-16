package pt.ulisboa.tecnico.cmu.proj.command;

import pt.ulisboa.tecnico.cmu.proj.response.Response;

public class UploadQuizCommand implements Command{
	private static final long serialVersionUID = -8807331723807741955L;
    private String message;

    public UploadQuizCommand(String message) {
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
