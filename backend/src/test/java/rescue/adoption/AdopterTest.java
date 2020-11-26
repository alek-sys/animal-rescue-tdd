package rescue.adoption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rescue.animals.Animals;
import rescue.notifications.NotificationSender;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

class AdopterTest {

	private Animals mockAnimals;
	private AdoptionRequests mockAdoptionRequests;
	private NotificationSender mockNotificationSender;

	private Adopter adopter;

	@BeforeEach
	void setUp() {
		this.mockAnimals = mock(Animals.class);
		this.mockAdoptionRequests = mock(AdoptionRequests.class);
		this.mockNotificationSender = mock(NotificationSender.class);

		this.adopter = new Adopter(mockAnimals, mockAdoptionRequests, mockNotificationSender);
	}

	@Test
	void shouldChangeAnimalStatusToAdopted() {
		int animalId = 1;
		this.adopter.adopt(animalId, "test-adopter-1@example.com");
		verify(this.mockAnimals).markPendingAdoption(animalId);
	}

	@Test
	void shouldNotifyAdopterAboutSuccess() {
		int animalId = 1;
		String adopterEmail = "test-adopter-1@example.com";
		mockExistingAdopter(animalId, adopterEmail);

		this.adopter.adopt(animalId, adopterEmail);

		verify(this.mockNotificationSender).notifyOnSuccess(adopterEmail);
	}

	@Test
	void shouldNotifyOtherAdoptersAboutRejection() {
		when(this.mockAdoptionRequests.getAllFor(1))
			.thenReturn(Arrays.asList(
				new AdoptionRequest(1, "Adopter 1", "test-adopter-1@example.com", ""),
				new AdoptionRequest(1, "Adopter 1", "other-adopter-1@example.com", ""),
				new AdoptionRequest(1, "Adopter 1", "other-adopter-2@example.com", "")));

		this.adopter.adopt(1, "test-adopter-1@example.com");

		verify(this.mockNotificationSender).notifyOnRejection("other-adopter-1@example.com");
		verify(this.mockNotificationSender).notifyOnRejection("other-adopter-2@example.com");
	}

	private void mockExistingAdopter(int animalId, String adopterEmail) {
		when(this.mockAdoptionRequests.getAllFor(animalId))
			.thenReturn(Collections.singletonList(new AdoptionRequest(1, "Adopter 1", adopterEmail, "")));
	}
}