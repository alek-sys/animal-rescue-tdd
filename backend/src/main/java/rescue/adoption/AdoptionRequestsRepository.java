package rescue.adoption;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

interface AdoptionRequestsRepository extends CrudRepository<AdoptionRequestEntity, Integer> {

	@Query("select * from ADOPTION_REQUESTS where animal = :animalId")
	Iterable<AdoptionRequestEntity> findAllForAnimal(@Param("animalId") Integer animalId);
}
