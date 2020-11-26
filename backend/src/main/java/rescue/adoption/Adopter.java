package rescue.adoption;

import org.springframework.stereotype.Component;
import rescue.animals.Animals;
import rescue.notifications.NotificationSender;

@Component
public class Adopter {

	private final Animals animals;
	private final AdoptionRequests adoptionRequests;
	private final NotificationSender notificationSender;

	public Adopter(Animals animals, AdoptionRequests adoptionRequests, NotificationSender notificationSender) {
		this.animals = animals;
		this.adoptionRequests = adoptionRequests;
		this.notificationSender = notificationSender;
	}

	public void adopt(int animalId, String adopterEmail) {
		for (AdoptionRequest adoptionRequest : this.adoptionRequests.getAllFor(animalId)) {
			if (adopterEmail.equals(adoptionRequest.getEmail())) {
				this.notificationSender.notifyOnSuccess(adoptionRequest.getEmail());
			} else {
				this.notificationSender.notifyOnRejection(adoptionRequest.getEmail());
			}
		}

		this.animals.markPendingAdoption(animalId);
	}
}
