package algorithm_data.Questions;

public class Monument {
	private String id;
	private String description;
	
	public Monument(String id, String val) {
		this.id = id;
		description = val;
	}

	public String getMonumentID() {
		return id;
	}
	
	public String getMonumentDescription() {
		return description;
	}
	
}
