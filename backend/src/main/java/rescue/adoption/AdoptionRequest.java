package rescue.adoption;

@SuppressWarnings("nullness")
public class AdoptionRequest {

	private Integer id;
	private String adopterName;
	private String email;
	private String notes;

	public AdoptionRequest(Integer id, String adopterName, String email, String notes) {
		this.id = id;
		this.adopterName = adopterName;
		this.email = email;
		this.notes = notes;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAdopterName() {
		return adopterName;
	}

	public void setAdopterName(String adopterName) {
		this.adopterName = adopterName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
