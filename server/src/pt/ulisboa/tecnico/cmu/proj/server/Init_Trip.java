package pt.ulisboa.tecnico.cmu.proj.server;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import algorithm_data.Questions.Monument;

public class Init_Trip {
	
	public static Monument[] initialize(Monument[] m) {
		String filename = "src/Files/trip.json";
		
		Monument[] arrayOfStops;
		
		JSONParser parser = new JSONParser();
		try {
			File file = new File(filename);
			Object obj = parser.parse( new FileReader(file) );
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray monuments = (JSONArray)jsonObject.get("trip");//Take the array	
			
			arrayOfStops = new Monument[monuments.size()];
			for(int i=0; i<monuments.size(); i++){
				JSONObject tmp = (JSONObject)monuments.get(i);
				
				String id = (String)tmp.get("monument id");
	            
	            for(int j=0; j<m.length; ++j) {
	            	if(m[j].getMonumentID().equals(id)) {
	            		arrayOfStops[i] = m[j];
	            		break;
	            	}
	            }
			}
			return arrayOfStops;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
					
		return null;
	}

}
