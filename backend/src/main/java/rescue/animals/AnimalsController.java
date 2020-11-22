package rescue.animals;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animals")
class AnimalsController {

	private final Animals animals;

	public AnimalsController(Animals animals) {
		this.animals = animals;
	}

	@GetMapping
	Iterable<Animal> animals() {
		return this.animals.getAll();
	}
}
