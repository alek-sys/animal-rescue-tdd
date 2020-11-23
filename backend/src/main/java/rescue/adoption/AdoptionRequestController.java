package rescue.adoption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.security.Principal;

@RestController
@RequestMapping("/animals/{animalId}/adoption-requests")
class AdoptionRequestController {

	private final AdoptionRequests adoptionRequests;

	AdoptionRequestController(AdoptionRequests adoptionRequests) {
		this.adoptionRequests = adoptionRequests;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	void createNewRequest(
		@PathVariable Integer animalId,
		@RequestBody @Validated Request request,
		Principal principal
	) {
		this.adoptionRequests.adopt(animalId, principal.getName(), request.getEmail(), request.getNotes());
	}

	@PutMapping("/{requestId}")
	@ResponseStatus(HttpStatus.OK)
	void editRequest(
		@PathVariable Integer requestId,
		@RequestBody @Validated Request request,
		Principal principal
	) throws RequestNotFoundException {
		this.adoptionRequests.editRequest(requestId, principal.getName(), request.getEmail(), request.getNotes());
	}

	@ExceptionHandler(RequestNotFoundException.class)
	ResponseEntity<?> notFound() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adoption request not found");
	}

	@SuppressWarnings("nullness")
	static private class Request {

		@Email
		@NotBlank
		String email;

		String notes = "";

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}
	}
}
