package pt.ulisboa.tecnico.cmu.proj.server;

import java.io.File;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import algorithm_data.Questions.Choice;
import algorithm_data.Questions.Monument;
import algorithm_data.Questions.Question;
import algorithm_data.Questions.QuestionsByMonument;

public class Init_Quizzes {

	public static QuestionsByMonument[] initialize(Monument[] m) {
		String filename = "src/Files/quizzes.json";
		
		QuestionsByMonument[] quizzes;
		
		JSONParser parser = new JSONParser();
		try {
			File file = new File(filename);
			Object obj = parser.parse( new FileReader(file) );
			JSONObject jsonObject = (JSONObject) obj;
			
			JSONArray quizzesAll = (JSONArray)jsonObject.get("quizzes");//Take the array	
						
			quizzes = new QuestionsByMonument[quizzesAll.size()];
			
			/* Search for each quizz */
			for(int i=0; i<quizzesAll.size(); ++i) {
				JSONObject tmp = (JSONObject)quizzesAll.get(i);
				JSONObject oneQuiz = (JSONObject)tmp.get("quiz");
				String monument_id = (String)oneQuiz.get("monument id");
				
				/*Check monument id*/
				for(int insideM=0; insideM<m.length; ++insideM) {
					if(m[insideM].getMonumentID().equals(monument_id)) {
						quizzes[i] = new QuestionsByMonument(m[insideM]);
						break;
					}
				}
				
				JSONArray quiz = (JSONArray)oneQuiz.get("questions");//Take the array
				for(int j=0; j<quiz.size(); ++j) {
					JSONObject tmp_2 = (JSONObject)quiz.get(j);
					String q = (String)tmp_2.get("q");
					Question question_obj = new Question(q); //Question created
					JSONArray options = (JSONArray)tmp_2.get("options");//Take the array
					for(int k=0; k<options.size(); ++k) {
						JSONObject tmp_3 = (JSONObject)options.get(k);
						String val = (String)tmp_3.get("choice");
						boolean correctness = (boolean)tmp_3.get("correct");
						Choice op = new Choice(val, correctness);
						question_obj.insertAnswer(op);
					}
					quizzes[i].addQuestion(question_obj);	
							
				}				
			}
			
			return quizzes;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
					
		return null;
	}
}
