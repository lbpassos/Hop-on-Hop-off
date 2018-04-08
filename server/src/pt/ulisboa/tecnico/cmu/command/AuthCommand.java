package pt.ulisboa.tecnico.cmu.command;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import pt.ulisboa.tecnico.cmu.response.Response;


public class AuthCommand implements Command{
	private static final long serialVersionUID = -8807331723807741900L;
	private String name;
	private int code;
	
	public AuthCommand(String s){ //String in JSON
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(s);
			JSONArray array = new JSONArray();
			array.add(obj);
			JSONObject teste = (JSONObject)array.get(0);
			this.name = (String)teste.get("username");
			this.code = (int)teste.get("code");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Response handle(CommandHandler chi) {
		return chi.handle(this);
	}
	
	public String getMessage() {
		return this.message;
	}
}



