package rescue.animals;

import org.springframework.lang.Nullable;
import rescue.adoption.AdoptionRequest;

import java.util.Collections;
import java.util.Objects;

public class Animal {

	private Integer id;
	private String name;
	private String avatarUrl;
	private String description;
	private String rescueDate;
	private Iterable<AdoptionRequest> adoptionRequests;

	public Animal() {
		this.id = null;
		this.name = "";
		this.avatarUrl = "";
		this.description = "";
		this.rescueDate = "";
		this.adoptionRequests = Collections.emptyList();
	}

	public Animal(Integer id, String name, String avatarUrl) {
		this.id = id;
		this.name = name;
		this.avatarUrl = avatarUrl;
		this.description = "";
		this.rescueDate = "";
		this.adoptionRequests = Collections.emptyList();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRescueDate() {
		return rescueDate;
	}

	public void setRescueDate(String rescueDate) {
		this.rescueDate = rescueDate;
	}

	public void setAdoptionRequests(Iterable<AdoptionRequest> adoptionRequests) {
		this.adoptionRequests = adoptionRequests;
	}

	public Iterable<AdoptionRequest> getAdoptionRequests() {
		return adoptionRequests;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Animal that = (Animal) o;
		return Objects.equals(id, that.id) && Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
