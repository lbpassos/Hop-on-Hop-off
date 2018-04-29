package pt.ulisboa.tecnico.cmu.proj.server;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import algorithm_data.Questions.Monument;

public class Init_Monuments {
	
	public static Monument[] initialize() {
		String filename = "src/Files/monuments.json";
		
		Monument[] arrayOfMonuments;
		
		JSONParser parser = new JSONParser();
		try {
			File file = new File(filename);
			Object obj = parser.parse( new FileReader(file) );
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray monuments = (JSONArray)jsonObject.get("monument");//Take the array	
			
			arrayOfMonuments = new Monument[monuments.size()];
			for(int i=0; i<monuments.size(); i++){
				JSONObject tmp = (JSONObject)monuments.get(i);
				
				String id = (String)tmp.get("id");
	            String name = (String)tmp.get("name");
	            
	            arrayOfMonuments[i] = new Monument( id, name);
			}
			return arrayOfMonuments;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
					
		return null;
	}
}
