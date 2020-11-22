package rescue.animals;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

interface AnimalRepository extends CrudRepository<AnimalEntity, Integer> {

	@Query("select * from ANIMALS where pending_adoption=0")
	Iterable<AnimalEntity> findAllAvailableForAdoption();
}
