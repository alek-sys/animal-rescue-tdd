package rescue.adoption;

import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
class JdbcAdoptionRequestsImpl implements AdoptionRequests {

	private final AdoptionRequestsRepository adoptionRequestsRepository;

	public JdbcAdoptionRequestsImpl(AdoptionRequestsRepository adoptionRequestsRepository) {
		this.adoptionRequestsRepository = adoptionRequestsRepository;
	}

	@Override
	public Integer adopt(Integer animalId, String adopterName, String email, String notes) {
		AdoptionRequestEntity entity = new AdoptionRequestEntity(animalId, adopterName, email, notes);
		Integer id = this.adoptionRequestsRepository.save(entity).getId();
		if (id == null) {
			throw new IllegalStateException("id cannot be null after save");
		}

		return id;
	}

	@Override
	public void editRequest(Integer requestId, String adopterName, String email, String notes) throws RequestNotFoundException {
		AdoptionRequestEntity existingRequest = this.adoptionRequestsRepository.findById(requestId)
			.orElseThrow(RequestNotFoundException::new);

		if (!adopterName.equals(existingRequest.getAdopterName())) {
			throw new RequestNotFoundException();
		}

		existingRequest.setEmail(email);
		existingRequest.setNotes(notes);
		this.adoptionRequestsRepository.save(existingRequest);
	}

	@Override
	public Iterable<AdoptionRequest> getAllFor(Integer animal) {
		return StreamSupport.stream(this.adoptionRequestsRepository.findAllForAnimal(animal).spliterator(), false)
			.map(this::mapRequest)
			.collect(Collectors.toList());
	}

	private AdoptionRequest mapRequest(AdoptionRequestEntity entity) {
		return new AdoptionRequest(entity.getId(), entity.getAdopterName(), entity.getEmail(), entity.getNotes());
	}
}
