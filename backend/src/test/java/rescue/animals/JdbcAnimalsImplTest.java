package rescue.animals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJdbcTest
class JdbcAnimalsImplTest {

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

	@Test
	void shouldFilterAnimalsPendingAdoption() {
		String expectedName = "Animal pending adoption name";
		AnimalEntity test = new AnimalEntity(expectedName, "");
		test.setPendingAdoption(true);

		this.animalRepository.save(test);

		Iterable<Animal> animals = this.animals.getAll();

		assertThat(animals).extracting(Animal::getName).doesNotContain(expectedName);
	}

	@Test
	void shouldMapDescription() {
		AnimalEntity test = new AnimalEntity("name", "");
		test.setDescription("test description");

		this.animalRepository.save(test);

		Iterable<Animal> animals = this.animals.getAll();

		assertThat(animals).extracting(Animal::getDescription).contains("test description");
	}

	@Test
	void shouldMapRescueDate() {
		AnimalEntity test = new AnimalEntity("name", "");
		test.setRescueDate(LocalDate.of(2019, 12, 20));

		this.animalRepository.save(test);

		Iterable<Animal> animals = this.animals.getAll();

		assertThat(animals).extracting(Animal::getRescueDate).contains("20 Dec 2019");
	}

	@Test
	void shouldMapEmptyRescueDate() {
		AnimalEntity test = new AnimalEntity("name", "");

		this.animalRepository.save(test);
		Iterable<Animal> animals = this.animals.getAll();

		assertThat(animals).filteredOn(a -> "name".equals(a.getName()))
			.first()
			.extracting(Animal::getRescueDate)
			.isEqualTo("N/A");
	}

	@Test
	void shouldChangeAdoptionStatus() {
		AnimalEntity test = new AnimalEntity("name", "");
		AnimalEntity entity = this.animalRepository.save(test);

		this.animals.markPendingAdoption(entity.getId());

		AnimalEntity updatedEntity = this.animalRepository.findById(entity.getId()).orElseThrow();
		assertThat(updatedEntity.getPendingAdoption()).isTrue();
	}

	@Test
	void shouldThrowExceptionWhenChangingAdoptionStatusForNonExistingAnimal() {
		assertThatThrownBy(() -> this.animals.markPendingAdoption(999))
			.isInstanceOf(IllegalArgumentException.class)
			.extracting(Throwable::getMessage)
			.isEqualTo("No animal found");
	}
}