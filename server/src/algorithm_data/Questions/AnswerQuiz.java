package algorithm_data.Questions;

import java.util.ArrayList;

class AnswersBoolean{
	private boolean[] values;
	public AnswersBoolean(int sz) {
		values = new boolean[sz];
	}
	
	public boolean[] get() {
		return values;
	}
	
	public void insert(int pos, boolean b) {
		values[pos] = b;
	}
	
	public int size() {
		return values.length;
	}
}


public class AnswerQuiz { //Just for dealing with the answer
	private String monumentId_download;
	private String monumentId_upload;
	private String sessionId;
	private String username;
	private ArrayList<AnswersBoolean> quest;
	
	public AnswerQuiz(String md, String mu, String sid, String user) {
		this.monumentId_download = md;
		this.monumentId_upload = mu;
		this.sessionId = sid;
		this.username = user;
		quest = new ArrayList<AnswersBoolean>();
	}
	
	public void insertVals(boolean[] v) {
		AnswersBoolean tmp = new AnswersBoolean(v.length);
		for(int i=0; i<v.length; ++i) {
			tmp.insert(i, v[i]);
		}
		quest.add(tmp);
	}
	
	public boolean[] getVals(int pos) {
		return quest.get(pos).get();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getSessionID() {
		return sessionId;
	}
	
	public String getPlaceOfDownload() {
		return monumentId_download;
	}
	
	public String getPlaceOfUpload() {
		return monumentId_upload;
	}

}
