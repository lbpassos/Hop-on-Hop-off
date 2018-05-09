package pt.ulisboa.tecnico.cmu.proj;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by ist426300 on 10-04-2018.
 */

public class JsonHandler {

    //Create JSON string for Login
    public static String LoginToServer(String user, String code){
        String tmp;

        JSONObject obj_t = new JSONObject();

        try {
            obj_t.put("username", user);
            Log.d("-----JSONHandler----- Code", obj_t.toString());
            obj_t.put("password", code);
            Log.d("-----JSONHandler----- Code", obj_t.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return obj_t.toString();
    }
    //Decode JSON string for Login
    public static String[] LoginFromServer(String response){
        String tmp[] = new String[2];

        try {
            JSONObject reader = new JSONObject(response);
            tmp[0] = reader.getString("session");
            tmp[1] = reader.getString("message");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return tmp;
    }

    //Create JSON string for Logout command
    /* Format
     * 	{
  		"logout":
        	{"session id": "x",
        	 "username": "XX",
        	 "response": "asassa"
        	}
		}
     */
    public static String LogouToServer(String user, String sid){
        String tmp;

        JSONObject obj_t = new JSONObject();
        JSONObject obj_return = new JSONObject();

        try {
            obj_t.put("session id", sid);
            Log.d("-----JSONHandler----- Code", obj_t.toString());
            obj_t.put("username", user);
            Log.d("-----JSONHandler----- Code", obj_t.toString());
            obj_t.put("response", "");
            Log.d("-----JSONHandler----- Code", obj_t.toString());
            obj_return.put("logout",obj_t);
            Log.d("-----JSONHandler----- Code", obj_return.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return obj_return.toString();
    }
    //Decode JSON string for Logout command
    public static String LogOutFromServer(String response){
        String tmp = "";

        try {
            JSONObject reader = new JSONObject(response);
            JSONObject logout = reader.getJSONObject("logout");
            tmp = logout.getString("response");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return tmp;
    }



}


