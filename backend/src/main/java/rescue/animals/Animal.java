package rescue.animals;

import org.springframework.lang.Nullable;

import java.util.Objects;

public class Animal {
	
	private final int id;
	private final String name;

	public Animal(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Animal that = (Animal) o;
		return id == that.id &&
			Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}
}
