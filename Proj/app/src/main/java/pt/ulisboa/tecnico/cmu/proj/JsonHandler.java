package pt.ulisboa.tecnico.cmu.proj;

import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import pt.ulisboa.tecnico.cmu.proj.questions.Choice;
import pt.ulisboa.tecnico.cmu.proj.questions.Question;
import pt.ulisboa.tecnico.cmu.proj.questions.QuestionsByMonument;
import pt.ulisboa.tecnico.cmu.proj.ranking.RankingToShow;

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



    //Create JSON string for ListLocations command
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
    public static String ListLocationsToServer(String user, String sid){
        String tmp;
        String[] to_Return;

        JSONObject obj_t = new JSONObject();
        JSONArray arrJSON = new JSONArray();

        JSONObject obj_return = new JSONObject();

        try {
            obj_t.put("session id", sid);
            obj_t.put("username", user);
            obj_t.put("list", arrJSON);
            obj_return.put("list command",obj_t);
            Log.d("-----JSONHandler----- Code", obj_return.toString());
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return obj_return.toString();
    }
    //Decode JSON string for ListLocations command
    public static String[] ListLocationsFromServer(String response){
        String tmp = "";

        JSONArray arrJSON = new JSONArray();

        try {
            String[] to_Return;
            JSONObject reader = new JSONObject(response);
            JSONObject listCommand = reader.getJSONObject("list command");
            tmp = listCommand.getString("list");
            arrJSON = new JSONArray(tmp);
            to_Return = new String[arrJSON.length()];
            for(int i =0; i<arrJSON.length(); ++i){
                JSONObject item = arrJSON.getJSONObject(i);
                to_Return[i] = item.getString("val");
            }
            return to_Return;

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /*
    	{
	"download quiz":
	  { "session id": "x",
	      "username": "XX",
	   "monument id": "xx",
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
    public static String DownloadQuizToServer(String user, String sid, String monumentId){
        JSONArray arrJSON = new JSONArray(); //empty array
        JSONObject obj_t = new JSONObject();
        JSONObject obj_f = new JSONObject();
        try {
            obj_t.put("questions", arrJSON);
            obj_t.put("monument id", monumentId);
            obj_t.put("username", user);
            obj_t.put("session id", sid);
            obj_f.put("download quiz", obj_t);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return obj_f.toString();
    }
    public static QuestionsByMonument DownloadQuizFromServer(String response){
        QuestionsByMonument qm = null;

        try {
            JSONObject reader = new JSONObject(response);
            JSONObject downloadQuizCommand = reader.getJSONObject("download quiz");

            qm = new QuestionsByMonument( downloadQuizCommand.getString("monument id") ); //get monument id

            JSONArray arrJSON  = downloadQuizCommand.getJSONArray("questions");

            //JSONObject questions = reader.getJSONObject("questions");
            //JSONArray arrJSON = new JSONArray(questions);
            //String[] to_Return = new String[arrJSON.length()];
            for(int i =0; i<arrJSON.length(); ++i){
                JSONObject item = arrJSON.getJSONObject(i);
                Question q_tmp = new Question(item.getString("q"));

                JSONArray arrJSON_options = item.getJSONArray("options");
                //JSONObject options = item.getJSONObject("options");
                //JSONArray arrJSON_options = new JSONArray(options);
                for(int j=0; j<arrJSON_options.length(); ++j) {
                    JSONObject item_choice = arrJSON_options.getJSONObject(j);
                    Choice ch_tmp = new Choice(item_choice.getString("choice"));
                    q_tmp.insertAnswer(ch_tmp);
                }
                qm.addQuestion(q_tmp);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
       return qm;
    }
    /*
    {
	"upload quiz":
	  { "session id": "x",
	      "username": "XX",
	   "monument id download": "xx",
	   "monument id upload": "xx",
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
    public static String UploadAnswerQuizToServer(String user, String sid, String monumentId_u, QuestionsByMonument qbm){

        //get full size of objects
        int total_choices = 0;
        for(int i=0; i<qbm.getSize(); ++i) {
            int sizeOfAnswers = qbm.getQuestion(i).getNumOfChoices();
            total_choices += sizeOfAnswers;
        }

        JSONObject[] obj_choices = new JSONObject[total_choices];
        //JSONObject[] obj_questions = new JSONObject[qbm.getSize()];
        JSONArray obj_questions = new JSONArray();

        JSONArray[] options = new JSONArray[qbm.getSize()]; //JSONArray();

        int idx = 0;
        for(int i=0; i<qbm.getSize(); ++i) {//number of questions
            options[i] = new JSONArray();
            Question q = qbm.getQuestion(i);//take question
            for(int j=0; j<qbm.getQuestion(i).getNumOfChoices(); ++j) {
                obj_choices[idx] = new JSONObject();
                try {
                    obj_choices[idx].put("answer", q.getChoiceObj(j).getStatus());
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                options[i].put( obj_choices[idx] );
                ++idx;
            }
            //obj_questions[i] = new JSONObject();
            JSONObject tmp = new JSONObject();
            try {
                tmp.put("options", options[i]);
            }
            catch(Exception e){
                e.printStackTrace();
            }
                obj_questions.put(tmp);
            //obj_questions[i].put("q", q.getQuestion());
            //obj_questions[i].put("options", options[i]);

        }
        JSONObject obj_final = new JSONObject();
        try {
            obj_final.put("questions", obj_questions);
            obj_final.put("monument id upload", monumentId_u);
            obj_final.put("monument id download", qbm.getMonument());
            obj_final.put("session id", sid);
            obj_final.put("username", user);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        JSONObject obj_toReturn = new JSONObject();
        try {
            obj_toReturn.put("upload quiz", obj_final);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return obj_toReturn.toString();
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
        public static String[] UploadAnswerQuizFromServer(String response){
            String[] tmp = new String[3];
            try {
                JSONObject reader = new JSONObject(response);
                JSONObject UploadAnswerQuiz = reader.getJSONObject("upload quiz");
                tmp[0] = UploadAnswerQuiz.getString("Number of Questions Answered");
                tmp[1] = UploadAnswerQuiz.getString("Number of Correct Questions");
                tmp[2] = UploadAnswerQuiz.getString("Monument description");
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
    public static String GetRankingToServer(String user, String sid){
        JSONArray options = new JSONArray();
        JSONObject obj = new JSONObject();
        JSONObject obj_f = new JSONObject();
        try {
            obj.put("result", options);
            obj.put("global", "");
            obj.put("session id", sid);
            obj.put("username", user);
            obj_f.put("ranking", obj);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return obj_f.toString();
    }
    public static RankingToShow GetRankingFromServer(String response){
        RankingToShow r = null;
        try {
            JSONObject reader = new JSONObject(response);
            JSONObject ranking = reader.getJSONObject("ranking");
            String global = ranking.getString("global");

            r = new RankingToShow(global);

            JSONArray jarray = ranking.getJSONArray("result");
            for(int i=0; i<jarray.length(); ++i){
                JSONObject obj_tmp = jarray.getJSONObject(i);
                //JSONObject obj_tmp_1 = obj_tmp.getJSONObject("monument");
                String description = obj_tmp.getString("monument desc");
                String numQ = obj_tmp.getString("numQuestions");
                String numCQ = obj_tmp.getString("numCorrectQuestions");
                String t =  obj_tmp.getString("total");
                r.insert(description, numQ, numCQ, t);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return r;

    }
}


