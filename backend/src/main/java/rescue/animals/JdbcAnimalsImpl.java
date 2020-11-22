package rescue.animals;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
class JdbcAnimalsImpl implements Animals {

	private final AnimalRepository animalRepository;

	public JdbcAnimalsImpl(AnimalRepository animalRepository) {
		this.animalRepository = animalRepository;
	}

	@Override
	public Iterable<Animal> getAll() {
		List<Animal> animals = StreamSupport
			.stream(this.animalRepository.findAllAvailableForAdoption().spliterator(), false)
			.map(this::toAnimal)
			.collect(Collectors.toList());

		return animals;
	}

	private Animal toAnimal(AnimalEntity animalEntity) {
		Animal animal = new Animal(animalEntity.getId(), animalEntity.getName(), animalEntity.getAvatarUrl());
		animal.setDescription(animalEntity.getDescription());
		animal.setRescueDate(formatDate(animalEntity.getRescueDate()));
		return animal;
	}

	private String formatDate(LocalDate date) {
		if (date == null) {
			return "N/A";
		}

		return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
	}
}
