package rescue.animals;

public interface Animals {
	Iterable<Animal> getAll();
	void markPendingAdoption(int animalId);
}
