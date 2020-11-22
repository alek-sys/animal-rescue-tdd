package rescue.animals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
class AnimalsTest {

	@Autowired
	AnimalRepository animalRepository;

	private Animals animals;

	@BeforeEach
	void setUp() {
		this.animals = new JdbcAnimalsImpl(this.animalRepository);
	}

	@Test
	void shouldReturnListOfAnimalsFromDatabase() {
		String expectedAnimalName = "Mr Huggins";
		String expectedAvatarUrl = "https://example.com/avatar.jpg";

		this.animalRepository.save(new AnimalEntity(expectedAnimalName, expectedAvatarUrl));

		Iterable<Animal> animals = this.animals.getAll();

		assertThat(animals)
			.filteredOn(animal -> expectedAnimalName.equals(animal.getName()))
			.first()
			.hasFieldOrPropertyWithValue("name", expectedAnimalName)
			.hasFieldOrPropertyWithValue("avatarUrl", expectedAvatarUrl);
	}
}