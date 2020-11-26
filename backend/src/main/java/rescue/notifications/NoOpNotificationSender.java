package rescue.notifications;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NoOpNotificationSender implements NotificationSender {

	private final Logger LOGGER = LoggerFactory.getLogger(NoOpNotificationSender.class);

	@Override
	public void notifyOnSuccess(String adopterEmail) {
		this.LOGGER.info("Received request to notify {} about successful adoption", adopterEmail);
	}

	@Override
	public void notifyOnRejection(String adopterEmail) {
		this.LOGGER.info("Received request to notify {} about rejection", adopterEmail);
	}
}
