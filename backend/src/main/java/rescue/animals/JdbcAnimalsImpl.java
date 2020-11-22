package rescue.animals;

import org.springframework.stereotype.Component;

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
		return new Animal(animalEntity.getId(), animalEntity.getName(), animalEntity.getAvatarUrl());
	}
}
