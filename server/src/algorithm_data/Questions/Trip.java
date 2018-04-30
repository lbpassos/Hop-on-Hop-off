package algorithm_data.Questions;

import java.util.ArrayList;

public class Trip {
	private Monument next;
	private Monument current;
	private Monument prev;
	private ArrayList<Monument> route;
	private boolean alreadyInMovement;
	private int stop;
	
	public Trip() {
		next = null;
		prev = null;
		current = null;
		route = new ArrayList<Monument>();
		alreadyInMovement = false;
		stop = 0;
	}
	
	public void addStop(Monument m, int pos) {
		route.add(pos, m);
	}
	
	public void startTrip() {
		next = route.get(stop);
	}
	
	/*Must be called before movement getToNextMonument*/
	public boolean isTourEnded() {
		if(stop+1==route.size()) {
			return true;
		}
		return false;
	}
	
	public void getToNextMonument() {
		if(alreadyInMovement==false) {
			next = route.get(stop+1); 
			prev = current;
			current = null;
			alreadyInMovement = true;
		}	
	}
	
	public void inMonument() {
		if(alreadyInMovement==true) {
			alreadyInMovement = false;
			current = next;
			next = null;
			++stop;
		}
	}
	
	public Monument previousStop() {
		return prev;
	}
	
	public Monument currentStop() {
		return current;
	}
}
