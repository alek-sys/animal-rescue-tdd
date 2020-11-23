package rescue.adoption;

public interface AdoptionRequests {
	Integer adopt(Integer animalId, String adopterName, String email, String notes);
	void editRequest(Integer requestId, String adopterName, String email, String notes) throws RequestNotFoundException;

	Iterable<AdoptionRequest> getAllFor(Integer animal);
}
