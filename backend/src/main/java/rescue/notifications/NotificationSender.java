package rescue.notifications;

public interface NotificationSender {
	void notifyOnSuccess(String adopterEmail);
	void notifyOnRejection(String adopterEmail);
}
