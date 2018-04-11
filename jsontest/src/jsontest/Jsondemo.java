package jsontest;

import java.text.ParseException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Jsondemo {

	public static void main(String[] args){
	      JSONObject obj_t = new JSONObject();

	      try {
	    	  obj_t.put("name", "foo");
	    	  obj_t.put("num", new Integer(100));
	    	  obj_t.put("balance", new Double(1000.21));
	    	  obj_t.put("is_vip", new Boolean(true));
	      }
	      catch(Exception e) {
	    	  e.printStackTrace();
	      }
	      System.out.println(obj_t);
	      
	      
	      String s = obj_t.toJSONString();
	      
	      JSONParser parser = new JSONParser();
	      
			
	      try{
	         Object obj = parser.parse(s);
	        //JSONArray array = (JSONArray)obj;
	         JSONArray array = new JSONArray();
	         array.add(obj);
	         JSONObject teste = (JSONObject)array.get(0);
	         System.out.println( teste.get("name"));
			
	         
	         //JSONArray a = (JSONArray)array.get(0);
	         /*System.out.println( array.get(0));
	         System.out.println( (Integer)array.get(1));
	        
	         
	         
	         System.out.println("The 2nd element of array");
	         System.out.println(array.get(0));
	         System.out.println();

	         
	         JSONObject obj2 = (JSONObject)array.get(1);
	         System.out.println("Field \"1\"");
	         System.out.println(obj2.get("1"));    

	         s = "{}";
	         obj = parser.parse(s);
	         System.out.println(obj);

	         s = "[5,]";
	         obj = parser.parse(s);
	         System.out.println(obj);

	         s = "[5,,2]";
	         obj = parser.parse(s);
	         System.out.println(obj);*/
	      }catch(Exception e){
	    	  e.printStackTrace();
	         //System.out.println("position: " + pe.getPosition());
	         //System.out.println(pe);
	      }
	   }
	
}
