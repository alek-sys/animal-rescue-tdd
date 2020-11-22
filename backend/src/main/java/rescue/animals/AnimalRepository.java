package rescue.animals;

import org.springframework.data.repository.CrudRepository;

interface AnimalRepository extends CrudRepository<AnimalEntity, Integer> {
}
