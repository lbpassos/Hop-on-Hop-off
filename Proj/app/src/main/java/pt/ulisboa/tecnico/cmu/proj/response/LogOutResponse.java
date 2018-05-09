package pt.ulisboa.tecnico.cmu.proj.response;

/**
 * Created by ist426300 on 10-04-2018.
 */

public class LogOutResponse implements Response {

    private static final long serialVersionUID = 734457624276534183L;
    private String message;

    public LogOutResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}