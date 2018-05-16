package algorithm_data.Questions;

import java.util.ArrayList;

class Rank{
	private String monument;//description
	private String numQuestions;
	private String numCorrectQuestions;
	private String total;
	
	public Rank(String m, String nq, String ncq, String t) {
		this.monument = m;
		this.numQuestions = nq;
		this.numCorrectQuestions = ncq;
		this.total = t;
	}
	public String getMonument() {
		return monument;
	}
	
	public String getNumQuestions() {
		return numQuestions;
	}
	
	public String getNumCorrectQuestions() {
		return numCorrectQuestions;
	}
	
	public String getTotal() {
		return total;
	}
	
}

public class RankingToSend {
	private String global;
	private ArrayList<Rank> cont;
	
	public RankingToSend(String global) {
		this.global = global;
		cont = new ArrayList<Rank>();
	}
	
	public void insert(String m, String nq, String ncq, String t) {
		cont.add(new Rank(m, nq, ncq, t));
	}
	
	public String getGlobalRanking() {
		return global;
	}
	public int size() {
		return cont.size();
	}
	
	public String getMonument(int pos) {
		return cont.get(pos).getMonument();
	}
	
	public String getNumQuestions(int pos) {
		return cont.get(pos).getNumQuestions();
	}
	
	public String getNumCorrectQuestions(int pos) {
		return cont.get(pos).getNumCorrectQuestions();
	}
	
	public String getTotal(int pos) {
		return cont.get(pos).getTotal();
	}
	

}
