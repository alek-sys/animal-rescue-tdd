package rescue.animals;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.Objects;

@Table("animals")
class AnimalEntity {

	@Id
	private Integer id;

	private String name;

	private String avatarUrl;

	private String description = "";

	private Boolean pendingAdoption = false;

	private LocalDate rescueDate;

	AnimalEntity(String name, String avatarUrl) {
		this.id = null;
		this.name = name;
		this.avatarUrl = avatarUrl;
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

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public Boolean getPendingAdoption() {
		return pendingAdoption;
	}

	public void setPendingAdoption(Boolean pendingAdoption) {
		this.pendingAdoption = pendingAdoption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRescueDate(LocalDate rescueDate) {
		this.rescueDate = rescueDate;
	}

	public LocalDate getRescueDate() {
		return rescueDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnimalEntity that = (AnimalEntity) o;
		return Objects.equals(id, that.id) &&
			Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
