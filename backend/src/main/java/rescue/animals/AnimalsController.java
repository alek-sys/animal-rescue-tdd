package rescue.animals;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rescue.adoption.AdoptionRequest;
import rescue.adoption.AdoptionRequests;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/animals")
class AnimalsController {

	private final Animals animals;
	private final AdoptionRequests adoptionRequests;

	public AnimalsController(Animals animals, AdoptionRequests adoptionRequests) {
		this.animals = animals;
		this.adoptionRequests = adoptionRequests;
	}

	@GetMapping
	Iterable<Animal> animals() {
		return StreamSupport.stream(this.animals.getAll().spliterator(), false)
			.peek(animal -> {
				Iterable<AdoptionRequest> adoptionRequests = this.adoptionRequests.getAllFor(animal.getId());
				animal.setAdoptionRequests(adoptionRequests);
			})
			.collect(Collectors.toList());
	}
}
