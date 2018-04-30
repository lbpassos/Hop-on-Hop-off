package algorithm_data.Questions;

import java.util.ArrayList;
import java.util.Comparator;

import authentication.User;

class CompareByMonument implements Comparator<User> {
	private Monument m;
	
	public CompareByMonument(Monument m) {
		this.m = m;
	}
	
	@Override
    public int compare(User u1, User u2) {
		IndividualPoints o1 = u1.getPoints();
		IndividualPoints o2 = u2.getPoints();
		
		if(o1.getNumberOfCorrectQuestionsAnswered(m)>o2.getNumberOfCorrectQuestionsAnswered(m)) {
			return 1;
		}
		if(o1.getNumberOfCorrectQuestionsAnswered(m)==o2.getNumberOfCorrectQuestionsAnswered(m)) {
			return 0;
		}
		return -1;
    }
}

class CompareByTotal implements Comparator<User> {
		
	@Override
    public int compare(User u1, User u2) {
		IndividualPoints o1 = u1.getPoints();
		IndividualPoints o2 = u2.getPoints();
		
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
	private ArrayList<User> total;
	
	public TotalRanking() {
		total = new ArrayList<User>();
	}
	
	public void insert(User ip) {
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
			User ip = total.get(i);
			rank[i] = new Ranking( ip.getName(), ip.getPoints().getNumberOfCorrectQuestionsAnswered(m));
		}
		return rank;		
	}
	
	public Ranking[] global() {
		total.sort(new CompareByTotal() );
		
		Ranking[] rank = new Ranking[total.size()];
		for(int i=0; i<total.size(); ++i) {
			User ip = total.get(i);
			rank[i] = new Ranking(ip.getName(), ip.getPoints().getTotalPoints() );
		}
		return rank;		
	}
}
