package algorithm_data.Questions;

public class Monument {
	private int id;
	private String description;
	
	public Monument(int id, String val) {
		this.id = id;
		description = val;
	}

	public int getMonumentID() {
		return id;
	}
	
	public String getMonumentDescription() {
		return description;
	}
	
}
