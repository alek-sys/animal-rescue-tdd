package rescue.adoption;

public interface AdoptionRequests {
	Integer adopt(Integer animalId, String adopter, String email, String notes);
	Iterable<AdoptionRequest> getAllFor(Integer animal);
}
