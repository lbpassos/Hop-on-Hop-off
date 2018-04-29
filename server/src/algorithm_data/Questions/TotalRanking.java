package algorithm_data.Questions;

import java.util.ArrayList;
import java.util.Comparator;

class CompareByMonument implements Comparator<IndividualPoints> {
	private Monument m;
	
	public CompareByMonument(Monument m) {
		this.m = m;
	}
	
	@Override
    public int compare(IndividualPoints o1, IndividualPoints o2) {
		if(o1.getNumberOfCorrectQuestionsAnswered(m)>o2.getNumberOfCorrectQuestionsAnswered(m)) {
			return 1;
		}
		if(o1.getNumberOfCorrectQuestionsAnswered(m)==o2.getNumberOfCorrectQuestionsAnswered(m)) {
			return 0;
		}
		return -1;
    }
}

class CompareByTotal implements Comparator<IndividualPoints> {
		
	@Override
    public int compare(IndividualPoints o1, IndividualPoints o2) {
		int total_o1 = o1.getTotalPoints();
		int total_o2 = o2.getTotalPoints();
		
		if(total_o1>total_o2) {
			return 1;
		}
		if(total_o1==total_o2) {
			return 0;
		}
		return -1;
    }
}

public class TotalRanking {
	private ArrayList<IndividualPoints> total;
	
	public TotalRanking() {
		total = new ArrayList<IndividualPoints>();
	}
	
	public void insert(IndividualPoints ip) {
		if( total.contains(ip)==true ) {
			total.remove(ip);
			total.add(ip);
			
		}
		total.add(ip);
	}
	
	public Ranking[] sortByMonument(Monument m) {
		total.sort(new CompareByMonument(m));
		
		Ranking[] rank = new Ranking[total.size()];
		for(int i=0; i<total.size(); ++i) {
			IndividualPoints ip = total.get(i);
			rank[i] = new Ranking(ip.getUser(), ip.getNumberOfCorrectQuestionsAnswered(m));
		}
		return rank;		
	}
	
	public Ranking[] global(Monument m) {
		total.sort(new CompareByTotal() );
		
		Ranking[] rank = new Ranking[total.size()];
		for(int i=0; i<total.size(); ++i) {
			IndividualPoints ip = total.get(i);
			rank[i] = new Ranking(ip.getUser(), ip.getNumberOfCorrectQuestionsAnswered(m));
		}
		return rank;		
	}
}
