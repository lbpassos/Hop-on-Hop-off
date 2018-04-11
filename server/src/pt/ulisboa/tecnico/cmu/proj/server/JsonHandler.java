package pt.ulisboa.tecnico.cmu.proj.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonHandler {
	
	//Decode JSON string received by Login
    public static String[] LoginFromClient(String command){
        String[] tmp = new String[2];
        
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(command);
        	JSONArray array = new JSONArray();
        	array.add(obj);
        	JSONObject teste = (JSONObject)array.get(0);
        	tmp[0] = teste.get("username").toString();
        	tmp[1] = teste.get("password").toString();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        return tmp;
    }
        
             
    //Encode JSON string for Login response to client
    public static String LoginFromServer(String session_id, String msg){
    	JSONObject obj_t = new JSONObject();
    	
    	 try {
	    	  obj_t.put("session", session_id);
	    	  obj_t.put("message", msg);
	      }
	      catch(Exception e) {
	    	  e.printStackTrace();
	      }
    	 
        return obj_t.toJSONString();
    }

}
