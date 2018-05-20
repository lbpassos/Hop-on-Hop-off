package pt.ulisboa.tecnico.cmu.proj.server;

import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import algorithm_data.Questions.AnswerQuiz;
import algorithm_data.Questions.Question;
import algorithm_data.Questions.QuestionsByMonument;
import algorithm_data.Questions.RankingToSend;

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

    //Decode JSON string received by List command
    /* Format
     * 	{
  		"list command": 
        	{"session id": "x",
        	"username": "XX",
        	"list": 
        			[
        				{"val": "1"},
        				{"val": "2"}
        			]
        	}
		}
     */
    public static String[] ListCommandFromClient(String command){
    	String[] tmp = new String[2];
        
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(command);
        	JSONArray array = new JSONArray();
        	array.add(obj);
        	JSONObject teste = (JSONObject)array.get(0);
        	JSONObject teste_1 = (JSONObject)teste.get("list command");
        	tmp[0] = teste_1.get("session id").toString();
        	tmp[1] = teste_1.get("username").toString();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        return tmp;
    }
    public static String ListCommandFromServer(String session_id, String username, String[] msg){
    	JSONObject[] obj_t = new JSONObject[msg.length];   	
    	JSONArray ja = new JSONArray();
    	JSONObject obj_t_final = new JSONObject();
    	JSONObject obj_t_header = new JSONObject();
    	
    	try {
    		for(int i=0; i<msg.length; ++i) {
    			obj_t[i] = new JSONObject(); 
    			obj_t[i].put("val", msg[i]); 
    			
    			ja.add( obj_t[i] );    			
    		}
    		obj_t_final.put("session id", session_id);
    		obj_t_final.put("username", username);
    		obj_t_final.put("list", ja);
    		obj_t_header.put("list command", obj_t_final);	
    	}
    	catch(Exception e) {
	    	  e.printStackTrace();
	    }
    	
    	return obj_t_header.toJSONString();	 	
    }
    
    
    
  //Decode JSON string received by Logout command
    /* Format
     * 	{
  		"logout": 
        	{"session id": "x",
        	 "username": "XX",
        	 "response": "asassa"
        	}
		}
     */
    public static String[] LogOutCommandFromClient(String command){
    	String[] tmp = new String[2];
        
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(command);
        	JSONArray array = new JSONArray();
        	array.add(obj);
        	JSONObject teste = (JSONObject)array.get(0);
        	JSONObject teste_1 = (JSONObject)teste.get("logout");
        	tmp[0] = teste_1.get("session id").toString();
        	tmp[1] = teste_1.get("username").toString();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        return tmp;
    }
    public static String LogOutCommandFromServer(String session_id, String username, String msg){
    	JSONObject obj_t = new JSONObject();   	
    	JSONArray ja = new JSONArray();
    	JSONObject obj_t_final = new JSONObject();
    	JSONObject obj_t_header = new JSONObject();
    	
    	try {
    		obj_t.put("session id", session_id);
    		obj_t.put("username", username);
    		obj_t.put("response", msg);
    		
    		obj_t_header.put("logout", obj_t);	
    	}
    	catch(Exception e) {
	    	  e.printStackTrace();
	    }
    	
    	return obj_t_header.toJSONString();	 	
    }
    
    
    
    /*
    {
	"ranking": 
	  {"session id": "x",
	    "username": "XX",
	    "response": [
	        {"Monument id": "xx",
	        "Value": "xx",
	        "Rank": "xx"
	        },
	        {"Monument id": "xx",
	        "Value": "xx",
	        "Rank": "xx"
	        }
	    ],
	      "GlobalRank": "12"
	  }
	}
     */
    public static String[] CheckRankingCommandFromClient(String command){
    	String[] tmp = new String[2];
        
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(command);
        	JSONArray array = new JSONArray();
        	array.add(obj);
        	JSONObject teste = (JSONObject)array.get(0);
        	JSONObject teste_1 = (JSONObject)teste.get("ranking");
        	tmp[0] = teste_1.get("session id").toString();
        	tmp[1] = teste_1.get("username").toString();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        return tmp;
    }
    
    public static String CheckRankingCommandFromServer(String session_id, String username, String[] m_id, String[] ca, String[] m_rank, String globalRank){
    	JSONObject[] obj_t = new JSONObject[m_id.length];   	
    	JSONArray ja = new JSONArray();
    	JSONObject obj_t_final = new JSONObject();
    	JSONObject obj_t_header = new JSONObject();
    	
    	try {
    		for(int i=0; i<m_id.length; ++i) {
    			obj_t[i] = new JSONObject(); 
    			obj_t[i].put("Monument id", m_id[i]);
    			obj_t[i].put("Value", ca[i]);
    			obj_t[i].put("Rank", m_id[i]);    			
    			ja.add( obj_t[i] );    			
    		}
    		obj_t_final.put("session id", session_id);
    		obj_t_final.put("username", username);
    		obj_t_final.put("response", ja);
    		obj_t_final.put("GlobalRank", globalRank);
    		obj_t_header.put("ranking", obj_t_final);
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	return obj_t_header.toJSONString();	 	
    }
    
    
    /*
    	{
	"download quiz": 
	  { "session id": "x",
	      "username": "XX",
	   "monument id": "xx",
	   "timestamp": "xx",
       "questions": 
          [
            {
              "q": "In which year was the Zoo founded?",
              "options": [
                {
                  "choice": "1871"
                },
                {
                  "choice": "1884"
                },
                {
                  "choice": "1894"
                },
                {
                  "choice": "1900"
                }
              ]
            }
	        ]
	  }
	}
    */
    public static String[] DownloadQuizCommandFromClient(String command){
    	String[] tmp = new String[3];
        
        JSONParser parser = new JSONParser();
        try {
        	Object obj = parser.parse(command);
        	JSONArray array = new JSONArray();
        	array.add(obj);
        	JSONObject teste = (JSONObject)array.get(0);
        	JSONObject teste_1 = (JSONObject)teste.get("download quiz");
        	tmp[0] = teste_1.get("session id").toString();
        	tmp[1] = teste_1.get("username").toString();
        	tmp[2] = teste_1.get("monument id").toString();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        
        return tmp;
    	
    }
    public static String DownloadQuizCommandFromServer(String session_id, String username, QuestionsByMonument qbm){
    	
    	//get full size of objects
    	int total_choices = 0;
    	for(int i=0; i<qbm.getSize(); ++i) {
    		int sizeOfAnswers = qbm.getQuestion(i).getNumOfChoices(); 
    		total_choices += sizeOfAnswers;
    	}
    	
    	JSONObject[] obj_choices = new JSONObject[total_choices];
    	//JSONObject[] obj_questions = new JSONObject[qbm.getSize()];
    	JSONArray obj_questions = new JSONArray();;
    		
    	JSONArray[] options = new JSONArray[qbm.getSize()]; //JSONArray();
    	
    	int idx = 0;
    	for(int i=0; i<qbm.getSize(); ++i) {//number of questions
    		options[i] = new JSONArray();
    		Question q = qbm.getQuestion(i);//take question
    		for(int j=0; j<qbm.getQuestion(i).getNumOfChoices(); ++j) {
    			obj_choices[idx] = new JSONObject(); 
    			obj_choices[idx].put("choice", q.getChoice(j));
    			options[i].add( obj_choices[idx] );
    			++idx;
    		}
    		//obj_questions[i] = new JSONObject();
    		JSONObject tmp = new JSONObject();
    		tmp.put("q", q.getQuestion());
    		tmp.put("options", options[i]);
    		obj_questions.add(tmp);
    		//obj_questions[i].put("q", q.getQuestion());
    		//obj_questions[i].put("options", options[i]);

    	}
    	JSONObject obj_final = new JSONObject(); 
    	obj_final.put("questions", obj_questions);
    	obj_final.put("monument id", qbm.getMonument().getMonumentID());
    	obj_final.put("session id", session_id);
    	obj_final.put("username", username);
    	
    	//Timestamp timestamp = new Timestamp(System.currentTimeMillis()); //get the timestamp
    	//obj_final.put("timestamp", timestamp.toString());
    	
    	
    	JSONObject obj_toReturn = new JSONObject(); 
    	obj_toReturn.put("download quiz", obj_final);
    	
    	return obj_toReturn.toJSONString();
    	//return obj_questions.toString();
    }
    
    
    
    
    
    /*
    {
	"upload quiz":
	  { "session id": "x",
	      "username": "XX",
	   "monument id download": "xx",
	   "monument id upload": "xx",
	   "timeOfQuizAnswer": "xx",
       "questions":
          [
            {
              "options": [
                {
                  "answer": true
                },
                {
                  "answer": false
                },
                {
                  "answer": false
                },
                {
                  "answer": false
                }
              ]
            }
	        ]
	  }
	}
    */
    public static AnswerQuiz UploadAnswerQuizFromClient(String command){
    	
    	AnswerQuiz aq = null;
    	
    	JSONParser parser = new JSONParser();
    	try {
        	Object obj = parser.parse(command);
        	JSONArray array = new JSONArray();
        	array.add(obj);
        	JSONObject teste = (JSONObject)array.get(0);
        	JSONObject teste_1 = (JSONObject)teste.get("upload quiz");
        	
        	String sid = teste_1.get("session id").toString();
        	String username = teste_1.get("username").toString();
        	String monuDown = teste_1.get("monument id download").toString();
        	String monuUp = teste_1.get("monument id upload").toString();
        	String timeStamp = teste_1.get("timeOfQuizAnswer").toString();
        	
        	aq = new AnswerQuiz(monuDown, monuUp, sid, username, timeStamp);
        	
        	JSONArray array_t = (JSONArray)teste_1.get("questions");
        	for(int i=0; i<array_t.size(); ++i) {
        		
        		JSONObject obj_tmp = (JSONObject)array_t.get(i);
        		JSONArray array_answers = (JSONArray)obj_tmp.get("options");
        		
        		boolean[] flags = new boolean[array_answers.size()];
        		for(int j=0; j<array_answers.size();++j) {
        			JSONObject obj_tmp_choice = (JSONObject)array_answers.get(j);
        			flags[j]=(boolean)obj_tmp_choice.get("answer");        			
        		}
        		aq.insertVals(flags);
        	}
        	
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    	
        return aq;
    }
        /*{
            "upload quiz":
                {
                    "session id": "x",
                    "username": "XX",
                    "Monument description": "xx",
                    "Number of Questions Answered": "xx",
                    "Number of Correct Questions": "xx"
                }
            }
        */
        public static String UploadAnswerQuizFromServer(String sid, String user, String m, String numQ, String numCQ, String ts){
            String tmp = "";
            try {
                JSONObject reader = new JSONObject();
                reader.put("session id", sid);
                reader.put("username", user);
                reader.put("Monument description", m);
                reader.put("Number of Questions Answered", numQ);
                reader.put("Number of Correct Questions", numCQ);
                reader.put("TimeStamp", ts);
                
                JSONObject UploadAnswerQuiz = new JSONObject();
                UploadAnswerQuiz.put("upload quiz", reader);
                tmp = UploadAnswerQuiz.toJSONString();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return tmp;
        }
        
        
        
        
        
        /*
        {
		"ranking":
	  		{ "session id": "x",
	      "username": "XX",
	        "global": "xx",
	        "result": [
	          {
	                        "monument desc": "xx",
	                        "numQuestions": "xx",
	                        "numCorrectQuestions": "xx",
        	                "total": "xx"
	          }
	          ]
	  }
}
        */
        public static String[] GetRankingFromClient(String command){
        	String[] tmp = new String[2];
            
            JSONParser parser = new JSONParser();
            try {
            	Object obj = parser.parse(command);
            	JSONArray array = new JSONArray();
            	array.add(obj);
            	JSONObject teste = (JSONObject)array.get(0);
            	JSONObject teste_1 = (JSONObject)teste.get("ranking");
            	tmp[0] = teste_1.get("session id").toString();
            	tmp[1] = teste_1.get("username").toString();
            }
            catch(Exception e) {
            	e.printStackTrace();
            }
            
            return tmp;
        }
        public static String GetRankingFromServer(String sid, String user, RankingToSend rts ){
        	String tmp = "";
        	
        	JSONObject[] reader = new JSONObject[rts.size()];
        	JSONArray array_reader = new JSONArray();
        	JSONObject monument_obj = new JSONObject();
        	JSONObject final_obj = new JSONObject();
            try {
            	for(int i=0; i<reader.length; ++i) {
            		reader[i] = new JSONObject();
            		reader[i].put("monument desc", rts.getMonument(i));
            		reader[i].put("numQuestions", rts.getNumQuestions(i));
            		reader[i].put("numCorrectQuestions", rts.getNumCorrectQuestions(i));
            		reader[i].put("total", rts.getTotal(i));
            		array_reader.add(reader[i]);
            		
            	}
            	final_obj.put("result", array_reader);
            	final_obj.put("global", rts.getGlobalRanking());
            	final_obj.put("username", user);
            	final_obj.put("session id", sid);
            	monument_obj.put("ranking", final_obj);
            	
            	
            	tmp = monument_obj.toJSONString();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return tmp;
	
        }
}
