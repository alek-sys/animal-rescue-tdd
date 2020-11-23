package rescue.adoption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJdbcTest
class JdbcAdoptionRequestsImplTest {

	@Autowired
	AdoptionRequestsRepository adoptionRequestsRepository;

	private JdbcAdoptionRequestsImpl requests;

	@BeforeEach
	void setUp() {
		this.requests = new JdbcAdoptionRequestsImpl(this.adoptionRequestsRepository);
	}

	@Test
	void shouldSaveRequestToDatabase() {
		Integer id = this.requests.adopt(1, "test-adopter", "test@example.com", "test note");

		AdoptionRequestEntity entity = this.adoptionRequestsRepository.findById(id).get();
		assertThat(entity.getAdopterName()).isEqualTo("test-adopter");
		assertThat(entity.getEmail()).isEqualTo("test@example.com");
		assertThat(entity.getNotes()).isEqualTo("test note");
	}

	@Test
	void shouldLoadRequestsFromDatabase() {
		this.adoptionRequestsRepository.save(new AdoptionRequestEntity(1, "adopter-1", "email1@example.com", ""));
		this.adoptionRequestsRepository.save(new AdoptionRequestEntity(1, "adopter-2", "email2@example.com", ""));
		this.adoptionRequestsRepository.save(new AdoptionRequestEntity(2, "adopter-3", "email3@example.com", ""));

		Iterable<AdoptionRequest> requests = this.requests.getAllFor(1);
		assertThat(requests).hasSize(2);
		assertThat(requests).extracting(AdoptionRequest::getAdopterName)
			.contains("adopter-1", "adopter-2");

		assertThat(requests).extracting(AdoptionRequest::getEmail)
			.contains("email1@example.com", "email2@example.com");

	}

	@Test
	void shouldUpdateRequestInDatabase() throws RequestNotFoundException {
		Integer existingRequestId = this.adoptionRequestsRepository
			.save(new AdoptionRequestEntity(1, "test-adopter", "old-email@example.com", "old note")).getId();

		this.requests.editRequest(existingRequestId, "test-adopter", "new-email@example.com", "new note");

		AdoptionRequestEntity entity = this.adoptionRequestsRepository.findById(existingRequestId).get();
		assertThat(entity.getEmail()).isEqualTo("new-email@example.com");
		assertThat(entity.getNotes()).isEqualTo("new note");
	}

	@Test
	void shouldThrowExceptionWhenRequestNotFound() {
		assertThatExceptionOfType(RequestNotFoundException.class)
			.isThrownBy(() -> this.requests.editRequest(999, "test-adopter", "new-email@example.com", "new note"));
	}

	@Test
	void shouldThrowExceptionWhenEditingRequestForDifferentAdopter() {
		Integer existingRequestId = this.adoptionRequestsRepository
			.save(new AdoptionRequestEntity(1, "test-adopter", "old-email@example.com", "old note")).getId();

		assertThatExceptionOfType(RequestNotFoundException.class)
			.isThrownBy(() -> this.requests.editRequest(existingRequestId, "new-adopter", "new-email@example.com", "new note"));
	}
}