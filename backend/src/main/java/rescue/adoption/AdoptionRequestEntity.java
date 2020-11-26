package rescue.adoption;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

@Table("adoption_requests")
class AdoptionRequestEntity {

	@Id
	@Nullable
	private Integer id;
	private Integer animal;
	private String adopterName;
	private String email;
	private String notes;

	public AdoptionRequestEntity(Integer animal, String adopterName, String email, String notes) {
		this.id = null;
		this.animal = animal;
		this.adopterName = adopterName;
		this.email = email;
		this.notes = notes;
	}

	@Nullable
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAnimal() {
		return animal;
	}

	public void setAnimal(Integer animal) {
		this.animal = animal;
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
